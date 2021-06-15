package net.mcreator.weaponsofwar.procedures;

import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.item.ItemEntity;

import net.mcreator.weaponsofwar.block.RabbitHideRopeBlock;
import net.mcreator.weaponsofwar.WwowMod;

import java.util.Map;

public class RabbitHideCoilBulletHitsBlockProcedure {
	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				WwowMod.LOGGER.warn("Failed to load dependency x for procedure RabbitHideCoilBulletHitsBlock!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				WwowMod.LOGGER.warn("Failed to load dependency y for procedure RabbitHideCoilBulletHitsBlock!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				WwowMod.LOGGER.warn("Failed to load dependency z for procedure RabbitHideCoilBulletHitsBlock!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				WwowMod.LOGGER.warn("Failed to load dependency world for procedure RabbitHideCoilBulletHitsBlock!");
			return;
		}
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		double ropeSpawnY = 0;
		double placeRopeYOffset = 0;
		placeRopeYOffset = (double) (-1);
		ropeSpawnY = (double) 0;
		for (int index0 = 0; index0 < (int) (8); index0++) {
			placeRopeYOffset = (double) ((placeRopeYOffset) + 1);
			if (((!(world.isAirBlock(new BlockPos((int) (Math.floor(x)), (int) ((Math.floor(y)) - (placeRopeYOffset)), (int) (Math.floor(z))))))
					|| (BlockTags.getCollection().getTagByID(new ResourceLocation(("forge:wwow/rope").toLowerCase(java.util.Locale.ENGLISH)))
							.contains((world.getBlockState(
									new BlockPos((int) (Math.floor(x)), (int) ((Math.floor(y)) - (placeRopeYOffset)), (int) (Math.floor(z)))))
											.getBlock())))) {
				if (world instanceof World && !world.isRemote()) {
					ItemEntity entityToSpawn = new ItemEntity((World) world, (Math.floor(x)), ((Math.floor(y)) - (ropeSpawnY)), (Math.floor(z)),
							new ItemStack(RabbitHideRopeBlock.block, (int) (1)));
					entityToSpawn.setPickupDelay((int) 10);
					world.addEntity(entityToSpawn);
				}
			}
			if (((world.isAirBlock(new BlockPos((int) (Math.floor(x)), (int) ((Math.floor(y)) - (placeRopeYOffset)), (int) (Math.floor(z)))))
					|| (BlockTags.getCollection().getTagByID(new ResourceLocation(("forge:wwow/rope").toLowerCase(java.util.Locale.ENGLISH)))
							.contains((world.getBlockState(
									new BlockPos((int) (Math.floor(x)), (int) ((Math.floor(y)) - (placeRopeYOffset)), (int) (Math.floor(z)))))
											.getBlock())))) {
				if ((!(BlockTags.getCollection().getTagByID(new ResourceLocation(("forge:wwow/rope").toLowerCase(java.util.Locale.ENGLISH)))
						.contains((world.getBlockState(
								new BlockPos((int) (Math.floor(x)), (int) ((Math.floor(y)) - (placeRopeYOffset)), (int) (Math.floor(z)))))
										.getBlock())))) {
					world.setBlockState(new BlockPos((int) (Math.floor(x)), (int) ((Math.floor(y)) - (placeRopeYOffset)), (int) (Math.floor(z))),
							RabbitHideRopeBlock.block.getDefaultState(), 3);
				}
				ropeSpawnY = (double) (placeRopeYOffset);
			}
		}
	}
}
