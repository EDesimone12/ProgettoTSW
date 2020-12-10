package control;

import model.Carrello;
import model.CarrelloDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/*Aggiungiamo il prodotto nel carrello della sessione o dell'utente */
@WebServlet("/ServletAggiungiCarrello")
public class ServletAggiungiCarrello extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        CarrelloDAO service= new CarrelloDAO();

        String e_mail= request.getParameter("e_mailCarrello").trim();
        String codProduct= request.getParameter("codProdCarrello").trim();
        String prezzo_con_simbolo= request.getParameter("PriceCarrello");
        float prezzo_effettivo = Float.parseFloat(prezzo_con_simbolo.substring(0,prezzo_con_simbolo.length()-3));

        if(session.getAttribute("utente")!= null){ //se è != null allora l'utente è loggato
            service.updateCarrello(e_mail,codProduct, prezzo_effettivo);
            session.setAttribute("successAdded",true);
            response.sendRedirect(".#productPosition");
        }else{//se l'utente non è loggato
            ArrayList<Carrello> carrelli=  new ArrayList<Carrello>();
            if(session.getAttribute("carrelli")!= null){
                carrelli=(ArrayList<Carrello>) session.getAttribute("carrelli");
            }
            Carrello nuovo = new Carrello("fittizio",codProduct,prezzo_effettivo);

            if( carrelli.contains(nuovo)){//Elemento già presente nel carrello in sessione,incrementa quantità
                Carrello aggiornato= carrelli.get(carrelli.indexOf(nuovo));
                aggiornato.addQuantita();
                carrelli.set(carrelli.indexOf(nuovo),aggiornato);
                session.setAttribute("carrelli",carrelli);
            }else{//Elemento non presente ,lo aggiungiamo al carrello in sessione
                carrelli.add(nuovo);
                session.setAttribute("carrelli",carrelli);
            }
            session.setAttribute("successAdded",true); //Valore per vedere se abbiamo aggiunto un prodotto al carrello
            response.sendRedirect(".#productPosition");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new MyException("Ops qualcosa è andato storto :(");
    }
}
