package edu.lwtech.csd299.topten;

import java.util.*;

import org.apache.log4j.Logger;

public class TopTenListDAL {
    
    private static final Logger logger = Logger.getLogger(TopTenListDAL.class.getName());
    
    private static int nextListId = 1000;
    private static List<TopTenList> memoryDB = new ArrayList<>();
    
    public static void reset() {
        nextListId = 1000;
        memoryDB = new ArrayList<>();
    }

    public static boolean insertTopTenList(TopTenList toptenlist) {
        logger.debug("Inserting " + toptenlist + "...");

        if (toptenlist.getId() != -1) {
            logger.error("Attempting to add previously added TopTenList: " + toptenlist);
            return false;
        }
        
        toptenlist = new TopTenList(generateNewListID(), toptenlist.getTitle(), toptenlist.getItems());
        memoryDB.add(toptenlist);
        
        logger.debug("TopTenList successfully inserted!");
        return true;
    }
    
    public static TopTenList getTopTenList(int index) {
        logger.debug("Trying to get TopTenList with index: " + index);
        
        TopTenList foundList = memoryDB.get(index);
        
        if (foundList != null) {
            logger.debug("Found list!");
        } else {
            logger.debug("Did not find list.");
        }
        
        return foundList;
    }
    
    public static List<TopTenList> getAllTopTenLists() {
        logger.debug("Getting all TopTenLists");
        return new ArrayList<>(memoryDB);
    }    
    
    public static int getNumLists() {
        return memoryDB.size();
    }
    
    public static int getFirstListID() {
        TopTenList list = memoryDB.get(0);
        return list.getId();
    }
    
    public static synchronized int generateNewListID() {
        return nextListId++;
    }
}
