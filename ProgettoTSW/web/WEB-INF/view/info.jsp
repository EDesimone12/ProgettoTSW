<%@ page import="java.util.ArrayList" %>
<%@ page import="model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>

<jsp:include page="/WEB-INF/view/header.jsp">
    <jsp:param name="pageTitle" value="Info"/>
</jsp:include>
<style>
    body{
        background-image: url(images/sfondo_registrazione.jpg);
        background-position: center;
    }
    header{
        position: relative;
    }
    div#wrapper-info{
        position: relative;
        min-height: 80%;
        width: 100%;
    }
    div#infoBody{
        position: absolute;
        top: 5%;
        left: 5%;
        right: 5%;
        bottom: 5%;
        background: white;
        border: 5px solid black;
        border-radius: 20px;
        opacity: 0.8;
    }
    div#infoBody h2{
        margin: 5%;
    }
    div#infoBody p#informazioniSito{
        text-align: justify;
        font-size: 20px;
        margin: 5%;
        word-break : break-word;
    }
</style>

<div id="wrapper-info">
    <div id="infoBody">
        <h2> Negozio Dello Buono</h2>
        <p id="informazioniSito"> Il Negozio DELLO BUONO è sul mercato dal 1988.
            Siamo specializzati nella vendita di Telefonia, Elettrodomestici, Articoli da Regalo e Bomboniere.
            Il nostro staff garantisce professionalità e cortesia nella scelta e la vendita di qualsiasi articolo
            della vasta gamma a disposizione nel nostro negozio. </p>
    </div>
</div>

<jsp:include page="/WEB-INF/view/footer.jsp">
    <jsp:param name="pageTitle" value="Info"/>
</jsp:include>