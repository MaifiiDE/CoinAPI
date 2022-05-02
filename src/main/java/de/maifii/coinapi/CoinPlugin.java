package de.maifii.coinapi;

import de.maifii.coinapi.api.CoinAPI;
import de.maifii.coinapi.api.CoinAPIImpl;
import de.maifii.coinapi.api.ICoinAPI;
import de.maifii.coinapi.database.MySQL;
import de.maifii.coinapi.listener.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class CoinPlugin extends JavaPlugin {
    private static CoinPlugin instance;

    private MySQL mySQL;

    private CoinAPIImpl coinAPI;

    public String host;

    public int port;

    public String database;

    public String user;

    public String password;

    public void onEnable() {
        instance = this;

        this.mySQL = MySQL.newBuilder().
                withUrl("127.0.0.1")
                .withPort(3306)
                .withDatabase("coinapi")
                .withUser("maifii")
                .withPassword("maifii").
                create();


        this.coinAPI = new CoinAPIImpl();
        this.coinAPI.createTables();
        CoinAPI.setAPI((ICoinAPI)this.coinAPI);
        Bukkit.getPluginManager().registerEvents((Listener)new PlayerJoinListener(), (Plugin)this);
    }

    public MySQL getMySQL() {
        return this.mySQL;
    }

    public CoinAPIImpl getCoinAPI() {
        return this.coinAPI;
    }

    public static CoinPlugin getInstance() {
        return instance;
    }
}
