package de.skysoldier.mcteams;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class PlayerTeam {
	
	private ArrayList<String> players;
	private String name;
	private ChatColor displayColor;
	private Scoreboard board;
	private Team team;
	
	public PlayerTeam(String name, ChatColor displayColor){
		players = new ArrayList<>();
		this.name = name;
		this.displayColor = displayColor;
		this.board = Bukkit.getScoreboardManager().getNewScoreboard();
		this.team = board.registerNewTeam("test");
		this.team.setPrefix(displayColor + "[" + name + "] " + Main.TEAM_CHAT_COLOR);
		Objective o = board.registerNewObjective("countdown", "dummy");
		o.setDisplayName("countdown: ");
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		Score score = o.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Countdown: "));
		score.setScore(998);
	}
	
	public void addPlayer(String name){
		players.add(name);
	}
	
	public void addOnlinePlayer(Player player){
		team.addPlayer(player);
	}
	
	public void removeOnlinePlayer(Player player){
		team.removePlayer(player);
	}
	
	public void setScoreboard(Player player){
		player.setScoreboard(board);
	}
	
	public boolean hasPlayer(String name){
		return players.contains(name);
	}
	
	public void broadcast(String senderName, String message){
		for(String player : players){
			Player p = Bukkit.getPlayer(player);
			if(p != null){
				p.sendMessage(Main.TEAM_CHAT_COLOR + "! " + displayColor + "[TeamChat] " + Main.PLAYER_CHAT_COLOR + senderName + ": " + Main.TEAM_CHAT_COLOR + message);
			}
		}
	}
	
	public String getName(){
		return name;
	}
	
	public ChatColor getDisplayColor(){
		return displayColor;
	}
}
