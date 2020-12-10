package control;

import model.MediaDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
/* Nel caso in cui l'utente voglia rimuovere una foto dalla galleria fa un sendRedirect a
    Galleria, altrimenti facciamo un forward
    alla jps in base al bottone premuto dall'admin */

@WebServlet("/ServletAdmin")
public class ServletAdmin extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MyException.checkAdmin(request);
        String removeFotoButton= request.getParameter("adminRemoveFotoButton");
        String addFotoButton= request.getParameter("adminAddFotoButton");
        String updateProductButton= request.getParameter("adminProductButton");
        String addProductButton= request.getParameter("adminAddProductButton");

        if(removeFotoButton!=null){
            String nameImg= request.getParameter("adminRemoveHidden");
            String pathImg= request.getParameter("adminRemovePathHidden");
            MediaDAO serviceM= new MediaDAO();
            serviceM.deleteByName(nameImg);

            //Eliminiamo il file anche dal server
            File fToDelete= new File(getServletContext().getRealPath("")+
                    File.separator+pathImg);
            fToDelete.delete();

            response.sendRedirect("Galleria");
        }else if(addFotoButton!=null){
            RequestDispatcher dispatcher =
                    request.getRequestDispatcher("WEB-INF/view/admin.jsp");
            dispatcher.forward(request, response);
        }else if(updateProductButton!=null){
            RequestDispatcher dispatcher =
                    request.getRequestDispatcher("WEB-INF/view/admin.jsp");
            dispatcher.forward(request, response);
        }else if(addProductButton!=null){
            RequestDispatcher dispatcher =
                    request.getRequestDispatcher("WEB-INF/view/admin.jsp");
            dispatcher.forward(request, response);
        }else{
            //L'utente è arrivato alla pagina senza aver clickato nessun bottone
            throw  new   MyException("Ops qualcosa è andato storto :(");
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new MyException("Ops qualcosa è andato storto :(");
    }
}
