package org.Jacpack.TechUp.util.misc;

/**
 * Reference
 * 
 * General purpose library to contain mod related constants
 * 
 * @author Alexbegt, DJP
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class Reference {
	
	/* Debug Mode On-Off */
    public static final boolean DEBUG_MODE = false;

    /* General Mod related constants */
    public static final String MOD_ID = "TechUp";
    public static final String MOD_NAME = "TechUp";
    public static final String VERSION = "0.0.0.1B";
    public static final String CHANNEL_NAME = MOD_ID;
    public static final String LOGGER_PREFIX = "[" + MOD_ID + "] ";
    public static final int SECOND_IN_TICKS = 20;
    public static final int SHIFTED_ID_RANGE_CORRECTION = 256;
    public static final String SERVER_PROXY_CLASS = "org.Jacpack.TechUp.util.misc.CommonProxy";
    public static final String CLIENT_PROXY_CLASS = "org.Jacpack.TechUp.client.ClientProxy";
    
    /* General Tile Entity related constants */
    public static final String TE_GEN_OWNER_NBT_TAG_LABEL = "owner";
    public static final String TE_GEN_STATE_NBT_TAG_LABEL = "state";
    public static final String TE_GEN_DIRECTION_NBT_TAG_LABEL = "direction";
    
    /* Texture related constants */
    public static final String SPRITE_SHEET_LOCATION = "/net/Harmonion/client/textures/";
    public static final String ITEM_SPRITE_SHEET = "Harmonion_item.png";
    public static final String BLOCK_SPRITE_SHEET = "Harmonion_block.png";
    
}
