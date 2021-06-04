package net.mcreator.weaponsofwar.world;

import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import net.minecraft.world.GameRules;

import net.mcreator.weaponsofwar.WwowModElements;

import java.lang.reflect.Method;

@WwowModElements.ModElement.Tag
public class WoodenItemRepairTimeGameRule extends WwowModElements.ModElement {
	public static final GameRules.RuleKey<GameRules.IntegerValue> gamerule = GameRules.register("woodenItemRepairTime", GameRules.Category.PLAYER,
			create(400));
	public WoodenItemRepairTimeGameRule(WwowModElements instance) {
		super(instance, 65);
	}

	public static GameRules.RuleType<GameRules.IntegerValue> create(int defaultValue) {
		try {
			Method createGameruleMethod = ObfuscationReflectionHelper.findMethod(GameRules.IntegerValue.class, "func_223559_b", int.class);
			createGameruleMethod.setAccessible(true);
			return (GameRules.RuleType<GameRules.IntegerValue>) createGameruleMethod.invoke(null, defaultValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
