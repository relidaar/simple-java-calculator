package interpreter;

import java.util.List;

import parser.Token;

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
		if (hasNext()) {
			mCurrentPosition += 1;
		}

		return mTokens.get(mCurrentPosition);
	}

	public Token current() {
		return positionIsValid(mCurrentPosition) ? mTokens.get(mCurrentPosition) : null;
	}

	private boolean positionIsValid(int pos) {
		return pos >= 0 && pos < mTokens.size();
	}
}