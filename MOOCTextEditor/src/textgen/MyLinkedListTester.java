/**
 * 
 */
package textgen;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 *
 */
public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH =10; 

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Feel free to use these lists, or add your own
	    shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		//System.out.println(shortList);	
		emptyList = new MyLinkedList<Integer>();
		//System.out.println(emptyList);	
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++)
		{
			longerList.add(i);
		}
		//System.out.println(longerList);	
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);
		//System.out.println(list1);		
	}

	
	/** Test if the get method is working correctly.
	 */
	/*You should not need to add much to this method.
	 * We provide it as an example of a thorough test. */
	@Test
	public void testGet()
	{
		//test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {			
		}
		
		// test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));
		
		try {
			shortList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.get(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		// test longer list contents
		for(int i = 0; i<LONG_LIST_LENGTH; i++ ) {
			assertEquals("Check "+i+ " element", (Integer)i, longerList.get(i));
		}
		
		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
	}
	
	
	/** Test removing an element from the list.
	 * We've included the example from the concept challenge.
	 * You will want to add more tests.  */
	@Test
	public void testRemove()
	{
		int a = list1.remove(0);
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
		assertEquals("Remove: check size is correct ", 2, list1.size());
		
		// TODO: Add more tests here
		System.out.println("After removing 65 from list 1, list 1 is \n" + list1);
		
		// test removing out of bounds
		try	{
			list1.remove(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e)	{
		}
		try	{
			list1.remove(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e)	{
		}
		
		// try removing from empty list
		try	{
			emptyList.remove(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e)	{
		}
	}
	
	/** Test adding an element into the end of the list, specifically
	 *  public boolean add(E element)
	 * */
	@Test
	public void testAddEnd()
	{
        // TODO: implement this test
		shortList.add("C");
		assertEquals("Check newly added", "C", shortList.get(2));
		assertEquals("AddEnd: check size is correct ", 3, shortList.size());		
	}

	
	/** Test the size of the list */
	@Test
	public void testSize()
	{
		// TODO: implement this test
		assertEquals("testSize: check size is correct ", 2, shortList.size());
		assertEquals("testSize: check size is correct ", 0, emptyList.size());	
		assertEquals("testSize: check size is correct ", 10, longerList.size());	
		assertEquals("testSize: check size is correct ", 3, list1.size());	
	}

	
	
	/** Test adding an element into the list at a specified index,
	 * specifically:
	 * public void add(int index, E element)
	 * */
	@Test
	public void testAddAtIndex()
	{
        // TODO: implement this test
		shortList.add(0, "C");
		assertEquals("Check adding at head of list", "C", shortList.get(0));
		assertEquals("AddEnd: check size is correct ", 3, shortList.size());
		shortList.add(1, "D");
		assertEquals("Check adding at middle of list", "D", shortList.get(1));
		assertEquals("AddEnd: check size is correct ", 4, shortList.size());
		shortList.add(4, "E");
		assertEquals("Check adding at end of list", "E", shortList.get(4));
		assertEquals("AddEnd: check size is correct ", 5, shortList.size());
		
		// test adding out of bounds
		try {
			shortList.add(6, "E");
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {		
		}
		try {
			shortList.add(-1, "E");
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {		
		}
		
		// check adding to empty list
		emptyList.add(0, 3);
		assertEquals("Check adding to empty list", (Integer)3, emptyList.get(0));
		assertEquals("AddEnd: check size is correct ", 1, emptyList.size());
		
	}
	
	/** Test setting an element in the list */
	@Test
	public void testSet()
	{
	    // TODO: implement this test
		int a = list1.set(0, 63);
		assertEquals("Set: Check return element", 65, a);
		assertEquals("Set: Check if set is successful", (Integer)63, list1.get(0));
		
		// test out of bounds
		try	{
			list1.set(-1, 45);
			fail("Check out of bounds");
		}
		catch(IndexOutOfBoundsException e)	{			
		}
		try	{
			list1.set(3, 45);
			fail("Check out of bounds");
		}
		catch(IndexOutOfBoundsException e)	{			
		}
		
		// test setting null element
		try	{
			list1.set(0, null);
			fail("Check setting null element");
		}
		catch(NullPointerException e)	{			
		}
	    
	}
	
	
	// TODO: Optionally add more test methods.
	
}
