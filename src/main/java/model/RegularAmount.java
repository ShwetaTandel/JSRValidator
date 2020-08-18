package model;

import contraints.CheckRegularAmount;
@CheckRegularAmount
public class RegularAmount {
	private Frequency frequency;
	private String amount;

	public RegularAmount(String amount, Frequency frequency) {
		this.amount = amount;
		this.frequency = frequency;

	}

	public Frequency getFrequency() {
		return frequency;
	}

	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
}
