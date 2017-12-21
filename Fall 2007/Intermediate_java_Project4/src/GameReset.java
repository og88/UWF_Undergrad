import java.io.IOException;

public class GameReset 
{
	/*This file resets the question bank, just delete Questions.bat then run the program*/
	public static void main(String[] args) throws IOException
	{
		reset();
	}
	public static void reset() throws IOException
	{
		Trivia_Game game = new Trivia_Game();
		int position;
		try
		{
			game.open("Questions.bat");
			Question q = new Question();
				q.setID(0);
				q.setQuestion("In 1975 an engineer created the first electronic camera while working for what company?");
				q.setValue(2);
				q.setAnswer("Kodak");
				game.write(q.getID(), q);
				
				q.setID(1);
				q.setQuestion("K-pop is a genre of music that originated in what country?");
				q.setValue(3);
				q.setAnswer("South Korea");
				game.write(q.getID(), q);
				
				q.setID(2);
				q.setQuestion("In 1867 the United States purchased Alaska from what country?");
				q.setValue(2);
				q.setAnswer("Russia");
				game.write(q.getID(), q);
				
				q.setID(3);
				q.setQuestion("When found on a beer bottle, what does the acronym IPA stand for?");
				q.setValue(2);
				q.setAnswer("India Pale Ale");
				game.write(q.getID(), q);
				
				q.setID(4);
				q.setQuestion("What is the name of the longest mountain range in North America?");
				q.setValue(2);
				q.setAnswer("Rocky Mountains");
				game.write(q.getID(), q);
				
				q.setID(5);
				q.setQuestion("What is the name of the official currency of Costa Rica?");
				q.setValue(4);
				q.setAnswer("Costa Rican Colon");
				game.write(q.getID(), q);
				
				q.setID(6);
				q.setQuestion("What is the highest enlisted rank a soldier can hold in the United States Army?");
				q.setValue(5);
				q.setAnswer("Sergeant Major of the Army");
				game.write(q.getID(), q);
				
				q.setID(7);
				q.setQuestion("Which major American airline is named after a greek letter?");
				q.setValue(2);
				q.setAnswer("Delta");
				game.write(q.getID(), q);
				
				q.setID(8);
				q.setQuestion("What is the name for a male bee that comes from an unfertilized egg?");
				q.setValue(2);
				q.setAnswer("Drone");
				game.write(q.getID(), q);
				
				q.setID(9);
				q.setQuestion("Jimmy Carter was the first U.S. president born in a what?");
				q.setValue(3);
				q.setAnswer("Hospital");
				game.write(q.getID(), q);
				
				q.setID(10);
				q.setQuestion("What do the letters in the acronym CD-ROM stand for?");
				q.setValue(2);
				q.setAnswer("Compact Disk Read Only Memory");
				game.write(q.getID(), q);
				
				q.setID(11);
				q.setQuestion("Which American author wrote the novel \"The Great Gatsby\", published in 1922?");
				q.setValue(2);
				q.setAnswer("F. Scott Fitzgerald");
				game.write(q.getID(), q);
				
				q.setID(12);
				q.setQuestion("Which actress plays the female lead in the American crime thriller television series \"The Blacklist\"?");
				q.setValue(4);
				q.setAnswer("Megan Boone");
				game.write(q.getID(), q);
				
				q.setID(13);
				q.setQuestion("Which American inventor is generally given credit for the invention of the lightning rod?");
				q.setValue(3);
				q.setAnswer("Benjamin Franklin");
				game.write(q.getID(), q);
				
				q.setID(14);
				q.setQuestion("Orphan Black is a sci-fi television series filmed in which country?");
				q.setValue(2);
				q.setAnswer("Cannada");
				game.write(q.getID(), q);
				
				q.setID(15);
				q.setQuestion("Which of the Beatles is barefoot on the Abby Road album cover?");
				q.setValue(2);
				q.setAnswer("Paul McCartney");
				game.write(q.getID(), q);
			
			for(int i = 0; i <= game.size(); i++)
			{
				position = game.find(i);
				if (position >= 0)
				{
					q = game.read(position);
					System.out.printf("Found :%s %s ID: %d Value: %d\n",q.getQuestion(), q.getAnswer(), q.getID(), q.getValue());		            
				}
				else
				{
					System.out.println("Could not find : " + i);		            

				}
			}
		}
		finally
		{
			game.close();
		}
	}
}
