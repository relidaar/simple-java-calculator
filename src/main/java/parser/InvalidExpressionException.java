package parser;

import tokenizer.Token;

public class InvalidExpressionException extends Exception {
	public InvalidExpressionException(Token token) {
		super("Invalid expression: \"%s\" at position %d".formatted(token.getValue(), token.getPosition()));
	}
}
