package parser.models;

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
	public Double evaluate() {
		var leftResult = mLeft.evaluate();
		var rightResult = mRight.evaluate();
		return switch (mType) {
		case DIVISION -> leftResult / rightResult;
		case MULTIPLICATION -> leftResult * rightResult;
		case MODULO -> leftResult % rightResult;
		};
	}

	public enum FactorType {
		MULTIPLICATION, DIVISION, MODULO;

		public static FactorType valueOf(Token token) {
			return token != null ? switch (token.getType()) {
			case STAR -> FactorType.MULTIPLICATION;
			case SLASH -> FactorType.DIVISION;
			case PERCENT -> FactorType.MODULO;
			default -> null;
			} : null;
		}

		public static boolean contains(Token token) {
			return valueOf(token) != null;
		}
	}
}