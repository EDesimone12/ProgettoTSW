package model;

import javax.servlet.annotation.WebServlet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdottoDAO {
    /***
     *
     * @return An ArrayList of all the product in the DB
     */
    public ArrayList<Prodotto> doRetrieveAll(){
            try (Connection con = ConPool.getConnection()) {
        PreparedStatement ps =
                con.prepareStatement("SELECT codice, nome, marca,immagine,sconto,prezzo,categoria,descrizione " +
                                            "FROM prodotto ");
        ResultSet rs = ps.executeQuery();
        ArrayList<Prodotto> p = new ArrayList<>();
        while (rs.next()) {
            Prodotto prodotto_add = new Prodotto(rs.getString(1),
                    rs.getString(2),rs.getString(3),
                    rs.getString(4),rs.getDouble(6),
                    rs.getString(7),rs.getString(8));
            prodotto_add.setSconto(rs.getInt(5));
            p.add(prodotto_add);
        }
        return p;
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}

    /***
     *
     * @param categoria Category for the research
     * @return An ArrayList of all the products belonging to the same category
     */
    public ArrayList<Prodotto> doRetrievebyCategoria(String categoria){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT codice, nome, marca,immagine,sconto,prezzo,categoria,descrizione " +
                                    "FROM prodotto where categoria=?");
            ps.setString(1,categoria);
            ResultSet rs =  ps.executeQuery();
            ArrayList<Prodotto> p = new ArrayList<>();
            while (rs.next()) {
                Prodotto prodotto_add = new Prodotto(rs.getString(1),
                        rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getDouble(6),
                        rs.getString(7),rs.getString(8));
                prodotto_add.setSconto(rs.getInt(5));
                p.add(prodotto_add);
            }
            return p;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Prodotto doRetrievebyCode(String codice){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT codice, nome, marca,immagine,sconto,prezzo,categoria,descrizione " +
                            "FROM prodotto where codice= ?;");
            ps.setString(1,codice);
            ResultSet rs =  ps.executeQuery();
            Prodotto p= new Prodotto();
            if(rs.next()) {
                p= new Prodotto(rs.getString(1),
                        rs.getString(2), rs.getString(3),
                        rs.getString(4),rs.getDouble(6),
                        rs.getString(7),rs.getString(8));
                p.setSconto(rs.getInt(5));
            }
            return p;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Prodotto> doRetrieveByNomeOrMarcaOrDescrizione(String value){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT codice, nome, marca,immagine,sconto,prezzo,categoria,descrizione " +
                            "FROM prodotto where MATCH(nome,marca,descrizione) AGAINST(?)");
            ps.setString(1,value);
            ResultSet rs =  ps.executeQuery();
            ArrayList<Prodotto> p = new ArrayList<>();
            while (rs.next()) {
                Prodotto prodotto_add = new Prodotto(rs.getString(1),
                        rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getDouble(6),
                        rs.getString(7),rs.getString(8));
                prodotto_add.setSconto(rs.getInt(5));
                p.add(prodotto_add);
            }
            return p;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doSave(String codice,String nome,String marca,String immagine,int sconto,float prezzo,String categoria,String descrizione){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("INSERT into prodotto (codice,nome,marca,immagine,sconto,prezzo,categoria,descrizione)" +
                            " values(?,?,?,?,?,?,?,?)");
            ps.setString(1,codice);
            ps.setString(2,nome);
            ps.setString(3,marca);
            ps.setString(4,immagine);
            ps.setInt(5,sconto);
            ps.setFloat(6,prezzo);
            ps.setString(7,categoria);
            ps.setString(8,descrizione);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProduct(String codice,String nome,String marca,String immagine,int sconto,float prezzo,String categoria,String descrizione){

        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("UPDATE prodotto set nome=? ,marca=?, immagine=?, sconto=? ," +
                            "prezzo=? , categoria=? , descrizione=? where codice=?");
            ps.setString(1,nome);
            ps.setString(2,marca);
            ps.setString(3,immagine);
            ps.setInt(4,sconto);
            ps.setFloat(5,prezzo);
            ps.setString(6,categoria);
            ps.setString(7,descrizione);
            ps.setString(8,codice);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteByCod(String codice){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("delete from prodotto where codice=?");
            ps.setString(1,codice);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
