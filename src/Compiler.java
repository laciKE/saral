import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.InputStream;

public class Compiler {
	public static void main(String[] args) throws Exception {
		String inputFile = null;
		if (args.length > 0)
			inputFile = args[0];
		InputStream is = System.in;
		if (inputFile != null)
			is = new FileInputStream(inputFile);
		ANTLRInputStream input = new ANTLRInputStream(is);
		SaralLexer lexer = new SaralLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		SaralParser parser = new SaralParser(tokens);
		ParseTree tree = parser.init(); // parse
		try {
			CompilerVisitor eval = new CompilerVisitor();
			CodeFragment code = eval.visit(tree);
			System.out.print(code.toString());
		} catch (IllegalStateException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		} catch (IllegalAccessError e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
	}
}
