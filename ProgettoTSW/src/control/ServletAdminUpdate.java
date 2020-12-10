package control;

import model.MediaDAO;
import model.Prodotto;
import model.ProdottoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

/*Servlet effettua gli aggiornamenti scelti dall'admin */
@WebServlet("/ServletAdminUpdate")
@MultipartConfig
public class ServletAdminUpdate extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MyException.checkAdmin(request);

        String insertImage= request.getParameter("submitImmagine");
        String insertProduct= request.getParameter("submitAddProduct");
        String updateProduct= request.getParameter("submitUpdateProduct");
        String removeProduct = request.getParameter("submitRemoveProduct");

        if(insertImage!=null){  //L'admin vuole "aggiungere" un immagine nella galleria
            Part filePart = request.getPart("fileImmagine"); //Prendiamo il parametro file(paragonabile a getParameter)
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();// Prendiamo il nome del file
            String path= getServletContext().getRealPath("")+File.separator+"galleria";

            File uploads = new File(path);
            int lenght= fileName.length(); //Lunghezza del Nome del file inserito
            File file = File.createTempFile(fileName.substring(0,lenght-4),fileName.substring(lenght-4,lenght), uploads);

            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, file.toPath() , StandardCopyOption.REPLACE_EXISTING);
            }catch (Exception e){
                e.printStackTrace();
            }

            int lastIndex= file.getAbsoluteFile().toString().lastIndexOf("\\")+1; //Inizio nome file finale
            int  totalLenght=file.getAbsoluteFile().toString().length();//Lunghezza path assoluto
            String finalFileName= file.getAbsolutePath().toString().substring(lastIndex,totalLenght); //Ricavo il nome effettivo del file

            MediaDAO serviceM= new MediaDAO();
            serviceM.doSave(finalFileName.substring(0,finalFileName.length()-4),"galleria/"+finalFileName);
            response.sendRedirect("Galleria");

        }else if(insertProduct!=null){ //L'admin vuole aggiungere un prodotto

            String codProduct= request.getParameter("codAddProduct");
            if ( !(codProduct!= null && codProduct.length() >= 4 && codProduct.matches("^[a-zA-Z0-9]+$")) ) {
                throw new MyException("Codice Prodotto non valido.");
            }

            String nameProduct= request.getParameter("nameAddProduct");
            if (!(nameProduct != null && nameProduct.length() >= 2 && nameProduct.matches("^[a-zA-Z0-9\\s]+$"))) {
                throw new MyException("Nome Prodotto non valido.");
            }

            String marcaProduct= request.getParameter("marcaAddProduct");
            if (!(marcaProduct != null && marcaProduct.length() >= 2 && marcaProduct.matches("^[a-zA-Z0-9]+$"))) {
                throw new MyException("Marca del Prodotto non valido.");
            }

            int scontoProduct;
            if(request.getParameter("scontoAddProduct").equals("") || request.getParameter("scontoAddProduct")==null){
                        scontoProduct=0;
            }else{
                if(request.getParameter("scontoAddProduct").matches("^[a-zA-Z]+$")){
                    throw new MyException("Sconto non valido");
                }
                scontoProduct= Integer.parseInt(request.getParameter("scontoAddProduct"));
                if ( !(scontoProduct >= 0 && scontoProduct <= 100)){
                    throw new MyException("Sconto  non valido.");
                }
            }

            float prezzoProduct;

            if(request.getParameter("prezzoAddProduct").matches("^[a-zA-Z]+$") || request.getParameter("prezzoAddProduct")==null){
                throw new MyException("Prezzo non valido");
            }else{
                prezzoProduct= Float.parseFloat(request.getParameter("prezzoAddProduct"));
                if( prezzoProduct < 0){
                    throw new MyException("Prezzo non valido.");
                }
            }

            String categoriaProduct= request.getParameter("categoriaAddProduct");
            if (!(categoriaProduct != null && categoriaProduct.length() >= 2 && categoriaProduct.length() <=50 && categoriaProduct.matches("^[a-zA-Z\\s]+$"))) {
                throw new MyException("Categoria non valida");
            }

            String descrizioneProduct= request.getParameter("descrizioneAddProduct");
            if (!(descrizioneProduct != null && descrizioneProduct.length() >= 10 &&  descrizioneProduct.length() <= 254)) {
                throw new MyException("Descrizione Prodotto non valida.");
            }

            /*
                ORA PRENDIAMO L'IMMAGINE DEL PRODOTTO

             */
            Part filePart = request.getPart("fileAddProduct"); //Prendiamo il parametro file(paragonabile a getParameter)
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();// Prendiamo il nome del file
            String path= getServletContext().getRealPath("")+File.separator+"images";

            File uploads = new File(path);
            int lenght= fileName.length(); //Lunghezza del Nome del file inserito
            File file = File.createTempFile(fileName.substring(0,lenght-4),fileName.substring(lenght-4,lenght), uploads);

            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, file.toPath() , StandardCopyOption.REPLACE_EXISTING);
            }catch (Exception e){
                e.printStackTrace();
            }

            int lastIndex= file.getAbsoluteFile().toString().lastIndexOf("\\")+1; //Inizio nome file finale
            int  totalLenght=file.getAbsoluteFile().toString().length();//Lunghezza path assoluto
            String finalFileName= file.getAbsolutePath().toString().substring(lastIndex,totalLenght); //Ricavo il nome effettivo del file

            ProdottoDAO serviceP= new ProdottoDAO();
            serviceP.doSave(codProduct,nameProduct,marcaProduct,"images/"+finalFileName,scontoProduct,
                    prezzoProduct,categoriaProduct,descrizioneProduct);

            //Aggiorniamo la lista categorie e prodotti del context
            ArrayList<Prodotto> prodotti= serviceP.doRetrieveAll();
            ArrayList<String> categorie= new ArrayList<String>();
            for (Prodotto prodotto: prodotti) {
                if(!categorie.contains(prodotto.getCategoria())){
                    categorie.add(prodotto.getCategoria());
                }
            }
            getServletContext().setAttribute("categorieContext", categorie);
            getServletContext().setAttribute("prodottiContext", prodotti);
            response.sendRedirect(".");

        }else if(updateProduct!=null){ // Modifichiamo il prodotto
            String codProduct= request.getParameter("codUpdateProduct");


            String nameProduct= request.getParameter("nameUpdateProduct");
            if (!(nameProduct != null && nameProduct.length() >= 2 && nameProduct.matches("^[a-zA-Z0-9\\s]+$"))) {
                throw new MyException("Nome Prodotto non valido.");
            }

            String marcaProduct= request.getParameter("marcaUpdateProduct");
            if (!(marcaProduct != null && marcaProduct.length() >= 2 && marcaProduct.matches("^[a-zA-Z0-9]+$"))) {
                throw new MyException("Marca del Prodotto non valida.");
            }

            int scontoProduct;
            if(request.getParameter("scontoUpdateProduct").equals("") || request.getParameter("scontoUpdateProduct")==null){
                scontoProduct=0;
            }else{
                if(request.getParameter("scontoUpdateProduct").matches("^[a-zA-Z]+$")){
                    throw new MyException("Sconto non valido");
                }
                scontoProduct= Integer.parseInt(request.getParameter("scontoUpdateProduct"));
                if ( !(scontoProduct >= 0 && scontoProduct <= 100)){
                    throw new MyException("Sconto  non valido.");
                }
            }

            float prezzoProduct;

            if(request.getParameter("prezzoUpdateProduct").matches("^[a-zA-Z]+$") || request.getParameter("prezzoUpdateProduct")==null){
                throw new MyException("Prezzo non valido");
            }else{
                prezzoProduct= Float.parseFloat(request.getParameter("prezzoUpdateProduct"));
                if( prezzoProduct < 0){
                    throw new MyException("Prezzo non valido.");
                }
            }

            String categoriaProduct= request.getParameter("categoriaUpdateProduct");
            if (!(categoriaProduct != null && categoriaProduct.length() >= 2 && categoriaProduct.length() <=50 && categoriaProduct.matches("^[a-zA-Z\\s]+$"))) {
                throw new MyException("Categoria non valida");
            }

            String descrizioneProduct= request.getParameter("descrizioneUpdateProduct");
            if (!(descrizioneProduct != null && descrizioneProduct.length() >= 10 &&  descrizioneProduct.length() <= 254)) {
                throw new MyException("Descrizione Prodotto non valida.");
            }

            /*
                ORA PRENDIAMO L'IMMAGINE DEL PRODOTTO

             */
            Part filePart = request.getPart("fileUpdateProduct"); //Prendiamo il parametro file(paragonabile a getParameter)
            String finalFileName="";
            ProdottoDAO serviceP= new ProdottoDAO();

            if(filePart.getSubmittedFileName().length()!=0){
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();// Prendiamo il nome del file
                String path= getServletContext().getRealPath("")+File.separator+"images";

                File uploads = new File(path); //Directory
                int lenght= fileName.length(); //Lunghezza del Nome del file inserito
                File file = File.createTempFile(fileName.substring(0,lenght-4),fileName.substring(lenght-4,lenght), uploads);

                //Prendo il vecchio file, da sostituire
                String oldPath= serviceP.doRetrievebyCode(codProduct).getImmagine();
                File fToDelete= new File(getServletContext().getRealPath("")+
                        File.separator+oldPath);
                //

                try (InputStream input = filePart.getInputStream()) {
                    Files.copy(input, file.toPath() , StandardCopyOption.REPLACE_EXISTING);
                }catch (Exception e){
                    e.printStackTrace();
                }

                //Eliminiamo prima la foto vecchia
                fToDelete.delete();
                //Abbiamo eliminato la foto vecchia

                int lastIndex= file.getAbsoluteFile().toString().lastIndexOf("\\")+1; //Inizio nome file finale
                int  totalLenght=file.getAbsoluteFile().toString().length();//Lunghezza path assoluto
                finalFileName= "images/"+file.getAbsolutePath().toString().substring(lastIndex,totalLenght); //Ricavo il nome effettivo del file

            }else{
                finalFileName= serviceP.doRetrievebyCode(codProduct).getImmagine();
            }


            serviceP.updateProduct(codProduct,nameProduct,marcaProduct,finalFileName,scontoProduct,
                    prezzoProduct,categoriaProduct,descrizioneProduct);

            //Aggiorniamo la lista categorie e prodotti del context
            ArrayList<Prodotto> prodotti= serviceP.doRetrieveAll();
            ArrayList<String> categorie= new ArrayList<String>();
            for (Prodotto prodotto: prodotti) {
                if(!categorie.contains(prodotto.getCategoria())){
                    categorie.add(prodotto.getCategoria());
                }
            }
            getServletContext().setAttribute("categorieContext", categorie);
            getServletContext().setAttribute("prodottiContext", prodotti);
            response.sendRedirect(".");

        }else if(removeProduct!=null){ // Rimuoviamo il prodotto
            ProdottoDAO serviceP= new ProdottoDAO();
            String codProduct= request.getParameter("codUpdateProduct");
            Prodotto toDelete= serviceP.doRetrievebyCode(codProduct);
            serviceP.deleteByCod(codProduct);

            //Eliminiamo l'immagine anche dal server
            if(toDelete==null){
                throw   new MyException("File non trovato ");
            }
            File fToDelete= new File(getServletContext().getRealPath("")+
                    File.separator+toDelete.getImmagine());
            fToDelete.delete();

            //Aggiorniamo la lista categorie e prodotti del context
            ArrayList<Prodotto> prodotti= serviceP.doRetrieveAll();
            ArrayList<String> categorie= new ArrayList<String>();
            for (Prodotto prodotto: prodotti) {
                if(!categorie.contains(prodotto.getCategoria())){
                    categorie.add(prodotto.getCategoria());
                }
            }
            getServletContext().setAttribute("categorieContext", categorie);
            getServletContext().setAttribute("prodottiContext", prodotti);

            response.sendRedirect(".");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new MyException("Ops qualcosa è andato storto :(");
    }
}
