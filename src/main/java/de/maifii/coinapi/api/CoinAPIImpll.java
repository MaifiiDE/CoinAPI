package de.maifii.coinapi.api;

import de.maifii.coinapi.CoinPlugin;
import de.maifii.coinapi.database.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CoinAPIImpll implements ICoinAPI{

    private MySQL mySQL;;

    @Override
    public Integer getCoins(UUID uuid) {
        String qry = "SELECT coins FROM coinapi WHERE uuid=?";
        try (ResultSet rs = mySQL.query(qry, uuid.toString())){
            if(rs.next()) {
               return rs.getInt("coins");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void addCoins(UUID uuid, int count) {
        int currentCoins = getCoins(uuid);
        String qry = "UPDATE coinapi SET coins=? WHERE uuid=?";
        mySQL.update(qry, currentCoins + count, uuid.toString());
    }

    @Override
    public void removeCoins(UUID uuid, int count) {
        int currentCoins = getCoins(uuid);
        String qry = "UPDATE coinapi SET coins=? WHERE uuid=?";
        if(currentCoins >= count) {
        mySQL.update(qry, currentCoins + count, uuid.toString());
        }
    }

    @Override
    public void setCoins(UUID uuid, int count) {
        String qry = "UPDATE coinapi SET coins=? WHERE uuid=?";
        mySQL.update(qry, count, uuid.toString());
    }

    public void initPlayer(UUID uuid) {
        mySQL.update("INSERT INTO coinapi (uuid, coins) VALUES (?,?)", uuid.toString(), 0);

    }

    public boolean isUserExists(UUID uuid) {
        String qry = "SELECT count(*) AS count FROM coinapi WHERE uuid=?";
        try (ResultSet rs = mySQL.query(qry, uuid.toString())){
            if(rs.next()) {
                return rs.getInt("count") != 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createTables() {
        this.mySQL = CoinPlugin.getInstance().getMySQL();
        mySQL.update("CREATE TABLE IF NOT EXISTS coinapi (uuid VARCHAR(36), coins INT(35))");

    }
}
