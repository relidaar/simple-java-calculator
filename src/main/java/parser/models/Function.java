package parser.models;

import java.util.List;

import tokenizer.Token;
import tokenizer.TokenType;

public class Function implements Expression {
	private FunctionType mType;
	private List<Expression> mArguments;

	public Function(FunctionType type, List<Expression> arguments) {
		super();
		this.mType = type;
		this.mArguments = arguments;
	}

	@Override
	public Double evaluate() {
		return switch (mType) {
		case POW -> Math.pow(mArguments.get(0).evaluate(), mArguments.get(1).evaluate());
		case LOG -> Math.log10(mArguments.get(0).evaluate());
		case LN -> Math.log(mArguments.get(0).evaluate());
		case EXP -> Math.exp(mArguments.get(0).evaluate());
		case SQRT -> Math.sqrt(mArguments.get(0).evaluate());
		};
	}

	public enum FunctionType {
		POW(2), LOG, LN, EXP, SQRT;

		private int mParametersCount;

		private FunctionType(int parametersCount) {
			mParametersCount = parametersCount;
		}

		private FunctionType() {
			this(1);
		}

		public int getParametersCount() {
			return mParametersCount;
		}
		
		public static FunctionType valueOf(Token token) {
			if (token == null || token.getType() != TokenType.IDENTIFIER) return null;
			return switch(token.getValue()) {
			case "pow" -> POW;
			case "log" -> LOG;
			case "ln" -> LN;
			case "exp" -> EXP;
			case "sqrt" -> SQRT;
			default -> null;
			};
		}
		
		public static boolean contains(Token token) {
			return valueOf(token) != null;
		}
	}
}
