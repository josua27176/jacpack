package org.Jacpack.TechUp.client.render;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.Jacpack.TechUp.client.ClientProxy;
import org.Jacpack.TechUp.util.Config;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;

public class JacPack_Renderer extends RenderPlayer
{
    public JacPack_Renderer()
    {
        RenderManager.instance.entityRenderMap.remove(EntityPlayer.class);
        RenderManager.instance.entityRenderMap.put(EntityPlayer.class, this);
        this.setRenderManager(RenderManager.instance);
    }
    
    public static boolean getPotion(EntityLiving var0, int var1)
    {
        try
        {
            Field var2 = null;
            Field[] var3 = EntityLiving.class.getDeclaredFields();
            int var4 = var3.length;

            for (int var5 = 0; var5 < var4; ++var5)
            {
                Field var6 = var3[var5];

                if (var6.getType() == HashMap.class)
                {
                    var2 = var6;
                    var6.setAccessible(true);
                    break;
                }
            }

            if (var2 != null)
            {
                return ((HashMap)var2.get(var0)).get(Integer.valueOf(var1)) != null;
            }
        }
        catch (Throwable var7)
        {
            ;
        }

        return false;
    }
    
    public static boolean getFullInvisibility(EntityPlayer var0)
    {
        return false;
    }

    public void renderPlayer(EntityPlayer par1EntityPlayer, double var2, double var4, double var6, float var8, float var9)
    {
        if (!getFullInvisibility(par1EntityPlayer))
        {
            super.renderPlayer(par1EntityPlayer, var2, var4, var6, var8, var9);
        }
    }

    /**
     * Method for adding special render rules
     */
    public void renderSpecials(EntityPlayer par1EntityPlayer, float var2)
    {
        if (!getFullInvisibility(par1EntityPlayer))
        {
            if (!getPotion(par1EntityPlayer, Integer.valueOf(Potion.invisibility.id).intValue()))
            {
                super.renderSpecials(par1EntityPlayer, var2);
                boolean var3 = false;

                try
                {
                    if (Config.mShowCapes)
                    {
                        if (Config.StaffJACCapeList.contains(par1EntityPlayer.username))
                        {
                            var3 = this.loadDownloadableImageTexture("https://dl.dropbox.com/u/48633261/minecraft/Capes/TechUpStaff.png", "/org/Jacpack/TechUp/client/textures/TechUpStaff.png");
                        }
                    }
                }
                catch (Throwable var19)
                {
                    ;
                }

                if (var3)
                {
                    GL11.glPushMatrix();
                    GL11.glTranslatef(0.0F, 0.0F, 0.125F);
                    double var4 = par1EntityPlayer.field_71091_bM + (par1EntityPlayer.field_71094_bP - par1EntityPlayer.field_71091_bM) * (double)var2 - (par1EntityPlayer.prevPosX + (par1EntityPlayer.posX - par1EntityPlayer.prevPosX) * (double)var2);
                    double var6 = par1EntityPlayer.field_71096_bN + (par1EntityPlayer.field_71095_bQ - par1EntityPlayer.field_71096_bN) * (double)var2 - (par1EntityPlayer.prevPosY + (par1EntityPlayer.posY - par1EntityPlayer.prevPosY) * (double)var2);
                    double var8 = par1EntityPlayer.field_71097_bO + (par1EntityPlayer.field_71085_bR - par1EntityPlayer.field_71097_bO) * (double)var2 - (par1EntityPlayer.prevPosZ + (par1EntityPlayer.posZ - par1EntityPlayer.prevPosZ) * (double)var2);
                    float var10 = par1EntityPlayer.prevRenderYawOffset + (par1EntityPlayer.renderYawOffset - par1EntityPlayer.prevRenderYawOffset) * var2;
                    double var11 = (double)MathHelper.sin(var10 * (float)Math.PI / 180.0F);
                    double var13 = (double)(-MathHelper.cos(var10 * (float)Math.PI / 180.0F));
                    float var15 = (float)var6 * 10.0F;

                    if (var15 < -6.0F)
                    {
                        var15 = -6.0F;
                    }

                    if (var15 > 32.0F)
                    {
                        var15 = 32.0F;
                    }

                    float var16 = (float)(var4 * var11 + var8 * var13) * 100.0F;
                    float var17 = (float)(var4 * var13 - var8 * var11) * 100.0F;

                    if (var16 < 0.0F)
                    {
                        var16 = 0.0F;
                    }

                    float var18 = par1EntityPlayer.prevCameraYaw + (par1EntityPlayer.cameraYaw - par1EntityPlayer.prevCameraYaw) * var2;
                    var15 += MathHelper.sin((par1EntityPlayer.prevDistanceWalkedModified + (par1EntityPlayer.distanceWalkedModified - par1EntityPlayer.prevDistanceWalkedModified) * var2) * 6.0F) * 32.0F * var18;

                    if (par1EntityPlayer.isSneaking())
                    {
                        var15 += 25.0F;
                    }

                    GL11.glRotatef(6.0F + var16 / 2.0F + var15, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(var17 / 2.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glRotatef(-var17 / 2.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                    ((ModelBiped)this.mainModel).renderCloak(0.0625F);
                    GL11.glPopMatrix();
                }
            }
        }
    }
}
