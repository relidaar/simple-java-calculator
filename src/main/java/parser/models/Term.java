package parser.models;

import parser.Context;
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
	public Double evaluate(Context context) throws Exception {
		var leftResult = mLeft.evaluate(context);
		var rightResult = mRight.evaluate(context);
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
