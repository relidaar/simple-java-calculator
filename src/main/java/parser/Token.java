package parser;

public class Token {
	private TokenType mType;
	private String mValue;

	public Token(TokenType type, String value) {
		mType = type;
		mValue = value;
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
}
