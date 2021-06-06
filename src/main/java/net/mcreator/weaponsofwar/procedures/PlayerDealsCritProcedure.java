package net.mcreator.weaponsofwar.procedures;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.mcreator.weaponsofwar.world.DaggerBackstabRangeGameRule;
import net.mcreator.weaponsofwar.WwowModElements;
import net.mcreator.weaponsofwar.WwowMod;

import java.util.Map;
import java.util.HashMap;

@WwowModElements.ModElement.Tag
public class PlayerDealsCritProcedure extends WwowModElements.ModElement {
	public PlayerDealsCritProcedure(WwowModElements instance) {
		super(instance, 78);
		MinecraftForge.EVENT_BUS.register(this);
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
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				WwowMod.LOGGER.warn("Failed to load dependency world for procedure PlayerDealsCrit!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		Entity sourceentity = (Entity) dependencies.get("sourceentity");
		IWorld world = (IWorld) dependencies.get("world");
		double playerYaw = 0;
		double attackedYaw = 0;
		double AttackRange = 0;
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
				if (dependencies.get("event") != null) {
					Object _obj = dependencies.get("event");
					if (_obj instanceof CriticalHitEvent) {
						CriticalHitEvent _evt = (CriticalHitEvent) _obj;
						if (_evt.hasResult())
							_evt.setDamageModifier(3);
					}
				}
				if (dependencies.get("event") != null) {
					Object _obj = dependencies.get("event");
					if (_obj instanceof Event) {
						Event _evt = (Event) _obj;
						if (_evt.hasResult())
							_evt.setResult(Event.Result.ALLOW);
					}
				}
			} else {
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

	@SubscribeEvent
	public void onPlayerCriticalHit(CriticalHitEvent event) {
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
		this.executeProcedure(dependencies);
	}
}
