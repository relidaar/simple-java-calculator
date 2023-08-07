package parser;

import java.util.List;

import tokenizer.Token;

public class TokenIterator {
	private final List<Token> mTokens;
	private int mCurrentPosition = 0;

	public TokenIterator(List<Token> mTokens) {
		this.mTokens = mTokens;
	}

	public boolean hasNext() {
		return mCurrentPosition + 1 < mTokens.size();
	}

	public Token next() {
		mCurrentPosition += 1;
		return current();
	}

	public Token current() {
		return positionIsValid(mCurrentPosition) ? mTokens.get(mCurrentPosition) : null;
	}
	
	public Token previous() {
		return positionIsValid(mCurrentPosition - 1) ? mTokens.get(mCurrentPosition - 1) : null;
	}

	private boolean positionIsValid(int pos) {
		return pos >= 0 && pos < mTokens.size();
	}
}