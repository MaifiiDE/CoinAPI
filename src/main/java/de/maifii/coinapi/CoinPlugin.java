package de.maifii.coinapi;

import de.maifii.coinapi.api.CoinAPI;
import de.maifii.coinapi.api.CoinAPIImpll;
import de.maifii.coinapi.database.MySQL;
import de.maifii.coinapi.listener.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class CoinPlugin extends JavaPlugin {

    private static CoinPlugin instance;
    private MySQL mySQL;
    private CoinAPIImpll coinAPI;

    @Override
    public void onLoad() {
        instance = this;
        this.mySQL = MySQL.newBuilder().
                withUrl("127.0.0.1")
                .withPort(3306)
                .withDatabase("coinapi")
                .withUser("maifii")
                .withPassword("maifii").
                create();
    }

    @Override
    public void onEnable() {
        coinAPI = new CoinAPIImpll();;
        coinAPI.createTables();
        CoinAPI.setApi(coinAPI);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
    }

    @Override
    public void onDisable() {

    }


    public static CoinPlugin getInstance() {
        return instance;
    }

    public MySQL getMySQL() {
        return mySQL;
    }

    public CoinAPIImpll getCoinAPIImpll() {
        return coinAPI;
    }
}
