package model;

public class Prodotto {
    private String codice;
    private String nome;
    private String marca;
    private String immagine; //Conterrà il percorso dell'immagine
    private int sconto;
    private double prezzo;
    private String categoria;
    private String descrizione;

    @Override
    public String toString() {
        return "Prodotto{" +
                "codice='" + codice + '\'' +
                ",Nome='" + nome + '\'' +
                ",Marca='" + marca + '\'' +
                ", immagine='" + immagine + '\'' +
                ", sconto=" + sconto +
                ", prezzo=" + prezzo +
                ", categoria='" + categoria + '\'' +
                ", descrizione='" + descrizione + '\'' +
                '}';
    }

    public String getCodice() {
        return codice;
    }
    public void setCodice(String codice) {
        this.codice = codice;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getImmagine() {
        return immagine;
    }
    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }
    public int getSconto() {
        return sconto;
    }
    public void setSconto(int sconto) {
        this.sconto = sconto;
    }
    public double getPrezzo() {
        return prezzo;
    }
    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public String getDescrizione() {
        return descrizione;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    public Prodotto(String codice, String nome, String marca, String immagine, double prezzo, String categoria, String descrizione){
        this.codice = codice;
        this.nome = nome;
        this.marca = marca;
        this.immagine = immagine;
        this.prezzo = prezzo;
        this.categoria = categoria;
        this.descrizione=descrizione;
    }
    public Prodotto(){
    }
}
