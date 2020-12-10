<%@ page import="model.Utente" %>
<%@ page import="java.lang.reflect.Array" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Carrello" %>
<%@ page import="model.CarrelloDAO" %>
<%--
  Created by IntelliJ IDEA.
  User: eugen
  Date: 18/05/2020
  Time: 12:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title> Negozio Dello Buono - ${param.pageTitle} </title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="./css/header.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="js/scriptHeader.js"></script>
</head>
<body>

<header>
    <%
        HttpSession session_check = request.getSession(false);
        Utente utente = null;
        boolean AreWrongData= false; //Valore per vedere se abbiamo appena provato a loggare
        if(session.getAttribute("utente")!=null){
            utente= (Utente) session.getAttribute("utente");
        }
        if(session.getAttribute("AreWrongData")!=null){
            AreWrongData= (boolean)session.getAttribute("AreWrongData");
        }
    %>
        <a href="."><img src="images/logoDelloBuono.png" class="logo" alt="logo"> </a>
        <nav id="schermo_piccolo">
                <button class="dropButtonPiccolo">Menu</button>
                <div class="dropdown-contentPiccolo">
                    <a href=".">Home</a>
                    <a href="Prodotti">Prodotti</a>
                    <a href="Galleria">Galleria</a>
                    <a href="Info">Info</a>
                    <a href="Carrello">Carrello</a>
                    <% if(utente==null){ %>
                    <button class="accediButton">Accedi</button>
                    <% }else{ %>
                    <div class="utenteview"> <!--NEL CASO IN CUI LOGGA CON DATI ESATTI-->
                        <p id="nomeCognomePiccolo">Benvenuto ${utente.nome}  ${utente.cognome}!</p> <br>
                        <form  id="utenteviewFormPiccolo" action="ServletLogout" method="post">
                            <input id="logoutPiccolo" type="submit" name="logout" value="Logout">
                        </form>
                    </div> <!--Fine rettangolo benvenuto utente -->
                    <% } %>
                </div><!-- Fine dropwon-Piccolo -->
        </nav><!--Fine nav schermo piccolo -->

        <nav id="schermo_grande">
            <ul class="nav__links">
                <li><a href=".">Home</a></li>
                <li><a href="Prodotti">Prodotti</a></li>
                <li><a href="Galleria">Galleria</a></li>
                <li><a href="Info">Info</a></li>
                <li>
                    <a href="Carrello">
                        <img src="images/shopping-cart.png" alt="carrello" style="width:24px;height:24px;border:0;">
                    </a>
                </li>
                <% if(utente==null){ %>
                <li>  <button class="accediButton">Accedi</button> </li>
                   <% }else{ %>
                <li class="utenteview"> <!--NEL CASO IN CUI LOGGA CON DATI ESATTI-->
                    <p id="nomeCognome">Benvenuto ${utente.nome}  ${utente.cognome}!</p> <br>
                    <form  id="utenteviewForm" action="ServletLogout" method="post">
                        <input id="logout" type="submit" name="logout" value="Logout">
                    </form>
                </li> <!--Fine rettangolo benvenuto utente -->
                  <% } %>
            </ul>
        </nav>

        <%
            if(utente == null ){
        %>
            <div class="dropdown-content">
                <div class="login"> <!-- Inizio LogIn   -->
                    <form action="servlet-utente" method="post">
                        <label for="email">Email:</label>
                        <input type="text" id="email" name="email"><br>
                        <label for="password">Password:</label>
                        <input type="password" id="password" name="password"><br>
                        <input type="submit"  name="accedi" id="login" value="Accedi">
                        <input type="submit" name="registrati" id="registrazione" value="Registrati">
                    </form>
                </div> <!-- Fine LogIn -->
            </div><!-- Fine dropwon-concent -->

         <%
                  if(AreWrongData==true){
          %>
             <script>
                 function errore_input(){
                     alert("Username e/o Password errati");
                 }
                 $(document).ready(errore_input());
              </script>
        <%
                    session.setAttribute("AreWrongData",false);
                  }
         }else{
            ArrayList<Carrello> carrelliSession= (ArrayList<Carrello>) request.getSession().getAttribute("carrelli");
            /* Se il carrello della sessione ha elementi, nel caso proponiamo
            l'aggiunta all'utente*/

            if(carrelliSession!=null ){
        %>
                <!-- Ajax per valutare quale carrello tenere -->
                <script>
                    function merge_carrello(){
                        var rs_confirm=confirm("Vuoi memorizzare il carrello pre-login?\nPremere 'OK' per aggiungere al tuo account ," +
                            " premi invece 'Annulla' per non salvarlo");
                        var xmlHttpReq = new XMLHttpRequest();
                        xmlHttpReq.onreadystatechange = function() { //funzione di callback
                            if (this.readyState == 4 && this.status == 200) {
                                if(this.responseText.localeCompare("true")==0){
                                    alert("Il carrello corrente è stato correttamente aggiunto all'account");
                                }else{
                                    alert("Il carrello dell'account non è stato modificato");
                                }
                            }
                        }
                        xmlHttpReq.open("GET", "ServletCarrelloAjax?rs_confirm="+rs_confirm , true);
                        xmlHttpReq.send();
                    }
                    $(document).ready(merge_carrello());
                </script>    <!-- Fine Ajax per valutare che carrello tenere -->
             <% }%>


     <%
        }
     %>
</header>