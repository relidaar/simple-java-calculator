package parser.models;

import parser.Context;
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
	public Double evaluate(Context context) throws Exception {
		var rightResult = mRight.evaluate(context);
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