package io.github.scarger.referme.listeners;

import io.github.scarger.referme.ReferME;
import io.github.scarger.referme.framework.PluginInjected;
import io.github.scarger.referme.storage.PlayerStorage;
import io.github.scarger.referme.storage.Storage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Synch on 2017-10-26.
 */
public class JoinListener extends PluginInjected implements Listener {

    public JoinListener(ReferME plugin) {
        super(plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        final Storage storage = getPlugin().getStorage();

        if(!(storage.getPlayers().getRaw().keySet().contains(event.getPlayer().getUniqueId()))){
            //#hacktoberfest
            PlayerStorage newPlayer = new PlayerStorage(storage.generateId());
            newPlayer.inject(getPlugin());
            //add to cache/storage..
            storage.getPlayers().put(event.getPlayer().getUniqueId(),newPlayer);
        }
    }
}
