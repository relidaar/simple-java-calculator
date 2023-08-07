package tokenizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Tokenizer {
	private static Set<Character> blankCharacters;
	
	private Tokenizer() {}

	static {
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
				tokens.add(NumberTokenizer.parse(scanner));
			} else if (TokenType.contains(currentSymbol)) {
				tokens.add(new Token(TokenType.valueOf(currentSymbol), String.valueOf(currentSymbol), scanner.getCurrentPosition()));
			} else {
				throw new InvalidInputException(scanner.getCurrentPosition(), currentSymbol);
			}
		}
		
		return tokens;
	}
}
