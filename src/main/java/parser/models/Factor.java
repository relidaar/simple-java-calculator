package parser.models;

import parser.Context;
import tokenizer.Token;

public class Factor implements Expression {
	private Expression mLeft;
	private Expression mRight;
	private FactorType mType;

	public Factor(Expression left, Expression right, FactorType type) {
		mLeft = left;
		mRight = right;
		mType = type;
	}

	@Override
	public Double evaluate(Context context) throws Exception {
		var leftResult = mLeft.evaluate(context);
		var rightResult = mRight.evaluate(context);
		return switch (mType) {
		case DIVISION -> leftResult / rightResult;
		case MULTIPLICATION -> leftResult * rightResult;
		case MODULO -> leftResult % rightResult;
		case POWER -> Math.pow(leftResult, rightResult);
		};
	}

	public enum FactorType {
		MULTIPLICATION, DIVISION, MODULO, POWER;

		public static FactorType valueOf(Token token) {
			return token != null ? switch (token.getType()) {
			case STAR -> FactorType.MULTIPLICATION;
			case SLASH -> FactorType.DIVISION;
			case PERCENT -> FactorType.MODULO;
			case CARET -> FactorType.POWER;
			default -> null;
			} : null;
		}

		public static boolean contains(Token token) {
			return valueOf(token) != null;
		}
	}
}