package net.mcreator.weaponsofwar.procedures;

import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Hand;
import net.minecraft.tags.ItemTags;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.mcreator.weaponsofwar.world.WoodenItemRepairTimeGameRule;
import net.mcreator.weaponsofwar.WwowModElements;
import net.mcreator.weaponsofwar.WwowMod;

import java.util.concurrent.atomic.AtomicReference;
import java.util.Map;
import java.util.HashMap;

@WwowModElements.ModElement.Tag
public class PlayerTicksProcedure extends WwowModElements.ModElement {
	public PlayerTicksProcedure(WwowModElements instance) {
		super(instance, 73);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				WwowMod.LOGGER.warn("Failed to load dependency entity for procedure PlayerTicks!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				WwowMod.LOGGER.warn("Failed to load dependency x for procedure PlayerTicks!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				WwowMod.LOGGER.warn("Failed to load dependency y for procedure PlayerTicks!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				WwowMod.LOGGER.warn("Failed to load dependency z for procedure PlayerTicks!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				WwowMod.LOGGER.warn("Failed to load dependency world for procedure PlayerTicks!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		double slotNum = 0;
		ItemStack theItem = ItemStack.EMPTY;
		boolean hasHealedItem = false;
		entity.getPersistentData().putDouble("wwowSearchInventory", ((entity.getPersistentData().getDouble("wwowSearchInventory")) + 1));
		if (((entity.getPersistentData().getDouble("wwowSearchInventory")) >= (world.getWorldInfo().getGameRulesInstance()
				.getInt(WoodenItemRepairTimeGameRule.gamerule)))) {
			slotNum = (double) (-1);
			for (int index0 = 0; index0 < (int) (41); index0++) {
				slotNum = (double) ((slotNum) + 1);
				if (((slotNum) <= 35)) {
					theItem = (new Object() {
						public ItemStack getItemStack(int sltid, Entity entity) {
							AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
							entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
								_retval.set(capability.getStackInSlot(sltid).copy());
							});
							return _retval.get();
						}
					}.getItemStack((int) ((slotNum)), entity));
				} else if (((slotNum) <= 39)) {
					theItem = ((entity instanceof LivingEntity)
							? ((LivingEntity) entity).getItemStackFromSlot(
									EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) ((slotNum) - 36)))
							: ItemStack.EMPTY);
				} else if (((slotNum) <= 40)) {
					theItem = ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemOffhand() : ItemStack.EMPTY);
				}
				if ((((((theItem)).getDamage()) > 0) && (((ItemTags.getCollection()
						.getTagByID(new ResourceLocation(("forge:wwow/wooden/tool").toLowerCase(java.util.Locale.ENGLISH)))
						.contains((theItem).getItem()))
						|| (ItemTags.getCollection()
								.getTagByID(new ResourceLocation(("forge:wwow/wooden/weapon").toLowerCase(java.util.Locale.ENGLISH)))
								.contains((theItem).getItem())))
						|| ((ItemTags.getCollection()
								.getTagByID(new ResourceLocation(("forge:wwow/wooden/armor").toLowerCase(java.util.Locale.ENGLISH)))
								.contains((theItem).getItem()))
								|| (ItemTags.getCollection()
										.getTagByID(new ResourceLocation(("forge:wwow/wooden/horse_armor").toLowerCase(java.util.Locale.ENGLISH)))
										.contains((theItem).getItem())))))) {
					hasHealedItem = (boolean) (true);
					((theItem)).setDamage((int) ((((theItem)).getDamage()) - 1));
					if (((slotNum) <= 35)) {
						{
							final ItemStack _setstack = (theItem);
							final int _sltid = (int) ((slotNum));
							_setstack.setCount((int) (((theItem)).getCount()));
							entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
								if (capability instanceof IItemHandlerModifiable) {
									((IItemHandlerModifiable) capability).setStackInSlot(_sltid, _setstack);
								}
							});
						}
					} else if (((slotNum) <= 39)) {
						if (entity instanceof LivingEntity) {
							if (entity instanceof PlayerEntity)
								((PlayerEntity) entity).inventory.armorInventory.set((int) ((slotNum) - 36), (theItem));
							else
								((LivingEntity) entity).setItemStackToSlot(
										EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) ((slotNum) - 36)), (theItem));
							if (entity instanceof ServerPlayerEntity)
								((ServerPlayerEntity) entity).inventory.markDirty();
						}
					} else if (((slotNum) <= 40)) {
						if (entity instanceof LivingEntity) {
							ItemStack _setstack = (theItem);
							_setstack.setCount((int) (((theItem)).getCount()));
							((LivingEntity) entity).setHeldItem(Hand.OFF_HAND, _setstack);
							if (entity instanceof ServerPlayerEntity)
								((ServerPlayerEntity) entity).inventory.markDirty();
						}
					}
				}
			}
			entity.getPersistentData().putDouble("wwowSearchInventory", 0);
		}
		if ((hasHealedItem)) {
			if (world instanceof ServerWorld) {
				((ServerWorld) world).spawnParticle(ParticleTypes.HAPPY_VILLAGER, x, y, z, (int) 25, 0.3, 1, 0.3, 1);
			}
		}
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			Entity entity = event.player;
			World world = entity.world;
			double i = entity.getPosX();
			double j = entity.getPosY();
			double k = entity.getPosZ();
			Map<String, Object> dependencies = new HashMap<>();
			dependencies.put("x", i);
			dependencies.put("y", j);
			dependencies.put("z", k);
			dependencies.put("world", world);
			dependencies.put("entity", entity);
			dependencies.put("event", event);
			this.executeProcedure(dependencies);
		}
	}
}
