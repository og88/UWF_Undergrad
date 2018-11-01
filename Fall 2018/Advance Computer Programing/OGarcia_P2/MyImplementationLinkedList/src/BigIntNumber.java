public class BigIntNumber {
	boolean isNegative; //Boolean to see if the number is negative
	boolean isTop;
	int size;
	
	class number {  //node for list. Holds numerical value and reference to lower items in the stack
		int value;
		number next;
	}

	number top; //The top of the stack

	/**
	 * default class constructor
	 */
	BigIntNumber() {
		top = null;
		isNegative = false;
		size = 0;
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
	 * simple push method. adds number to the top of a stack
	 * @param num
	 */
	public void push(int num) {
		if (top == null) {  //The stack is empty
			top = new number();
			top.value = num;
			top.next = null;
			isTop = true;  //The stack only has one value
		} else {
			number tmp = new number();
			tmp.value = num;
			tmp.next = top;
			top = tmp;
			isTop = false;  //The stack has more than one value
		}
		size++;
	}

	/**
	 * Simpe pop method. Remove the top value of a stack, and returns it.
	 * @return
	 */
	public int pop() {
		if (top == null) // If the program tries to pop an empty list, error code -1 will let it know it
							// has reached the end of the list
		{
			return -1;
		}
		if (top.next == null) // The list only has one value. That value will be pop leaving the list empty
		{
			number tmp = top;
			top = null;
			isTop = true;  //The next value will be the only value left on the stack
			size--;
			return tmp.value;
		} else // Regular popping of a number
		{
			number tmp = top;
			top = tmp.next;  //next value is now the top of the stack
			size--;
			return tmp.value;
		}
	}

	public int getSize() {
		// TODO Auto-generated method stub
		return size;
	}
}
