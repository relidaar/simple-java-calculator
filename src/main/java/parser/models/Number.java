package parser.models;

import parser.Context;

public class Number implements Expression {
	private double mValue;
	
	public Number(double value) {
		this.mValue = value;
	}

	@Override
	public Double evaluate(Context context) {
		return mValue;
	}
}