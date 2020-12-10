<%@ page import="control.MyException" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<jsp:include page="/WEB-INF/view/header.jsp">
    <jsp:param name="pageTitle" value="Registrazione"/>
</jsp:include>
<link type="text/css" rel="stylesheet" href="<c:url value="/css/registrazione.css"/>" />

<script src="js/scriptRegistrazione.js"></script>

<% if(request.getSession().getAttribute("utente") != null){
    throw new MyException(("Utente già loggato, effettua il logout!"));
}else{ %>
<div class="registrazione-wrapper">
    <div class="registrazione"> <!--Cellulare -->
            <form action="servlet-registrazione" method="post"  onsubmit="return validateForm()">
                <label for="nome">Nome:</label><br>
                <input type="text" id="nome" name="nome" required onkeyup="validateName()" > <br>
                <label for="cognome"> Cognome: </label><br>
                <input type="text" id="cognome" name="cognome" required onkeyup="validateSurname()" ><br>
                <label for="emailText">Email:</label><br>
                <input type="text" id="emailText" name="emailText" required onchange="validateMail()" > <br>
                <label for="nascita">Data di nascita:</label><br>
                <input type="date" id="nascita" name="nascita"   min="1900-01-01" required onblur="valiDate()"><br>
                <label for="passwordText"> Password:</label><br>
                <input type="password" id="passwordText" name="password" required onkeyup="validatePassword()" > <br>
                <label for="passwordConfirm"> Conferma Password:</label><br>
                <input type="password" id="passwordConfirm" name="passwordConfirm" required onkeyup="validatePasswordConfirm()"> <br>
            <input type="submit" id="registrati" name="registrati" value="registrati">
        </form>
    </div> <!-- Fine registrazione --->

    <div class="outputJquery">
        <p id="jname"> Inserire un Nome di almeno 3 caratteri </p>
        <p id="jcognome"> Inserire un Cognome di almeno 3 caratteri </p>
        <p id="jmail"> Inserire un indirizzo e-mail valido, non ancora usato!</p>
        <p id="jnascita"> Inserire data di nascita (Bisogna essere maggiorenni) </p>
        <p id="jpassword"> Inserire una Password contenente almeno 8 caratteri di cui un maiuscolo, un numero ed uno minuscolo </p>
        <p id="jpasswordConfirm"> Confermare la password! </p>
    </div>
</div><!-- Fine Wrapper registrazione --->
<% }%>

<jsp:include page="/WEB-INF/view/footer.jsp">
    <jsp:param name="pageTitle" value="Home"/>
</jsp:include>
