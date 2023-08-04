import interpreter.Interpreter;
import parser.Parser;

public class Main {

	public static void main(String[] args) {
		try {
			String input = "";
			var tokens = Parser.parse(input);
			var expresion = Interpreter.buildAST(tokens);
			var result = expresion.evaluate();
			System.out.format("%s = %s", input, result);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
