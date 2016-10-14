package io.github.axle2005.clearmob;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;






public class CommandExec implements CommandExecutor {

	ClearMob plugin;
	List<String> list_entities;
	
	
	CommandExec(ClearMob plugin) {
		this.plugin = plugin;

	}
	
	CommandExec(ClearMob plugin, List<String> list_entities) {
		this.plugin = plugin;
		this.list_entities = plugin.list_entities;

	}
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		//String cmd_args[] = args.toString().split(" ");
		String cmd_args = args.<String>getOne("run").get();
		//String cmd_args = args.toString();
			if(src instanceof Player) {
				Player player = (Player) src;
			    if (!src.hasPermission("clearmob.run")) {
			    	
			    	player.sendMessage(Text.of("You do not have permission to use this command!"));
			    	return null;
			      }
			    else
			    {
			    	if(cmd_args.equalsIgnoreCase("run"))
			    	{
	    		
			    		
			    	      int removedEntities = 0;
			    	      for (World world : Sponge.getServer().getWorlds()) {
			    	        for (Entity entity : world.getEntities()) {
			    	        	for(int i =0; i <=list_entities.size()-1;i++)
			    	        	{
			    	        		 if ((entity.getType().getId().equalsIgnoreCase(list_entities.get(i))))
					    	          		//+ " instanceof Monster))
					    	          {
					    	            entity.remove();
					    	            //plugin.getLogger().info(list_entities.get(i));
					    	            removedEntities++;
					    	          }
			    	        	}
			    	         
			    	        }
			    	      }
			    	      src.sendMessage(Text.of("[ClearMob] " + removedEntities+ " entities were removed"));
			    	      plugin.getLogger().info("[ClearMob] " + removedEntities+ " entities were removed");
			    	      
	  		
			    		

			    		return CommandResult.success();
			    	}
			    }
			    
			}
			else if(src instanceof ConsoleSource) {
		    	if(cmd_args.equalsIgnoreCase("run"))
		    	{
    		
		    		
		    	      int removedEntities = 0;
		    	      for (World world : Sponge.getServer().getWorlds()) {
		    	        for (Entity entity : world.getEntities()) {
		    	        	for(int i =0; i <=list_entities.size()-1;i++)
		    	        	{
		    	        		 if ((entity.getType().getId().equalsIgnoreCase(list_entities.get(i))))
				    	          		//+ " instanceof Monster))
				    	          {
				    	            entity.remove();
				    	            //plugin.getLogger().info(list_entities.get(i));
				    	            removedEntities++;
				    	          }
		    	        	}
		    	         
		    	        }
		    	      }
		    	      src.sendMessage(Text.of("[ClearMob] " + removedEntities+ " entities were removed"));
		    	      
  		
		    		

		    		return CommandResult.success();
		    	}
		    	
		    	
			}
		return CommandResult.empty();
	}
	
	
	
}
