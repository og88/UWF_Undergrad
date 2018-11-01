import java.util.LinkedList;

public class BigIntNumber {
	boolean isNegative; //Boolean to see if the number is negative
	boolean isTop;
	LinkedList<Integer> number = new LinkedList<Integer>();  //Stack to hold the numbers

	/**
	 * default class constructor
	 */
	BigIntNumber() {
		isNegative = false;
	}

	/**
	 * Sets the negative field for the number
	 * @param status
	 */
	public void setNegative(boolean status) {
		this.isNegative = status;
	}

	/**
	 * returns whether the number is negative
	 * @return
	 */
	public boolean isNegative() {
		return this.isNegative;
	}

	/**
	 * simple push method. adds number to the top of a stack. Uses linked list implemented features to push number
	 * @param num
	 */
	public void push(int num) {
		if (number.size() == 0) {  //The stack is empty
			number.push(num);
			isTop = true;  //The stack only has one value
		} else {
			number.push(num);
			isTop = false;  //The stack has more than one value
		}
	}

	/**
	 * Remove the top value of a stack, and returns it.
	 * @return
	 */
	public int pop() {
		if (number.size() == 0) // If the program tries to pop an empty list, error code -1 will let it know it	
		{                       // has reached the end of the list
			return -1;
		}
		if (number.size() == 1) // The list only has one value. That value will be pop leaving the list empty
		{
			isTop = true;  //The next value will be the only value left on the stack
			return number.pop();
		} else // Regular popping of a number
		{
			return number.pop();
		}
	}
	
	/**
	 * get the size of the linked list
	 * @return The size of the linked list
	 */
	public int getSize()
	{
		return number.size();
	}
}
