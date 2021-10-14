import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

// Part 2
// Your name
// Joe sakkab and Adnan Ifram
// Which assignment/problem this is
// Assignment 1 Part 2
// The date that the code was written
// 6th of October
// A description of what the program does
// A  polynomial class that turns a string into a polynomial and  run several methods
// such  as add, subtract, update, negate, getValue, getDegree getDerivative and toString.

public class Polynomial {
	private ArrayList<Integer> powerArr = new ArrayList<>();
	private ArrayList<double[]> arr = new ArrayList<>();

	// This is the default constructor of the polynomial class.
	// It has a value of 0 and a degree of -1.
	public Polynomial() {
	}

	// This is the main constructor of the polynomial that takes in a string object
	// as a parameter.
	public Polynomial(String s) {
		// It first checks is the string is null, and returns the default polynomial
		// object.
		if (s == null) {
			this.arr = (new Polynomial()).arr;
			// Checks if the string is nothing or if it contains any of illegal characters
			// using the checkString method. Throws an IllegalArgumentException if true.
		} else if (s.equals("") || checkString(s)) {
			throw new IllegalArgumentException("Polynomial is empty or has illgeal characters");
			// Otherwise it will call the stringtoArrayList method which converts the string
			// parameter into a proper ArrayList that stores the data in a meaningful way.
		} else {
			stringToArrayList(s);
			// Sort the main array
			sortArr();
		}
	}

	// This is the stringToArrayList method.
	public void stringToArrayList(String s) {

		s = s.replace("x", "#"); // Replaces every x with a #
		s = s.replace("#^", "$"); // Replaces every #^ with a $
		s = s.replaceAll("\\s+", "_"); // Replaces every series of whitespace with an underscore.

		// Creates a scanner object, a new ArrayList of String that stores the tokens, a
		// string variable of a single token,
		// and a integer sign variable that either holds a value of 1 or -1.
		Scanner scan = new Scanner(s);
		ArrayList<String> tokens = new ArrayList<>();
		int sign = 1;
		String current;
		double power;

		// Assigns all the tokens to an ArrayList of Strings called tokens.
		scan.useDelimiter("_");
		while (scan.hasNext()) {
			tokens.add(scan.next());
		}
		scan.close();

		// Loops through the tokens ArrayList
		for (int i = 0; i < tokens.size(); i++) {
			// assigns the current token to a current variable of type string
			current = tokens.get(i);
			// this is a double arr of size 2 that stores the current token as a coefficient
			// and its power.
			double coef = 0.0;

			// Check if the current token contains a #
			if (current.contains("#")) {
				// If it is only a #, then it is only an x, so save the coefficient of the term
				// to be 1.0 or -1.0 according to the sign
				if (current.equals("#")) {
					coef = 1.0 * sign;
					// else, it contains a # so the save the coefficient of the term to be
					// the coefficient multiplied by the sign
				} else {
					coef = Double.parseDouble(current.replace("#", "")) * sign;
				}
				// Update the main arr with power of 1
				this.update(coef, 1);

				// check if the token contains a $, meaning the power is > 1.
			} else if (current.contains("$")) {
				// Assign the power to the substring from $ till the end.
				power = Double.parseDouble(current.substring(current.indexOf("$") + 1));

				if (power < 0 || (Math.floor(power) != power)) {
					throw new IllegalArgumentException("Make sure that the power is a positive and an integer");
				} else {
					// Check if the first char in the token is $,then the coefficient is 1.
					if (current.indexOf("$") == 0) {
						coef = 1.0 * sign;
					} else {
						coef = Double.parseDouble(current.substring(0, current.indexOf("$"))) * sign;
					}
					// Assign the power of the term to the power and update the main array arr.
					this.update(coef, (int) power);
				}
				// Check if the token is a plus of a minus, then use the signState method to
				// assign a
				// 1or -1 to the sign variable.
			} else if (current.equals("+") || current.equals("-")) {
				sign = signState(current);
				// checks for tokens that do not contain x^ and it not a plus or minus
				// Meaning that this is a constant
			} else if (!(current.contains("$") && current.equals("+") && current.equals("-"))) {
				try {
					// try parsing it to a double, and throw an IllegalArgumenException if it is
					// not.
					coef = Double.parseDouble(current) * sign;
				} catch (IllegalArgumentException e) {
					System.out.println("Make sure that the format is correct");
				}
				// Update the main array of coefficient 0.
				this.update(coef, 0);
			} else {
				throw new IllegalArgumentException("Make sure that the format is correct");
			}

		}
	}

