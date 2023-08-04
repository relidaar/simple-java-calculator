import interpreter.Interpreter;
import parser.Parser;

public class Main {

	public static void main(String[] args) {
		try {
			var tokens = Parser.parse("4.5 + 3.2");
			var expresion = Interpreter.parse(tokens);
			var result = expresion.evaluate();
			System.out.println(result);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
