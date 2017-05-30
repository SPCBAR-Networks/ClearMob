package io.github.axle2005.clearmob;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.tileentity.TileEntityType;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.text.Text;

public class Util {

	ClearMob plugin;

	public Util(ClearMob plugin) {
		this.plugin = plugin;
	}

	public static List<EntityType> getEntityType(List<String> entitys) {
		List<EntityType> listEntityType = new ArrayList<EntityType>(Arrays.asList(EntityTypes.PRIMED_TNT));
		for (String s : entitys) {
			Optional<EntityType> entity = Sponge.getRegistry().getType(EntityType.class, s);

			try {
				entity = Sponge.getRegistry().getType(EntityType.class, s);
				if (entity.isPresent()) {
					listEntityType.add(entity.get());
				}

			} catch (IllegalArgumentException e) {

			}
		}

		return listEntityType;
	}

	public static List<TileEntityType> getTileEntityType(List<String> entitys) {
		List<TileEntityType> listEntityType = new ArrayList<TileEntityType>(Arrays.asList());
		for (String s : entitys) {
			Optional<TileEntityType> entity = null;

			try {
				entity = Sponge.getRegistry().getType(TileEntityType.class, s);
				if (entity.isPresent()) {
					listEntityType.add(entity.get());
				}

			} catch (IllegalArgumentException e) {

			}

		}

		return listEntityType;
	}
	public static List<ItemType> getItemType(List<String> entitys) {
		List<ItemType> listEntityType = new ArrayList<ItemType>(Arrays.asList());
		for (String s : entitys) {
			Optional<ItemType> entity = null;

			try {
				entity = Sponge.getRegistry().getType(ItemType.class, s);
				if (entity.isPresent()) {
					listEntityType.add(entity.get());
				}

			} catch (IllegalArgumentException e) {

			}

		}

		return listEntityType;
	}

	public static EntityType getEntityType(String s) {
		return Sponge.getRegistry().getType(EntityType.class, s).get();

	}

	public static TileEntityType getTileEntityType(String s) {
		return Sponge.getRegistry().getType(TileEntityType.class, s).get();

	}

	public static Boolean playerPermCheck(CommandSource src, String perm) {
		if (src instanceof Player && !src.hasPermission(perm)) {
			src.sendMessage(Text.of("You are missing: "+perm+", and can not run this."));
			return false;
		} else
			return true;
	}

}
