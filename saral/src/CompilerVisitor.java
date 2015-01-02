// Generated from Saral.g4 by ANTLR 4.4
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

public class CompilerVisitor extends SaralBaseVisitor<CodeFragment>  {
	@Override public CodeFragment visitArglist(@NotNull SaralParser.ArglistContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitRet(@NotNull SaralParser.RetContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitValBool(@NotNull SaralParser.ValBoolContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitVarID(@NotNull SaralParser.VarIDContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitProc_call(@NotNull SaralParser.Proc_callContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitValFloat(@NotNull SaralParser.ValFloatContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitVar_definition(@NotNull SaralParser.Var_definitionContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitIf_statement(@NotNull SaralParser.If_statementContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitBlock(@NotNull SaralParser.BlockContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitWhile_statement(@NotNull SaralParser.While_statementContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitConst_declaration(@NotNull SaralParser.Const_declarationContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitCompare(@NotNull SaralParser.CompareContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitConst_definition(@NotNull SaralParser.Const_definitionContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitUnaryMinus(@NotNull SaralParser.UnaryMinusContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitValChar(@NotNull SaralParser.ValCharContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitBoolNot(@NotNull SaralParser.BoolNotContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitMul(@NotNull SaralParser.MulContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitFunc(@NotNull SaralParser.FuncContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitAdd(@NotNull SaralParser.AddContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitTypeBasic(@NotNull SaralParser.TypeBasicContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitFor_statement(@NotNull SaralParser.For_statementContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitExtern_proc_declaration(@NotNull SaralParser.Extern_proc_declarationContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitVar_declaration(@NotNull SaralParser.Var_declarationContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitBoolOr(@NotNull SaralParser.BoolOrContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitFunc_block(@NotNull SaralParser.Func_blockContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitValue(@NotNull SaralParser.ValueContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitExtern_func_declaration(@NotNull SaralParser.Extern_func_declarationContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitVarArray(@NotNull SaralParser.VarArrayContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitProc_definition(@NotNull SaralParser.Proc_definitionContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitValVar(@NotNull SaralParser.ValVarContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitValInt(@NotNull SaralParser.ValIntContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitInit(@NotNull SaralParser.InitContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitBoolAnd(@NotNull SaralParser.BoolAndContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitArray_declaration(@NotNull SaralParser.Array_declarationContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitParen(@NotNull SaralParser.ParenContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitParam_list(@NotNull SaralParser.Param_listContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitStatement(@NotNull SaralParser.StatementContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitAssignment(@NotNull SaralParser.AssignmentContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitFunc_definition(@NotNull SaralParser.Func_definitionContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitFunc_call(@NotNull SaralParser.Func_callContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitValString(@NotNull SaralParser.ValStringContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitBlock_statement(@NotNull SaralParser.Block_statementContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitTypeArray(@NotNull SaralParser.TypeArrayContext ctx) { return visitChildren(ctx); }
	@Override public CodeFragment visitSimple_statement(@NotNull SaralParser.Simple_statementContext ctx) { return visitChildren(ctx); }
}
