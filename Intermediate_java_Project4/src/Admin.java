import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;


public class Admin 
{
	public static void main(String[] args) throws IOException	
	{
		Scanner in = new Scanner(System.in);
		int response =0; 
		while(response != 4)
		{
			System.out.println("1. List all questions\n"
					+ "2. Delete question\n"
					+ "3. Add question\n"
					+ "4. Quit");
			response = in.nextInt();
			switch(response)
			{
			case 1:
				listQuestions();
				break;
			case 2:
				delete();
				break;
			case 3:
				addQuestion();
				break;
			case 4:
				break;
			default:
				System.out.println("Invalid Response");
			}
		}
		in.close();

	}
	
	public static void listQuestions() throws IOException
	{
		Trivia_Game game = new Trivia_Game();
		try
		{
			game.open("Questions.bat");
			game.list();
		}
		finally 
		{
			game.close();
		}	
	}
	
	public static void delete() throws IOException
	{
		Trivia_Game game = new Trivia_Game();
		Scanner in = new Scanner(System.in);
		Question q = new Question();
		try
		{
			game.open("Questions.bat");
			game.list();
			System.out.println("Which question would you like to remove");
			int x = in.nextInt();
			int position = game.find(x);
			if(position != -1)
			{
				q = game.read(position);
				q.setID(-1);
				game.write(position, q);
			}
		}
		finally 
		{
			game.close();
		}
	}

	public static void addQuestion() throws IOException
	{
		Scanner in = new Scanner(System.in);
		Question nq = new Question();
		Trivia_Game game = new Trivia_Game();
		int position;
		try
		{
			game.open("Questions.bat");
			nq.setID((game.size()+1));
			System.out.println("Please input the question");
			String response =in.nextLine();
			nq.setQuestion(response);
			System.out.println("Please input the answer");
			response =in.nextLine();
			nq.setAnswer(response);
			System.out.println("Please input the value of the question (1-5)");
			int val = in.nextInt();
			nq.setValue(val);
			//in.close();
			System.out.print(game.size());
			game.write(nq.getID(), nq);
		}
		finally
		{
			game.close();
		}
	}
}
