package validation.test;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import model.Frequency;
import model.RegularAmount;

public class RegularAmountValidatorTest {
	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	/**
	 * Invalid Amount -  GIVEN any Frequency WHEN a non-numeric or blank Amount is
	 * provided THEN no validation error
	 */
	@Test
	public void testInvalidAmount() {
		// Invalid amount - non numeric
		RegularAmount income = new RegularAmount("rtqrt", Frequency.TWO_WEEK);
		Set<ConstraintViolation<RegularAmount>> constraintViolations = validator.validate(income);
		assertEquals(0, constraintViolations.size());
		
		income = new RegularAmount("", Frequency.TWO_WEEK);
		constraintViolations = validator.validate(income);
		assertEquals(0, constraintViolations.size());
		
	}
	
	/**
	 *  Null Frequency -  GIVEN any Amount
	 *  WHEN a null Frequency is provided
	 *  THEN no validation error
	 */
	@Test
	public void testNullFrequency() {
		RegularAmount income = new RegularAmount("12000.00", null);
		Set<ConstraintViolation<RegularAmount>> constraintViolations = validator.validate(income);
		assertEquals(0, constraintViolations.size());
	}
	
	/**
	 *  Weekly -  GIVEN a WEEK Frequency
	 *  WHEN a any Amount is provided
	 *  THEN no validation error
	 */
	@Test
	public void testWeeklyAndMonthlyFrequency() {
		RegularAmount income = new RegularAmount("12000.99", Frequency.WEEK);
		Set<ConstraintViolation<RegularAmount>> constraintViolations = validator.validate(income);
		assertEquals(0, constraintViolations.size());
		
		income = new RegularAmount("6272220000", Frequency.MONTH);
		constraintViolations = validator.validate(income);
		assertEquals(0, constraintViolations.size());

	}

	
	/***
	 *  GIVEN a Frequency is in the set TWO_WEEK, FOUR_WEEK, QUARTER, YEAR
	 *	AND an associated Number of Weeks is 2, 4, 13, 52 respectively
	 *  WHEN a Amount that divides by the Number of Weeks to a whole number of pence is provided
	 *	THEN no validation error
	 * 
	 **/
	
	@Test
	public void testWeeklyIncomeForTwoWeek() {

		// Valid Amount
		RegularAmount income = new RegularAmount("24", Frequency.TWO_WEEK);
		Set<ConstraintViolation<RegularAmount>> constraintViolations = validator.validate(income);
		assertEquals(0, constraintViolations.size());

		// Invalid amount - Validation Error 
		income = new RegularAmount("24.99", Frequency.TWO_WEEK);
		constraintViolations = validator.validate(income);
		assertEquals(1, constraintViolations.size());
		assertEquals("Invalid weekly amount", constraintViolations.iterator().next().getMessage());

	}

	@Test
	public void testWeeklyIncomeForFourWeek() {

		// Valid Amount
		RegularAmount income = new RegularAmount("24.00", Frequency.FOUR_WEEK);
		Set<ConstraintViolation<RegularAmount>> constraintViolations = validator.validate(income);
		assertEquals(0, constraintViolations.size());

		// Invalid amount - Validation Error 
		income = new RegularAmount("24.99", Frequency.FOUR_WEEK);
		constraintViolations = validator.validate(income);
		assertEquals(1, constraintViolations.size());
		assertEquals("Invalid weekly amount", constraintViolations.iterator().next().getMessage());

	}

	@Test
	public void testWeeklyIncomeForQuarter() {

		// Valid Amount
		RegularAmount income = new RegularAmount("26.00", Frequency.QUARTER);
		Set<ConstraintViolation<RegularAmount>> constraintViolations = validator.validate(income);
		assertEquals(0, constraintViolations.size());

		// Invalid amount - Validation Error 
		income = new RegularAmount("24.99", Frequency.QUARTER);
		constraintViolations = validator.validate(income);
		assertEquals(1, constraintViolations.size());
		assertEquals("Invalid weekly amount", constraintViolations.iterator().next().getMessage());

	}

	@Test
	public void testWeeklyIncomeForYear() {

		// Valid Amount
		RegularAmount income = new RegularAmount("9822.80", Frequency.YEAR);
		Set<ConstraintViolation<RegularAmount>> constraintViolations = validator.validate(income);
		assertEquals(0, constraintViolations.size());

		// Invalid amount - Validation Error 
		income = new RegularAmount("9822.00", Frequency.YEAR);
		constraintViolations = validator.validate(income);
		assertEquals(1, constraintViolations.size());
		assertEquals("Invalid weekly amount", constraintViolations.iterator().next().getMessage());

	}
}