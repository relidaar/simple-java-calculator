package parser;

public final class OperatorParser {
	private OperatorParser() {
	}

	public static Token parse(Scanner scanner) {
		return new Token(TokenType.OPERATOR, String.valueOf(scanner.getCurrentElement()));
	}
}
