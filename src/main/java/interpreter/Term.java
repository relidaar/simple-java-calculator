package interpreter;

import parser.Token;
import parser.TokenType;

public class Term implements Expression {
	private Expression mLeft;
	private Expression mRight;
	private TermType mType;

	public Term(Expression left, Expression right, TermType type) {
		mLeft = left;
		mRight = right;
		mType = type;
	}

	@Override
	public Double evaluate() {
		var leftResult = mLeft.evaluate();
		var rightResult = mRight.evaluate();
		return switch (mType) {
		case ADDITION -> leftResult + rightResult;
		case SUBTRACTION -> leftResult - rightResult;
		};
	}

	public enum TermType {
		ADDITION, SUBTRACTION;

		public static TermType valueOf(Token token) throws Exception {
			if (token.getType() != TokenType.BINARY_OPERATOR) {
				throw new Exception("Invalid token type");
			}

			return switch (token.getValue()) {
			case "+" -> TermType.ADDITION;
			case "-" -> TermType.SUBTRACTION;
			default -> null;
			};
		}

		public static boolean contains(Token token) throws Exception {
			return token.getType() == TokenType.BINARY_OPERATOR && valueOf(token) != null;
		}
	}
}
