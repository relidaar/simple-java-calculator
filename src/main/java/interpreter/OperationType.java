package interpreter;

import parser.Token;
import parser.TokenType;

public enum OperationType {
	Addition, Subtraction;

	public static OperationType getOperation(Token token) throws Exception {
		if (token.getType() != TokenType.OPERATOR) {
			throw new Exception("Invalid token type");
		}

		return switch (token.getValue()) {
		case "+" -> OperationType.Addition;
		case "-" -> OperationType.Subtraction;
		default -> throw new Exception("Unsupported operation");
		};
	}
}