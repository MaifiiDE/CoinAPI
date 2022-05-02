package de.maifii.coinapi.listener;

import de.maifii.coinapi.CoinPlugin;
import de.maifii.coinapi.api.CoinAPIImpll;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        CoinAPIImpll coinAPIImpll = CoinPlugin.getInstance().getCoinAPIImpll();
        if(!coinAPIImpll.isUserExists(event.getPlayer().getUniqueId())) {
            coinAPIImpll.initPlayer(event.getPlayer().getUniqueId());
        }
    }
}
