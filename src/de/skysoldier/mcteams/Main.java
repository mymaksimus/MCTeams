package de.skysoldier.mcteams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public static ChatColor GLOBAL_CHAT_COLOR = ChatColor.DARK_AQUA;
	public static ChatColor TEAM_CHAT_COLOR = ChatColor.DARK_GREEN;
	public static ChatColor PLAYER_CHAT_COLOR = ChatColor.YELLOW;
	private HashMap<String, OnlinePlayer> onlinePlayers;
	private ArrayList<PlayerTeam> teams;
	
	public void onEnable(){
		new EventManager(this);
		onlinePlayers = new HashMap<>();
		teams = new ArrayList<>();
		FileConfiguration config = getConfig();
		Set<String> s = config.getConfigurationSection("teams").getKeys(false);
		for(String key : s){
			String currentPath = "teams." + key + ".";
			PlayerTeam team = new PlayerTeam(key, ChatColor.valueOf(((String)config.get(currentPath + "display_color")).toUpperCase()));
			List<?> l = config.getList(currentPath + "players");
			for(Object o : l){
				team.addPlayer((String) o);
			}
			teams.add(team);
		}
		if(!config.contains("GLOBAL_CHAT_COLOR")) config.set("GLOBAL_CHAT_COLOR", "DARK_AQUA");
		if(!config.contains("TEAM_CHAT_COLOR")) config.set("TEAM_CHAT_COLOR", "DARK_GREEN");
		if(!config.contains("PLAYER_CHAT_COLOR")) config.set("PLAYER_CHAT_COLOR", "YELLOW");
		GLOBAL_CHAT_COLOR = ChatColor.valueOf((String) config.get("GLOBAL_CHAT_COLOR"));
		TEAM_CHAT_COLOR = ChatColor.valueOf((String) config.get("TEAM_CHAT_COLOR"));
		PLAYER_CHAT_COLOR = ChatColor.valueOf((String) config.get("PLAYER_CHAT_COLOR"));
		saveConfig();
	}
	
	public void addOnlinePlayer(String name){
		onlinePlayers.put(name, new OnlinePlayer(getPlayerTeam(name)));
	}
	
	public void removeOnlinePlayer(String name){
		onlinePlayers.remove(name);
	}
	
	public OnlinePlayer getOnlinePlayer(String playerName){
		return onlinePlayers.get(playerName);
	}
	
	public PlayerTeam getPlayerTeam(String name){
		for(PlayerTeam t : teams){
			if(t.hasPlayer(name)) return t;
		}
		return null;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		return true;
	}
}
