package org.Jacpack.TechUp.item.armor;

import org.Jacpack.TechUp.api.JACTools;
import org.Jacpack.TechUp.creativetabs.CreativeTabsHandler;
import org.Jacpack.TechUp.item.ModItems;
import org.Jacpack.TechUp.util.misc.Reference;

import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IArmorTextureProvider;

public class ItemHazmatArmor extends ItemArmor implements IArmorTextureProvider
{
	 public ItemHazmatArmor(int i, EnumArmorMaterial enumArmorMaterial, int l)
	    {
	        super(i, enumArmorMaterial, 0, l);
	        
	        this.setMaxDamage(enumArmorMaterial.getDurability(l));
	        this.setTextureFile(Reference.SPRITE_SHEET_LOCATION + Reference.ITEM_SPRITE_SHEET);
	        this.setCreativeTab(CreativeTabsHandler.tabTechUpI);
	    }
	
	@Override
	public String getArmorTextureFile(ItemStack itemstack) {
		return "/FILE LOCATION!";
	}
	
	/**
	 * Return whether this item is repairable in a anvil.
	 */
	public boolean getIsRepairable(ItemStack itemstack1, ItemStack itemstack2) {
		return JACTools.isOreClass(itemstack2, "");
		
	}
}
	

