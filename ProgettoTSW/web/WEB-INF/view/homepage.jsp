<%@ page import="model.ProdottoDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Prodotto" %>
<%@ page import="model.Utente" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>

<jsp:include page="/WEB-INF/view/header.jsp">
    <jsp:param name="pageTitle" value="Home"/>
</jsp:include>


<link type="text/css" rel="stylesheet" href="<c:url value="/css/home_product.css" />" />
<script src="js/scriptHomePage.js"></script>

    <a id="banner_ancor" href="https://www.windtre.it/offerte-smartphone/" target="_blank">
        <img id="banner_wind_1" src="images/banner_wind.png" alt="Promozioni Wind" width="100%">
    </a>
    <%
        Utente utente = (Utente) session.getAttribute("utente");
        if(utente == null){
            utente= new Utente("fittizio");
        }
        String defaultFilter= "Tutto";
        ArrayList<Prodotto> prodotti= new ArrayList<Prodotto>();
        if(session.getAttribute("isFilterUp")!=null && !session.getAttribute("isFilterUp").equals("Tutto")){
            defaultFilter= (String) session.getAttribute("isFilterUp");
            prodotti =  (ArrayList<Prodotto>)session.getAttribute("prodottiSessione"); //Prodotti per  la "pagina" prodotti

        }else{
            prodotti =  (ArrayList<Prodotto>)application.getAttribute("prodottiContext"); //Prodotti per  la "pagina" prodotti
        }

        ArrayList<String> categorie= (ArrayList<String>)application.getAttribute("categorieContext");  //Categorie per filtro
        if(session.getAttribute("successAdded")!= null && (boolean)session.getAttribute("successAdded")){%>
            <script>  function successAdded(){
                        alert("Prodotto aggiunto al carrello correttamente!");
                        <% session.setAttribute("successAdded",false); %>
                    }
                    window.setTimeout(successAdded,1500);//WAIT 1.5 secondi
           </script>
        <% }
        if(session.getAttribute("successBought")!= null && (boolean)session.getAttribute("successBought")){ %>
            <script>  function successBought(){
                alert("Acquisto effettuato con successo!");
                <% session.setAttribute("successBought",false); %>
            }
            window.setTimeout(successBought,1500);//WAIT 1.5 secondi
            </script>
      <% }%>

  <div id="wrapper-product">
            <span id="productPosition"></span>
            <div id="left_inner_div">
                <form id="form_filter"  action="ServletFilter" method="get">
                    <label for="search_filter"> Categoria: </label>
                    <select id="search_filter" name="filter"  onchange="this.form.submit();">    <!--- Filtro generato automaticamente -->
                        <option value="Tutto" > Tutto </option>
                        <% for (String categoria: categorie) { %>
                        <%
                            if(categoria.equals(defaultFilter)){ %>
                        <option value="<%=categoria %>" selected> <%=categoria %> </option>
                        <%        session.setAttribute("isFilterUp","Tutto");
                        }else{ %>
                        <option value="<%=categoria %>"> <%=categoria %> </option>
                        <%  } %>

                        <%} %>
                    </select>

                </form>
                <% if(utente.isAdmin()){ %>
                <form id="adminAddForm" method="post" action="ServletAdmin">
                    <input type="submit" id="adminAddProductButton" name="adminAddProductButton" value="Aggiungi prodotto">
                </form>
                <%  } %>

                <%
                 for (Prodotto prodotto: prodotti) { %>
                <div class="prodotto">

                    <div class="prodottoDivImg">
                        <img class="productImg" width="128px" height="132px" src="<%= prodotto.getImmagine()%>"
                             alt="<%=prodotto.getNome()%> - <%=prodotto.getMarca() %>">
                    </div><!--Fine prodotto div Img -->

                    <div class="prodottoDivAbout">
                        <h3 class="productTitle"> <%=prodotto.getNome()%> - <%=prodotto.getMarca() %> </h3>
                        <p class="productPrice"> Prezzo:
                            <% if( prodotto.getSconto()==0){ %>
                            <%= prodotto.getPrezzo()%>€
                            <%} else{ %>
                            <s><%= prodotto.getPrezzo() %>€ </s> &#x27A1; <!--- Html-Code freccia -> -->
                            <b style="color:red"> <%= prodotto.getPrezzo()-((prodotto.getPrezzo()*prodotto.getSconto())/100)%>€ </b>
                            <% } %>
                        </p>
                        <p class="productCod" hidden> <%=prodotto.getCodice()%> </p>
                        <p class="productDescription">
                            <% if(prodotto.getDescrizione().length()>=62){%>
                            <%= prodotto.getDescrizione().substring(0,62)+"..."%>
                            <% }else{%>
                            <%= prodotto.getDescrizione() %>
                            <%} %>
                        </p>
                        <p class="trueDescription"> <%= prodotto.getDescrizione() %></p>
                        <%
                            if(utente.isAdmin()){ %>
                        <form method="post" action="ServletAdmin">
                            <input type="hidden" class="adminProductCod" name="adminProductCod" value="<%=prodotto.getCodice()%>">
                            <input type="submit" class="adminProductButton" name="adminProductButton" value="Modifica">
                        </form>
                        <%  } %>

                        <button class="productButton"> Visualizza </button>
                    </div><!--Fine div prodotto -->
                </div><!--Fine prodotto div about -->
                    <% } %>
            </div> <!--Fine left inner div -->

            <div id="right_inner_div">
                    <img class="productRightImgLogo" src="images/logoDelloBuono.png">
                    <div id="right_inner_Flex_div">
                        <img class="productRightImg" width="256px" height="256px" src="">
                        <fieldset id="visualizedTitlePrice">
                            <h3 class="productRightTitle"> </h3>
                            <p class="productRightPrice"></p>

                            <form method="post" action="ServletAggiungiCarrello">
                                <input type="hidden" id="e_mailCarrello" name="e_mailCarrello" value="<%= utente.getE_mail()%>" >
                                <input type="hidden" id="codProdCarrello" name="codProdCarrello" value="" >
                                <input type="hidden" id="PriceCarrello" name="PriceCarrello" value="" >
                                <input type="submit" id="buttonAggiungiCarrello" value="Aggiungi al Carrello">
                            </form>
                        </fieldset>
                    </div> <!-- Fine right_inner_Flex_div -->


                    <fieldset id="visualizedDescription">
                        <h3> Descrizione:</h3>
                        <p class="productRightDescription">  </p>
                    </fieldset>
            </div><!-- Fine div right inner div -->
  </div><!--Fine div wrapper -->

<jsp:include page="/WEB-INF/view/footer.jsp">
    <jsp:param name="pageTitle" value="Home"/>
</jsp:include>


