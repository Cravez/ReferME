package io.github.scarger.referme.storage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import io.github.scarger.referme.message.MessageDefault;
import io.github.scarger.referme.storage.type.JsonSerializable;
import org.bukkit.ChatColor;

import java.util.*;

/**
 * Created by Synch on 2017-11-01.
 */
public final class ConfigurationStorage implements JsonSerializable{
    private @Expose @Since(1.0) String prefix;
    private @Expose @Since(1.0) boolean autoChange;
    private @Expose @Since(1.0) int hourRequirement;

    private @Expose @Since(1.0) List<String> rewardCommands;
    private @Expose @Since(1.0) Map<Integer,List<String>> achievements;
    private @Expose @Since(1.0) Map<String,String> messages;

    public ConfigurationStorage(){
        final String[] DEFAULT_COMMANDS = {"me welcome %player%!", "me %referrer% gj for bringing %player%", "give %player% diamond"};
        //more init
        this.prefix = "&e[ReferME] &b";
        this.autoChange = true;
        this.hourRequirement = 3;
        this.rewardCommands =
                Arrays.asList(DEFAULT_COMMANDS);
        this.achievements = new HashMap<>();
        this.messages = new HashMap<>();
        //setup any defaults that can't be instantiated on instance creation
        populateDefaults();
    }

    public String getPrefix(){
        return ChatColor.translateAlternateColorCodes('&',prefix);
    }

    public boolean isAutoChange(){
        return autoChange;
    }

    public int getHourRequirement(){
        return hourRequirement;
    }

    public List<String> getRewardCommands(){
        return rewardCommands;
    }

    public Map<Integer,List<String>> getAchievements(){
        return achievements;
    }

    public Map<String,String> getMessages(){
        return messages;
    }

    private void populateDefaults(){
        //populate achievements
        achievements.put(10, Collections.singletonList("broadcast %player% has brought 10 people to the server, wow!"));
        //populate messages
        Arrays.stream(MessageDefault.values()).forEach(msg -> messages.put(msg.getKey(),msg.getValue()));

    }
}
