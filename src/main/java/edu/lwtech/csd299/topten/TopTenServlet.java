package edu.lwtech.csd299.topten;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import org.apache.log4j.*;
import freemarker.core.*;
import freemarker.template.*;

@WebServlet(name = "TopTenServlet", urlPatterns = {"/list"}, loadOnStartup = 0)
public class TopTenServlet extends HttpServlet {

    private static final long serialVersionUID = 202001280001L;
    private static final Logger logger = Logger.getLogger(TopTenServlet.class);

    private static final String TEMPLATE_DIR = "/WEB-INF/classes/templates";
    private static final Configuration freemarker = new Configuration(Configuration.getVersion());

    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.warn("=========================================");
        logger.warn("  TopTenServlet init() started");
        logger.warn("    http://localhost:8080/topten/list");
        logger.warn("=========================================");

        logger.info("Getting real path for templateDir");
        String templateDir = config.getServletContext().getRealPath(TEMPLATE_DIR);
        logger.info("...real path is: " + templateDir);
        
        logger.info("Initializing Freemarker. templateDir = " + templateDir);
        try {
            freemarker.setDirectoryForTemplateLoading(new File(templateDir));
        } catch (IOException e) {
            logger.error("Template directory not found in directory: " + templateDir, e);
        }
        logger.info("Successfully Loaded Freemarker");
        
        createSampleTopTenLists();

        logger.warn("Initialize complete!");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("IN - GET " + request.getRequestURI());
        long startTime = System.currentTimeMillis();

        String command = request.getParameter("cmd");
        if (command == null) command = "showList";

        String template = "";
        Map<String, Object> model = new HashMap<>();
        String index = request.getParameter("index");
        int listIndex = (index == null) ? 0 : Integer.parseInt(index);

        switch (command) {

            case "createList":
                template = "createList.tpl";
                model.put("message", "");
                break;
                
            case "showList":
                int numLists = TopTenListDAL.getNumLists();
                int nextIndex = (listIndex + 1) % numLists;
                int prevIndex = listIndex - 1;
                if (prevIndex < 0) prevIndex = numLists-1;

                template = "showList.tpl";
                model.put("topTenList", TopTenListDAL.getTopTenList(listIndex));
                model.put("prevIndex", prevIndex);
                model.put("nextIndex", nextIndex);
                break;
                
            default:
                logger.debug("Unknown GET command received: " + command);

                // Send 404 error response
                try {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                } catch (IOException e)  {
                    logger.error("IO Error: ", e);
                }
                return;
        }
        processTemplate(response, template, model);

        long time = System.currentTimeMillis() - startTime;
        logger.info("OUT- GET " + request.getRequestURI() + " " + time + "ms");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("IN -POST " + request.getRequestURI());
        long startTime = System.currentTimeMillis();
        
        String command = request.getParameter("cmd");
        if (command == null) command = "";

        String confirmationMessage = "";
        String template = "confirmation.tpl";
        Map<String, Object> model = new HashMap<>();
        
        switch (command) {

            case "create":
                TopTenList newList = getTopTenListFromRequest(request);

                if (newList == null) {
                    logger.info("Create request ignored because one or more fields were empty.");
                    confirmationMessage = "Your new TopTenList was not created because one or more fields were empty.";
                } else {
                    insertTopTenList(newList);
                    confirmationMessage = "Your new TopTen List has been created successfully.";
                }
                break;
                
            default:
                logger.info("Unknown POST command received: " + command);

                // Send 404 error response
                try {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                } catch (IOException e)  {
                    logger.error("IO Error: ", e);
                }
                return;
        }

        model.put("confirmationMessage", confirmationMessage);
        
        processTemplate(response, template, model);

        long time = System.currentTimeMillis() - startTime;
        logger.info("OUT- GET " + request.getRequestURI() + " " + time + "ms");
    }
    
    @Override
    public void destroy() {
        logger.warn("-----------------------------------------");
        logger.warn("  TopTenServlet destroy() completed");
        logger.warn("-----------------------------------------");
    }

    @Override
    public String getServletInfo() {
        return "top-ten-list Sample Servlet";
    }

    // ========================================================================

    private void processTemplate(HttpServletResponse response, String template, Map<String, Object> model) {
        logger.debug("Processing Template: " + template);
        
        try (PrintWriter out = response.getWriter()) {
            Template view = freemarker.getTemplate(template);
            view.process(model, out);
        } catch (TemplateException | MalformedTemplateNameException | ParseException e) {
            logger.error("Template Error: ", e);
        } catch (IOException e) {
            logger.error("IO Error: ", e);
        } 
    }    

    private TopTenList getTopTenListFromRequest(HttpServletRequest request) {
        String title = request.getParameter("title");
        if (title == null || title == "") return null;
        
        List<String> items = new ArrayList<>();
        for (int i=10; i > 0; i--) {
            String item = request.getParameter("item" + i);
            if (item == null || item == "") return null;
            items.add(item);
        }
        
        TopTenList newList = new TopTenList(title, items);
        return newList;
    }

    private void createSampleTopTenLists() {
        logger.debug("Creating sample TopTenLists...");
        
        List<String> listMovies = new ArrayList<>( Arrays.asList(
                "10.) Airplane!",
                "9.) Hudsucker Proxy",
                "8.) Intolerable Cruelty",
                "7.) O Brother, Where Art Thou?",
                "6.) Spaceballs",
                "5.) High Anxiety",
                "4.) Blazing Saddles",
                "3.) Young Frankenstein",
                "2.) The Jerk",
                "1.) Life of Brian"
        ) );
        List<String> listBooks = new ArrayList<>( Arrays.asList(
                "10.) Lord of the Rings",
                "9.) Patriot Games",
                "8.) Pride and Prejudice",
                "7.) Waldens Pond",
                "6.) The Intel 8086 Processor Implemention Guide",
                "5.) Steve Jobs",
                "4.) Clear and Present Danger",
                "3.) A Brief History of Time",
                "2.) Red Storm Rising",
                "1.) Starship Trooper"
        ) );
        List<String> listCars = new ArrayList<>( Arrays.asList(
                "10.) VW Beetle",
                "9.) Lotus Esprit",
                "8.) Porsche 959",
                "7.) Porsche 911 RS Turbo",
                "6.) BWM M3",
                "5.) Ferrari La Ferrari",
                "4.) BWM 750i",
                "3.) Porsche 962",
                "2.) VW Thing",
                "1.) BMW M550i"
        ) );
        TopTenList topTenMovies = new TopTenList("My Top Ten Movies", listMovies);
        TopTenList topTenBooks = new TopTenList("My Top Ten Books", listBooks);
        TopTenList topTenCars = new TopTenList("My Top Ten Cars", listCars);
        
        TopTenListDAL.insertTopTenList(topTenMovies);
        TopTenListDAL.insertTopTenList(topTenBooks);
        TopTenListDAL.insertTopTenList(topTenCars);
        
        logger.info("...Top Ten Lists inserted");
    }

    private boolean insertTopTenList(TopTenList toptenlist) {
        logger.debug("Inserting " + toptenlist + "...");

        toptenlist = new TopTenList(toptenlist.getTitle(), toptenlist.getItems());
        TopTenListDAL.insertTopTenList(toptenlist);
        
        logger.debug("New TopTenList successfully inserted!");
        return true;
    }

}
