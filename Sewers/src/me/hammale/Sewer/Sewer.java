package me.hammale.Sewer;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Sewer extends JavaPlugin {
	static Server server;
	static Plugin plugin;
	
	//private final SewerBlockListener blockListener = new SewerBlockListener();
	//public final SewerPlayerListener playerListener = new SewerPlayerListener();
	
	private SewerCommandListener cmdExecutor;
	private SewerChunkListener chunkListener;
	private SewerBlockListener blockListener;
	
	public FileConfiguration config;
	
	@Override
	public void onEnable()
    {	 
		loadConfiguration();
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println("[Sewers] Version " + pdfFile.getVersion() + " Enabled!");
	    cmdExecutor = new SewerCommandListener(this);
	    chunkListener = new SewerChunkListener(this);
	    blockListener = new SewerBlockListener();
		getCommand("sewer").setExecutor(cmdExecutor);
		registerEvents();
		
		server = this.getServer();
		plugin = this;
    }
	public void onDisable()
    {
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println("[Sewers] Version " + pdfFile.getVersion() + " Disabled!");
    }
	private void registerEvents()
    {
		getServer().getPluginManager().registerEvent(Event.Type.CHUNK_POPULATED, this.chunkListener, Event.Priority.Normal, this);
		getServer().getPluginManager().registerEvent(Event.Type.BLOCK_BREAK, blockListener, Event.Priority.Normal, this);
    }
	
	public void loadConfiguration(){
		
	    config = getConfig();
	    config.options().copyDefaults(true); 
	    
	    for(World w : this.getServer().getWorlds()) {
		    String wrld = w.getName();		        
		    String path2 = "ChunkGeneration.World.AllowSewers." + wrld;		    
		    config.addDefault(path2, 1);
	    }
	    
		String path1 = "ChunkGeneration.Frequency";
	    config.addDefault(path1, 100);
	    
	    config.options().copyDefaults(true);
	    saveConfig();
	}
	
	public int isAllowedWorld(World w){
	    config = getConfig();
	    String wrld = w.getName();
	    int amnt = config.getInt("ChunkGeneration.World.AllowSewers." + wrld); 
	    return amnt;
	}
	
	public int getFreq(){
	    config = getConfig();
	    int amnt = config.getInt("ChunkGeneration.Frequency"); 
	    return amnt;
	}
	
}