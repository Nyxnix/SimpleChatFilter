package me.nyxnix.simplechatfilter;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class SimpleChatFilter extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.print("SimpleChatFilter has started!");
        getServer().getPluginManager().registerEvents(this, this);

        //Setup Config
        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }

    @EventHandler
    public void onPlayerMessage(AsyncPlayerChatEvent event){

        Player player = event.getPlayer();
        String playerMessage = event.getMessage();

        // Pulling strings from config file
        final List<String> words = getConfig().getStringList("words");

        // Main section that replaces swears with asterisks
        // Pulls strings from config file then if that string is present in the message
        // It will be replaced with asterisks
        for(String listItem : words){
            if(playerMessage.contains(listItem)){
                playerMessage = playerMessage.replace(listItem, "***");
                event.setMessage(playerMessage);
            }
        }
    }
}
