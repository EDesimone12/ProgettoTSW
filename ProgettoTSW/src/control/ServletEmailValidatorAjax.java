package control;

import model.UtenteDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/* Servlet che utilizziamo per validare lato client la mail, quindi per vedere se è già presente*/
@WebServlet("/ServletEmailValidatorAjax")
public class ServletEmailValidatorAjax extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(request.getParameter("emailText")== null){
            throw new MyException("Ops qualcosa è andato storto :(");
        }

        String email= request.getParameter("emailText");

        UtenteDAO service= new UtenteDAO();
        if( service.doRetrieveByEmail(email) ){
            response.getWriter().append("true");
        }else{
            response.getWriter().append("false");
        }
    }
}
