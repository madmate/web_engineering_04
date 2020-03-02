import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;

@javax.servlet.annotation.WebServlet(name = "WebServlet")
public class WebServlet extends HttpServlet {

    private String message;

    public void init() throws ServletException {
        // Do required initialization

        message = "Hello World";
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();

        try {
            int timesAccessed = (Integer) session.getAttribute("Times Accessed");
            session.setAttribute("Times Accessed", ++timesAccessed);
        } catch(NullPointerException e) {
            session.setAttribute("Times Accessed", 1);
        }

        Enumeration<String> sessionAttributes = session.getAttributeNames();
        out.println("<table>");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th colspan=\"2\">Session</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tr>");
        out.println("<td>" + "sessionId" + "</td>" + "<td>" + session.getId() + "</td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td>" + "Creation Time" + "</td>" + "<td>" + new Date(session.getCreationTime()) + "</td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td>" + "Last Time Accessed" + "</td>" + "<td>" + new Date(session.getLastAccessedTime()) + "</td>");
        out.println("</tr>");
        while (sessionAttributes.hasMoreElements()) {
            String key = sessionAttributes.nextElement();
            out.println("<tr>");
            out.println("<td>" + key + "</td>" + "<td>" + session.getAttribute(key) + "</td>");
            out.println("</tr>");
        }
        out.println("</table>");

        Enumeration<String> headerNames = request.getHeaderNames();
        out.println("<table>");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th colspan=\"2\">Header Attributes</th>");
        out.println("</tr>");
        out.println("</thead>");
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            out.println("<tr>");
            out.println("<td>" + key + "</td>" + "<td>" + request.getHeader(key) + "</td>");
            out.println("</tr>");
        }
        out.println("</table>");
        out.println("<h1>" + message + "</h1>");
    }
}
