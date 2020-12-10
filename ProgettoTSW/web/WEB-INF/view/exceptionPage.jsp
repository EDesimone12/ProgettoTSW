<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isErrorPage="true"%>
<jsp:include page="/WEB-INF/view/header.jsp">
    <jsp:param name="pageTitle" value="Errore"/>
</jsp:include>

<style>
    body{
        background-color: #3066be;
    }
    header{
        position: relative;
    }
    section{
        min-height: 70%;
    }
</style>

<section>
    <!-- Stampiamo il messaggio d'errore di MyException -->
    <h1><%= exception.getMessage() %></h1>
</section>

<jsp:include page="/WEB-INF/view/footer.jsp">
    <jsp:param name="pageTitle" value="Carrello"/>
</jsp:include>


