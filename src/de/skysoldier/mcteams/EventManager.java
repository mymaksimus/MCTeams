package de.skysoldier.mcteams;

import org.bukkit.ChatColor;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventManager implements Listener {
	
	private Main main;
	
	public EventManager(Main main){
		this.main = main;
		main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent e){
		Player p = e.getPlayer();
		main.addOnlinePlayer(p.getName());
		final PlayerTeam team = main.getOnlinePlayer(p.getName()).getTeam();
		if(team != null){
			p.setPlayerListName(team.getDisplayColor() + e.getPlayer().getName());
			p.setDisplayName(team.getDisplayColor() + "[" + team.getName() + "] " + Main.PLAYER_CHAT_COLOR + e.getPlayer().getName());
		}
		else {
			p.kickPlayer("New here? Please ask an admin for adding you to a team.");
		}
		team.addOnlinePlayer(e.getPlayer());
		main.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
			public void run() {
				team.setScoreboard(e.getPlayer());
			}
		}, 5);
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e){
		PlayerTeam team = main.getPlayerTeam(e.getPlayer().getName());
		main.removeOnlinePlayer(e.getPlayer().getName());
		team.removeOnlinePlayer(e.getPlayer());
	}
	
	@EventHandler 
	public void onChat(AsyncPlayerChatEvent e){
		e.setFormat("%1$s: %2$s");
		OnlinePlayer p = main.getOnlinePlayer(e.getPlayer().getName());
		if(p == null){
			e.getPlayer().kickPlayer("Something went wrong.");
			e.setCancelled(true);
			return;
		}
		if(e.getMessage().startsWith("!") && e.getMessage().length() > 1){
			main.getOnlinePlayer(e.getPlayer().getName()).getTeam().broadcast(e.getPlayer().getName(), e.getMessage().substring(1));
			e.setCancelled(true);
		}
		else {
			e.setMessage(Main.GLOBAL_CHAT_COLOR + e.getMessage());
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e){
		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player){
			if(main.getOnlinePlayer(((Player) e.getDamager()).getName()).getTeam().equals(main.getOnlinePlayer(((Player) e.getEntity()).getName()).getTeam())){
				e.setCancelled(true);
			}
		}
		if(e.getEntity() instanceof Cow){
			((Cow) e.getEntity()).setCustomName("" + ChatColor.DARK_BLUE + ChatColor.MAGIC + "....." + ChatColor.RESET + ChatColor.DARK_RED + " [Dad ADMIN] " + ChatColor.RESET + ChatColor.DARK_BLUE + ChatColor.MAGIC + ".....");
		}
	}
}
