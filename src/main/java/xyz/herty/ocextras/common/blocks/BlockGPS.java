package xyz.herty.ocextras.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xyz.herty.ocextras.common.tileentity.TileEntityGPS;

public class BlockGPS extends BlockOCEBase {
    public static final String NAME = "gps_block";
    public static Block DEFAULTITEM;

    public BlockGPS() {
        super(NAME, Material.IRON, 1f);
    }

    public void onBlockPlacedBy(World w, BlockPos p, IBlockState s, EntityLivingBase e, ItemStack i) {
        TileEntity te = w.getTileEntity(p);
        ((TileEntityGPS) te).setOwner(e.getUniqueID());
    }

    @Override
    public TileEntity createNewTileEntity(World w, int m) {
        return new TileEntityGPS();
    }
}
