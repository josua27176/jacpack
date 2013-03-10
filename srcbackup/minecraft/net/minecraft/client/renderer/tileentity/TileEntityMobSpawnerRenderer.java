package net.minecraft.client.renderer.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class TileEntityMobSpawnerRenderer extends TileEntitySpecialRenderer
{
    public void renderTileEntityMobSpawner(TileEntityMobSpawner par1TileEntityMobSpawner, double par2, double par4, double par6, float par8)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2 + 0.5F, (float)par4, (float)par6 + 0.5F);
        Entity var9 = par1TileEntityMobSpawner.getMobEntity();

        if (var9 != null)
        {
            var9.setWorld(par1TileEntityMobSpawner.getWorldObj());
            float var10 = 0.4375F;
            GL11.glTranslatef(0.0F, 0.4F, 0.0F);
            GL11.glRotatef((float)(par1TileEntityMobSpawner.yaw2 + (par1TileEntityMobSpawner.yaw - par1TileEntityMobSpawner.yaw2) * (double)par8) * 10.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
            GL11.glTranslatef(0.0F, -0.4F, 0.0F);
            GL11.glScalef(var10, var10, var10);
            var9.setLocationAndAngles(par2, par4, par6, 0.0F, 0.0F);
            RenderManager.instance.renderEntityWithPosYaw(var9, 0.0D, 0.0D, 0.0D, 0.0F, par8);
        }

        GL11.glPopMatrix();
    }

    public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
    {
        this.renderTileEntityMobSpawner((TileEntityMobSpawner)par1TileEntity, par2, par4, par6, par8);
    }
}
