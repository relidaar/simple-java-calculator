package parser;

import tokenizer.Token;

public class UndefinedIdentifier extends Exception {
	public UndefinedIdentifier(Token token) {
		super("Undefined identifier: \"%s\" at position %d".formatted(token.getValue(), token.getPosition()));
	}
	
	public UndefinedIdentifier(String identifier) {
		super("Undefined identifier \"%s\"".formatted(identifier));
	}
}
