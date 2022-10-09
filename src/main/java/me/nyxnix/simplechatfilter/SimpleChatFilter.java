package me.nyxnix.simplechatfilter;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String playerMessageOriginal = event.getMessage();

        // Pulling strings from config file
        final List<String> words = getConfig().getStringList("words");

        Pattern pattern = Pattern.compile(words.toString(), Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(playerMessage);
        boolean matchFound = matcher.find();

        if(matchFound) {
            for(String listItem : words) {
                int wordLen = listItem.length();
                String censorChar = "*".repeat(wordLen);
                playerMessage = playerMessage.toLowerCase().replace(listItem, censorChar);
                event.setMessage(playerMessage);
            }
            // log infraction to console
            System.out.println("Blacklisted word found! Player: "+player.getPlayerListName()+" Message: "+playerMessageOriginal);
        }
    }
}
