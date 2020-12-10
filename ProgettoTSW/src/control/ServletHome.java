package control;

import model.Carrello;
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

/* Fa un forward alla jsp Home quando chiamata, mentre al primo avvio inizializza
    le informazioni necessarie al sito nel context*/

@WebServlet(name = "Home", urlPatterns="", loadOnStartup=1)
public class ServletHome extends HttpServlet {
    @Override
    public void init() throws ServletException {

        ProdottoDAO service= new ProdottoDAO();
        ArrayList<Prodotto> prodotti=service.doRetrieveAll();
        ArrayList<String> categorie= new ArrayList<String>();
        for (Prodotto prodotto: prodotti) {
            if(!categorie.contains(prodotto.getCategoria())){
                categorie.add(prodotto.getCategoria());
            }
        }
        getServletContext().setAttribute("categorieContext", categorie);
        getServletContext().setAttribute("prodottiContext", prodotti);
        super.init();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session==null){
            session= request.getSession(true);
        }
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("WEB-INF/view/homepage.jsp");
        dispatcher.forward(request, response);
    }
}
