import java.io.IOException;
import java.io.RandomAccessFile;

public class Player 
{
	
	
	String name;
	String nickName;
	int totalScore;
	
	private static RandomAccessFile file;

	public static final int N_SIZE = 50;
	public static final int Nn_SIZE = 20;
	public static final int SCORE_SIZE = 4;
	public static final int RECORD_SIZE = N_SIZE + Nn_SIZE + SCORE_SIZE;
	
	Player()
	{
		this("","",0);
	}

	Player(String Name, String Nickname, int Score)
	{
		this.name = Name;
		this.nickName = Nickname;
		this.totalScore = Score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
	
	public String toString() //Returns the user information nicely formatted
	{
		return (getNickName() + ": Total Score: " + totalScore);
	}
	
	public  void open(String filename) throws IOException
	{
		if (file != null) //If a file is already opened, the program closes it first.
		{
			file.close();
		}
		file = new RandomAccessFile(filename, "rw"); //opens requested file
	}
	
	public void close() throws IOException
	{
		if (file != null) //If a file is opened, the program closes it first
		{
			file.close();
		}
		file = null; //Sets file to null
	}
	
	/*Retrieves the user information from a file*/
	public void read() throws IOException
	{
		file.seek(RECORD_SIZE);
		name = file.readUTF();
		nickName = file.readUTF();
		totalScore = file.readInt();
	}
	
	/*Stores user information*/
	public void write() throws IOException
	{
		file.seek(RECORD_SIZE);
		file.writeUTF(name);
		file.writeUTF(nickName);
		file.writeInt(totalScore);
	}
	
}

