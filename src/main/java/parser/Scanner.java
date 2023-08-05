package parser;

class Scanner {
	private final String mInput;
	private int mCurrentPosition = -1;

	public Scanner(String input) {
		if (input == null) {
			throw new NullPointerException();
		}

		mInput = input;
	}

	public boolean hasNext() {
		return mCurrentPosition + 1 < mInput.length();
	}

	public char next() {
		if (!hasNext()) {
			throw new IndexOutOfBoundsException();
		}

		mCurrentPosition += 1;
		return mInput.charAt(mCurrentPosition);
	}

	public char peekNext() {
		if (!hasNext()) {
			throw new IndexOutOfBoundsException();
		}
		return mInput.charAt(mCurrentPosition + 1);
	}

	public int getCurrentPosition() {
		return mCurrentPosition;
	}

	public char getCurrentElement() {
		return mInput.charAt(mCurrentPosition);
	}
}
