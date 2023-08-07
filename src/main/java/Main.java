import parser.Parser;
import tokenizer.Tokenizer;

public class Main {

	public static void main(String[] args) {
		try {
			String input = "";
			var tokens = Tokenizer.parse(input);
			var expression = Parser.buildExpression(tokens);
			
			if (expression != null) {
				var result = expression.evaluate();
				System.out.format("%s = %s", input, result);
			}
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}