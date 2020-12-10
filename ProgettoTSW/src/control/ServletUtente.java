package control;

import model.Utente;
import model.UtenteDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/* Servlet associata al form nel header che ci permette di effettuare il login o la registrazione */
@WebServlet("/servlet-utente")
public class ServletUtente extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        throw new MyException("Ops qualcosa è andato storto :(");
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UtenteDAO serviceu = new UtenteDAO();
        HttpSession session = req.getSession(false);

        String loginButton = req.getParameter("accedi");
        String registrationButton = req.getParameter("registrati");
        Utente test= (Utente) session.getAttribute("utente");
        if(test==null){
            if (loginButton != null) {
                String email = req.getParameter("email");
                String password = req.getParameter("password");
                Utente user = serviceu.doRetrieveByUsernamePassword(email, password);
                req.setAttribute("utente", user);

                session.setAttribute("utente", user);
                if (user == null) {
                    session.setAttribute("AreWrongData", true); //SBAGLIATO IL LOGIN
                } else {
                    session.setAttribute("AreWrongData", false);
                }

                resp.sendRedirect(".");

            } else {
                RequestDispatcher dispatcher =
                        req.getRequestDispatcher("WEB-INF/view/registrazione.jsp");
                dispatcher.forward(req, resp);
            }
        }else{
            throw new MyException("Effettua il logout per registrarti!");
        }

    }

}
