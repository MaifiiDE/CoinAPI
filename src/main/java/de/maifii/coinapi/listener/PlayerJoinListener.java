package de.maifii.coinapi.listener;

import java.util.UUID;
import de.maifii.coinapi.CoinPlugin;
import de.maifii.coinapi.api.CoinAPI;
import de.maifii.coinapi.api.CoinAPIImpl;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        CoinAPI.getAPI().addCoins(event.getPlayer().getUniqueId(), 100);
        UUID uuid = event.getPlayer().getUniqueId();
        CoinAPIImpl coinsAPI = CoinPlugin.getInstance().getCoinAPI();
        if (!coinsAPI.isUserExists(uuid))
            coinsAPI.initPlayer(uuid);
    }
}
