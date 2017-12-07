
public class SentenceTester {
	public static void main(String argv[])
	{
		Sentence s = new Sentence("The boy run is running");
		Boolean test = Sentence.compare(Sentence.sentence, "unn");
		int test2 = Sentence.indexOf("unn");
		System.out.println(test);
		System.out.println(test2);
		s = new Sentence("She sees seashells by the sea shore");
		test = Sentence.compare(Sentence.sentence, "unn");
		test2 = Sentence.indexOf("unn");
		System.out.println(test);
		System.out.println(test2);
		s = new Sentence("There are 4GiB addresses (for bytes) in 32 bits."
				+ " To manage a cluster of machines with more than 4GiB of RAM,"
				+ " each system must manage larger addresses.");
		test = Sentence.compare(Sentence.sentence, "4GiB");
		test2 = Sentence.indexOf("4GiB");
		System.out.println(test);
		System.out.println(test2);
	}
}