	// Method to sort the main ArrayList of terms
	public void sortArr() {
		// Create a new result polynomial that is the default polynomial.
		ArrayList<double[]> result = new ArrayList<>();
		// Sort the powerArr ArrayList in descending order by using the sort then
		// reverse
		// methods from the collections class.
		Collections.sort(powerArr);
		Collections.reverse(powerArr);
		// Loop through each element in the powerArr
		for (int j = 0; j < arr.size(); j++) {
			// loop through each element in the powerArr
			for (int i = 0; i < arr.size(); i++) {
				// If the powers match, add the whole term to the beginning of
				// the result ArrayList
				if (arr.get(i)[1] == powerArr.get(j)) {
					result.add(arr.get(i));
				}
			}
		}
		// Reassign the arr to the result since this method is a mutator.
		arr = result;

	}

	// Method to print out the main ArrayList of terms
	public String toStringArr() {
		sortArr();
		int s = arr.size();
		System.out.println(s);
		String result = "  {\n";

		if (s == 0) {
			result = "0";
		} else {
			for (int i = 0; i < s - 1; i++) {
				result += "   [" + arr.get(i)[0] + ", " + arr.get(i)[1] + "],\n ";
			}
			result += "   [" + arr.get(s - 1)[0] + ", " + arr.get(s - 1)[1] + "]\n  }";
		}
		return result;
	}

	// Method to update the polynomial by adding the coefficient to an already
	// present degree or make a new term for a degree that is not present
	public void update(double coefficient, int degree) {
		// throw an IllegalArgument exception if the degree is negative
		if (degree < 0) {
			throw new IllegalArgumentException();
		}

		// This if statement checks if the term should be a new term or an addition
		// of an already existing term.

		// If the degree parameter is not contained in poawerArr
		// It adds the term as a new term
		if (!(powerArr.contains(degree))) {
			double[] newTerm = { coefficient, degree };
			this.powerArr.add((int) degree);
			this.arr.add(newTerm);
			// If the degree parameter is contained in powerArr
			// Then the terms coefficients are added.
		} else if (powerArr.contains(degree)) {
			int location = powerArr.indexOf(degree);
			arr.get(location)[0] += coefficient;
		}
		// This sorts the main array
		// It is important to sort the array every time it gets updated or changed.
		sortArr();
	}

	// Method to return the highest degree of a polynomial
	public int getDegree() {
		// If the array is empty, then it is the default polynomial and returns a degree
		// of -1.
		if (this.arr.isEmpty()) {
			return -1;
		}
		return powerArr.get(0);
	}

	// Method to return the evaluated value of the polynomial given an input 'd' as
	// a double
	public double getValue(double d) {

		// If the array is empty, then it is the default polynomial and return a value
		// of 0.0.
		if (arr.isEmpty()) {
			return 0.0;
		}

		double result = 0;
		// a for loop that loops through each term in the array and calculates the value
		// of
		// the polynomial given an input d.
		for (int i = 0; i < arr.size(); i++) {
			result += arr.get(i)[0] * (Math.pow(d, arr.get(i)[1]));
		}
		return result;
	}

	// Method to return the coefficient of a given power 'i' present in a term in
	// the polynomial
	public double getCoefficient(int i) {
		// if the given degree is negative, then throw an IllegalArgumentException
		if (i < 0) {
			throw new IllegalArgumentException("Please Insert Valid Degree");
		}
		// If the power exists in the powerArr, return the coefficient associated with
		// that degree
		if (powerArr.indexOf(i) != -1) {
			return arr.get(powerArr.indexOf(i))[0];
		}
		// Else return 0.
		return 0;
	}

	// Method to add a polynomial to another polynomial
	public Polynomial add(Polynomial other) {
		// If the polynomial being added is null, then throw an IllegalArgumentException
		if (other == null) {
			throw new IllegalArgumentException("Input paramater for method is null");
		}
		// loop through the polynomial being added.
		// add the terms that have similar degrees and add any new ones using the update
		// method.
		Polynomial result = new Polynomial(this.toString());
		ArrayList<double[]> otherArr = other.arr;
		for (int i = 0; i < otherArr.size(); i++) {
			result.update(otherArr.get(i)[0], (int) otherArr.get(i)[1]);
		}
		// return a new polynomial
		return result;

	}

	// Method to subtract a polynomial form another polynomial
	public Polynomial subtract(Polynomial other) {
		// If the polynomial being added is null, then throw an IllegalArgumentException
		if (other == null) {
			throw new IllegalArgumentException();
		}
		/// call the add method on the negated polynomial, which subtracts both
		/// polynomials from each other.
		Polynomial result = new Polynomial(this.toString());
		return result.add(other.negate());
	}

