package interpreter;

import parser.Token;
import parser.TokenType;

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

		public static FactorType valueOf(Token token) throws Exception {
			if (token.getType() != TokenType.BINARY_OPERATOR) {
				throw new Exception("Invalid token type");
			}

			return switch (token.getValue()) {
			case "*" -> FactorType.MULTIPLICATION;
			case "/" -> FactorType.DIVISION;
			case "%" -> FactorType.MODULO;
			default -> null;
			};
		}

		public static boolean contains(Token token) throws Exception {
			return token.getType() == TokenType.BINARY_OPERATOR && valueOf(token) != null;
		}
	}
}