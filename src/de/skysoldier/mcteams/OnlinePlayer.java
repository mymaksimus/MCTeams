package de.skysoldier.mcteams;

public class OnlinePlayer {
	
	private PlayerTeam team;
	
	public OnlinePlayer(PlayerTeam team){
		this.team = team;
	}
	
	public PlayerTeam getTeam(){
		return team;
	}
}
