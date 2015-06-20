package web;

import ejb.NewsEntity;
import ejb.NewsEntityFacade;
import ejb.SessionManagerBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ListNews", urlPatterns = {"/ListNews"})
public class ListNews extends HttpServlet {
    @EJB
    private SessionManagerBean sessionManagerBean;
    @EJB
    private NewsEntityFacade newsEntityFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession(true);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ListNews</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListNews at " + request.getContextPath() + "</h1>");

            List news = newsEntityFacade.findAll();
            for (Iterator it = news.iterator(); it.hasNext();) {
                NewsEntity elem = (NewsEntity) it.next();
                out.println(" <b>" + elem.getTitle() + " </b><br />");
                out.println(elem.getBody() + "<br /> ");
            }
            out.println("<a href='PostMessage'>Add new message</a>");

            out.println("<br><br>");
            out.println(sessionManagerBean.getActiveSessionsCount() + " user(s) reading the news.");

            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
