package io.github.axle2005.clearmob.commands;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;

import io.github.axle2005.clearmob.ClearMob;
import io.github.axle2005.clearmob.Util;

public class CommandDump implements CommandExecutor {

    ClearMob plugin;

    Map<String, Integer> entityData;
    int value = 0;

    public CommandDump(ClearMob plugin) {
	this.plugin = plugin;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext arguments) throws CommandException {
	String args = arguments.<String>getOne("tileentity|entity|nearby|all").get();
	if (!Util.playerPermCheck(src, "clearmob.admin")) {
	    return CommandResult.empty();
	}
	entityData = new ConcurrentHashMap<>();
	switch (args) {
	case "entity": {
	    entityDump();
	    return CommandResult.success();
	}
	case "tileentity": {
	    tileEntityDump();
	    return CommandResult.success();
	}
	case "nearby": {
	    nearbyDump(src);
	    return CommandResult.success();
	}
	case "all": {
	    entityAllDump();
	    return CommandResult.success();
	}
	default: {
	    src.sendMessage(Text.of("/clearmob <dump><tileentity|entity|nearby|all>"));
	    return CommandResult.empty();
	}
	}
    }

    private void entityDump() {
	for (World world : Sponge.getServer().getWorlds()) {
	    for (Entity entity : world.getEntities()) {
		editMap("Entity: " + entity.getType().getId());
	    }
	    mapFeedback();
	}

    }

    private void tileEntityDump() {
	for (World world : Sponge.getServer().getWorlds()) {
	    for (TileEntity entity : world.getTileEntities()) {
		editMap("Tile Entity: " + entity.getType().getId());
	    }
	    mapFeedback();
	}
    }

    private void nearbyDump(CommandSource src) {
	if (src instanceof Player) {
	    Player player = (Player) src;

	    for (Entity entity : player.getNearbyEntities(10)) {
		editMap("Entity: " + entity.getType().getId());
	    }
	    mapFeedback();

	} else {
	    src.sendMessage(Text.of("You must be a player to use this"));
	}

    }

    private void entityAllDump() {
	for (World world : Sponge.getServer().getWorlds()) {
	    for (Entity entity : world.getEntities()) {
		editMap("Entity: " + entity.getType().getId());
	    }
	    for (TileEntity entity : world.getTileEntities()) {
		editMap("Entity: " + entity.getType().getId());
	    }
	    mapFeedback();
	}
    }

    private void mapFeedback() {
	ClearMob instance = ClearMob.getInstance();
	for (String s : entityData.keySet()) {
	    instance.getLogger().info(s + " [" + entityData.get(s) + "]");
	}

    }

    private void editMap(String key) {
	if (entityData.containsKey(key)) {
	    value = entityData.get(key) + 1;
	    entityData.put(key, value);
	} else
	    entityData.put(key, 1);
    }

}
