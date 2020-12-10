package model;

import java.util.Objects;

public class Carrello {
    private String e_mail;
    private String codProdotto;
    private float prezzoEffettivo;
    private int quantita;

    public String toString() {
        return "Carrello{" +
                "e_mail='" + e_mail + '\'' +
                ", codProdotto='" + codProdotto + '\'' +
                ", prezzoEffettivo=" + prezzoEffettivo +
                ", quantità="+ quantita+
                '}';
    }
    public String getE_mail() {
        return e_mail;
    }

    public String getCodProdotto() {
        return codProdotto;
    }

    public float getPrezzoEffettivo() {
        return prezzoEffettivo;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }
    public void addQuantita(){
        this.quantita++;
    }
    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public void setCodProdotto(String codProdotto) {
        this.codProdotto = codProdotto;
    }

    public void setPrezzoEffettivo(float prezzoEffettivo) {
        this.prezzoEffettivo = prezzoEffettivo;
    }

    public Carrello(){
    }
    public Carrello(String e_mail, String codProdotto, float prezzoEffettivo){
        this. e_mail= e_mail;
        this.codProdotto=codProdotto;
        this.prezzoEffettivo=prezzoEffettivo;
        this.quantita=1;
    }
    public Carrello(String e_mail, String codProdotto, float prezzoEffettivo, int quantita){
        this.e_mail = e_mail;
        this.codProdotto = codProdotto;
        this.prezzoEffettivo=prezzoEffettivo;
        this.quantita=quantita;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carrello carrello = (Carrello) o;
        return Objects.equals(this.getE_mail(), carrello.getE_mail()) &&
                Objects.equals(this.getCodProdotto(), carrello.getCodProdotto());
    }
}
