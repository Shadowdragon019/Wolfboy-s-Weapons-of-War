
package net.mcreator.weaponsofwar.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.world.World;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.IItemTier;
import net.minecraft.client.util.ITooltipFlag;

import net.mcreator.weaponsofwar.WwowModElements;

import java.util.List;

@WwowModElements.ModElement.Tag
public class WoodenDaggerItem extends WwowModElements.ModElement {
	@ObjectHolder("wwow:wooden_dagger")
	public static final Item block = null;
	public WoodenDaggerItem(WwowModElements instance) {
		super(instance, 22);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new SwordItem(new IItemTier() {
			public int getMaxUses() {
				return 30;
			}

			public float getEfficiency() {
				return 2f;
			}

			public float getAttackDamage() {
				return -1.5f;
			}

			public int getHarvestLevel() {
				return 0;
			}

			public int getEnchantability() {
				return 15;
			}

			public Ingredient getRepairMaterial() {
				return Ingredient.fromStacks(new ItemStack(SmoothStickPieceItem.block, (int) (1)));
			}
		}, 3, -1.9f, new Item.Properties().group(ItemGroup.COMBAT)) {
			@Override
			public void addInformation(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
				super.addInformation(itemstack, world, list, flag);
				list.add(new StringTextComponent("Backstabs do more damage"));
			}
		}.setRegistryName("wooden_dagger"));
	}
}
