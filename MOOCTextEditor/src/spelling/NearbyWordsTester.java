/**
 * 
 */
package spelling;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


/**
 * @author UC San Diego Intermediate MOOC team
 *
 */
public class NearbyWordsTester  {
	String word;
	String longWord;
	Dictionary dict;
	NearbyWords nearbyWords;
	List<String> listOfStrings;
	List<String> listOfLongStrings;
	List<String> suggests;
	@Before
	public void setUp() throws Exception {
		// word to test
		word = "i";
		longWord = "tailo";
		//longWord = "kangaro";
		// load dictionary
		dict = new DictionaryHashSet();
		DictionaryLoader.loadDictionary(dict, "data/dict.txt");
		nearbyWords = new NearbyWords(dict);
		listOfStrings = new ArrayList<String>();
		listOfLongStrings = new ArrayList<String>();
		suggests = new ArrayList<String>();
	}
	
	/** Test if the size method is working correctly.
	 */
	@Test
	public void testSubsitution()		{
		nearbyWords.subsitution(word, listOfStrings, true);
		System.out.println("One away word Strings by subsitution for \"" + word + "\" are:");
		System.out.println(listOfStrings + "\n");
		assertEquals("Number of strings that can appear for substitution is ", 25, listOfStrings.size());
		
		nearbyWords.subsitution(longWord, listOfLongStrings, true);
		System.out.println("One away word Strings by subsitution for \"" + longWord + "\" are:");
		System.out.println(listOfLongStrings + "\n");
	}
	
	@Test
	public void testInsertions()		{
		nearbyWords.insertions(word, listOfStrings, false);
		System.out.println("One away word Strings by insertion for \"" + word + "\" are:");
		System.out.println(listOfStrings + "\n");
		assertEquals("Number of strings that can appear for substitution is ", 51, listOfStrings.size());
		
		nearbyWords.insertions(longWord, listOfLongStrings, true);
		System.out.println("One away word Strings by insertion for \"" + longWord + "\" are:");
		System.out.println(listOfLongStrings + "\n");
	}
	
	@Test
	public void testDeletions()		{
		nearbyWords.deletions(word, listOfStrings, true);
		System.out.println("One away word Strings by deletion for \"" + word + "\" are:");
		System.out.println(listOfStrings + "\n");
		assertEquals("Number of strings that can appear for deletion is ", 0, listOfStrings.size());
		
		nearbyWords.deletions(longWord, listOfLongStrings, true);
		System.out.println("One away word Strings by deletion for \"" + longWord + "\" are:");
		System.out.println(listOfLongStrings + "\n");
	}
	
	@Test
	public void testSuggestions()		{
		suggests = nearbyWords.suggestions(longWord, 10);
		System.out.println("Spelling Suggestions for \"" + longWord + "\" are:");
		System.out.println(suggests + "\n");
	}

}
