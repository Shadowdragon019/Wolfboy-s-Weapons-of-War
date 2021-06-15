package net.mcreator.weaponsofwar.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.CriticalHitEvent;

import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.mcreator.weaponsofwar.world.DaggerBackstabRangeGameRule;
import net.mcreator.weaponsofwar.WwowMod;

import java.util.Map;
import java.util.HashMap;

public class PlayerDealsCritProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
		@SubscribeEvent
		public static void onPlayerCriticalHit(CriticalHitEvent event) {
			Entity entity = event.getTarget();
			PlayerEntity sourceentity = event.getPlayer();
			double i = sourceentity.getPosX();
			double j = sourceentity.getPosY();
			double k = sourceentity.getPosZ();
			World world = sourceentity.world;
			Map<String, Object> dependencies = new HashMap<>();
			dependencies.put("x", i);
			dependencies.put("y", j);
			dependencies.put("z", k);
			dependencies.put("world", world);
			dependencies.put("entity", entity);
			dependencies.put("sourceentity", sourceentity);
			dependencies.put("damagemodifier", event.getDamageModifier());
			dependencies.put("isvanillacritical", event.isVanillaCritical());
			dependencies.put("event", event);
			executeProcedure(dependencies);
		}
	}
	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				WwowMod.LOGGER.warn("Failed to load dependency entity for procedure PlayerDealsCrit!");
			return;
		}
		if (dependencies.get("sourceentity") == null) {
			if (!dependencies.containsKey("sourceentity"))
				WwowMod.LOGGER.warn("Failed to load dependency sourceentity for procedure PlayerDealsCrit!");
			return;
		}
		if (dependencies.get("damagemodifier") == null) {
			if (!dependencies.containsKey("damagemodifier"))
				WwowMod.LOGGER.warn("Failed to load dependency damagemodifier for procedure PlayerDealsCrit!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				WwowMod.LOGGER.warn("Failed to load dependency world for procedure PlayerDealsCrit!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		Entity sourceentity = (Entity) dependencies.get("sourceentity");
		double damagemodifier = dependencies.get("damagemodifier") instanceof Integer
				? (int) dependencies.get("damagemodifier")
				: (double) dependencies.get("damagemodifier");
		IWorld world = (IWorld) dependencies.get("world");
		double playerYaw = 0;
		double attackedYaw = 0;
		double AttackRange = 0;
		double criticalDamage = 0;
		double damageMultiply = 0;
		boolean allowCrit = false;
		boolean denyCrit = false;
		damageMultiply = (double) 1;
		playerYaw = (double) (sourceentity.rotationYaw);
		attackedYaw = (double) (entity.rotationYaw);
		if (((playerYaw) < 0)) {
			playerYaw = (double) ((playerYaw) + 360);
		}
		if (((attackedYaw) < 0)) {
			attackedYaw = (double) ((attackedYaw) + 360);
		}
		AttackRange = (double) ((world.getWorldInfo().getGameRulesInstance().getInt(DaggerBackstabRangeGameRule.gamerule)) / 2);
		if ((ItemTags.getCollection().getTagByID(new ResourceLocation(("forge:wwow/dagger").toLowerCase(java.util.Locale.ENGLISH))).contains(
				((sourceentity instanceof LivingEntity) ? ((LivingEntity) sourceentity).getHeldItemMainhand() : ItemStack.EMPTY).getItem()))) {
			if (((((attackedYaw) > ((playerYaw) - (AttackRange))) && ((attackedYaw) < ((playerYaw) + (AttackRange))))
					|| (((attackedYaw) < (AttackRange)) && ((((attackedYaw) + 365) > ((playerYaw) - (AttackRange)))
							&& (((attackedYaw) + 365) < ((playerYaw) + (AttackRange))))))) {
				damageMultiply = (double) (3 * (damageMultiply));
				allowCrit = (boolean) (true);
			} else {
				denyCrit = (boolean) (true);
			}
		}
		if ((ItemTags.getCollection().getTagByID(new ResourceLocation(("forge:wwow/stone/weapon").toLowerCase(java.util.Locale.ENGLISH))).contains(
				((sourceentity instanceof LivingEntity) ? ((LivingEntity) sourceentity).getHeldItemMainhand() : ItemStack.EMPTY).getItem()))) {
			damageMultiply = (double) (3 * (damageMultiply));
		}
		if (dependencies.get("event") != null) {
			Object _obj = dependencies.get("event");
			if (_obj instanceof CriticalHitEvent) {
				CriticalHitEvent _evt = (CriticalHitEvent) _obj;
				if (_evt.hasResult())
					criticalDamage = (double) ((damagemodifier) * (damageMultiply));
				_evt.setDamageModifier((float) criticalDamage);
			}
		}
		if ((allowCrit)) {
			if (dependencies.get("event") != null) {
				Object _obj = dependencies.get("event");
				if (_obj instanceof Event) {
					Event _evt = (Event) _obj;
					if (_evt.hasResult())
						_evt.setResult(Event.Result.ALLOW);
				}
			}
		} else if ((denyCrit)) {
			if (dependencies.get("event") != null) {
				Object _obj = dependencies.get("event");
				if (_obj instanceof Event) {
					Event _evt = (Event) _obj;
					if (_evt.hasResult())
						_evt.setResult(Event.Result.DENY);
				}
			}
		}
	}
}
