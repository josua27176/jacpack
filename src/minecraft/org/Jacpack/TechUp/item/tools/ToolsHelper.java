package org.Jacpack.TechUp.item.tools;

import net.minecraftforge.common.EnumHelper;
import net.minecraft.item.EnumToolMaterial;

public class ToolsHelper {
	
	/*    
	WOOD(0, 59, 2.0F, 0, 15),
    STONE(1, 131, 4.0F, 1, 5),
    IRON(2, 250, 6.0F, 2, 14),
    EMERALD(3, 1561, 8.0F, 3, 10),
    GOLD(0, 32, 12.0F, 0, 22);
	 */
	
	//int harvestLevel, int maxUses, float efficiency, int damage, int enchantability)
	public static EnumToolMaterial JACBronzeTool = EnumHelper.addToolMaterial("JAC_Bronze_Tool", 3, 351, 8.0F, 2, 14);
	
	public static EnumToolMaterial JACSteelTool = EnumHelper.addToolMaterial("JAC_Bronze_Tool", 3, 351, 8.0F, 2, 14);
	
	public static EnumToolMaterial JACCopperTool = EnumHelper.addToolMaterial("JAC_Bronze_Tool", 3, 351, 8.0F, 2, 14);
	
	public static EnumToolMaterial JACSilverTool = EnumHelper.addToolMaterial("JAC_Bronze_Tool", 3, 351, 8.0F, 2, 14);
	
	public static EnumToolMaterial JACAluminumTool = EnumHelper.addToolMaterial("JAC_Aluminum_Tool", 3, 351, 8.0F, 2, 14);
	

}
