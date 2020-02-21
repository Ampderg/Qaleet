package edu.lwtech.csd299.topten;

import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class TopTenListDALTests {

    TopTenList topTenList;

    // Note: Cannot use @Before since we're testing a static class
    public void setUp() {
        TopTenListDAL.reset();

        List<String> items = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            items.add("Item " + i);

        TopTenListDAL.insertTopTenList(new TopTenList("Test List 1", items));
        TopTenListDAL.insertTopTenList(new TopTenList("Test List 2", items));
        topTenList = new TopTenList("Test List 3", items);
    }

    @Test
    public void resetTest() {
        setUp();
        TopTenListDAL.reset();
        assertEquals(0, TopTenListDAL.getNumLists());
    }

    @Test
    public void insertTopTenListTest() {
        setUp();
        TopTenListDAL.insertTopTenList(topTenList);
        assertEquals(3, TopTenListDAL.getNumLists());
    }
    
    @Test
    public void getTopTenListTest() {
        setUp();
        TopTenList theList = TopTenListDAL.getTopTenList(0);
        assertEquals(1000, theList.getId());
        theList = TopTenListDAL.getTopTenList(1);
        assertEquals(1001, theList.getId());
    }
    
    @Test
    public void getAllTopTenListsTest() {
        setUp();
        List<TopTenList> allLists = new ArrayList<>();

        allLists = TopTenListDAL.getAllTopTenLists();
        assertEquals(2, allLists.size());
    }    
    
    @Test
    public void getNumListsTest() {
        TopTenListDAL.reset();
        assertEquals(0, TopTenListDAL.getNumLists());
        setUp();
        assertEquals(2, TopTenListDAL.getNumLists());
        TopTenListDAL.insertTopTenList(topTenList);
        assertEquals(3, TopTenListDAL.getNumLists());
    }
    
    @Test
    public void getFirstListIDTest() {
        setUp();
        assertEquals(1000, TopTenListDAL.getFirstListID());
    }
    
    @Test
    public void generateNewListIDTest() {
        setUp();
        assertEquals(1002, TopTenListDAL.generateNewListID());
    }
    
}
