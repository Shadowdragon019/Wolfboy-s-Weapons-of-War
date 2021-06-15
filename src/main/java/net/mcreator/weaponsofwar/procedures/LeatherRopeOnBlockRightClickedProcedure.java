package net.mcreator.weaponsofwar.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraft.world.IWorld;
import net.minecraft.world.GameType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Hand;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.item.ItemStack;
import net.minecraft.item.BlockItem;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;

import net.mcreator.weaponsofwar.WwowMod;

import java.util.Map;
import java.util.HashMap;

public class LeatherRopeOnBlockRightClickedProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
		@SubscribeEvent
		public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
			PlayerEntity entity = event.getPlayer();
			if (event.getHand() != entity.getActiveHand()) {
				return;
			}
			double i = event.getPos().getX();
			double j = event.getPos().getY();
			double k = event.getPos().getZ();
			IWorld world = event.getWorld();
			Map<String, Object> dependencies = new HashMap<>();
			dependencies.put("x", i);
			dependencies.put("y", j);
			dependencies.put("z", k);
			dependencies.put("world", world);
			dependencies.put("entity", entity);
			dependencies.put("direction", event.getFace());
			dependencies.put("event", event);
			executeProcedure(dependencies);
		}
	}
	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				WwowMod.LOGGER.warn("Failed to load dependency entity for procedure LeatherRopeOnBlockRightClicked!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				WwowMod.LOGGER.warn("Failed to load dependency x for procedure LeatherRopeOnBlockRightClicked!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				WwowMod.LOGGER.warn("Failed to load dependency y for procedure LeatherRopeOnBlockRightClicked!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				WwowMod.LOGGER.warn("Failed to load dependency z for procedure LeatherRopeOnBlockRightClicked!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				WwowMod.LOGGER.warn("Failed to load dependency world for procedure LeatherRopeOnBlockRightClicked!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		double ropePiece = 0;
		ItemStack itemInHand = ItemStack.EMPTY;
		if ((BlockTags.getCollection().getTagByID(new ResourceLocation(("forge:wwow/rope").toLowerCase(java.util.Locale.ENGLISH)))
				.contains((world.getBlockState(new BlockPos((int) x, (int) y, (int) z))).getBlock()))) {
			if (((ItemTags.getCollection().getTagByID(new ResourceLocation(("forge:wwow/rope").toLowerCase(java.util.Locale.ENGLISH)))
					.contains(((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY).getItem()))
					|| (ItemTags.getCollection().getTagByID(new ResourceLocation(("forge:wwow/rope").toLowerCase(java.util.Locale.ENGLISH))).contains(
							((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY).getItem())))) {
				if ((ItemTags.getCollection().getTagByID(new ResourceLocation(("forge:wwow/rope").toLowerCase(java.util.Locale.ENGLISH)))
						.contains(((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY).getItem()))) {
					itemInHand = ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY);
					if (entity instanceof LivingEntity) {
						((LivingEntity) entity).swing(Hand.MAIN_HAND, true);
					}
				} else {
					itemInHand = ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY);
					if (entity instanceof LivingEntity) {
						((LivingEntity) entity).swing(Hand.OFF_HAND, true);
					}
				}
				ropePiece = (double) 0;
				for (int index0 = 0; index0 < (int) (y); index0++) {
					ropePiece = (double) ((ropePiece) + 1);
					if ((world.isAirBlock(new BlockPos((int) x, (int) (y - (ropePiece)), (int) z)))) {
						if ((!(new Object() {
							public boolean checkGamemode(Entity _ent) {
								if (_ent instanceof ServerPlayerEntity) {
									return ((ServerPlayerEntity) _ent).interactionManager.getGameType() == GameType.CREATIVE;
								} else if (_ent instanceof PlayerEntity && _ent.world.isRemote()) {
									NetworkPlayerInfo _npi = Minecraft.getInstance().getConnection()
											.getPlayerInfo(((AbstractClientPlayerEntity) _ent).getGameProfile().getId());
									return _npi != null && _npi.getGameType() == GameType.CREATIVE;
								}
								return false;
							}
						}.checkGamemode(entity)))) {
							if (entity instanceof PlayerEntity) {
								ItemStack _stktoremove = (itemInHand);
								((PlayerEntity) entity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
										((PlayerEntity) entity).container.func_234641_j_());
							}
						}
						world.setBlockState(new BlockPos((int) x, (int) (y - (ropePiece)), (int) z), /* @BlockState */(new Object() {
							public BlockState toBlock(ItemStack _stk) {
								if (_stk.getItem() instanceof BlockItem) {
									return ((BlockItem) _stk.getItem()).getBlock().getDefaultState();
								}
								return Blocks.AIR.getDefaultState();
							}
						}.toBlock((itemInHand))), 3);
						break;
					} else if ((!(BlockTags.getCollection()
							.getTagByID(new ResourceLocation(("forge:wwow/rope").toLowerCase(java.util.Locale.ENGLISH)))
							.contains((world.getBlockState(new BlockPos((int) x, (int) (y - (ropePiece)), (int) z))).getBlock())))) {
						break;
					}
				}
			}
		}
	}
}
