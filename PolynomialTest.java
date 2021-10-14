// Part 1
// Your name
//Joe sakkab and Adnan Ifram
//Which assignment/problem this is
//Assignment 1 Part 1
//The date that the code was written
//4th of October
//A description of what the program does
//tests the output of running the polynomial code on test cases
// Unit tests for both the "teacher" and "student" implementations of Polynomial
// 
// These tests will be run when you select either:
// - "Run Unit Tests (Teacher)" (to test the teacher's implementation); or
// - "Run Unit Tests (Student)" (to test the student's implementation)
// in the rocket menu at the top

import static org.junit.Assert.*;

import org.junit.Test;

public class PolynomialTest {
	Polynomial pHighestToLowest = new Polynomial("-3.2x^5 + 5x^6 + 80.0");
	Polynomial pIntegersOnly = new Polynomial("-3x^2 + 5");
	Polynomial pDecimals = new Polynomial("-3.6789x^2 + 5.20394");
	Polynomial pDefault = new Polynomial("0.0");
	Polynomial pZeroDegree = new Polynomial("5.0x^0");
	Polynomial pFirstDegree = new Polynomial("1.3x^1");
	Polynomial pOneCoeffPos = new Polynomial("1x^2 + 1.0x");
	Polynomial pOneCoeffNeg = new Polynomial("-1x^2 - 1.0x");
	Polynomial pNegCoeff = new Polynomial("-3.0x^3 + -2.0x + -5.0");
	Polynomial p = new Polynomial("3.0x^2 - 2.0x + 1.0");
	Polynomial p1 = new Polynomial("2.0x^2 - 4.0x");
	Polynomial p2 = new Polynomial("-3.0x^2 - 2.0x + 1.0");
	Polynomial p3 = new Polynomial("3.0x^2 + 2.0x - 1.0");
	Polynomial pPosInfinity = new Polynomial("500000000.00x^5003 + 654321x^320");
	Polynomial pNegInfinity = new Polynomial("-500000000.00x^5003 - 654321x^320");

	@Test
	public void testConstructorDefault() {
		Polynomial p = new Polynomial(); // creates default constructor
		assertEquals(-1, p.getDegree()); // 1 default degree = -1
		assertTrue(0 == p.getValue(0)); // 2 value=0 to all values of x
		assertTrue(0 == p.getValue(5.0)); // 3 value=0 to all values of x
		assertEquals("0", p.toString()); // 4 testing what the polynomial equats to
		assertTrue(0.0 == pDefault.getValue(0.0)); // 5 Default value also must equal 0.0
		assertTrue(0.0 == pDefault.getValue(33.0)); // 6 Default value also must equal 0.0
		assertTrue(0.0 == pDefault.getValue(-500.0)); // 7 Default value also must equal 0.0
	}

	@Test
	public void testConstructorString() {
		assertEquals("0", new Polynomial(null).toString()); // 82 creating null polynomial = 0
		assertEquals("5.0x^6 - 3.2x^5 + 80.0", pHighestToLowest.toString()); // 8 Sort by degree
		assertEquals("-3.0x^2 + 5.0", pIntegersOnly.toString()); // 9 Converts int values to double
		assertEquals("-3.6789x^2 + 5.20394", pDecimals.toString()); // 10 Allows large decimals
		assertEquals("0", pDefault.toString()); // 11 P(x) = 0.0
		assertEquals("5.0", pZeroDegree.toString()); // 12 x^0 converted to 1
		assertEquals("1.3x", pFirstDegree.toString()); // 13 x^1 converted to x
		assertEquals("3.0x^2 - x + 1.0", new Polynomial("3.0x^2 - x + 1.0").toString()); // 14 x without power
		assertEquals("x^2 + x", pOneCoeffPos.toString()); // 15 Positive with no coefficients
		assertEquals("-x^2 - x", pOneCoeffNeg.toString()); // 16 Negative with no coefficients
		assertEquals("-3.0x^3 - 2.0x - 5.0", pNegCoeff.toString()); // 17 Negative coefficients
		assertEquals("3.0x^2 - 2.0x + 1.0", new Polynomial("3.0x^2  - 2.0x     +       1.0").toString()); // 18 Large
																											// spaces
//		assertEquals("1.0" , new Polynomial("3.0x^-2 + 1.0").toString()); //FIXED THIS PROBLEM
		assertEquals("3.0x^2 + 2.0x + 1.0", new Polynomial("3.0x^2   2.0x        1.0").toString()); // 19 //ASK
																									// different
																									// behavior from
																									// teacher
																									// implementation  No + or - symbols
//		// Highlight in test plan. Different situations form teachers test plan.
//		//Highlight the difference (bold color code)
		assertEquals("5.0E8x^6 + 7.8x^3 + 9.0", new Polynomial("500000000.00x^6 + 7.8x^3 + 9.0").toString()); // 20 Large coefficients 
		assertEquals("5.0x^50034 + 6.5x^32093", new Polynomial("5.0x^50034 + 6.5x^32093").toString()); // 21 Large powers
		assertEquals("5.0E8x^50034 + 654321.0x^32093",
				new Polynomial("500000000.00x^50034 + 654321x^32093").toString()); // 22 Large coefficients and powers
		assertEquals("-5.0E8x^50034 - 654321.0x^32093",
				new Polynomial("-500000000.00x^50034 - 654321x^32093").toString()); // 23 Large negative coefficients and powers
		assertEquals("-1.23456789E8x^50034 - 654321.0x^32093",
				new Polynomial("-123456789.00x^50034 - 654321x^32093").toString()); // 24 Large number with a lot of random numbers
		assertEquals("1.2345678987654E17x^50034 - 654321.0x^32093",
				new Polynomial("1.2345678987654E17x^50034 - 654321.0x^32093").toString()); // 25 Large random numbers with an exponential value.
		assertEquals("33.0x^5 - 290.5x^3 + 75.9", new Polynomial("+33.0x^5 - 290.5x^3 + 75.9").toString()); // 26 Adding + in beginning of polynomial
		assertEquals("33.0x^5 - 290.5x^3 - 75.9", new Polynomial("33.0x^5 - +290.5x^3 + -75.9").toString()); // 27 Adding – then +coefficient
		assertEquals("512.0", new Polynomial("512.0").toString()); // 28 constant
		assertEquals("-512.0", new Polynomial("-512.0").toString()); // 29 Negative constant
	}

