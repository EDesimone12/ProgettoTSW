
//Variabili globali per la validazione
var isNameValid=false;
var isSurnameValid=false;
var isEmailValid=false;
var isNascitaValid=false;
var isPasswordValid=false;
var isPasswordConfirmValid=false;

//Validazione Data
function setMaxDate() {
    var today = new Date();
    var dd = String(today.getDate()).padStart(2, '0'); // creiamo il formato giorno esempio: 01
    var mm = String(today.getMonth() + 1).padStart(2, '0'); // creiamo il formato mese  esempio: 01
    var yyyy = today.getFullYear() - 18;
    today = yyyy + '-' + mm + '-' + dd;
    document.getElementById("nascita").max = today;
}
window.onload = setMaxDate;

//Valida Nome
function validateName(){
        if(  $("#nome").val().match("^[a-zA-Z\\s]+$") &&  $("#nome").val().length >= 3 ){
            $("#jname").css({"color":"#4CAF50"});
            isNameValid=true;
        }else{
            $("#jname").css({"color":"FF0000"});
            isNameValid=false;
        }
}
//Valida Cognome
function validateSurname() {

    if(  $("#cognome").val().match("^[a-zA-Z\\s]+$") &&  $("#cognome").val().length >= 3 ){
        $("#jcognome").css("color","#4CAF50");
        isSurnameValid=true;
    }else{
        $("#jcognome").css("color","FF0000");
       isSurnameValid=false;
    }
}
//Valida Mail (anche collegamento Ajax)
function validateMail() {
    var xmlHttpReq = new XMLHttpRequest();
    var email= $("#emailText").val().match("[A-z0-9\\.\\+_-]+@[A-z0-9\\._-]+\\.[A-z]{2,6}");

    if(email.length==0){
        isEmailValid=false;
    }else{
        xmlHttpReq.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                if(this.responseText.localeCompare("true")==0){
                    $("#jmail").css("color","#4CAF50")
                    isEmailValid=true;
                }else{
                    $("#jmail").css("color","FF0000");
                    isEmailValid=false;
                }
            }
        }
        xmlHttpReq.open("GET", "ServletEmailValidatorAjax?emailText="+encodeURIComponent($("#emailText").val()) , true);
        xmlHttpReq.send();
    }

}
//Valida Data
function valiDate(){
    $("#jnascita").css("color","#4CAF50");
    isNascitaValid=true;
}
//Validate Password
function validatePassword() {
    var password= $("#passwordText").val();
    var passwordUpper= $("#passwordText").val().toUpperCase();
    var passwordLower= $("#passwordText").val().toLowerCase();
   if( password.length >= 8 && password.localeCompare(passwordUpper)!= 0 &&
       password.localeCompare(passwordLower)!= 0 &&
       password.match(".*[0-9].*")){

       $("#jpassword").css("color","#4CAF50");
       isPasswordValid=true;
   }else{
       $("#jpassword").css("color","#FF0000");
       isPasswordValid=false;
   }
}
//Controlliamo se la password e conferma sono uguali
function validatePasswordConfirm() {
if($("#passwordText").val().localeCompare($("#passwordConfirm").val())==0){
    $("#jpasswordConfirm").css("color","#4CAF50");
    isPasswordConfirmValid=true;
}else{
    $("#jpasswordConfirm").css("color","#FF0000");
    isPasswordConfirmValid=false;
}
}
//valida Form
function validateForm(){
    if( isNameValid && isSurnameValid && isEmailValid && isNascitaValid && isPasswordValid && isPasswordConfirmValid){
        alert("Registrazione avvenuta con successo! \n");
        return true;
    }
    else{
        alert("Compila il form correttamente seguendo le regole! \n");
        return false;
    }

}