package model;

import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.util.ArrayList;

public class CarrelloDAO {
    /***
     *
     * @param e_mail of the owner
     * @return The cart belonged to the owner
     */
    public ArrayList<Carrello> doRetrievebyEmail(String e_mail){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT e_mail , prodotto ,prezzo_effettivo , quantita" +
                            " from carrello " +
                            "where e_mail=?");
            ps.setString(1,e_mail);
            ResultSet rs =  ps.executeQuery();
            ArrayList<Carrello> carrello= new ArrayList<Carrello>();
            while(rs.next()){
                Carrello oggetto= new Carrello();
                oggetto.setE_mail(rs.getString(1));
                oggetto.setCodProdotto(rs.getString(2));
                oggetto.setPrezzoEffettivo(rs.getFloat(3));
                oggetto.setQuantita(rs.getInt(4));
                carrello.add(oggetto);
            }
            return carrello;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //Funzione che restituisce se è presente un Carrello con e_mail e prodotto uguali a e_mail e codProdotto
    public boolean doretrieveByE_mailAndCodP(String e_mail, String codProdotto){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("select e_mail , prodotto ,prezzo_effettivo , quantita" +
                            " from  carrello " +
                            "where e_mail=? and prodotto=? ");
            ps.setString(1,e_mail);
            ps.setString(2,codProdotto);
            ResultSet rs =  ps.executeQuery();

            if(rs.next()){
               return true; // La coppia  e_mail , prodotto è già presente
            }else {
                return false;
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //Funzione per aggiornare la quantità di un Carrello(Prodotto)
    public void updateQta(String e_mail, String codProdotto, int quantita) {
        try (Connection con = ConPool.getConnection()) {
                PreparedStatement ps =
                        con.prepareStatement("UPDATE carrello set quantita=quantita+? where " +
                                "e_mail=? and prodotto=? ");
                ps.setInt(1,quantita);
                ps.setString(2, e_mail);
                ps.setString(3, codProdotto);
                ps.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //Funzione per rimuovere un prodotto dal Carrello , quindi il Carrello con e_mail e codProdotto
    public void removeCarrello(String e_mail, String codProdotto){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("delete from carrello where " +
                            "e_mail=? and prodotto=? ");
            ps.setString(1, e_mail);
            ps.setString(2, codProdotto);
            ps.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //Funzione per svuotare il carrello all'acquisto
    public void removeCarrelloAll(String e_mail){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("delete from carrello where e_mail=?");
            ps.setString(1, e_mail);
            ps.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //Funzione per inserire un nuovo carrello o aumenta la quantità di 1 se già presente
    public void updateCarrello(String e_mail ,String codProdotto, float prezzo_effettivo){
        try (Connection con = ConPool.getConnection()) {
            if(this.doretrieveByE_mailAndCodP(e_mail,codProdotto)) {//Se la coppia mail prodotto è già presente
                PreparedStatement ps =
                        con.prepareStatement("UPDATE carrello set quantita=quantita+1 where " +
                                "e_mail=? and prodotto=? ");
                ps.setString(1,e_mail);
                ps.setString(2,codProdotto);
                ps.executeUpdate();
            }else {//se dobbiamo aggiungere un nuovo prodotto al carrello di e_mail
                PreparedStatement ps =
                        con.prepareStatement("insert into  carrello(e_mail,prodotto,prezzo_effettivo) " +
                                "values (?,?,?)");
                ps.setString(1,e_mail);
                ps.setString(2,codProdotto);
                ps.setFloat(3,prezzo_effettivo);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
