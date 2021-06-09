
package net.mcreator.weaponsofwar;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;

import net.mcreator.weaponsofwar.item.IronDaggerItem;
import net.mcreator.weaponsofwar.item.FancyDaggerItem;

@WwowModElements.ModElement.Tag
public class FancyDaggerRBrewingRecipe extends WwowModElements.ModElement {
	public FancyDaggerRBrewingRecipe(WwowModElements instance) {
		super(instance, 100);
	}

	@Override
	public void init(FMLCommonSetupEvent event) {
		BrewingRecipeRegistry.addRecipe(Ingredient.fromStacks(new ItemStack(IronDaggerItem.block, (int) (1))),
				Ingredient.fromStacks(new ItemStack(Items.GLOWSTONE_DUST, (int) (1))), new ItemStack(FancyDaggerItem.block, (int) (1)));
	}
}
