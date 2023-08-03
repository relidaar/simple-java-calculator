package interpreter;

import java.util.List;

import parser.Token;

class TokenIterator {
	private List<Token> mTokens;
	private int mCurrentPosition = -1;
	
	public TokenIterator(List<Token> tokens) {
		if (tokens == null) {
			throw new NullPointerException();
		}
		
		mTokens = tokens;
	}

	public boolean hasNext() {
		return mCurrentPosition + 1 < mTokens.size();
	}
	
	public Token next() {
		if (!hasNext()) {
			throw new IndexOutOfBoundsException();
		}
		
		mCurrentPosition += 1;
		return mTokens.get(mCurrentPosition);
	}
	
	public Token peakNext() {
		if (!hasNext()) {
			throw new IndexOutOfBoundsException();
		}
		return mTokens.get(mCurrentPosition + 1);
	}

	public int getCurrentPosition() {
		return mCurrentPosition;
	}
}
