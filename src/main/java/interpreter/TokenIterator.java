package interpreter;

import java.util.List;

import parser.Token;

public class TokenIterator {
	private final List<Token> mTokens;
	private int mCurrentPosition = -1;
	
	public TokenIterator(List<Token> mTokens) {
		this.mTokens = mTokens;
	}
	
	public boolean hasNext() {
		return hasAt(mCurrentPosition + 1);
	}
	
	public boolean hasAt(int position) {
		return position < mTokens.size();
	}
	
	public Token next() {
		if (!hasNext()) {
			return null;
		}
		
		mCurrentPosition += 1;
		return mTokens.get(mCurrentPosition);
	}
	
	public Token peakNext() {
		if (!hasNext()) {
			return null;
		}
		return mTokens.get(mCurrentPosition + 1);
	}	

	public int getCurrentPosition() {
		return mCurrentPosition;
	}

	public Token getCurrentToken() {
		return mTokens.get(mCurrentPosition);
	}
}