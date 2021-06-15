package net.mcreator.weaponsofwar.procedures;

import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;

import net.mcreator.weaponsofwar.WwowMod;

import java.util.Map;

public class LeatherRopeBlockValidPlacementConditionProcedure {
	public static boolean executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				WwowMod.LOGGER.warn("Failed to load dependency x for procedure LeatherRopeBlockValidPlacementCondition!");
			return false;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				WwowMod.LOGGER.warn("Failed to load dependency y for procedure LeatherRopeBlockValidPlacementCondition!");
			return false;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				WwowMod.LOGGER.warn("Failed to load dependency z for procedure LeatherRopeBlockValidPlacementCondition!");
			return false;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				WwowMod.LOGGER.warn("Failed to load dependency world for procedure LeatherRopeBlockValidPlacementCondition!");
			return false;
		}
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		if ((!(world.isAirBlock(new BlockPos((int) x, (int) (y - 1), (int) z))))) {
			return (true);
		}
		return (false);
	}
}
