package cs414.midterm.yqiu;

//********************************************************************
//Rational.java       Author: Lewis and Loftus
//
//Represents one rational number with a numerator and denominator.
//********************************************************************

public class Rational
{
	private int numerator, denominator;

	//-----------------------------------------------------------------
	//  Sets up the rational number by ensuring a nonzero denominator
	//  and making only the numerator signed.
	//-----------------------------------------------------------------
	public Rational (int numer, int denom)
	{

		// Make the numerator "store" the sign
		if (denom < 0)
		{
			numer = numer * -1;
			denom = denom * -1;
		}

		numerator = numer;
		denominator = denom;

		reduce();
	}

	//-----------------------------------------------------------------
	//  Adds this rational number to the one passed as a parameter.
	//  A common denominator is found by multiplying the individual
	//  denominators.
	//-----------------------------------------------------------------
	public Rational add (Rational op2)
	{
		int commonDenominator = denominator * op2.getDenominator();
		int numerator1 = numerator * op2.getDenominator();
		int numerator2 = op2.getNumerator() * denominator;
		int sum = numerator1 + numerator2;

		return new Rational (sum, commonDenominator);
	}

	//-----------------------------------------------------------------
	//  Subtracts the rational number passed as a parameter from this
	//  rational number.
	//-----------------------------------------------------------------
	public Rational subtract (Rational op2)
	{
		int commonDenominator = denominator * op2.getDenominator();
		int numerator1 = numerator * op2.getDenominator();
		int numerator2 = op2.getNumerator() * denominator;
		int difference = numerator1 - numerator2;

		return new Rational (difference, commonDenominator);
	}

	//-----------------------------------------------------------------
	//  Multiplies this rational number by the one passed as a
	//  parameter.
	//-----------------------------------------------------------------
	public Rational multiply (Rational op2)
	{
		int numer = numerator * op2.getNumerator();
		int denom = denominator * op2.getDenominator();

		return new Rational (numer, denom);
	}

	//-----------------------------------------------------------------
	//  Divides this rational number by the one passed as a parameter
	//  by multiplying by the reciprocal of the second rational.
	//-----------------------------------------------------------------
	public Rational divide (Rational op2)
	{
		return multiply (op2.reciprocal());
	}

	//-----------------------------------------------------------------
	//  Returns the reciprocal of this rational number.
	//-----------------------------------------------------------------
	public Rational reciprocal ()
	{
		return new Rational (denominator, numerator);
	}

	//-----------------------------------------------------------------
	//  Returns the numerator of this rational number.
	//-----------------------------------------------------------------
	public int getNumerator ()
	{
		return numerator;
	}

	//-----------------------------------------------------------------
	//  Returns the denominator of this rational number.
	//-----------------------------------------------------------------
	public int getDenominator ()
	{
		return denominator;
	}

	//-----------------------------------------------------------------
	//  Determines if this rational number is equal to the one passed
	//  as a parameter.  Assumes they are both reduced.
	//-----------------------------------------------------------------
	public boolean equals (Object op2)
	{
		if(op2 instanceof Rational) {
			return ( numerator == ((Rational)op2).getNumerator() &&
					denominator == ((Rational)op2).getDenominator() );
		} else {
			return false;
		}
	}

	//-----------------------------------------------------------------
	//  Returns this rational number as a string.
	//-----------------------------------------------------------------
	public String toString ()
	{
		String result;

		if (numerator == 0)
			result = "0";
		else
			if (denominator == 1)
				result = numerator + "";
			else
				result = numerator + "/" + denominator;

		return result;
	}

	//-----------------------------------------------------------------
	//  Reduces this rational number by dividing both the numerator
	//  and the denominator by their greatest common divisor.
	//-----------------------------------------------------------------
	private void reduce ()
	{
		if (numerator != 0)
		{
			int common = gcd (Math.abs(numerator), denominator);

			numerator = numerator / common;
			denominator = denominator / common;
		}
	}

	//-----------------------------------------------------------------
	//  Computes and returns the greatest common divisor of the two
	//  positive parameters. Uses Euclid's algorithm.
	//-----------------------------------------------------------------
	private int gcd (int num1, int num2)
	{
		int a;
		int b;
		int rem=1;
		int result=1;
		if(num1 > num2) {
			a = num1;
			b = num2;
		} else {
			a = num2;
			b = num1;
		}
		while(rem!=0) {
			rem = a % b;
			if(rem==0) result=b;
			a = b;
			b = rem;
		}
		return result;
	}
}

