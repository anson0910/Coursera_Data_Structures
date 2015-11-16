package spelling;

import java.util.List;
import java.util.Set;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteDictionaryTrie()
	{
		this.root = new TrieNode();
		this.size = 0;
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should ignore the word's case.
	 * That is, you should convert the string to all lower case as you insert it. */
	public boolean addWord(String word)
	{
	    //TODO: Implement this method.
		word = word.toLowerCase();
		TrieNode curNode = this.root;
		Character curChar = '\0';
		int charIdxNoMatch = 0;	// first index of word that doesn't match word in dictionary
		while (charIdxNoMatch < word.length())	{
			curChar = word.charAt(charIdxNoMatch);
			if (curNode.getChild(curChar) == null)
				break;
			curNode = curNode.getChild(curChar);
			charIdxNoMatch++;
		}
		
		// if charIdxNoMatch reaches end of word, it is in dictionary
		if (charIdxNoMatch == word.length())	{
			if (curNode.endsWord())
				return false;
			else	{
				// or it is in a node, but not a word yet
				curNode.setEndsWord(true);
				this.size++;
				return true;
			}
		}
		
		while (charIdxNoMatch < word.length())	{
			curChar = word.charAt(charIdxNoMatch);
			curNode = curNode.insert(curChar);			
			charIdxNoMatch++;
		}
		curNode.setEndsWord(true);	// last node corresponds to inserted word
		this.size++;
		return true;
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    //TODO: Implement this method
	    return this.size;
	}
	
	
	/** Returns whether the string is a word in the trie */
	@Override
	public boolean isWord(String s) 
	{
	    // TODO: Implement this method
		if (s.length() == 0)
			return false;
		
		s = s.toLowerCase();
		TrieNode curNode = this.root;
		int charIdxNoMatch = 0;	// first index of word that doesn't match word in dictionary
		
		while (charIdxNoMatch < s.length())		{
			Character curChar = s.charAt(charIdxNoMatch);
			if (curNode.getChild(curChar) == null)
				return false;
			curNode = curNode.getChild(curChar);
			charIdxNoMatch++;
		}
		
		// if charIdxNoMatch reaches over end of word, it is in dictionary
		return true;
		
	}

	/** 
	 *  * Returns up to the n "best" predictions, including the word itself,
     * in terms of length
     * If this string is not in the trie, it returns null.
     * @param text The text to use at the word stem
     * @param n The maximum number of predictions desired.
     * @return A list containing the up to n best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // TODO: Implement this method
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
    	 
    	 int numCompletionsProvided = 0;
    	 // list of completions
    	 List<String> predictions = new LinkedList<String>();
    	 List<TrieNode> queue = new LinkedList<TrieNode>();
    	 TrieNode stem = new TrieNode();    	 
    	 prefix = prefix.toLowerCase();
    	 
    	 TrieNode curNode = this.root;
    	 Character curChar = '\0';
    	 int charIdxNoMatch = 0;
    	 while (charIdxNoMatch < prefix.length())	{
    		 curChar = prefix.charAt(charIdxNoMatch);
    		 if (curNode.getChild(curChar) == null)
    			 break;
    		 curNode = curNode.getChild(curChar);
    		 charIdxNoMatch++;
    	 }
		
		// if charIdxNoMatch doesn't match length of word, it is not in dictionary
		if (charIdxNoMatch != prefix.length())	
			return predictions;
		
		stem = curNode;
		queue.add(stem);
		while ( !queue.isEmpty() && numCompletionsProvided < numCompletions )	{
			curNode = queue.remove(0);
			if (curNode.endsWord())	{
				predictions.add(curNode.getText());
				numCompletionsProvided++;
			}
			// add all children to queue
			Set<Character> childrenChars = curNode.getValidNextCharacters();
			for (Character childChar : childrenChars)	
				queue.add(curNode.getChild(childChar));			
		}    	 
    	 
         return predictions;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}