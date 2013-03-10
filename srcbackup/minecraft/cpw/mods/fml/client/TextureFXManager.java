package cpw.mods.fml.client;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_BINDING_2D;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.texturefx.TextureFX;
import net.minecraft.client.texturepacks.ITexturePack;
import net.minecraft.src.ModTextureStatic;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ModContainer;

public class TextureFXManager
{
    private static final TextureFXManager INSTANCE = new TextureFXManager();

    private class TextureProperties
    {
        private int textureId;
        private Dimension dim;
    }

    private Map<Integer,TextureProperties> textureProperties = Maps.newHashMap();
    private Multimap<String, OverrideInfo> overrideInfo = ArrayListMultimap.create();
    private HashSet<OverrideInfo> animationSet = new HashSet<OverrideInfo>();

    private List<TextureFX> addedTextureFX = new ArrayList<TextureFX>();

    private Minecraft client;

    void setClient(Minecraft client)
    {
        this.client = client;
    }

    public boolean onUpdateTextureEffect(TextureFX effect)
    {
        ITextureFX ifx = (effect instanceof ITextureFX ? ((ITextureFX)effect) : null);

        if (ifx != null && ifx.getErrored())
        {
            return false;
        }

        String name = effect.getClass().getSimpleName();
        client.mcProfiler.startSection(name);
        try
        {
            if (!FMLClientHandler.instance().hasOptifine())
            {
                effect.onTick();
            }
        }
        catch (Exception e)
        {
            FMLLog.log("fml.TextureManager", Level.WARNING, "Texture FX %s has failed to animate. Likely caused by a texture pack change that they did not respond correctly to", name);
            if (ifx != null)
            {
                ifx.setErrored(true);
            }
            client.mcProfiler.endSection();
            return false;
        }
        client.mcProfiler.endSection();

        if (ifx != null)
        {
            Dimension dim = getTextureDimensions(effect);
            int target = ((dim.width >> 4) * (dim.height >> 4)) << 2;
            if (effect.imageData.length != target)
            {
                FMLLog.log("fml.TextureManager", Level.WARNING, "Detected a texture FX sizing discrepancy in %s (%d, %d)", name, effect.imageData.length, target);
                ifx.setErrored(true);
                return false;
            }
        }
        return true;
    }

    //Quick and dirty image scaling, no smoothing or fanciness, meant for speed as it will be called every tick.
    public void scaleTextureFXData(byte[] data, ByteBuffer buf, int target, int length)
    {
        int sWidth = (int)Math.sqrt(data.length / 4);
        int factor = target / sWidth;
        byte[] tmp = new byte[4];

        buf.clear();

        if (factor > 1)
        {
            for (int y = 0; y < sWidth; y++)
            {
                int sRowOff = sWidth * y;
                int tRowOff = target * y * factor;
                for (int x = 0; x < sWidth; x++)
                {
                    int sPos = (x + sRowOff) * 4;
                    tmp[0] = data[sPos + 0];
                    tmp[1] = data[sPos + 1];
                    tmp[2] = data[sPos + 2];
                    tmp[3] = data[sPos + 3];

                    int tPosTop = (x * factor) + tRowOff;
                    for (int y2 = 0; y2 < factor; y2++)
                    {
                        buf.position((tPosTop + (y2 * target)) * 4);
                        for (int x2 = 0; x2 < factor; x2++)
                        {
                            buf.put(tmp);
                        }
                    }
                }
            }
        }

        buf.position(0).limit(length);
    }

    public void onPreRegisterEffect(TextureFX effect)
    {
        Dimension dim = getTextureDimensions(effect);
        if (effect instanceof ITextureFX)
        {
            ((ITextureFX)effect).onTextureDimensionsUpdate(dim.width, dim.height);
        }
    }


    public int getEffectTexture(TextureFX effect)
    {
        Integer id = effectTextures.get(effect);
        if (id != null)
        {
            return id;
        }

        int old = GL11.glGetInteger(GL_TEXTURE_BINDING_2D);
        effect.bindImage(client.renderEngine);
        id = GL11.glGetInteger(GL_TEXTURE_BINDING_2D);
        GL11.glBindTexture(GL_TEXTURE_2D, old);
        effectTextures.put(effect, id);
        effect.textureId = id;
        return id;
    }

    public void onTexturePackChange(RenderEngine engine, ITexturePack texturepack, List<TextureFX> effects)
    {
        pruneOldTextureFX(texturepack, effects);

        for (TextureFX tex : effects)
        {
            if (tex instanceof ITextureFX)
            {
                ((ITextureFX)tex).onTexturePackChanged(engine, texturepack, getTextureDimensions(tex));
            }
        }

        loadTextures(texturepack);
    }

