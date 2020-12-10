package model;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utente {
    private String e_mail;
    private String nome;
    private String cognome;
    private String data_nascita;
    private String password;
    private boolean admin;
    private  String  carta_di_credito;

    public String toString() {
        return "Utente{" +
                "e_mail='" + e_mail + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", data_nascita='" + data_nascita + '\'' +
                ", password='" + password + '\'' +
                ", admin=" + admin +
                ", carta_di_credito='" + carta_di_credito + '\'' +
                '}';
    }
    public String getE_mail() {
        return e_mail;
    }
    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCognome() {
        return cognome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    public String getData_nascita() {
        return data_nascita;
    }
    public void setData_nascita(String data_nascita) {
        this.data_nascita = data_nascita;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(password.getBytes(StandardCharsets.UTF_8));
            this.password = String.format("%040x", new BigInteger(1, digest.digest()));
    } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException(e);
        }
    }
    public boolean isAdmin() {
        return admin;
    }
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    public String getCarta_di_credito() {
        return carta_di_credito;
    }
    public void setCarta_di_credito(String carta_di_credito) {
        this.carta_di_credito = carta_di_credito;
    }

    public Utente(){
    }
    public Utente(String e_mail){
        this.e_mail=e_mail;  //Costruttore fittizio per la sessione
        this.admin=false;
    }
    public Utente(String e_mail, String nome, String cognome, String data_nascita, String password) {
        this.e_mail = e_mail;
        this.nome = nome;
        this.cognome = cognome;
        this.data_nascita = data_nascita;
        this.setPassword(password);
        this.carta_di_credito="";
    }
}
