package control;

import model.CarrelloDAO;
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
/* Servlet usata nel carrello per procedere all'acquisto oppure per registrarsi in caso l'utente non sia loggato*/
@WebServlet("/ServletAcquisto")
public class ServletAcquisto extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       String buttonValue= request.getParameter("procediPagamentoAcquisto");
       String carta_di_credito= request.getParameter("procediPagamentoCarta");


        HttpSession session = request.getSession();

       if(buttonValue.equals("Acquista")){
           /*Controlliamo se l'utente riesce ad arrivare in qualche modo
                al bottone "acquista" senza effettuare il login
            */
           if(session.getAttribute("utente")==null){
               throw new MyException("Accedere per poter acquistare!");
           }

           //Controlliamo la carta di credito
           if( !(carta_di_credito!= null &&  carta_di_credito.length()==16 && carta_di_credito.matches("^[0-9]+$"))  ){
               throw new MyException("Carta di credito non valida!");
           }

           Utente user = (Utente) session.getAttribute("utente");
           UtenteDAO serviceU = new UtenteDAO();
           CarrelloDAO serviceC= new CarrelloDAO();

           //Aggiorno l'utente nel database e nella sessione
           user.setCarta_di_credito(carta_di_credito);
           serviceU.updateCartaUser(user.getE_mail() ,carta_di_credito);
           session.setAttribute("utente",user);
           serviceC.removeCarrelloAll(user.getE_mail());

           session.setAttribute("successBought",true);//Valore per vedere se abbiamo acquistato correttamente
           response.sendRedirect(".");

       }else if(buttonValue.equals("Registrati")){
           /*Controlliamo se l'utente riesce ad arrivare in qualche modo
              al bottone "Registrati" nonostante sia loggato
            */
           if(session.getAttribute("utente")!=null){
               throw new MyException("Effettua il logout per registrarti!");
           }
           RequestDispatcher dispatcher =
                   request.getRequestDispatcher("WEB-INF/view/registrazione.jsp");
           dispatcher.forward(request, response);
       }
    }
}
