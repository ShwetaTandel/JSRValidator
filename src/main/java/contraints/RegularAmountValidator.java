package contraints;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import model.Frequency;
import model.RegularAmount;

public class RegularAmountValidator implements ConstraintValidator<CheckRegularAmount, RegularAmount> {

	private RegularAmount regAmount;
	final String regExp = "[0-9]+([,.][0-9]{1,2})?";
	final Pattern pattern = Pattern.compile(regExp);

	/***
	 * This validator handles only the validation for per week amount should be whole number in case frequency is 2,4,13,52
	 * I have assumed null, negative validations for the amount are handles by different annotations as stated in the assignment
	 */
	public boolean isValid(RegularAmount regAmount, ConstraintValidatorContext constraintContext) {
		boolean isValid = true;
		if (regAmount.getAmount() != null) {
			// Invalid Amount - Non numeric or empty string - no validation
			if (regAmount.getAmount().equals("") || !regAmount.getAmount().matches(regExp)) {
				return isValid;
			}
			// Frequency is null -no validation error
			if (regAmount.getFrequency() == null) {
				return isValid;
			}
			if (regAmount.getFrequency() == Frequency.TWO_WEEK || regAmount.getFrequency() == Frequency.FOUR_WEEK
					|| regAmount.getFrequency() == Frequency.QUARTER || regAmount.getFrequency() == Frequency.YEAR) {
				String weeklyAmount = new Float(
						Float.parseFloat(regAmount.getAmount()) / regAmount.getFrequency().getNumberOfWeeks())
								.toString();
				
				isValid = weeklyAmount.matches(regExp);

			}
			if (!isValid) {
				// Add validation error if the weekly amount is not whole number
				constraintContext.disableDefaultConstraintViolation();
				constraintContext.buildConstraintViolationWithTemplate("Invalid weekly amount")
						.addConstraintViolation();
			}
		}
		return isValid;

	}

	public void initialize(CheckRegularAmount constraintAnnotation) {
		if (regAmount == null) {
			regAmount = new RegularAmount("", Frequency.MONTH);
		}

	}
}
