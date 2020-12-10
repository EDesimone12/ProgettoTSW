package control;

import model.Carrello;
import model.CarrelloDAO;
import model.Utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/*Servlet per gestire la risposta dell'utente al confirm js sulla richiesta di come modificare il carrello*/
@WebServlet("/ServletCarrelloAjax")
public class ServletCarrelloAjax extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(request.getParameter("rs_confirm")==null || request.getSession().getAttribute("utente")==null
            || request.getSession().getAttribute("carrelli")==null){
            throw new MyException("Accesso non valido");
        }

        boolean rs_confirm= Boolean.parseBoolean(request.getParameter("rs_confirm"));

        if(rs_confirm){//Aggiungiamo al carrello dell'account,  l'utente ha detto 'Ok'
            CarrelloDAO service= new CarrelloDAO();
            ArrayList<Carrello> carrelloSessione= (ArrayList<Carrello>) request.getSession().getAttribute("carrelli");
            for (Carrello carrello:carrelloSessione) {
                Utente utenteLoggato= (Utente)request.getSession().getAttribute("utente");
                String e_mail= utenteLoggato.getE_mail();
                String codProd= carrello.getCodProdotto();
                float prezzo_eff= carrello.getPrezzoEffettivo();

                for(int i=0; i<carrello.getQuantita(); i++){
                    service.updateCarrello(e_mail,codProd,prezzo_eff);
                }
            }
            request.getSession().setAttribute("carrelli" ,null);
            response.getWriter().append("true");
        }else{
            request.getSession().setAttribute("carrelli" ,null);
            response.getWriter().append("false");
        }
    }
}
