package xyz.herty.ocextras.common.tileentity;

import li.cil.oc.api.Network;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.Visibility;
import net.minecraft.block.Block;
import xyz.herty.ocextras.common.interfaces.IOwner;

import java.util.UUID;

public class TileEntityGPS extends TileEntityOCEBase implements IOwner {

    private UUID ownerUUID;
    public Block block;

    public void setOwner(UUID uuid) {
        this.ownerUUID = uuid;
    }

    public UUID getOwner() {
        return this.ownerUUID;
    }

    public TileEntityGPS() {
        super("oce_gps");
        node = Network.newNode(this, Visibility.Network).withComponent(getComponentName()).withConnector(32000).create();
    }

    @Callback(doc = "function():int; gets the current x position of the device.", direct = true)
    public Object[] getX(Context ctx, Arguments arg) {
        return new Object[]{ this.getPos().getX() };
    }

    @Callback(doc = "function():int; gets the current y position of the device.", direct = true)
    public Object[] getY(Context ctx, Arguments arg) {
        return new Object[]{ this.getPos().getY() };
    }

    @Callback(doc = "function():int; gets the current z position of the device.", direct = true)
    public Object[] getZ(Context ctx, Arguments arg) {
        return new Object[]{ this.getPos().getZ() };
    }

    @Callback(doc = "function():int, int, int; gets the current position of the device. (x, y, z)", direct = true)
    public Object[] getPos(Context ctx, Arguments arg) {
        return new Object[]{ this.getPos().getX(), this.getPos().getY(), this.getPos().getZ() };
    }

    @Callback(doc = "function():int; gets the dimension ID of the dimension where the device resides", direct = true)
    public Object[] getDimension(Context ctx, Arguments arg) {
        return new Object[]{ (int) this.getWorld().provider.getDimension() };
    }
}
