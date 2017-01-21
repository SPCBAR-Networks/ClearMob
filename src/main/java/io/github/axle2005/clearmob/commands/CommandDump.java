package io.github.axle2005.clearmob.commands;

import java.util.ArrayList;
import java.util.List;

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

public class CommandDump implements CommandExecutor {

	ClearMob plugin;

	public CommandDump(ClearMob plugin) {
		this.plugin = plugin;
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext arguments) throws CommandException {
		String args = arguments.<String>getOne("tileentity|entity|nearby|all").get();
		if (src instanceof Player && !src.hasPermission("clearmob.dump")) {
			Player player = (Player) src;
			player.sendMessage(Text.of("You do not have permission to use this command!"));
			return CommandResult.empty();
		}

		if (args.equalsIgnoreCase("entity")) {
			entityDump();
			return CommandResult.success();
		} else if (args.equalsIgnoreCase("tileentity")) {
			tileEntityDump();
			return CommandResult.success();
		} else if (args.equalsIgnoreCase("nearby")) {
			nearbyDump(src);
			return CommandResult.success();
		} else if (args.equalsIgnoreCase("all")) {
			entityAllDump();
			return CommandResult.success();
		} else {
			src.sendMessage(Text.of("/clearmob <dump><tileentity|entity|nearby|all>"));
			return CommandResult.empty();
		}
	}

	private void entityDump() {
		List<String> listdump = new ArrayList<String>();
		List<Integer> count = new ArrayList<Integer>();
		for (World world : Sponge.getServer().getWorlds()) {
			for (Entity entity : world.getEntities()) {

				if (!listdump.contains("Entity: " + entity.getType())
						&& !plugin.getListEntityType().contains(entity.getType())) {
					listdump.add("Entity: " + entity.getType());
					count.add(1);
				} else if (listdump.contains("Entity: " + entity.getType())) {
					count.set(listdump.indexOf("Entity: " + entity.getType()),
							count.get(listdump.indexOf("Entity: " + entity.getType())) + 1);

				}

			}
		}
		if (listdump.isEmpty()) {
			plugin.getLogger().info("No Entities to Add");
		} else {
			for (int i = 0; i <= listdump.size() - 1; i++) {
				plugin.getLogger().info(listdump.get(i) + ": (" + count.get(i) + ")");
			}
		}

	}

	private void tileEntityDump() {
		List<String> listdump = new ArrayList<String>();
		List<Integer> count = new ArrayList<Integer>();
		for (World world : Sponge.getServer().getWorlds()) {
			for (TileEntity entity : world.getTileEntities()) {

				if (!listdump.contains("Tile Entity: " + entity.getType())
						&& !plugin.getListEntityType().contains(entity.getType())) {
					listdump.add("Tile Entity: " + entity.getType());
					count.add(1);
				} else if (listdump.contains("Tile Entity: " + entity.getType())) {
					count.set(listdump.indexOf("Tile Entity: " + entity.getType()),
							count.get(listdump.indexOf("Tile Entity: " + entity.getType())) + 1);

				}
			}
		}
		if (listdump.isEmpty()) {
			plugin.getLogger().info("No Tile Entities to Add");
		} else {
			for (String s : listdump) {
				plugin.getLogger().info(s);
			}
		}
	}

	private void nearbyDump(CommandSource src) {
		if (src instanceof Player) {
			Player player = (Player) src;
			List<String> listdump = new ArrayList<String>();

			for (Entity entity : player.getNearbyEntities(10)) {
				if (!listdump.contains("Entity: " + entity.getType().getId())
						&& !plugin.getListEntityType().contains(entity.getType().getId())) {
					listdump.add("Entity: " + entity.getType().getId());
				}
			}
			if (listdump.isEmpty()) {
				plugin.getLogger().info("No Tile Entities to Add");
			} else {
				for (String s : listdump) {
					plugin.getLogger().info(s);
				}
			}
		} else {
			src.sendMessage(Text.of("You must be a player to use this"));
		}

	}

	private void entityAllDump() {
		List<String> listentitydump = new ArrayList<String>();
		List<String> listtiledump = new ArrayList<String>();
		List<Integer> listentitycount = new ArrayList<Integer>();
		List<Integer> listtilecount = new ArrayList<Integer>();
		for (World world : Sponge.getServer().getWorlds()) {
			for (Entity entity : world.getEntities()) {

				if (!listentitydump.contains("Entity: " + entity.getType().getId())) {
					listentitydump.add("Entity: " + entity.getType().getId());
					listentitycount.add(1);
				} else if (listentitydump.contains("Entity: " + entity.getType().getId())) {
					listentitycount.set(listentitydump.indexOf("Entity: " + entity.getType().getId()),
							listentitycount.get(listentitydump.indexOf("Entity: " + entity.getType().getId())) + 1);

				}

			}
			for (TileEntity entity : world.getTileEntities()) {

				if (!listtiledump.contains("Tile Entity: " + entity.getType().getId())) {
					listtiledump.add("Tile Entity: " + entity.getType().getId());
					listtilecount.add(1);
				} else if (listtiledump.contains("Tile Entity: " + entity.getType().getId())) {
					listtilecount.set(listtiledump.indexOf("Tile Entity: " + entity.getType().getId()),
							listtilecount.get(listtiledump.indexOf("Tile Entity: " + entity.getType().getId())) + 1);

				}
			}
		}
		if (listentitydump.isEmpty()) {
			plugin.getLogger().info("No Entities to Add");
		} else {
			for (int i = 0; i <= listentitydump.size() - 1; i++) {
				plugin.getLogger().info(listentitydump.get(i) + ": (" + listentitycount.get(i) + ")");
			}
		}
		if (listtiledump.isEmpty()) {
			plugin.getLogger().info("No Tile Entities to Add");
		} else {

			for (int i = 0; i <= listtiledump.size() - 1; i++) {
				plugin.getLogger().info(listtiledump.get(i) + ": (" + listtilecount.get(i) + ")");
			}
		}

	}

}
