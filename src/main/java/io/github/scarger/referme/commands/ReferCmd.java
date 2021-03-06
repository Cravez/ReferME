package io.github.scarger.referme.commands;

import io.github.scarger.referme.ReferME;
import io.github.scarger.referme.events.ReferralEvent;
import io.github.scarger.referme.storage.PlayerStorage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Created by Synch on 2017-10-27.
 */
public class ReferCmd extends SubCommand {

    public ReferCmd(ReferME plugin){
        super(plugin,"refer","referme.referral",Arrays.asList("code"),true,true);
    }

    @Override
    void run(CommandSender sender, List<String> args) {
        int idCode;

        try {
            idCode = Integer.parseInt(args.get(0));
        }
        catch (NumberFormatException e){
            sender.sendMessage(getPlugin().getConfig().getPrefix()+
                    getPlugin().getConfig().getMessages().get("invalid-number"));
            return;
        }

        if(getPlugin().getStorage().getPlayers().getRaw().get(((Player) sender).getUniqueId()).getId() != idCode){
            tryReferral(sender,
                    getPlugin().getStorage().getPlayers().getRaw().values().stream()
                            .filter(p -> p.getId()==idCode).reduce((s,s2) -> s2).orElse(null));
        }
        else{
            sender.sendMessage(getPlugin().getConfig().getPrefix()+
                    getPlugin().getConfig().getMessages().get("self-referral"));
        }

    }

    @Override
    String getDescription() {
        return "Specify who brought you to the server (using their #id)";
    }


    private void tryReferral(CommandSender sender, PlayerStorage playerStorage){
        Player player = (Player) sender;

        if(playerStorage == null || playerStorage.getUUID() == null){
            sender.sendMessage(getPlugin().getConfig().getPrefix()+
                    getPlugin().getConfig().getMessages().get("incorrect-id"));
            return;
        }
        //call event w/ UUID
        UUID referralUUID = playerStorage.getUUID();
        ReferralEvent referralEvent = new ReferralEvent((Player) sender, Bukkit.getOfflinePlayer(referralUUID));

        //call the referral event
        Bukkit.getServer().getPluginManager()
                .callEvent(referralEvent);

        if(!(referralEvent.isCancelled())) {
            saveReferral(player.getUniqueId(),referralUUID);
        }
    }

    //saving method(to lower clutter..)
    private void saveReferral(UUID playerUUID, UUID referralUUID){
        getPlugin().getStorage().getPlayers()
                .getRaw().get(playerUUID).setReferrer(referralUUID);
    }
}
