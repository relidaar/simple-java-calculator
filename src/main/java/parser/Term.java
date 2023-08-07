package parser;

import tokenizer.Token;

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

		public static TermType valueOf(Token token) {
			return token != null ? switch (token.getType()) {
			case PLUS -> TermType.ADDITION;
			case MINUS -> TermType.SUBTRACTION;
			default -> null;
			} : null;
		}

		public static boolean contains(Token token) {
			return valueOf(token) != null;
		}
	}
}
