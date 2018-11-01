public class Operations {

	/**
	 * method to determine how the operation will be performed
	 * 
	 * @param n1 First number stack
	 * @param n2 Second number stack
	 * @param op What operation will be used, Subtract == true, add == false
	 * @return
	 */
	public static BigIntNumber operation(BigIntNumber n1, BigIntNumber n2, boolean op) {
		BigIntNumber result = null;
		if (!n1.isNegative() && !n2.isNegative()) { // Both numbers are positive, normal operations
			if (n1.getSize() >= n2.getSize()) { // If the first number is greater than the second, the result will always be
										// positive
				result = solve(n1, n2, op);
			} else {  //If the first number is smaller than the second, subtraction will always result in a negative number. The number are swapped
				result = solve(n2, n1, op); 
				if (op) {  //The swapped number result in -(n2 - n1) which equals n1-n2 if n1 < n2
					result.setNegative(switchOperation(result.isNegative()));
				}
			}
		} else if (n1.isNegative() && n2.isNegative()) { //If both numbers are negative, the program will do the above operations, but negate the results (-n1 + -n2) = -1 * (n1 + n2)
			if (n1.getSize() >= n2.getSize()) {
				result = solve(n1, n2, op);
				result.setNegative(switchOperation(result.isNegative()));
			} else {
				result = solve(n2, n1, op);
				if(!op)
				{
					result.setNegative(switchOperation(result.isNegative()));	
				}
			}
		} else if (!n1.isNegative() && n2.isNegative()) { //If one number is negative, the operations are swapped
			op = switchOperation(op);
			if (n1.getSize() >= n2.getSize()) {
				result = solve(n1, n2, op);
			} else {
				result = solve(n2, n1, op);
				if(op)
				{
				result.setNegative(switchOperation(result.isNegative()));
				}
			}
		} else if (n1.isNegative() && !n2.isNegative()) { //Similar to the above one,  but the results are negated like the second one
			op = switchOperation(op);
			if (n1.getSize() >= n2.getSize()) {
				result = solve(n1, n2, op);
				result.setNegative(switchOperation(result.isNegative()));
			} else {
				result = solve(n2, n1, op);
				if(!op)
				{
				result.setNegative(switchOperation(result.isNegative()));
				}
			}
		}

		return result;  // returns the results
	}

	/**
	 * method to run the operation
	 * @param n1 First number
	 * @param n2 Second number
	 * @param op operations
	 * @return
	 */
	public static BigIntNumber solve(BigIntNumber n1, BigIntNumber n2, boolean op) {
		boolean done = false;
		int carryOver = 0; //CarryOver from the previous operation
		int first;  //Value of the first digit
		int second; //Value of the second digit
		int re = 0; //The result of the operation between the two digits
		BigIntNumber result = new BigIntNumber();  //Stack to hold the result
		while (!done) {
			//Retrieve the values of the digits
			first = n1.pop();
			second = n2.pop();

			if (first == -1 && second == -1) { //Both stacks have reached there end
				done = true;
				if (carryOver > 0) { //If the last operations ended in a value greater then 10, this will account for it
					result.push(1);
				} 
				if(carryOver < 0)
				{
					result.setNegative(true);
				}
			} else if (first == -1) {  //If the first stack is empty, a zero is used instead
				first = 0;
			} else if (second == -1) { //If the second stack is empty, a zero is used instead
				second = 0;
			}
			if (op && !done) { //If the operation is subtraction
				if (!n1.isTop) {  //The digit can borrow a 10 from a higher digit only if there is a higher digit
					first += 10;
				}
				first += carryOver;  //Any carryover from a previous operation has to be applied
				carryOver = 0;
				re = first - second;  //Result of operation
				if (re >= 10) {  //If the digit didn't need the borrow 10 from a higher one, it is returned
					re -= 10;
				} else if (re < 10 && !n1.isTop) {  //If the digit did need the higher 10, then it will be removed from the next operation
					carryOver = -1;
				}else {
					carryOver = 0;
					}
				if (re < 0) {  //If the final digit ended up being less than 0, then the result will be negative
					carryOver = -1;
					re *= -1;
				}
			} else if (!done) { //If we are not done with the operation, we will add
				first += carryOver; //Apply any carry over
				carryOver = 0; 
				re = first + second;  //perform the operations
				if (re >= 10) {
					carryOver = 1; //if the result is over 10 add one to the next digit
					re -= 10;
				}
			}
			result.push(re);  //push the resulting digit into result stack
			re = 0;  //resets result
		}
		return result;
	}

	/**
	 * method to reverse booleans
	 * @param value
	 * @return
	 */
	static public boolean switchOperation(boolean value) {
		if (value) {  //if the boolean is true, switch it to false
			value = false;
		} else { //if it is false, switch it to true
			value = true;
		}
		return value;
	}
}
