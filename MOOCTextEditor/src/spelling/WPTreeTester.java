/**
 * 
 */
package spelling;

//import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class WPTreeTester{
	WPTree wp;
	String word1, word2, word3, word4, word5;
	
	@Before
	public void setUp() throws Exception 	{
		wp = new WPTree();
		word1 = "time";
		word2 = "theme";
		word3 = "fffffffffffffff";
		word4 = "the";
		word5 = "kangaroo";
	}
	
	@Test
	public void testfindPath()	{
		List<String> path = wp.findPath(word1, word2);
		System.out.println(path);
		
		// test finding word that doesn't exist in dictionary
		path = wp.findPath(word1, word3);
		System.out.println(path);
		assertEquals("Finding word that doesn't exist in dictionary should return null", null, path);
		
		// test running for a long time
		path = wp.findPath(word4, word5);
		System.out.println(path);
	}
	

}

