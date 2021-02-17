package xyz.herty.ocextras.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        /*if (te instanceof TILEENTITY1) {
            return new CONTAINER(player.inventory, (TILEENTITY1) te);
        }*/
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        /*if (te instanceof TILEENTITY1) {
            TILEENTITY1 containerTileEntity = (TILEENTITY1) te;
            return new GUI(containerTileEntity, new CONTAINER(player.inventory, containerTileEntity))
        }*/
        return null;
    }
}
