package net.mcreator.weaponsofwar.procedures;

import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraft.world.IWorld;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.mcreator.weaponsofwar.item.RabbitHideCoilItem;
import net.mcreator.weaponsofwar.item.LeatherRopeCoilItem;
import net.mcreator.weaponsofwar.block.RabbitHideRopeBlock;
import net.mcreator.weaponsofwar.block.LeatherRopeBlock;
import net.mcreator.weaponsofwar.WwowMod;

import java.util.Map;
import java.util.HashMap;

public class LeatherRopeOnBlockRightClickProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
		@SubscribeEvent
		public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
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
			dependencies.put("event", event);
			executeProcedure(dependencies);
		}
	}
	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				WwowMod.LOGGER.warn("Failed to load dependency entity for procedure LeatherRopeOnBlockRightClick!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if ((entity.isSneaking())) {
			if (((ItemTags.getCollection().getTagByID(new ResourceLocation(("forge:wwow/rope").toLowerCase(java.util.Locale.ENGLISH)))
					.contains(((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY).getItem()))
					|| (ItemTags.getCollection().getTagByID(new ResourceLocation(("forge:wwow/rope").toLowerCase(java.util.Locale.ENGLISH))).contains(
							((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY).getItem())))) {
				if ((((new ItemStack(LeatherRopeBlock.block, (int) (1))
						.getItem() == ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY).getItem())
						&& (((((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY)).getCount()) >= 8))
						|| ((new ItemStack(LeatherRopeBlock.block, (int) (1))
								.getItem() == ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY)
										.getItem())
								&& (((((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY))
										.getCount()) >= 8)))) {
					if (entity instanceof PlayerEntity) {
						ItemStack _stktoremove = new ItemStack(LeatherRopeBlock.block, (int) (1));
						((PlayerEntity) entity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 8,
								((PlayerEntity) entity).container.func_234641_j_());
					}
					if (entity instanceof PlayerEntity) {
						ItemStack _setstack = new ItemStack(LeatherRopeCoilItem.block, (int) (1));
						_setstack.setCount((int) 1);
						ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) entity), _setstack);
					}
				} else if ((((new ItemStack(RabbitHideRopeBlock.block, (int) (1))
						.getItem() == ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY).getItem())
						&& (((((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY)).getCount()) >= 8))
						|| ((new ItemStack(RabbitHideRopeBlock.block, (int) (1))
								.getItem() == ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY)
										.getItem())
								&& (((((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY))
										.getCount()) >= 8)))) {
					if (entity instanceof PlayerEntity) {
						ItemStack _stktoremove = new ItemStack(RabbitHideRopeBlock.block, (int) (1));
						((PlayerEntity) entity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 8,
								((PlayerEntity) entity).container.func_234641_j_());
					}
					if (entity instanceof PlayerEntity) {
						ItemStack _setstack = new ItemStack(RabbitHideCoilItem.block, (int) (1));
						_setstack.setCount((int) 1);
						ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) entity), _setstack);
					}
				} else {
					if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
						((PlayerEntity) entity).sendStatusMessage(new StringTextComponent("Too little rope!"), (true));
					}
				}
			}
		}
	}
}
