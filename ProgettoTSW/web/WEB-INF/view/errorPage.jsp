<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isErrorPage="true"%>
<jsp:include page="/WEB-INF/view/header.jsp">
    <jsp:param name="pageTitle" value="Errore - ${requestScope['javax.servlet.error.status_code']}"/>
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
    <h1>Errore ${requestScope['javax.servlet.error.status_code']}</h1>
    <!-- Eccezione generata-->
    <pre>${requestScope['javax.servlet.error.exception']}</pre>

</section>
<jsp:include page="/WEB-INF/view/footer.jsp">
    <jsp:param name="pageTitle" value="Carrello"/>
</jsp:include>

