package me.hammale.Sewer;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.InGameHUD;
import org.getspout.spoutapi.gui.PopupScreen;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SewerCommandListener implements CommandExecutor {
    
	private final SewerGenerator sewergen = new SewerGenerator();
	
	private SewerLocate loc;
	
	private Sewer plugin;
 
	public SewerCommandListener(Sewer plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		Player p = null;
		if (sender instanceof Player) {
			p = (Player) sender;
		}
		
		if (command.getName().equalsIgnoreCase("sewer")) {
			
			if(args.length == 1){
			String arg = args[0];
			if(arg.equalsIgnoreCase("create")){		
			if (p == null) {
				sender.sendMessage("[Sewers] This command can only be run by a player!");
			} else if (p.isOp()) {
				p.sendMessage(ChatColor.GREEN + "Generating Sewer...");
				Block b = p.getLocation().getBlock();
				b.setType(Material.AIR);
				Material m = (Material.SMOOTH_BRICK);
				BlockFace bf = BlockFace.NORTH;
				Block set2 = b;
				//sewergen.start1(set2, m, bf);
				sewergen.apreview(set2, m, bf);
				//nest.nest1(set2, m, bf);
				//bridge.nsbridge(set2, m, bf);
				//cavein.cavein1(set2, m, bf);
				//grave.grave(set2, m, bf);
				p.sendMessage(ChatColor.GREEN + "Sewer Complete!");
										
			return true;
			}
			else {
				sender.sendMessage("This command can only be run by an OP!");
			}
		}
			
			if(arg.equalsIgnoreCase("locate")){
				if (p == null) {
					sender.sendMessage("This command can only be run by a player!");
				} else if (p.isOp()) {
					p.sendMessage(ChatColor.GREEN + "Locating Sewer...");
					
					Location l = loc.findSewer(p.getWorld(), p);
					if(l != null){
					p.sendMessage(ChatColor.GREEN + "Closest sewer located @: " + ChatColor.BLUE + l.getX()+ ChatColor.GREEN + ", " + ChatColor.YELLOW + l.getY()+ ChatColor.GREEN + ", " + ChatColor.RED + l.getZ());				
					}else{
						p.sendMessage(ChatColor.RED + "No Sewer's found!");
					}
				return true;
				}
				else {
					sender.sendMessage("This command can only be run by an OP!");
				}
			}
			
			if(arg.equalsIgnoreCase("nav")){
				if (p == null) {
					sender.sendMessage("This command can only be run by a player!");
				} else if (p.isOp()) {
					SpoutPlayer player = (SpoutPlayer) p;
					if(player.isSpoutCraftEnabled()){
					if(loc.nav == true){
						sender.sendMessage(ChatColor.RED + "Stopping Sewer navigation...");
						loc.StopNav(p);
					}else{
						if(!(plugin.active.isEmpty())){
							
							sender.sendMessage(ChatColor.RED + "OH NOES! It appears that " + plugin.active + " is already navigating! One at a time please!");
							
						}
						sender.sendMessage(ChatColor.GREEN + "Starting Sewer navigation...");
						sender.sendMessage(ChatColor.GREEN + "To stop navigation type '/sewer nav'");
						loc.DisplayArrows(p);					
					}					
				return true;
					}else{
						sender.sendMessage(ChatColor.RED + "OH NOES! You aren't using SpoutCraft so navigation is impossiable!");
					}
				}
				else {
					sender.sendMessage("This command can only be run by an OP!");
				}
			}
			
	}
}
		return false;
}		


}