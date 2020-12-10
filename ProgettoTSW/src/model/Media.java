package model;

public class Media {
    private String name; //conterrà il nome dell'immagine
    private String immagine; //Conterrà il percorso dell'immagine


    public Media(String name, String immagine) {
        this.name= name;
        this.immagine = immagine;

    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getImmagine() {
        return immagine;
    }
    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }
    @Override
    public String toString() {
        return "Media{" +
                "name='" + name + '\'' +
                ", immagine='" + immagine + '\'' +
                '}';
    }
}

