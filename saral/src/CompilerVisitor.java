import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.stringtemplate.v4.*;

public class CompilerVisitor extends SaralBaseVisitor<CodeFragment> {
	private SymbolTable symbolTable = new SymbolTable();
	private int labelIndex = 0;
	private int registerIndex = 0;
	private int functionIndex = 0;
	CodeFragment function_declarations = new CodeFragment();

	public CompilerVisitor() {
		super();
		symbolTable.addTable();
	}

	private String generateNewLabel() {
		return String.format("L%d", this.labelIndex++);
	}

	private String generateNewRegister() {
		return String.format("%%R%d", this.registerIndex++);
	}

	private String generateNewFunction() {
		return String.format("@F%d", this.functionIndex++);
	}

	@Override
	public CodeFragment visitInit(@NotNull SaralParser.InitContext ctx) {
		CodeFragment body = visit(ctx.statements());

		ST template = new ST("declare i32 @printInt(i32*)\n"
				+ "declare i32 @printChar(i8*)\n"
				+ "declare i32 @printBool(i2*)\n"
				+ "declare i32 @printFloat(float*)\n"
				+ "declare i32 @printString(i8*)\n"
				+ "declare i32 @scanInt(i32*)\n"
				+ "declare i32 @scanChar(i8*)\n"
				+ "declare i32 @scanBool(i2*)\n"
				+ "declare i32 @scanFloat(float*)\n"
				+ "declare i32 @scanString(i8*)\n" + "<function_declarations>"
				+ "define i32 @main() {\n" + "start:\n" + "<body_code>"
				+ "ret i32 0\n" + "}\n");

		template.add("function_declarations", function_declarations);
		template.add("body_code", body);

		CodeFragment code = new CodeFragment();
		code.addCode(template.render());
		code.setRegister(body.getRegister());
		return code;
	}

	@Override
	public CodeFragment visitStatements(
			@NotNull SaralParser.StatementsContext ctx) {
		CodeFragment code = new CodeFragment();
		for (SaralParser.StatementContext s : ctx.statement()) {
			CodeFragment statement = visit(s);
			code.addCode(statement);
			code.setRegister(statement.getRegister());
		}
		return code;
	}

	@Override
	public CodeFragment visitStatement(@NotNull SaralParser.StatementContext ctx) {
		SaralParser.Simple_statementContext ss = ctx.simple_statement();
		if (ss != null) {
			return visit(ss);
		}

		SaralParser.Block_statementContext bs = ctx.block_statement();
		if (bs != null) {
			return visit(bs);
		}

		return new CodeFragment();
	}

	@Override
	public CodeFragment visitVar_declaration(
			@NotNull SaralParser.Var_declarationContext ctx) {
		Type type = visit(ctx.type()).getType();
		String id = ctx.ID().getText();
		CodeFragment code = variableDeclaration(id, type, false);

		return code;
	}

	@Override
	public CodeFragment visitVar_definition(
			@NotNull SaralParser.Var_definitionContext ctx) {
		Type type = visit(ctx.type()).getType();
		String id = ctx.ID().getText();
		SaralParser.ExpressionContext exp = ctx.expression();
		CodeFragment code = variableDefinition(id, type, false, exp);

		return code;
	}

	@Override
	public CodeFragment visitConst_definition(
			@NotNull SaralParser.Const_definitionContext ctx) {
		Type type = visit(ctx.type()).getType();
		String id = ctx.ID().getText();
		SaralParser.ExpressionContext exp = ctx.expression();
		CodeFragment code = variableDefinition(id, type, true, exp);

		return code;
	}

	private CodeFragment variableDefinition(String identifier, Type type,
			boolean constant, SaralParser.ExpressionContext valueExp) {
		CodeFragment code = variableDeclaration(identifier, type, false);
		Variable var = symbolTable.getVariable(identifier);
		CodeFragment value = visit(valueExp);
		CodeFragment assign = generateAssign(var.getName(), var.getType(),
				var.getRegister(), value);
		code.appendCode(assign);

		return code;
	}

	private CodeFragment variableDeclaration(String identifier, Type type,
			boolean constant) {
		CodeFragment code = new CodeFragment();

		if (!symbolTable.containsVariable(identifier)) {
			String mem_register = this.generateNewRegister();
			symbolTable.addVariable(new Variable(identifier, type,
					mem_register, constant));
			ST template = new ST(
					"<mem_register> = alloca <type> ; <comment> declaration\n");
			template.add("mem_register", mem_register);
			template.add("type", type.getCode());
			template.add("comment", identifier);
			code.addCode(template.render());
			code.setRegister(mem_register);
			code.setType(type);
		} else {
			String varType = "variable";
			if (symbolTable.getVariable(identifier).isConstant()) {
				varType = "constant";
			}
			throw new IllegalStateException(String.format(
					"Error: %s '%s' already declared.", varType, identifier));
		}

		return code;
	}

