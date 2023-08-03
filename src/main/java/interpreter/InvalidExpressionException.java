package interpreter;

import parser.Token;

public class InvalidExpressionException extends Exception {
	public InvalidExpressionException(int position, Token currentToken, Token nextToken) {
		super("Invalid expression at position %d: %s %s".formatted(position, currentToken.getValue(), nextToken.getValue()));
	}
	
	public InvalidExpressionException(int position, Token token) {
		super("Invalid expression at position %d: %s".formatted(position, token.getValue()));
	}
}
