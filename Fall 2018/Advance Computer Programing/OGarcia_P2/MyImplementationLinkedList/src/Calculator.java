

import java.io.FileNotFoundException;
import java.io.IOException;

public class Calculator {
	static BigIntNumber number1 = new BigIntNumber();  //first number stack
	static BigIntNumber number2 = new BigIntNumber();  //second number stack
	static fileReader fr = new fileReader();
	static boolean subtraction;

	/**
	 * The purpose of this main file is to initialize the file reader, launch the operations to perform additions and subtractions, and close the file reader
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		try {
			fr.initialize();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setUp();

		try {
			fr.closeReader();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 

	/**
	 * This method is used to print the equation for the user to see the result. It checks to see if the resulting number is a negative number.
	 *  If it is, the printer adds a - sign to the beginning of the number. The resulting number was added to the result stack in reverse order, so 
	 *  all the program needs to do is pop and print the values until the stack is empty.
	 * @param result
	 */
	public static void printEquation(BigIntNumber result) {
		System.out.print(fr.getEquation() + " = ");
		if (result.isNegative) { //Negative number checker. prints - if the rsult is negative
			System.out.print("-");
		}
		int x = result.pop();
		while (x == 0) {  //Ignores any zerros that may have been padded onto the result
			if (result.isTop) {
				System.out.print(x);
			}
			x = result.pop();
		}
		while (x != -1) {   //Print the digits in the resulting number one by one until the list is empty
			System.out.print(x);
			x = result.pop();
		}
		System.out.println("");
	}

	/**
	 * The setup methods creates a number stack two numbers. 
	 * It parse the a string from a file and determines if certain numbers are negative and what operations need to be performed on the two numbers.
	 * @throws IOException
	 */
	public static void setUp() throws IOException {
		boolean complete = false;
		while (!complete) {  //The parser will loop until it gathers all the information or encounters an error
			fr.resetEquation();
			number1.setNegative(false);     //Two number are created
			number2.setNegative(false);
			int x = Parser(number1);        //First number is sent to get its value
			if (x == -1) {
				complete = true;
			}
			boolean done = false;     //Next the set up parser checks to see what operation will be performed on the two numbers
			boolean error = false;
			subtraction = false;      //Subtraction is a special case use by the operator method.
			while (!done && !complete) {
				if (x >= 48 && x <= 57) {    //If the setup encounter a spac between two numbers with no operation, the program warns the user
					System.out.println("Missing operation");
					error = true;
					done = true;
				} else if (x == -1) {  //A number was found, but no operator or second number
					System.out.println("Reached end of file without operation");
					error = true;
					done = true;
				} else if (x == 45) {  //A - was found. The operation is subtraction
					subtraction = true;
					x = fr.readNext();
					done = true;
				} else if (x == 43) {   //A + was was found, the program will perform addition
					subtraction = false;
					done = true;
				} else {
					x = fr.readNext();  //keep reading file until above methods catch something
				}
			}

			if (!error && !complete) {  //If the previous operations ran fine, continue by sending second number to get its values
				Parser(number2);
				BigIntNumber result = Operations.operation(number1, number2, subtraction);
				printEquation(result);
			}
		}
	}
	
/**
 * Parser adds numbers to a stack. The numbers will be added in reverse order.
 * @param num
 * @return
 * @throws IOException
 */
	public static int Parser(BigIntNumber num) throws IOException {
		int x = fr.readNext();
		if (x == -1) {  //End of file was found
			return -1;
		}
		boolean done = false;
		while (x != -1 && done != true) {
			if (x == 45) {  //A negative sign was found. This means the number will be negative
				num.setNegative(true);
			}
			if (x >= 48 && x <= 57) {  //Numbers where found
				while (x >= 48 && x <= 57) {  //loop through the number adding them to a stack
					num.push((x - 48)); 
					x = fr.readNext();
				}
				done = true;
			}
			if (!done) { //read net char until number or - sign is found
				x = fr.readNext();
			}
		}
		return x;
	}
}