	@Override
	public CodeFragment visitArray_declaration(
			@NotNull SaralParser.Array_declarationContext ctx) {
		return visitChildren(ctx);
	}

	private CodeFragment generateAssign(String varName, Type varType,
			String varRegister, CodeFragment value) {
		CodeFragment code = new CodeFragment();
		;
		if (varType != value.getType()) {
			throw new IllegalStateException(
					String.format(
							"Error: incompatible types in assignment to '%s': '%s' and '%s'.",
							varName, varType.getName(), value.getType()
									.getName()));
		}

		ST template = new ST(
				"<value_code>"
						+ "store <type> <value_register>, <type>* <mem_register> ; <comment> assign\n");

		template.add("value_code", value);
		template.add("type", value.getType().getCode());
		template.add("value_register", value.getRegister());
		template.add("mem_register", varRegister);
		template.add("comment", varName);

		code.addCode(template.render());
		code.setRegister(value.getRegister());
		code.setType(value.getType());

		return code;
	}

	@Override
	public CodeFragment visitAssignment(
			@NotNull SaralParser.AssignmentContext ctx) {
		CodeFragment code = new CodeFragment();
		CodeFragment lvalue = visit(ctx.var());
		code.addCode(lvalue);
		CodeFragment rvalue = visit(ctx.expression());
		CodeFragment assign = generateAssign(lvalue.getComment(),
				lvalue.getType(), lvalue.getRegister(), rvalue);
		code.appendCode(assign);

		return code;
	}

	@Override
	public CodeFragment visitTypeBasic(@NotNull SaralParser.TypeBasicContext ctx) {
		CodeFragment code = new CodeFragment();
		String t = ctx.getText();
		if (t.equals("neskutočné numeralio")) {
			code.setType(Type.INT);
		} else if (t.equals("skutočné numeralio")) {
			code.setType(Type.FLOAT);
		} else if (t.equals("písmeno")) {
			code.setType(Type.CHAR);
		} else if (t.equals("logický")) {
			code.setType(Type.BOOL);
		}

		return code;
	}

