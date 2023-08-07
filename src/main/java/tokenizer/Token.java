package tokenizer;

public class Token {
	private TokenType mType;
	private String mValue;
	private int mInputPosition;
	
	public Token(TokenType type, String value, int inputPosition) {
		mType = type;
		mValue = value;
		mInputPosition = inputPosition;
	}

	@Override
	public String toString() {
		return "Token(%s, %s)".formatted(mType, mValue);
	}

	public TokenType getType() {
		return mType;
	}

	public String getValue() {
		return mValue;
	}
	
	public int getPosition() {
		return mInputPosition;
	}
}
