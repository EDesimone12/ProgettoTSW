<%@ page import="control.MyException" %>
<%@ page import="model.MediaDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Prodotto" %>
<%@ page import="model.ProdottoDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>


<jsp:include page="/WEB-INF/view/header.jsp">
    <jsp:param name="pageTitle" value="Area Admin"/>
</jsp:include>
<link type="text/css" rel="stylesheet" href="<c:url value="/css/admin.css"/>" />

<div id="wrapper-admin">

        <div id="adminContainer">
            <%
                String addFotoButton= request.getParameter("adminAddFotoButton");
                String updateProductButton= request.getParameter("adminProductButton");
                String addProductButton= request.getParameter("adminAddProductButton");

                if(addFotoButton!=null){ %> <!-- Inizio form aggiunta foto(media) -->
                    <form id="addFotoForm" method="post" action="ServletAdminUpdate" enctype="multipart/form-data">
                        <label for="fileImmagine">Caricare Immagine:</label>
                        <input type="file" name="fileImmagine" id="fileImmagine"><br>
                        <input type="submit" name="submitImmagine" id="submitImmagine" value="Invia">
                    </form>
               <% }else if(addProductButton!=null){ %><!-- Inizio form modifica prodotto -->
            <form id="addProductForm" method="post" action="ServletAdminUpdate" enctype="multipart/form-data">
                <label for="codAddProduct">Codice:</label>
                <input type="text" name="codAddProduct" id="codAddProduct" minlength="4"  maxlength="100" required >
                <label for="codAddProduct"> (4-100 caratteri) </label><br>

                <label for="nameAddProduct">Nome:</label>
                <input type="text" name="nameAddProduct" id="nameAddProduct" minlength="2"  maxlength="14" required>
                <label for="nameAddProduct">(2-14 caratteri)</label>    <br>

                <label for="marcaAddProduct">Marca:</label>
                <input type="text" name="marcaAddProduct" id="marcaAddProduct" minlength="2" maxlength="8" required >
                <label for="marcaAddProduct"> (2-8 caratteri) </label>    <br>

                <label for="scontoAddProduct">Sconto:</label>
                <input type="number" name="scontoAddProduct" id="scontoAddProduct" min="0" max="100">
                <label for="scontoAddProduct"> (0-100) </label>    <br>

                <label for="prezzoAddProduct">Prezzo:</label>
                <input type="number" name="prezzoAddProduct" id="prezzoAddProduct" step="0.01" min="0" required > <br>

                <label for="categoriaAddProduct">Categoria:</label>
                <input list="datalistAddCategoria" name="categoriaAddProduct" id="categoriaAddProduct" minlength="2" maxlength="50"> <br>
                <datalist id="datalistAddCategoria">
                    <%
                        ArrayList<String> categorie=  (ArrayList<String>) request.getServletContext().getAttribute("categorieContext");
                        for (String categoria:categorie) { %>
                    <option value="<%=categoria%>"> <%=categoria%></option>
                    <% } %>

                </datalist>

                <br>
                <label for="descrizioneAddProduct">Descrizione:</label>
                <textarea style="resize: none;" rows="4" cols="40" minlength="10"  maxlength="254" form="addProductForm" name="descrizioneAddProduct"
                          id="descrizioneAddProduct" required > </textarea> <br>

                <input type="file" name="fileAddProduct" id="fileAddProduct" required> <br>
                <input type="submit" name="submitAddProduct" id="submitAddProduct" value="Invia">
            </form>
               <% }else if(updateProductButton!=null){
                   ProdottoDAO serviceP= new ProdottoDAO();
                   Prodotto product=  serviceP.doRetrievebyCode(request.getParameter("adminProductCod"));
               %> <!-- Inizio form modifica prodotto-->
                    <form id="updateProductForm" method="post" action="ServletAdminUpdate" enctype="multipart/form-data">
                        <label for="codUpdateProduct">Codice:</label>
                        <input type="text" name="codUpdateProduct" id="codUpdateProduct" minlength="4"  maxlength="100"
                               value="<%=product.getCodice()%>" readonly> <br>

                        <label for="nameUpdateProduct">Nome:</label>
                        <input type="text" name="nameUpdateProduct" id="nameUpdateProduct" minlength="2"  maxlength="14"
                               value="<%= product.getNome() %>"required>
                        <label for="nameAddProduct">(2-14 caratteri)</label>    <br>

                        <label for="marcaUpdateProduct">Marca:</label>
                        <input type="text" name="marcaUpdateProduct" id="marcaUpdateProduct" minlength="2" maxlength="8"
                               value="<%= product.getMarca()%>"required >
                        <label for="marcaAddProduct"> (2-8 caratteri) </label>    <br>

                        <label for="scontoUpdateProduct">Sconto:</label>
                        <input type="number" name="scontoUpdateProduct" id="scontoUpdateProduct" min="0" max="100"
                               value="<%=product.getSconto()%>">
                        <label for="scontoUpdateProduct"> (0-100) </label>    <br>

                        <label for="prezzoUpdateProduct">Prezzo:</label>
                        <input type="number" name="prezzoUpdateProduct" id="prezzoUpdateProduct" step="0.01" min="0"
                               value="<%=product.getPrezzo()%>"required > <br>

                        <label for="categoriaUpdateProduct">Categoria:</label>
                        <input list="datalistUpdateCategoria" name="categoriaUpdateProduct" id="categoriaUpdateProduct"
                               value="<%=product.getCategoria()%>" minlength="2" maxlength="50"> <br>
                        <datalist id="datalistUpdateCategoria">
                            <%
                                ArrayList<String> categorie=  (ArrayList<String>) request.getServletContext().getAttribute("categorieContext");
                                for (String categoria:categorie) { %>
                                    <option value="<%=categoria%>"> <%=categoria%></option>
                               <% } %>

                        </datalist>

                        <br>
                        <label for="descrizioneUpdateProduct">Descrizione:</label>
                        <textarea style="resize: none;" rows="4" cols="40" minlength="10"  maxlength="254" form="updateProductForm" name="descrizioneUpdateProduct"
                                  id="descrizioneUpdateProduct"  required > <%=product.getDescrizione()%> </textarea> <br>

                        <input type="file" name="fileUpdateProduct" id="fileUpdateProduct" > <br>
                        <input type="submit" name="submitUpdateProduct" id="submitUpdateProduct" value="Modifica">
                        <input type="submit" name="submitRemoveProduct" id="submitRemoveProduct" value="Rimuovi">
                    </form>
             <%  } %>

        </div>  <!-- Fine admin product -->

</div><!-- Fine wrapper admin -->

<jsp:include page="/WEB-INF/view/footer.jsp">
    <jsp:param name="pageTitle" value="Area Admin"/>
</jsp:include>