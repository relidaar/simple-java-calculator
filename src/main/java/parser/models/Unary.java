package parser.models;

import tokenizer.Token;
import tokenizer.TokenType;

public class Unary implements Expression {
	private Expression mRight;
	private UnaryType mType;
		
	public Unary(Expression right, UnaryType type) {
		mRight = right;
		mType = type;
	}

	@Override
	public Double evaluate() {
		var rightResult = mRight.evaluate();
		return switch (mType) {
		case NEGATIVE -> -rightResult;
		case POSITIVE -> rightResult;
		};
	}

	public enum UnaryType {
		POSITIVE, NEGATIVE;

		public static UnaryType valueOf(Token token) {
			TokenType type = token.getType();
			return switch (type) {
			case MINUS -> NEGATIVE;
			case PLUS -> POSITIVE;
			default -> null;
			};
		}
	}
}