package com.kpabr.DeeperCaves.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

import com.kpabr.DeeperCaves.DeeperCaves;
import com.kpabr.DeeperCaves.DeeperTeleporter;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDeeperPortal extends BlockBase
{
    public static boolean field_149948_a;
    private int dim;

    public BlockDeeperPortal(Material p_i45404_1_, int dimension)
    {
        super(p_i45404_1_);
        this.setLightLevel(1.0F);
        this.dim = dimension;

    }

    /**
     * Adds all intersecting collision boxes to a list. (Be sure to only add boxes to the list if they intersect the
     * mask.) Parameters: World, X, Y, Z, mask, list, colliding entity
     */
    public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_) {}


    @Override
    public boolean isNormalCube()
    {
    	return false;
    }
    @Override
	public Item getItemDropped(int par1, Random par2Random, int par3)
    {
        return (this.dim == DeeperCaves.worldgen.dropDimID)?Item.getItemFromBlock(DeeperCaves.blocks.dropPortal):null;
    }
    @Override
    public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata)
    {
    	return false;
    }
    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_)
    {
        if (p_149670_5_.ridingEntity == null && p_149670_5_.riddenByEntity == null && !p_149670_1_.isRemote)
        {

        	try
        	{
        	EntityPlayerMP player = (EntityPlayerMP)p_149670_5_;
        	if(player.dimension != this.dim && !(this.dim == 7 && player.dimension > 7))
        	{
        	player.mcServer.getConfigurationManager().transferPlayerToDimension(player, this.dim, new DeeperTeleporter(player.mcServer.worldServerForDimension(this.dim)));
        	}
        	}
        	catch(ClassCastException e)
        	{
        		return; //not a player
        	}
        }
    }



    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
    	this.blockIcon = p_149651_1_.registerIcon("deepercaves:portal_2");
    }

    public MapColor getMapColor(int p_149728_1_)
    {
        return MapColor.obsidianColor;
    }
}
