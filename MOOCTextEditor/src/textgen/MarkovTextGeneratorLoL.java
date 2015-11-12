package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText) 
	{
		// TODO: Implement this method		
		String delims ="[ ]+";		// delimiters to parse string
		String [] sourceStrings = sourceText.split(delims);		
		/*
		if (sourceStrings.length == 0)
			throw new IllegalStateException("Trying to train on empty file.");
		*/
		starter = "";
		// for each word curWord in the source text
		for (String curWord : sourceStrings)	{
			//System.out.println(curWord);
			// check if starter is already a node in the list
			boolean nodeInList = false;
			ListNode nodeToProcess = new ListNode("");
			for (ListNode curNode : this.wordList)
				if (curNode.getWord().equals(starter))	{
					nodeInList = true;
					nodeToProcess = curNode;
					break;
				}
			//  if not in list, add a node to the list with "starter" as the node's word
			if (!nodeInList)	{
				nodeToProcess = new ListNode(starter);
				wordList.add(nodeToProcess);
			}
			// add curWord as a nextWord to the "starter" node
			nodeToProcess.addNextWord(curWord);
			starter = curWord;			
		}		
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    // TODO: Implement this method
		
		if (this.wordList.isEmpty())		{
			//throw new IllegalStateException("Trying to generate text before training on any text.");
			this.wordList.add(new ListNode(""));
			this.wordList.get(0).addNextWord("");
		}
		
		String generatedText = "";
		String starter = "";
		ListNode nodeToGenNextWord;
		
		// while you need more words
		for (int i = 0; i < numWords; ++i)	{
			// find the "node" corresponding to "starter" in the list,
			// if it doesn't exist, find "node" corresponding to the empty String "" (at index 0)
			nodeToGenNextWord = this.wordList.get(0);
			for (ListNode curNode : this.wordList)
				if (curNode.getWord().equals(starter))	{
					nodeToGenNextWord = curNode;
					break;
				}
			String randomNextWord = nodeToGenNextWord.getRandomNextWord(this.rnGenerator);			
			generatedText += randomNextWord + " ";	// add the random word to the "output"
			starter = randomNextWord;					// set "starter" to the random word 
		}
		
		return generatedText;
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		// TODO: Implement this method.
		this.wordList.clear();
		train(sourceText);
	}
	
	// TODO: Add any private helper methods you need here.
	
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		/*
		String textString3 = "hi there hi Leo";
		gen.train(textString3);
		System.out.println(gen);
		*/
		try	{		// try to generate text before training on any text
			String s = gen.generateText(20);
		}
		catch(Exception e)	{
			System.out.println(e);
		}

		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));	
		
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		// TODO: Implement this method
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
		int randNum = generator.nextInt(nextWords.size());
		return nextWords.get(randNum);
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


