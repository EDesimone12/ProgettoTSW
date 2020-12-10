package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MediaDAO {
    /***
     *
     * @return An ArrayList of all the media
     */
    public ArrayList<Media> doRetrieveAll(){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT nome, immagine  FROM media ");
            ResultSet rs = ps.executeQuery();
            ArrayList<Media> m = new ArrayList<>();
            while (rs.next()) {
                Media media_add = new Media(rs.getString(1),rs.getString(2));
                m.add(media_add);
            }
            return m;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteByName(String nome){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("DELETE    FROM media  where nome=?");
            ps.setString(1,nome);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doSave(String nome,String percorso){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("INSERT into media(nome,immagine)  values(?,?)");
            ps.setString(1,nome);
            ps.setString(2,percorso);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
