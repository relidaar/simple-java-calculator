package interpreter;

import parser.Token;
import parser.TokenType;

public enum OperationType {
	Addition, Subtraction, Multiplication, Division;
	
	public static OperationType valueOf(Token token) throws Exception {
		if (token.getType() != TokenType.OPERATOR) {
			throw new Exception("Invalid token type");
		}

		return switch (token.getValue()) {
		case "+" -> OperationType.Addition;
		case "-" -> OperationType.Subtraction;
		case "*" -> OperationType.Multiplication;
		case "/" -> OperationType.Division;
		default -> throw new Exception("Unsupported operation");
		};
	}
}