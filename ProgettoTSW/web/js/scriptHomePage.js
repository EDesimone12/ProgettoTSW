
$(document).ready(function () {
    //Funzione per lo spostamento dei prodotti da visualizzare
    $(".productButton").click(function slidedProduct(){
        //Togliamo il logo
        $(".productRightImgLogo").css("display","none");
        //Facciamo apparire gli elementi
        $("#right_inner_Flex_div").css("display","Flex");
        $(".productRightImg").css("display","block");
        $("#visualizedDescription").css("display","block");
        $("#visualizedTitlePrice").css("display","block");
        //Settiamo il focus sul div di giù(right inner div)
        if (window.matchMedia("(max-width: 795px)").matches) {
            $("body").scrollTop(550);
        }


        /*Sovrascriviamo il valore dell'attributo src di destra con quella clickata, utilizziamo l'elemento
         this per ricavare quella clickata ed accediamo al fratello img.productImg per prendere il valore di src */
        $(".productRightImg").attr("src",$(this).parent().parent().find("img.productImg").attr("src"));
        $(".productRightTitle").text($(this).parent().find("h3.productTitle").text());

        /*Sovrascriviamo il valore del paragrafo descrizione, di destra , con il paragrafo descrizione
         dell'elemento clickato */
        $(".productRightDescription").text($(this).parent().find("p.trueDescription").text());

        //Controlliamo se il prezzo è scontato o meno ed eseguiamo la stessa operazione fatta per la descrizione
        if($(this).parent().find("p.productPrice").find("b").text()===""){
            var length= $(this).parent().find("p.productPrice").text().length ;
            var prezzoEffettivo = $(this).parent().find("p.productPrice").text().substring(8,length).trim();

            $(".productRightPrice").text("Prezzo:"+ prezzoEffettivo);
            $("#PriceCarrello").attr("value",prezzoEffettivo); //Prezzo per il form a destra(hidden)
            $(".productRightPrice").css("color","black");
        }else{

            $(".productRightPrice").text("Prezzo:"+$(this).parent().find("p.productPrice").find("b").text());
            $("#PriceCarrello").attr("value",$(this).parent().find(".productPrice").find("b").text().trim()); //Prezzo per il form a destra(hidden)
            $(".productRightPrice").css("color","red");
        }
        //hidden values of the form
        $("#codProdCarrello").attr("value",$(this).parent().find(".productCod").text());
    });
});


