package xyz.herty.ocextras.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.herty.ocextras.OCExtras;
import xyz.herty.ocextras.common.tileentity.TileEntityBoomBox;

public class BlockBoomBox extends BlockOCEBase {
    public static final String NAME = "boombox";
    public static Block DEFAULTITEM;

    public static final int GUI_ID = 1;

    public BlockBoomBox() {
        super(NAME, Material.IRON, 2f);
    }

    @Override
    public TileEntity createNewTileEntity(World w, int m) {
        return new TileEntityBoomBox();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hx, float hy, float hz) {
        if (world.isRemote) return true;
        TileEntity te = world.getTileEntity(pos);
        if (!(te instanceof TileEntityBoomBox)) return false;
        player.openGui(OCExtras.instance, GUI_ID, world, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof IInventory && te instanceof TileEntityBoomBox) {
            if (((TileEntityBoomBox)te).isPasswordSet()) {
                InventoryHelper.dropInventoryItems(world, pos, (IInventory) te);
                world.updateComparatorOutputLevel(pos, this);
            }
        }
        super.breakBlock(world, pos, state);
    }
}
