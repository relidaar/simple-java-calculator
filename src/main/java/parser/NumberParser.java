package parser;

public final class NumberParser {
	private NumberParser() {
	}

	public static Token parse(Scanner scanner) throws Exception {
		var buffer = new StringBuffer();
		buffer.append(scanner.getCurrentElement());

		while (scanner.hasNext()) {
			char currentSymbol = scanner.peakNext();
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

		return new Token(TokenType.NUMBER, buffer.toString());
	}

	private static StringBuffer loadDecimalPart(Scanner scanner)
			throws Exception {
		StringBuffer result = new StringBuffer();
		result.append(scanner.getCurrentElement());
		
		while (scanner.hasNext()) {
			char currentSymbol = scanner.peakNext();
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
		
		if (!Character.isDigit(scanner.peakNext()))
			throw new Exception("Exponent must be definied with a number");
		
		while (scanner.hasNext()) {
			char currentSymbol = scanner.peakNext();
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
