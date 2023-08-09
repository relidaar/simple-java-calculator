package parser.models;

import java.util.List;

import parser.Context;
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
	public Double evaluate(Context context) throws Exception {
		return switch (mType) {
		case LOG -> Math.log10(mArguments.get(0).evaluate(context));
		case LN -> Math.log(mArguments.get(0).evaluate(context));
		case EXP -> Math.exp(mArguments.get(0).evaluate(context));
		case SQRT -> Math.sqrt(mArguments.get(0).evaluate(context));
		};
	}

	public enum FunctionType {
		LOG, LN, EXP, SQRT;

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
			return toFunctionType(token.getValue());
		}
		
		public static FunctionType toFunctionType(String value) {
			return switch(value) {
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
