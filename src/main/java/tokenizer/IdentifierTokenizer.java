package tokenizer;

public final class IdentifierTokenizer {
	private IdentifierTokenizer() {}

	public static Token parse(Scanner scanner) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(scanner.getCurrentElement());

		while (scanner.hasNext()) {
			char currentSymbol = scanner.peekNext();
			if (!Character.isLetterOrDigit(currentSymbol) && currentSymbol != '_') {
				break;
			}
			scanner.next();
			buffer.append(currentSymbol);
		}

		return new Token(TokenType.IDENTIFIER, buffer.toString(), scanner.getCurrentPosition());
	}

}