    private HashMap<Integer, Dimension> textureDims = new HashMap<Integer, Dimension>();
    private IdentityHashMap<TextureFX, Integer> effectTextures = new IdentityHashMap<TextureFX, Integer>();
    private ITexturePack earlyTexturePack;
    public void setTextureDimensions(int id, int width, int height, List<TextureFX> effects)
    {
        Dimension dim = new Dimension(width, height);
        textureDims.put(id, dim);

        for (TextureFX tex : effects)
        {
            if (getEffectTexture(tex) == id && tex instanceof ITextureFX)
            {
                ((ITextureFX)tex).onTextureDimensionsUpdate(width, height);
            }
        }
    }

    public Dimension getTextureDimensions(TextureFX effect)
    {
        return getTextureDimensions(getEffectTexture(effect));
    }

    public Dimension getTextureDimensions(int id)
    {
        return textureDims.get(id);
    }

    public void addAnimation(TextureFX anim)
    {
        OverrideInfo info=new OverrideInfo();
        info.index=anim.iconIndex;
        info.imageIndex=anim.tileImage;
        info.textureFX=anim;
        if (animationSet.contains(info)) {
            animationSet.remove(info);
        }
        animationSet.add(info);
    }


    public void loadTextures(ITexturePack texturePack)
    {
        registerTextureOverrides(client.renderEngine);
    }


    public void registerTextureOverrides(RenderEngine renderer) {
        for (OverrideInfo animationOverride : animationSet) {
            renderer.registerTextureFX(animationOverride.textureFX);
            addedTextureFX.add(animationOverride.textureFX);
            FMLLog.log("fml.TextureManager", Level.FINE, "Registered texture override %d (%d) on %s (%d)", animationOverride.index, animationOverride.textureFX.iconIndex, animationOverride.textureFX.getClass().getSimpleName(), animationOverride.textureFX.tileImage);
        }

        for (String fileToOverride : overrideInfo.keySet()) {
            for (OverrideInfo override : overrideInfo.get(fileToOverride)) {
                try
                {
                    BufferedImage image=loadImageFromTexturePack(renderer, override.override);
                    ModTextureStatic mts=new ModTextureStatic(override.index, 1, override.texture, image);
                    renderer.registerTextureFX(mts);
                    addedTextureFX.add(mts);
                    FMLLog.log("fml.TextureManager", Level.FINE, "Registered texture override %d (%d) on %s (%d)", override.index, mts.iconIndex, override.texture, mts.tileImage);
                }
                catch (IOException e)
                {
                    FMLLog.log("fml.TextureManager", Level.WARNING, e, "Exception occurred registering texture override for %s", fileToOverride);
                }
            }
        }
    }

    protected void registerAnimatedTexturesFor(ModContainer mod)
    {
    }

    public void onEarlyTexturePackLoad(ITexturePack fallback)
    {
        if (client==null) {
            // We're far too early- let's wait
            this.earlyTexturePack = fallback;
        } else {
            loadTextures(fallback);
        }
    }


    public void pruneOldTextureFX(ITexturePack var1, List<TextureFX> effects)
    {
        ListIterator<TextureFX> li = addedTextureFX.listIterator();
        while (li.hasNext())
        {
            TextureFX tex = li.next();
            if (tex instanceof FMLTextureFX)
            {
                if (((FMLTextureFX)tex).unregister(client.renderEngine, effects))
                {
                    li.remove();
                }
            }
            else
            {
                effects.remove(tex);
                li.remove();
            }
        }
    }
    public void addNewTextureOverride(String textureToOverride, String overridingTexturePath, int location) {
        OverrideInfo info = new OverrideInfo();
        info.index = location;
        info.override = overridingTexturePath;
        info.texture = textureToOverride;
        overrideInfo.put(textureToOverride, info);
        FMLLog.log("fml.TextureManager", Level.FINE, "Overriding %s @ %d with %s. %d slots remaining",textureToOverride, location, overridingTexturePath, SpriteHelper.freeSlotCount(textureToOverride));
    }

    public BufferedImage loadImageFromTexturePack(RenderEngine renderEngine, String path) throws IOException
    {
        InputStream image=client.texturePackList.getSelectedTexturePack().getResourceAsStream(path);
        if (image==null) {
            throw new RuntimeException(String.format("The requested image path %s is not found",path));
        }
        BufferedImage result=ImageIO.read(image);
        if (result==null)
        {
            throw new RuntimeException(String.format("The requested image path %s appears to be corrupted",path));
        }
        return result;
    }

    public static TextureFXManager instance()
    {
        return INSTANCE;
    }

    public void fixTransparency(BufferedImage loadedImage, String textureName)
    {
        if (textureName.matches("^/mob/.*_eyes.*.png$"))
        {
            for (int x = 0; x < loadedImage.getWidth(); x++) {
                for (int y = 0; y < loadedImage.getHeight(); y++) {
                    int argb = loadedImage.getRGB(x, y);
                    if ((argb & 0xff000000) == 0 && argb != 0) {
                        loadedImage.setRGB(x, y, 0);
                    }
                }
            }
        }
    }

}
