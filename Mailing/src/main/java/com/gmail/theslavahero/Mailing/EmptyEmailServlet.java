package com.gmail.theslavahero.Mailing;

import com.gmail.theslavahero.Mailing.mail.Mail;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import static com.gmail.theslavahero.Mailing.database.DatabaseMethods.getCurrencyArray;
import static com.gmail.theslavahero.Mailing.mail.Mail.toJson;

public class EmptyEmailServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("text/html");
        String receiver = request.getParameter("email");

        ArrayList<String> currencyList = getCurrencyArray();
        String currencies = "";
        StringBuilder sb = new StringBuilder(currencies);

        for (int i = 0;i < currencyList.size(); i++) {
            if (request.getParameter(currencyList.get(i)) != null) {
                sb.append(currencyList.get(i));
                sb.append(".");
            }
        }
        currencies = sb.toString();


        Mail mail = new Mail(receiver, currencies);
        String json = toJson(mail);
        try {

            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            if (currencies.equals("")) {
                out.println("Please check that you have chosen at least one currency. <br>");
                out.println("<a href=\"index.jsp\">Go Back</a>");
            } else {
                request.setAttribute("attributeName", json);
                RequestDispatcher rd = request.getRequestDispatcher("/Email");
                rd.forward(request, response);
            }
            out.println("</body></html>");

        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }


    }
}
