package control;

import model.Prodotto;
import model.ProdottoDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/* Servlet che utilizziamo per filtrare i prodotti in base al filtro scelto */
@WebServlet("/ServletFilter")
public class ServletFilter extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String categoria= request.getParameter("filter");
        ProdottoDAO serviceP= new ProdottoDAO();
        HttpSession session = request.getSession();
        if(categoria.equals("Tutto")){
            session.setAttribute("isFilterUp","Tutto");
        }else
            session.setAttribute("isFilterUp",categoria);
            session.setAttribute("prodottiSessione",serviceP.doRetrievebyCategoria(categoria));

        response.sendRedirect(".#productPosition");
    }
}
