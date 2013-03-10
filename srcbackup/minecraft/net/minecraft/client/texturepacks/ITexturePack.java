package net.minecraft.client.texturepacks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.InputStream;
import net.minecraft.client.renderer.RenderEngine;

@SideOnly(Side.CLIENT)
public interface ITexturePack
{
    /**
     * Delete the OpenGL texture id of the pack's thumbnail image, and close the zip file in case of TexturePackCustom.
     */
    void deleteTexturePack(RenderEngine var1);

    /**
     * Bind the texture id of the pack's thumbnail image, loading it if necessary.
     */
    void bindThumbnailTexture(RenderEngine var1);

    /**
     * Gives a texture resource as InputStream.
     */
    InputStream getResourceAsStream(String var1);

    /**
     * Get the texture pack ID
     */
    String getTexturePackID();

    /**
     * Get the file name of the texture pack, or Default if not from a custom texture pack
     */
    String getTexturePackFileName();

    /**
     * Get the first line of the texture pack description (read from the pack.txt file)
     */
    String getFirstDescriptionLine();

    /**
     * Get the second line of the texture pack description (read from the pack.txt file)
     */
    String getSecondDescriptionLine();

    /**
     * Return the texture pack's resolution (16 by default). Used only by PlayerUsageSnooper. Presumably meant to be
     * overriden by HD texture mods.
     */
    int getTexturePackResolution();
}
