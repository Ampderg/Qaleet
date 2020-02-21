package edu.lwtech.csd299.topten;

import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class TopTenListTests {

    TopTenList topTenList;

    @Before
    public void setUp() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            items.add("Item " + i);
        topTenList = new TopTenList(5, "Test List", items);
    }

    @Test
    public void getIdTest() {
        assertEquals(5, topTenList.getId());
    }

    @Test
    public void getTitleTest() {
        assertEquals("Test List", topTenList.getTitle());
    }

    @Test
    public void getItemsTest() {
        List<String> items = topTenList.getItems();
        for (String item : items)
            assertTrue(item.contains("Item "));
    }

    @Test
    public void toStringTest() {
        assertTrue(topTenList.toString().startsWith("5:"));
        assertTrue(topTenList.toString().contains("Test List"));
        assertTrue(topTenList.toString().contains("Item 0"));
        assertTrue(topTenList.toString().contains("Item 9"));
    }

}
