package de.maifii.coinapi.api;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import de.maifii.coinapi.CoinPlugin;
import de.maifii.coinapi.database.MySQL;

public class CoinAPIImpl implements ICoinAPI {
    private MySQL mySQL;

    public Integer getCoins(UUID uuid) {
        String qry = "SELECT coins FROM coinapi WHERE uuid=?";
        try {
            ResultSet resultSet = this.mySQL.query(qry, new Object[] { uuid.toString() });
            try {
                if (resultSet.next()) {
                    Integer integer = Integer.valueOf(resultSet.getInt("coins"));
                    if (resultSet != null)
                        resultSet.close();
                    return integer;
                }
                if (resultSet != null)
                    resultSet.close();
            } catch (Throwable throwable) {
                if (resultSet != null)
                    try {
                        resultSet.close();
                    } catch (Throwable throwable1) {
                        throwable.addSuppressed(throwable1);
                    }
                throw throwable;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addCoins(UUID uuid, int amount) {
        int currentCoins = getCoins(uuid).intValue();
        String qry = "UPDATE coinapi SET coins=? WHERE uuid=?";
        this.mySQL.update(qry, new Object[] { Integer.valueOf(currentCoins + amount), uuid.toString() });
    }

    public void removeCoins(UUID uuid, int amount) {
        int currentCoins = getCoins(uuid).intValue();
        String qry = "UPDATE coinapi SET coins=? WHERE uuid=?";
        if (currentCoins >= amount)
            this.mySQL.update(qry, new Object[] { Integer.valueOf(currentCoins - amount), uuid.toString() });
    }

    public void setCoins(UUID uuid, int amount) {
        String qry = "UPDATE coinapi SET coins=? WHERE uuid=?";
        this.mySQL.update(qry, new Object[] { Integer.valueOf(amount), uuid.toString() });
    }

    public void initPlayer(UUID uuid) {
        this.mySQL.update("INSERT INTO coinapi (uuid, coins) VALUES (?,?)", new Object[] { uuid.toString(), Integer.valueOf(0) });
    }

    public boolean isUserExists(UUID uuid) {
        String qry = "SELECT count(*) AS count FROM coinapi WHERE uuid=?";
        try {
            ResultSet resultSet = this.mySQL.query(qry, new Object[] { uuid.toString() });
            try {
                if (resultSet.next()) {
                    boolean bool = (resultSet.getInt("count") != 0) ? true : false;
                    if (resultSet != null)
                        resultSet.close();
                    return bool;
                }
                if (resultSet != null)
                    resultSet.close();
            } catch (Throwable throwable) {
                if (resultSet != null)
                    try {
                        resultSet.close();
                    } catch (Throwable throwable1) {
                        throwable.addSuppressed(throwable1);
                    }
                throw throwable;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createTables() {
        this.mySQL = CoinPlugin.getInstance().getMySQL();
        this.mySQL.update("CREATE TABLE IF NOT EXISTS coinapi (uuid VARCHAR(40), coins INT(40))", new Object[0]);
    }
}
