import javax.servlet.ServletException;
import javax.servlet.http.*;
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
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Cookie cookie = new Cookie("user", username);
        cookie.setMaxAge(2592000);
        response.addCookie(cookie);
        PrintWriter out = response.getWriter();
        out.println(cookie.getValue());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"de\">");
        out.println("<head>");
        out.println("<meta charset=\"utf-8\" />");
        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />");
        out.println("<link rel='stylesheet' type='text/css' href=\"" + request.getContextPath() + "/resources/css/pure.css\" />");
        out.println("<title>WebServlet</title>");
        out.println("</head>");
        out.println("<body>");

        String username = "";
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies) {
            if (cookie.getName().equals("user")) {
                username = cookie.getValue();
                break;
            }
        }

        out.println("<form name=\"login\" method=\"post\" action=\"webservlet\">");
        out.println("Username: <input type=\"text\" name=\"username\" value=\"" + username + "\"/><br/>");
        out.println("Password: <input type=\"password\" name=\"password\"/><br/>");
        out.println("<input type=\"submit\" value=\"Login\" />");
        out.println("</form>");

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

        out.println("</body>");
        out.println("</html>");
    }
}
