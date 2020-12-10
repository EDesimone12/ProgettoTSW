package control;

import model.Utente;
import model.UtenteDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

/* Servlet per gestire la registrazione  , che valida prima i parametri inseriti dall'utente
 in caso siano adatti effettuiamo la registrazione */
@WebServlet("/servlet-registrazione")
public class ServletRegistrazione extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        throw new MyException("Ops qualcosa è andato storto :(");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getSession().getAttribute("utente") != null) {
            throw new MyException("Utente già loggato!");
        } else {
            UtenteDAO service_user = new UtenteDAO();
            String nome = req.getParameter("nome");
            String cognome = req.getParameter("cognome");
            String email = req.getParameter("emailText");
            String nascita = req.getParameter("nascita");
            String password = req.getParameter("password");
            String confirmpassword = req.getParameter("passwordConfirm");


            if (!(nome != null && nome.length() >= 3 && nome.matches("^[a-zA-Z\\s]+$"))) {
                throw new MyException("Nome non valido.");
            }

            if (!(cognome != null && cognome.length() >= 3 && cognome.matches("^[a-zA-Z\\s]+$"))) {
                throw new MyException("Cognome non valido.");
            }

            //Controllo che la password abbia, almeno un numero,una lettera maiuscola ed una minuscola e lunghezza >=8
            if (!(password != null && password.length() >= 8 && !password.toUpperCase().equals(password)
                    && !password.toLowerCase().equals(password) && password.matches(".*[0-9].*"))) {
                throw new MyException("Password non valida.");
            }

            if (!password.equals(confirmpassword)) {
                throw new MyException("Conferma la tua password correttamente.");
            }
            //Validiamo l'indirizzo email,anche complessi come un indirizzo del tipo: m.rossi10@studenti.unisa.it
            if (!(email != null && email.matches("[A-z0-9\\.\\+_-]+@[A-z0-9\\._-]+\\.[A-z]{2,6}"))) {
                throw new MyException("Email non valida.");
            }

            GregorianCalendar dataAttuale = new GregorianCalendar();
            if(  dataAttuale.get(Calendar.YEAR) - Integer.parseInt(nascita.substring(0,4)) < 18 ||
                    dataAttuale.get(Calendar.YEAR) - Integer.parseInt(nascita.substring(0,4)) > 120){
                System.out.println(dataAttuale.get(Calendar.YEAR) - Integer.parseInt(nascita.substring(0,3)));
                throw new MyException("Data non valida, utente minorenne o inesistente");
            }

            Utente user = new Utente(email, nome, cognome, nascita, password);
            service_user.updateUser(user);

            RequestDispatcher dispatcher =
                    req.getRequestDispatcher("WEB-INF/view/homepage.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
