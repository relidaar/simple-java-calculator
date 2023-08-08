import parser.Context;
import parser.Parser;
import tokenizer.Tokenizer;

public class Main {

	public static void main(String[] args) {
		try {
			var context = new Context();
			
			String input = "";
			var tokens = Tokenizer.parse(input);
			var expression = Parser.buildExpression(tokens);
			
			if (expression != null) {
				var result = expression.evaluate(context);
				System.out.format("Context: %s\n", context);
				System.out.format("%s = %s", input, result);
			}
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}