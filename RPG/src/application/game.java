package application;

import java.util.InputMismatchException;
import java.util.Scanner;

public class game {
public static void main(String[] args)
{
	Scanner keyboard = new Scanner(System.in);
	int month = 0;
	double input = 0, totalincome = 0;
	while(input >= 0)
	{
		try {
			input = keyboard.nextDouble();
			totalincome += input;
			month++;
			System.out.println(totalincome);
		}
		catch(InputMismatchException e ) {
			System.out.println("Stuff");
			keyboard.nextLine();
			input = keyboard.nextDouble();
		}
	}
}
}
