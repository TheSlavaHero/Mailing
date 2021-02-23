package com.gmail.theslavahero.Mailing;

import com.gmail.theslavahero.Mailing.mail.EverydayMailThread;
import com.gmail.theslavahero.Mailing.mail.Mail;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.gmail.theslavahero.Mailing.database.DatabaseMethodsMail.addMail;
import static com.gmail.theslavahero.Mailing.mail.Mail.fromJson;

public class EmailServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html");
        String json = (String) request.getAttribute("attributeName");
        Mail mail = fromJson(json);
        addMail(mail);
        try {
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h1>" + "Your e-mail has been added" + "</h1>");
            out.println("<a href=\"index.jsp\">Add another e-mail</a>");
            out.println("</body></html>");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread thread = new Thread(new EverydayMailThread(), "mailthread");
        thread.setDaemon(true);
        if (!thread.isAlive()) {
            thread.start();
        }
    }
}
