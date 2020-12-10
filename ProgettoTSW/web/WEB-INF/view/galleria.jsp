<%@ page import="java.util.ArrayList" %>
<%@ page import="model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>

<jsp:include page="/WEB-INF/view/header.jsp">
    <jsp:param name="pageTitle" value="Galleria"/>
</jsp:include>

<link type="text/css" rel="stylesheet" href="<c:url value="/css/galleria.css"/>" />
<script src="js/scriptGalleria.js"></script>

<div id="wrapper-galleria">
    <div id="mainPicture">
        <img id="mainPictureMedia"  src="images/logoDelloBuono.png">
    </div>  <!-- Fine mainPicture -->
    <%
        MediaDAO serviceM= new MediaDAO();
        ArrayList<Media> pictures= serviceM.doRetrieveAll();
        Utente utente = (Utente) session.getAttribute("utente");
        if(utente == null){
            utente= new Utente("fittizio");
        }
    %>

    <% if(utente.isAdmin()){ %>
    <form id="aggiunfiFotoForm" method="post" action="ServletAdmin">
        <input type="submit" id="adminAddFotoButton" name="adminAddFotoButton" value="Aggiungi Foto">
    </form>
    <%  } %>

    <div id="picturesContainer">
        <%
            for (Media picture: pictures) {%>

            <button class="pictureButton">
                <img class="pictureMedia" title="<%= picture.getName()%>" alt="<%= picture.getName()%>"
                     width="128px" height="128px" src="<%= picture.getImmagine()%>">

                <% if(utente.isAdmin()){ %>
                <form class="removeForm" method="post" action="ServletAdmin">
                    <input type="hidden" name="adminRemoveHidden" value="<%= picture.getName()%>">
                    <input type="hidden" name="adminRemovePathHidden" value="<%= picture.getImmagine()%>">
                    <input type="submit" class="adminRemoveFotoButton" name="adminRemoveFotoButton" value="X">
                </form>
                <%  } %>

            </button>

        <%  } %>
    </div>  <!--Fine picturesContainer -->
</div>  <!-- Fine Wrapper-Galleria -->

<jsp:include page="/WEB-INF/view/footer.jsp">
    <jsp:param name="pageTitle" value="Galleria"/>
</jsp:include>