	@Override
	public CodeFragment visitTypeArray(@NotNull SaralParser.TypeArrayContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public CodeFragment visitValue(@NotNull SaralParser.ValueContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public CodeFragment visitValVar(@NotNull SaralParser.ValVarContext ctx) {
		CodeFragment code = visitChildren(ctx);
		String register = generateNewRegister();

		code.addCode(String.format("%s = load %s* %s ; %s value\n", register,
				code.getType().getCode(), code.getRegister(), code.getComment()));
		code.setRegister(register);
		return code;
	}

	@Override
	public CodeFragment visitVarID(@NotNull SaralParser.VarIDContext ctx) {
		CodeFragment code = new CodeFragment();
		String id = ctx.ID().getText();
		Variable var = null;
		if (!symbolTable.containsVariable(id)) {
			throw new IllegalStateException(String.format(
					"Error: identifier '%s' does not exists", id));
		} else {
			var = symbolTable.getVariable(id);
		}
		code.setType(var.getType());
		code.setRegister(var.getRegister());
		code.addComment(var.getName());

		return code;
	}

	@Override
	public CodeFragment visitValInt(@NotNull SaralParser.ValIntContext ctx) {
		CodeFragment code = new CodeFragment();
		String value = ctx.INT().getText();
		Type type = Type.INT;
		String register = generateNewRegister();
		code.setRegister(register);
		code.setType(type);
		code.addCode(String.format("%s = add %s 0, %s\n", register,
				type.getCode(), value));

		return code;
	}

	@Override
	public CodeFragment visitValFloat(@NotNull SaralParser.ValFloatContext ctx) {
		CodeFragment code = new CodeFragment();
		String value = ctx.FLOAT().getText();
		Type type = Type.FLOAT;
		String register = generateNewRegister();
		code.setRegister(register);
		code.setType(type);
		code.addCode(String.format("%s = fadd %s 0.0, %s\n", register,
				type.getCode(), value));

		return code;
	}

	@Override
	public CodeFragment visitValBool(@NotNull SaralParser.ValBoolContext ctx) {
		CodeFragment code = new CodeFragment();
		String valueName = ctx.BOOL().getText();
		int value = Type.boolToValue(valueName);
		Type type = Type.BOOL;
		String register = generateNewRegister();
		code.setRegister(register);
		code.setType(type);
		code.addCode(String.format("%s = add %s 0, %d ; %s\n", register,
				type.getCode(), value, valueName));

		return code;
	}

	@Override
	public CodeFragment visitValChar(@NotNull SaralParser.ValCharContext ctx) {
		CodeFragment code = new CodeFragment();
		String valueName = ctx.CHAR().getText();
		int value = Type.charToValue(valueName);
		Type type = Type.CHAR;
		String register = generateNewRegister();
		code.setRegister(register);
		code.setType(type);
		code.addCode(String.format("%s = add %s 0, %d ; %s\n", register,
				type.getCode(), value, valueName));

		return code;

	}

	@Override
	public CodeFragment visitValString(@NotNull SaralParser.ValStringContext ctx) {
		return visitChildren(ctx);
	}

	// TODO
	@Override
	public CodeFragment visitVarArray(@NotNull SaralParser.VarArrayContext ctx) {
		return visit(ctx.var());
	}

	@Override
	public CodeFragment visitWrite(@NotNull SaralParser.WriteContext ctx) {
		CodeFragment code = new CodeFragment();
		CodeFragment varCode = visit(ctx.var());

		String func = "";
		Type type = varCode.getType();
		switch (type) {
		case INT:
			func = "printInt";
			break;
		case BOOL:
			func = "printBool";
			break;
		case CHAR:
			func = "printChar";
			break;
		case FLOAT:
			func = "printFloat";
			break;
		default:
			break;
		// TODO STRING
		}

		ST template = new ST("<value_code>"
				+ "call i32 @<func> (<type>* <value>)\n");
		template.add("value_code", varCode);
		template.add("func", func);
		template.add("type", type.getCode());
		template.add("value", varCode.getRegister());
		code.addCode(template.render());

		return code;
	}

	@Override
	public CodeFragment visitWrite2(@NotNull SaralParser.Write2Context ctx) {
		CodeFragment code = new CodeFragment();
		System.err
				.println("Warning: command 'povidz' is not supported in this version of Šaral");
		return code;
	}

	@Override
	public CodeFragment visitRead(@NotNull SaralParser.ReadContext ctx) {
		CodeFragment code = new CodeFragment();
		CodeFragment varCode = visit(ctx.var());

		String func = "";
		Type type = varCode.getType();
		switch (type) {
		case INT:
			func = "scanInt";
			break;
		case BOOL:
			func = "scanBool";
			break;
		case CHAR:
			func = "scanChar";
			break;
		case FLOAT:
			func = "scanFloat";
			break;
		default:
			break;
		// TODO STRING
		}

		ST template = new ST("<value_code>"
				+ "call i32 @<func> (<type>* <value>)\n");
		template.add("value_code", varCode);
		template.add("func", func);
		template.add("type", type.getCode());
		template.add("value", varCode.getRegister());
		code.addCode(template.render());

		return code;

	}

	@Override
	public CodeFragment visitRead2(@NotNull SaralParser.Read2Context ctx) {
		CodeFragment code = new CodeFragment();
		System.err
				.println("Warning: command 'sluchaj' is not supported in this version of Šaral");
		return code;
	}

	@Override
	public CodeFragment visitUnaryMinus(
			@NotNull SaralParser.UnaryMinusContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public CodeFragment visitBoolNot(@NotNull SaralParser.BoolNotContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public CodeFragment visitBoolOr(@NotNull SaralParser.BoolOrContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public CodeFragment visitBoolAnd(@NotNull SaralParser.BoolAndContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public CodeFragment visitAdd(@NotNull SaralParser.AddContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public CodeFragment visitMul(@NotNull SaralParser.MulContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public CodeFragment visitCompare(@NotNull SaralParser.CompareContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public CodeFragment visitIf_statement(
			@NotNull SaralParser.If_statementContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public CodeFragment visitWhile_statement(
			@NotNull SaralParser.While_statementContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public CodeFragment visitFor_statement(
			@NotNull SaralParser.For_statementContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public CodeFragment visitBlock(@NotNull SaralParser.BlockContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public CodeFragment visitArglist(@NotNull SaralParser.ArglistContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public CodeFragment visitParam_list(
			@NotNull SaralParser.Param_listContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public CodeFragment visitRet(@NotNull SaralParser.RetContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public CodeFragment visitProc_call(@NotNull SaralParser.Proc_callContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public CodeFragment visitFunc(@NotNull SaralParser.FuncContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public CodeFragment visitExtern_proc_declaration(
			@NotNull SaralParser.Extern_proc_declarationContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public CodeFragment visitFunc_block(
			@NotNull SaralParser.Func_blockContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public CodeFragment visitExtern_func_declaration(
			@NotNull SaralParser.Extern_func_declarationContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public CodeFragment visitProc_definition(
			@NotNull SaralParser.Proc_definitionContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public CodeFragment visitParen(@NotNull SaralParser.ParenContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public CodeFragment visitFunc_definition(
			@NotNull SaralParser.Func_definitionContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public CodeFragment visitFunc_call(@NotNull SaralParser.Func_callContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public CodeFragment visitBlock_statement(
			@NotNull SaralParser.Block_statementContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public CodeFragment visitSimple_statement(
			@NotNull SaralParser.Simple_statementContext ctx) {
		return visitChildren(ctx);
	}
}
