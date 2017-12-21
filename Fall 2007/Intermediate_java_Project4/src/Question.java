
public class Question 
{
	String Qquestion;
	String Answer;
	int Value;
	int ID;

	Question()
	{
		this("","",0,0);
	}
	
	Question(String qquestion, String answer, int value, int id)
	{
		this.Qquestion = qquestion;
		this.Answer = answer;
		this.Value = value;
		this.ID = id;
	}
	
	public String getQuestion() {
		return Qquestion;
	}
	public void setQuestion(String question) {
		Qquestion = question;
	}
	public String getAnswer() {
		return Answer;
	}
	public void setAnswer(String answer) {
		Answer = answer;
	}
	public int getValue() {
		return Value;
	}
	public void setValue(int value) {
		Value = value;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
}
