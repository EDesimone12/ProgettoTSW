
$(document).ready( function(){ //La Utilizziamo per far comparire il piccolo div per il login
    $(".accediButton").click(function () {
        $(".dropdown-content").toggle();
        $("input[type=text] , input[type=password]").val("");
    })
    $("#login").click(function () {
        $(".dropdown-content").toggle();
    })
    $("#registrazione").click(function () {
        $(".dropdown-content").toggle();
    })

    //Menu schermo piccolo(media queries)
    $(".dropButtonPiccolo").click(function () {
        $(".dropdown-contentPiccolo").slideToggle();
    })
});




