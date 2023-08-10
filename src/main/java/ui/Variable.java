package ui;

public class Variable {
	private final String mName;
	private final Double mValue;
	
	public Variable(String name, Double value) {
		mName = name;
		mValue = value;
	}

	public String getName() {
		return mName;
	}

	public double getValue() {
		return mValue;
	}
}
