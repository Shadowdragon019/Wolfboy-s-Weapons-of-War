package net.mcreator.weaponsofwar.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.World;
import net.minecraft.util.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.enchantment.EnchantmentHelper;

import net.mcreator.weaponsofwar.enchantment.SplashingEnchantment;
import net.mcreator.weaponsofwar.WwowMod;

import java.util.Map;
import java.util.HashMap;

public class BeforeEntityIsHurtProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
		@SubscribeEvent
		public static void onEntityAttacked(LivingHurtEvent event) {
			if (event != null && event.getEntity() != null) {
				Entity entity = event.getEntity();
				Entity sourceentity = event.getSource().getTrueSource();
				double i = entity.getPosX();
				double j = entity.getPosY();
				double k = entity.getPosZ();
				double amount = event.getAmount();
				World world = entity.world;
				Map<String, Object> dependencies = new HashMap<>();
				dependencies.put("x", i);
				dependencies.put("y", j);
				dependencies.put("z", k);
				dependencies.put("amount", amount);
				dependencies.put("world", world);
				dependencies.put("entity", entity);
				dependencies.put("sourceentity", sourceentity);
				dependencies.put("event", event);
				executeProcedure(dependencies);
			}
		}
	}
	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				WwowMod.LOGGER.warn("Failed to load dependency entity for procedure BeforeEntityIsHurt!");
			return;
		}
		if (dependencies.get("sourceentity") == null) {
			if (!dependencies.containsKey("sourceentity"))
				WwowMod.LOGGER.warn("Failed to load dependency sourceentity for procedure BeforeEntityIsHurt!");
			return;
		}
		if (dependencies.get("amount") == null) {
			if (!dependencies.containsKey("amount"))
				WwowMod.LOGGER.warn("Failed to load dependency amount for procedure BeforeEntityIsHurt!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		Entity sourceentity = (Entity) dependencies.get("sourceentity");
		double amount = dependencies.get("amount") instanceof Integer ? (int) dependencies.get("amount") : (double) dependencies.get("amount");
		boolean changeDamage = false;
		double damage = 0;
		double damageMultiply = 0;
		double SplashingLevel = 0;
		damageMultiply = (double) 1;
		if (((ItemTags.getCollection().getTagByID(new ResourceLocation(("forge:wwow/broadsword").toLowerCase(java.util.Locale.ENGLISH)))
				.contains(((sourceentity instanceof LivingEntity) ? ((LivingEntity) sourceentity).getHeldItemMainhand() : ItemStack.EMPTY).getItem()))
				&& (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getTotalArmorValue() : 0) > 0))) {
			changeDamage = (boolean) (true);
			damageMultiply = (double) (1.5 * (damageMultiply));
		}
		if (((EnchantmentHelper.getEnchantmentLevel(SplashingEnchantment.enchantment,
				((sourceentity instanceof LivingEntity) ? ((LivingEntity) sourceentity).getHeldItemMainhand() : ItemStack.EMPTY)) != 0))) {
			sourceentity.extinguish();
			if (((EntityTypeTags.getCollection().getTagByID(new ResourceLocation(("forge:wwow/ender").toLowerCase(java.util.Locale.ENGLISH)))
					.contains(entity.getType()))
					|| (EntityTypeTags.getCollection().getTagByID(new ResourceLocation(("forge:wwow/heat").toLowerCase(java.util.Locale.ENGLISH)))
							.contains(entity.getType())))) {
				changeDamage = (boolean) (true);
				SplashingLevel = (double) (EnchantmentHelper.getEnchantmentLevel(SplashingEnchantment.enchantment,
						((sourceentity instanceof LivingEntity) ? ((LivingEntity) sourceentity).getHeldItemMainhand() : ItemStack.EMPTY)));
				damageMultiply = (double) ((1 + ((SplashingLevel) * 0.5)) * (damageMultiply));
			}
		}
		if ((changeDamage)) {
			LivingHurtEvent event = (LivingHurtEvent) dependencies.get("event");
			damage = (double) ((amount) * (damageMultiply));
			event.setAmount((float) damage);
		}
	}
}