	@Test(expected = IllegalArgumentException.class)
	public void Incorrectformat1() {
		Polynomial p = new Polynomial("3.0x^2-2.0x+1.0"); // 30 no white spaces
	}

	@Test(expected = IllegalArgumentException.class)
	public void Incorrectformat2() {
		assertEquals("1.0", new Polynomial("3.0x^-2 + 1.0").toString()); // 31 negative power
	}

//	@Test(expected = IllegalArgumentException.class)
//	public void Incorrectformat3() {
//		assertEquals("3.0x^2 + 1.0", new Polynomial("3.0x^2   2.0x        1.0").toString());
//	}

	@Test(expected = IllegalArgumentException.class)
	public void testempty() {
		Polynomial p = new Polynomial(""); // 32 empty polynomial
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLetter() {
		Polynomial p = new Polynomial("HelloWorld!"); // 33 polynomial with letters
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDecimalDegree() {
		Polynomial p = new Polynomial("3.0x^4.56"); // 34 double degree
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNonNegativeIntegerDegree() {
		Polynomial p = new Polynomial("3.0x^-0.5"); // 35 negative integer degree
	}

	@Test
	public void testGetDegree() {
		assertEquals(2, p.getDegree()); // 36 polynomial with degree of 2
		assertEquals(0, pZeroDegree.getDegree()); // 37 Polynomial with degree of 0
	}

	@Test
	public void testGetCoefficient() {
		assertTrue(3.0 == p.getCoefficient(2)); // 38 coefficient of the second degree getting positive 
		assertTrue(-2.0 == p.getCoefficient(1)); // 39 Coefficient of the first degree getting negative
		assertTrue(1.0 == p.getCoefficient(0)); // 40 Coefficient of the 0 degree
		assertTrue(0.0 == p.getCoefficient(5)); // 41 Coefficient of a non existant degree
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetNegativeDegree() {
		p.getCoefficient(-3); // 42 getting coefficient of a negative degree
	}

	@Test
	public void testUpdate() {
		Polynomial p = new Polynomial("3.0x^2 - 2.0x + 1.0");

		p.update(4.0, 2);
		assertFalse("3.0x^2 - 2.0x + 1.0".equals(p.toString())); // 43 // Make sure that the update method updated the
																	// original polynomial 
		assertEquals("7.0x^2 - 2.0x + 1.0", p.toString()); // 44 Updating with an existing term

		p.update(0, 2);
		assertEquals("7.0x^2 - 2.0x + 1.0", p.toString()); // 45 Updating by a 0 coefficient with an existing power

		p.update(0.0, 6);
		assertEquals("7.0x^2 - 2.0x + 1.0", p.toString()); // 46 Updating by a 0.0 coefficient with a non existing power

		Polynomial p2 = new Polynomial("3.0x^2 - 2.0x + 1.0");
		p2.update(-3.2, 1);
		assertEquals("3.0x^2 - 5.2x + 1.0", p2.toString()); // 47 Updating with a negative coefficient

		p2.update(5.7, 7);
		assertEquals("5.7x^7 + 3.0x^2 - 5.2x + 1.0", p2.toString()); // 48 Updating with a non existing power

	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateNegative() {
		Polynomial p = new Polynomial("3.0x^2 - 2.0x + 1.0");
		p.update(3.0, -9); // 49 updating with negative power
	}

	@Test
	public void testAdd() {
		Polynomial p1 = p.add(pHighestToLowest);
		assertEquals("3.0x^2 - 2.0x + 1.0", p.toString()); // 50 // Check that the add method is an accessor not a
															// modifier
		assertEquals("5.0x^6 - 3.2x^5 + 3.0x^2 - 2.0x + 81.0", p1.toString()); // 51 Adding a non arranged polynomial

		p1 = p.add(p2);
		assertEquals("-4.0x + 2.0", p1.toString()); // 52 Deletes a 0 coefficient

		p1 = p2.add(p3);
		assertEquals("0", p1.toString()); // 53 Check with prof FIXED Adding a negated polynomial
//		assertTrue(0.0 == p1.getValue(1)); // 

		p1 = p.add(pDefault);
		assertEquals("3.0x^2 - 2.0x + 1.0", p1.toString()); // 54 Adding default polynomial
	}

	@Test
	public void testSubtract() {
		Polynomial p1 = p.subtract(pHighestToLowest);
		assertEquals("3.0x^2 - 2.0x + 1.0", p.toString()); // 55// Check that the subtract method is an accessor not a
															// modifier
		assertEquals("-5.0x^6 + 3.2x^5 + 3.0x^2 - 2.0x - 79.0", p1.toString()); // 56 Subtracting a non arranged polynomial

		p1 = p.subtract(p2);
		assertEquals("6.0x^2", p1.toString()); // 57 Deletes a 0 coefficient

		p1 = p2.subtract(p2);
		assertEquals("0", p1.toString()); // 58 subtracting itself
//		assertTrue(0.0 == p1.getValue(1)); // 

		p1 = p.subtract(pDefault);
		assertEquals("3.0x^2 - 2.0x + 1.0", p1.toString()); // 59 Subtracting default polynomial
	}

	@Test
	public void testNegate() {
		Polynomial pCheck = p.negate();
		assertEquals("3.0x^2 - 2.0x + 1.0", p.toString()); // 60 testing that it is an accessor not a mutator
		assertEquals("-3.0x^2 + 2.0x - 1.0", p.negate().toString()); // 61 Negating a polynomial
		assertEquals("0", pDefault.negate().toString()); // 62 Negating the 0 polynomial

	}

	@Test
	public void testGetValue() {
		Polynomial p = new Polynomial("3.0x^2 - 2.0x + 1.0");
		assertTrue(2.0 == p.getValue(1.0)); // 63 getting value when x=1 as double
		assertTrue(9.0 == p.getValue(2)); // 64 Getting value when x=2 as integer

		assertTrue(0.0 == p1.getValue(2.0)); // 65 When value = 0 to a non-null polynomial
		assertTrue(16.0 == p1.getValue(-2.0)); // 66 Negative x value

		Polynomial constant = new Polynomial("5.6");
		assertTrue(5.6 == constant.getValue(1)); // 67 When the polynomial does not contain any x’s for int
		assertTrue(5.6 == constant.getValue(50.6)); // 68 When the polynomial does not contain any x’s for large double

		assertTrue(0.0 == p1.getValue(0.0)); // 69 When x = 0 
		assertTrue(1.0 == p.getValue(0.0)); // 70 When x= 0 with constant in the polynomial
		assertEquals("-Infinity", new Polynomial("-500000000.00x^5000 + 654321x^320").getValue(2.0) + ""); // 71 // Getting value of large negative polynomial
																											
		assertEquals("Infinity", new Polynomial("500000000.00x^5000 - 654321x^320").getValue(2.0) + ""); // 72 Getting value of large polynomial
		assertEquals("-Infinity", new Polynomial("-500000000.00x^5000 - 654321x^320").getValue(2.0) + ""); // 73 Should be negative infinity // Fixing the NAN problem for very large negative numbers 
		assertEquals("Infinity", new Polynomial("500000000.00x^5000 + 654321x^320").getValue(2.0) + ""); // 74 Should be infinity // Fixing the NAN problem for very large numbers 

	}

	@Test
	public void testGetDerivative() {
		assertEquals("4.0x - 4.0", p1.getDerivative().toString()); // 75 normal polynomial derivative
		assertEquals("6.0x - 2.0", p.getDerivative().toString()); // 76 removes the x and the carrot and removes the number without an x
		assertEquals("1.3", pFirstDegree.getDerivative().toString()); // 77 Removes the power of 1
		assertEquals("0", pDefault.getDerivative().toString()); // 78 Default polynomial 
		assertEquals("0", pZeroDegree.getDerivative().toString()); // 79 Derivative of a constant
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddNull() {
		p.add(null); // 80 adding null to a polynomial
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSubtractNull() {
		p.subtract(null); // 81 subtracting null to a polynomial
	}
}