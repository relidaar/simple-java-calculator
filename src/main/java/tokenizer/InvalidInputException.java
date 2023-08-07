package tokenizer;

public class InvalidInputException extends Exception {

	public InvalidInputException(int position, char symbol) {
		super("Invalid input: at the position %d".formatted(position));
	}
}