	// Method to negate a polynomial
	public Polynomial negate() {

		// if the degree of the polynomial is -1, this is the default polynomial,
		// so it should return a new default polynomial
		if (this.getDegree() == -1) {
			return this;
		}
		// loop through each term and multiply the coefficient of every term by -1
		Polynomial result = new Polynomial(this.toString());
		ArrayList<double[]> resultArr = result.arr;
		for (int i = 0; i < resultArr.size(); i++) {
			resultArr.get(i)[0] *= -1;
		}
		// return a new negated polynomial.
		return result;
	}

	// Method to get the derivative of the polynomial
	public Polynomial getDerivative() {
		Polynomial result = new Polynomial();
		// if the highest degree of the polynomial is 0.0, return a new polynomial.
		if (arr.get(0)[1] == 0.0) {
			return new Polynomial("0.0");
		}

		int i = 0;
		double coef;
		int power = 1;
		int digits;

		// While the degree is positive, and i is in bounds
		// the new coefficient is the degree multiplied by the coefficient
		// and the new degree is the degree - 1.
		while (i < arr.size() && power > 0) {
			coef = arr.get(i)[0];
			power = (int) arr.get(i)[1];
			digits = (coef + "").length() - (coef + "").indexOf(".") - 1;

			// Assign the new coefficient and power.
			coef *= power;
			coef = Double.parseDouble(String.format("%." + digits + "f", coef));

			// Check if the new power after derivation is not negative
			if (power - 1 >= 0) {
				power--;
			} else if (power - 1 < 0) {
				power = 0;
			}
			// Add the term to the result polynomial
			result.update(coef, power);
			i++;
		}

		// sort and return the polynomial
		result.sortArr();
		return result;
	}

	// Method that converts the polynomial to a string
	public String toString() {
		String result = "";
		int power = 0;
		double coef = 0;
		int digits = 2;
		String sign;
		// Checks if the degree is -1, which means that the polynomial is a default, so
		// it returns a string of 0.
		if (this.getDegree() == -1) {
			return "0";
		}

		// loop through the main ArryaList
		for (int i = 0; i < arr.size(); i++) {
			// Assign the variables coef and power to the coefficient and power of the
			// current term.
			power = (int) arr.get(i)[1];
			coef = arr.get(i)[0];
			//
			String coefString = Double.toString(coef);
			digits = coefString.length() - coefString.indexOf(".") - 1;

			// Check if the coefficient is positive or negative
			if (coef < 0) {
				sign = " - ";
				coef *= -1;
			} else {
				sign = " + ";
			}

			coef = Double.parseDouble(String.format("%." + digits + "f", coef));
			// If statements that check for various conditions of the power and coefficient
			// while the coefficient is non-zero
			if (coef != 0) {
				if (coef == 1.0 && power > 1.0) {
					result += sign + "x^" + power;
				} else if (coef != 1.0 && power > 1.0) {
					result += sign + coef + "x^" + power;
				} else if (coef == 1.0 && power == 1.0) {
					result += sign + "x";
				} else if (coef != 1.0 && power == 1.0) {
					result += sign + coef + "x";
				} else if (power == 0.0) {
					result += sign + coef;
				}
			}

		}

		// Manipulates the string to be in the correct format
		// Removes all extra spaces from the beginning and the first whitespace
		// between the sign and the first term
		result = result.trim().replaceFirst(" ", "");

		// returns an "0" string if the result string is empty
		if (result.equals("")) {
			return "0";
			// replaces the first + character of the first term with an empty string
			// if the first term is positive
		} else if (result.charAt(0) == '+') {
			result = result.replaceFirst("\\+", "");
		}

		return result;
	}

	// Method to determine the sign of a coefficient based on the '+' and '-'
	// operators
	private int signState(String sign) {
		if (sign.equals("+")) {
			return 1;
		} else if (sign.equals("-")) {
			return -1;
		} else {
			throw new IllegalArgumentException("Not a valid operation sign: Must be + or -");
		}
	}

	// Check for characters that are not allowed, returns true if there are illegal
	// characters.
	public static boolean checkString(String poly) {
		String s = "E0123456789.x^+- "; // All the characters that are allowed in the polynomial string
		// loops through the polynomial string
		for (int i = 0; i < poly.length(); i++) {
			// checks if the current character in the polynomial string is
			if (!s.contains(poly.charAt(i) + "")) {
				return true;
			}
		}
		return false;
	}
}
