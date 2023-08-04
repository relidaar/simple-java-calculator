package parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Parser {

	private static Set<Character> supportedOperators;
	private static Set<Character> blankCharacters;
	
	private Parser() {}

	static {
		supportedOperators = new HashSet<>();
		supportedOperators.add('+');
		supportedOperators.add('-');

		blankCharacters = new HashSet<>();
		blankCharacters.add(' ');
		blankCharacters.add('\t');
		blankCharacters.add('\n');	
	}

	public static List<Token> parse(String input) throws Exception {
		if (input == null || input.isBlank()) {
			return Collections.emptyList();
		}

		List<Token> tokens = new ArrayList<>();
		var scanner = new Scanner(input);
		while (scanner.hasNext()) {
			char currentSymbol = scanner.next();

			if (blankCharacters.contains(currentSymbol)) {
			} else if (Character.isDigit(currentSymbol)) {
				parseNumber(currentSymbol, scanner, tokens);
			} else if (supportedOperators.contains(currentSymbol)) {
				tokens.add(new Token(TokenType.OPERATOR, String.valueOf(currentSymbol)));
			} else {
				throw new InvalidInputException(scanner.getCurrentPosition(), currentSymbol);
			}

		}
		return tokens;
	}

	private static void parseNumber(char currentSymbol, Scanner scanner, List<Token> tokens) throws Exception {
		var buffer = new StringBuffer();
		buffer.append(currentSymbol);

		while (scanner.hasNext()) {
			currentSymbol = scanner.peakNext();
			if (currentSymbol == '.') {
				scanner.next();
				buffer.append(loadDecimalPart(currentSymbol, scanner, tokens));
				break;
			}
			if (currentSymbol == 'e') {
				scanner.next();
				buffer.append(loadExponent(currentSymbol, scanner, tokens));
				break;
			}
			if (!Character.isDigit(currentSymbol))
				break;
			scanner.next();
			buffer.append(currentSymbol);
		}

		tokens.add(new Token(TokenType.NUMBER, buffer.toString()));
	}
	
	private static StringBuffer loadDecimalPart(char currentSymbol, Scanner scanner, List<Token> tokens) throws Exception {
		StringBuffer result = new StringBuffer();
		result.append(currentSymbol);
		while (scanner.hasNext()) {
			currentSymbol = scanner.peakNext();
			if (currentSymbol == 'e') {
				scanner.next();
				result.append(loadExponent(currentSymbol, scanner, tokens));
				break;
			}
			if (!Character.isDigit(currentSymbol))
				break;
			scanner.next();
			result.append(currentSymbol);
		}
		
		return result;
	}
	
	private static StringBuffer loadExponent(char currentSymbol, Scanner scanner, List<Token> tokens) throws Exception {
		StringBuffer result = new StringBuffer();
		result.append(currentSymbol);
		if (!Character.isDigit(scanner.peakNext()))
			throw new Exception("Exponent must be definied with a number");
		while (scanner.hasNext()) {
			currentSymbol = scanner.peakNext();
			if (!Character.isDigit(currentSymbol))
				break;
			scanner.next();
			result.append(currentSymbol);
		}
		
		return result;
	}
	
}
