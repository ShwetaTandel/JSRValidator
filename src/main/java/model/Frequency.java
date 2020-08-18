package model;
public enum Frequency {
	 WEEK(1), TWO_WEEK(2), FOUR_WEEK(4), MONTH(5), QUARTER(13), YEAR(52);
	
	 private final int numberOfWeeks;

	 Frequency(int numberOfWeeks) {
	        this.numberOfWeeks = numberOfWeeks;
	    }
	    
	    public int getNumberOfWeeks() {
	        return this.numberOfWeeks;
	    }
	}
