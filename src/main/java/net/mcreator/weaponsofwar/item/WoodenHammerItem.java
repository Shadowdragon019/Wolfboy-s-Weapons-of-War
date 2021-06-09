
package net.mcreator.weaponsofwar.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.IItemTier;
import net.minecraft.item.AxeItem;
import net.minecraft.block.Blocks;

import net.mcreator.weaponsofwar.WwowModElements;

@WwowModElements.ModElement.Tag
public class WoodenHammerItem extends WwowModElements.ModElement {
	@ObjectHolder("wwow:wooden_hammer")
	public static final Item block = null;
	public WoodenHammerItem(WwowModElements instance) {
		super(instance, 117);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new AxeItem(new IItemTier() {
			public int getMaxUses() {
				return 59;
			}

			public float getEfficiency() {
				return 2f;
			}

			public float getAttackDamage() {
				return 8f;
			}

			public int getHarvestLevel() {
				return 0;
			}

			public int getEnchantability() {
				return 15;
			}

			public Ingredient getRepairMaterial() {
				return Ingredient.fromStacks(new ItemStack(Blocks.OAK_LOG, (int) (1)), new ItemStack(Blocks.SPRUCE_LOG, (int) (1)),
						new ItemStack(Blocks.BIRCH_LOG, (int) (1)), new ItemStack(Blocks.JUNGLE_LOG, (int) (1)),
						new ItemStack(Blocks.ACACIA_LOG, (int) (1)), new ItemStack(Blocks.DARK_OAK_LOG, (int) (1)),
						new ItemStack(Blocks.WARPED_STEM, (int) (1)), new ItemStack(Blocks.CRIMSON_STEM, (int) (1)));
			}
		}, 1, -3.7f, new Item.Properties().group(ItemGroup.COMBAT)) {
		}.setRegistryName("wooden_hammer"));
	}
}
