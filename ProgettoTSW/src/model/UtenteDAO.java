package model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtenteDAO {
    public Utente doRetrieveByUsernamePassword(String e_mail, String password){ //Funzione per il login
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT e_mail,nome,cognome,data_nascita,password,admin,carta_di_credito FROM utente where e_mail=? and password=SHA1(?)");
            ps.setString(1,e_mail);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();
            Utente p = new Utente();
            if(rs.next()) {
                p.setE_mail(rs.getString(1));
                p.setNome(rs.getString(2));
                p.setCognome(rs.getString(3));
                p.setData_nascita(rs.getString(4));
                p.setPassword(rs.getString(5));
                if(rs.getString(6).equals("1")){
                    p.setAdmin(true);
                }else{
                    p.setAdmin(false);
                }
                if(rs.getString(7)!= null){
                    p.setCarta_di_credito(rs.getString(7));
                }else
                    p.setCarta_di_credito("");
            }
            if(p.getE_mail()==null){
                return null;
            }else{
                return p;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean doRetrieveByEmail(String e_mail){ //Funzione per il controllo se l'email è già presente
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT e_mail FROM utente where e_mail=?");
            ps.setString(1,e_mail);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
              return false;
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //Funzione per aggiornare la carta di credito di un utente
    public void updateCartaUser(String e_mail , String carta_di_credito){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("UPDATE utente set carta_di_credito=? where e_mail=?");
            ps.setString(1,carta_di_credito);
            ps.setString(2, e_mail);
            ps.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateUser(Utente u){ //Funzione che permette di inserire un utente all'interno del database
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("insert into  utente(e_mail,nome,cognome,data_nascita,password) " +
                                    "values (?,?,?,?,?)");
            ps.setString(1,u.getE_mail());
            ps.setString(2,u.getNome());
            ps.setString(3,u.getCognome());
            ps.setString(4,u.getData_nascita());
            ps.setString(5,u.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
