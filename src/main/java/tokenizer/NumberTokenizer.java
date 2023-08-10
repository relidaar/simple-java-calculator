package tokenizer;

public final class NumberTokenizer {
	private NumberTokenizer() {
	}

	public static Token parse(Scanner scanner) throws Exception {
		var buffer = new StringBuffer();
		buffer.append(scanner.getCurrentElement());

		while (scanner.hasNext()) {
			char currentSymbol = scanner.peekNext();
			if (currentSymbol == '.') {
				scanner.next();
				buffer.append(loadDecimalPart(scanner));
				break;
			}
			
			if (isExponentSymbol(currentSymbol)) {
				scanner.next();
				buffer.append(loadExponent(scanner));
				break;
			}
			
			if (!Character.isDigit(currentSymbol))
				break;
			
			scanner.next();
			buffer.append(currentSymbol);
		}

		return new Token(TokenType.NUMBER, buffer.toString(), scanner.getCurrentPosition());
	}

	private static StringBuffer loadDecimalPart(Scanner scanner)
			throws Exception {
		StringBuffer result = new StringBuffer();
		result.append(scanner.getCurrentElement());
		
		while (scanner.hasNext()) {
			char currentSymbol = scanner.peekNext();
			if (isExponentSymbol(currentSymbol)) {
				scanner.next();
				result.append(loadExponent(scanner));
				break;
			}
			
			if (!Character.isDigit(currentSymbol))
				break;
			
			scanner.next();
			result.append(currentSymbol);
		}

		return result;
	}
	
	private static StringBuffer loadExponent(Scanner scanner) throws Exception {
		StringBuffer result = new StringBuffer();
		result.append(scanner.getCurrentElement());
		if (!scanner.hasNext()) {
			throw new Exception("Exponent must be definied");
		}
		char next = scanner.peekNext();
		if (!Character.isDigit(next) && next != '-' && next != '+')
			throw new Exception("Exponent must be definied with a number");
		result.append(scanner.next());
		while (scanner.hasNext()) {
			char currentSymbol = scanner.peekNext();
			if (!Character.isDigit(currentSymbol))
				break;
			
			scanner.next();
			result.append(currentSymbol);
		}
		
		return result;
	}
	
	private static boolean isExponentSymbol(char symbol) {
		return Character.toLowerCase(symbol) == 'e';
	}
}
