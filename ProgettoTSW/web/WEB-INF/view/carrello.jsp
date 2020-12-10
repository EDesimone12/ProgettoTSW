<%@ page import="java.util.ArrayList" %>
<%@ page import="model.*" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>

<jsp:include page="/WEB-INF/view/header.jsp">
    <jsp:param name="pageTitle" value="Carrello"/>
</jsp:include>

<link type="text/css" rel="stylesheet" href="<c:url value="/css/carrello.css"/>" />

<div id="wrapper-carrello">
    <div id="carrelloBody">
        <%
            Utente utente= (Utente) session.getAttribute("utente");

            ArrayList<Carrello> carrelli = new ArrayList<Carrello>();
            String carta_di_credito="";

            if(utente!= null) { /* Utente loggato */
                CarrelloDAO serviceC = new CarrelloDAO();
                carrelli = serviceC.doRetrievebyEmail(utente.getE_mail());
                carta_di_credito=utente.getCarta_di_credito();
        %>
                <script>
                    /*Mostro la casella di testo  e modifico il pulsante se l'utente è loggato */
                    $(document).ready(function () {
                        $("#labelCarta").css("display","block");
                        $("#procediPagamentoCarta").css("display","block");
                        $("#procediPagamentoAcquisto").attr("value","Acquista");
                    });
                </script>
        <!-- Impossibile procedere all'acquisto se non ci sono prodotti -->
             <%
                 if(carrelli.isEmpty()){ %>
                     <script>
                         $(document).ready(function () {
                            $("#procediPagamentoAcquisto").prop("disabled",true);
                            $("#procediPagamentoAcquisto").css("background-color","red");
                            $("#procediPagamentoTotale").css("display","none");
                         });
                     </script>
                <% }else{ %>
                     <script>
                         $(document).ready(function () {
                             $("#procediPagamentoAcquisto").prop("disabled",false);
                             $("#procediPagamentoAcquisto").css("background-color","#4CAF50");
                             $("#procediPagamentoTotale").css("display","block");
                         });
                     </script>
                <%}%>

            <%}else{ /* Utente non loggato (Sessione) */
                    if(session.getAttribute("carrelli")!=null){
                        carrelli= (ArrayList<Carrello>) session.getAttribute("carrelli");
                    }
            %>
                <script>
                    /*Nascondo  la casella carta e modifico il pulsante  se l'utente non è loggato */
                    $(document).ready(function () {
                        $("#labelCarta").css("display","none");
                        $("#procediPagamentoCarta").css("display","none");
                        $("#procediPagamentoAcquisto").attr("value","Registrati");
                    });
                </script>
        <!-- Totale invisibile se non ci sono prodotti -->
        <%
            if(carrelli.isEmpty()){ %>
        <script>
            $(document).ready(function () {
                $("#procediPagamentoTotale").css("display","none");
            });
        </script>
        <% }else{ %>
        <script>
            $(document).ready(function () {
                $("#procediPagamentoTotale").css("display","block");
            });
        </script>
        <%}%>

        <%    } /* Fine - Utente non loggato (Sessione)  */
        for (Carrello carrello:carrelli) {
            ProdottoDAO serviceP= new ProdottoDAO();
            Prodotto current = serviceP.doRetrievebyCode(carrello.getCodProdotto());

        %>
            <div class="carrelloProduct">
                <div class="carrelloProductDivImg">
                    <img class="carrelloProductImg" width="128px" height="128px" src="<%= current.getImmagine()%>"
                         alt="<%= current.getNome()%> - <%= current.getMarca()%>">
                </div> <!-- FIne div Img -->
                <div class="carrelloProductDivAbout">
                    <h2 class="carrelloProductTitle"> <%= current.getNome()%> - <%= current.getMarca()%></h2>   <br>
                    <p class="carrelloProductPrice"> Prezzo:
                        <% if( current.getSconto()==0){ %>
                        <%= current.getPrezzo()%>€
                        <%} else{ %>
                        <s><%= current.getPrezzo()%>€ </s> &#x27A1; <!--- Html-Code freccia -> -->
                        <b style="color:red"> <%= current.getPrezzo()-((current.getPrezzo()*current.getSconto())/100)%>€ </b>
                        <% } %> - Quantità: <%=carrello.getQuantita()%>
                    </p>    <br>
                    <p class="carrelloProductDescription"> <%= current.getDescrizione()%> </p> <br>
                    <form class="carrelloRemove" method="get" action="ServletRemoveAdd">
                        <input type="hidden" class="carrelloProductCode" name="carrelloProductCode" value="<%=current.getCodice()%>">
                        <select class="carrelloProductNumberQt" name="carrelloProductNumberQt">
                            <% for (int i=1; i<=10; i++) { %>
                            <option value="<%=i %>"> <%= i%> </option>
                            <%} %>
                        </select>
                        <input type="submit" class="carrelloProductAdd" name="carrelloProductAdd" value="Aggiungi prodotto">
                        <input type="submit" class="carrelloProductRemove" name="carrelloProductRemove" value="Rimuovi prodotto">
                    </form> <br>
                </div><!-- Fine div Carrello Product About -->
            </div><!--Fine div Carrello product -->
        <%}%>

    </div><!-- Fine carrelloBody -->
    <div id="procediPagamento">
        <div id="procediPagamentoFattura">
            <%
                float totale=0;
                DecimalFormat df= new DecimalFormat(".##");
                for (Carrello carrello:carrelli) {
                    ProdottoDAO serviceP = new ProdottoDAO();
                    Prodotto current = serviceP.doRetrievebyCode(carrello.getCodProdotto());

            %>
                    <p class="procediPagamentoProduct">
                        <%= current.getNome()%> - <%= current.getMarca()%> x<%= carrello.getQuantita()%>
                        <b class="procediPagamentoProductPrice"> <%= df.format(carrello.getPrezzoEffettivo()*carrello.getQuantita())%>€ </b>
                    </p>
                        <% totale+= carrello.getPrezzoEffettivo()*carrello.getQuantita(); %>
               <% } %>

        </div> <!-- Fine Procedi Pagamento Fattura -->
        <p id="procediPagamentoTotale"> <b> Totale: <%= df.format(totale)%>€ </b> </p>
        <form method="get" action="ServletAcquisto" onsubmit="return validateAcquisto()">
            <label id="labelCarta" for="procediPagamentoCarta">Carta di Credito </label>
            <input type="text" id="procediPagamentoCarta" placeholder="(max 16 numeri)"
                   name="procediPagamentoCarta" onkeyup="validateCarta()"  value="<%=carta_di_credito%>">
            <input type="submit" id="procediPagamentoAcquisto" name="procediPagamentoAcquisto" value="Acquista">
        </form>
    </div> <!-- Fine procedi pagamento -->
</div><!-- Fine wrapper-carrello -->

<script>
    var isCartaValid=false;
    function validateCarta(){
        if(  $("#procediPagamentoCarta").val().match("^[0-9]+$") &&  $("#procediPagamentoCarta").val().length == 16 ){
            $("#labelCarta").css("color","#4CAF50");
            isCartaValid=true;
        }else{
            $("#labelCarta").css("color","FF0000");
            isCartaValid=false;
        }
    }
    function validateAcquisto(){
        validateCarta();
        if(isCartaValid || $("#procediPagamentoAcquisto").attr("value").localeCompare("Registrati")==0){
            return true;
        }else
            return false;
    }
</script>

<jsp:include page="/WEB-INF/view/footer.jsp">
    <jsp:param name="pageTitle" value="Carrello"/>
</jsp:include>