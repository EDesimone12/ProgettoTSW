package control;

import model.Utente;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class MyException extends ServletException {
    public static void checkAdmin(HttpServletRequest request) throws MyException {
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        if (utente == null ||!utente.isAdmin()) {
            throw new MyException("Non hai i permessi per accedere a questa risorsa");
        }
    }
    public MyException() {
        super();
    }

    public MyException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

    public MyException(String message) {
        super(message);
    }

    public MyException(Throwable rootCause) {
        super(rootCause);
    }


}
