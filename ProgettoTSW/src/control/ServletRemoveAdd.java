package control;

import model.Carrello;
import model.CarrelloDAO;
import model.Utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
/* Servlet del carrello usata per rimuovere o aumentare la quantità un prodotto al carrello  */
@WebServlet("/ServletRemoveAdd")
public class ServletRemoveAdd extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String addButton = request.getParameter("carrelloProductAdd");
        String removeButton= request.getParameter("carrelloProductRemove");
        HttpSession session = request.getSession();
        Utente user= (Utente) session.getAttribute("utente");
        String codProduct= request.getParameter("carrelloProductCode");

        if(user!= null){//Utente loggato
            CarrelloDAO serviceC= new CarrelloDAO();
            String e_mail= user.getE_mail();

            if(addButton!= null){
                int quantitaAdd= Integer.parseInt(request.getParameter("carrelloProductNumberQt"));
                serviceC.updateQta(e_mail,codProduct,quantitaAdd);

            }else if(removeButton!=null){
                serviceC.removeCarrello(e_mail,codProduct);
            }
        }else{//Utente NON looggato (Sessione)
            ArrayList<Carrello> carrelli= (ArrayList<Carrello>) session.getAttribute("carrelli");

            if(addButton!= null){
                int quantitaAdd= Integer.parseInt(request.getParameter("carrelloProductNumberQt"));
                for (Carrello carrello: carrelli) {
                    if(carrello.getCodProdotto().equals(codProduct)){ //Cerchiamo il carrello(prodotto) a cui aggiornare la quantità
                        quantitaAdd= quantitaAdd+carrello.getQuantita();
                        Carrello updated = carrelli.get(carrelli.indexOf(carrello));
                        updated.setQuantita(quantitaAdd);
                        carrelli.set(carrelli.indexOf(carrello),updated);
                    }
                }

            }else if(removeButton!=null){
                for(int i=0; i< carrelli.size(); i++){
                    if(carrelli.get(i).getCodProdotto().equals(codProduct)){
                        carrelli.remove(i);
                        i--;
                    }
                }
                if(carrelli.isEmpty()){
                    session.setAttribute("carrelli",null);
                }
            }
        }

        response.sendRedirect("Carrello");
    }
}
