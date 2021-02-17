package xyz.herty.ocextras.common.tileentity;

import li.cil.oc.api.API;
import li.cil.oc.api.Network;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.herty.ocextras.BuildInfo;
import xyz.herty.ocextras.OCExtras;

public class TileEntityOCEBase extends TileEntity implements ITickable, ManagedEnvironment {
    public ComponentConnector node;
    private ManagedEnvironment oc_fs;

    protected EnvironmentHost container;
    private final String componentName;
    protected boolean isUpgrade = false;

    public TileEntityOCEBase(String name) {
        super();
        componentName = name;
    }

    public TileEntityOCEBase(String name, EnvironmentHost host) {
        isUpgrade = true;
        componentName = name;
        container = host;
        setupNode();
    }

    @Override
    public void update() {
        if (node() != null && node().network() == null) {
            Network.joinOrCreateNetwork(this);
        }
    }

    protected ManagedEnvironment oc_fs() {
        return this.oc_fs;
    }

    protected void initOCFilesystem(String path, String name) {
        oc_fs = li.cil.oc.api.FileSystem.asManagedEnvironment(li.cil.oc.api.FileSystem.fromClass(OCExtras.class, BuildInfo.modID, path), name);
        ((Component) oc_fs().node()).setVisibility(Visibility.Network);
    }

    protected String getComponentName() {
        return componentName;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        if(!isUpgrade)
            super.readFromNBT(nbt);
        if (node() != null && node().host() == this) {
            node().load(nbt.getCompoundTag("oc:node"));
        }
        if (oc_fs() != null && oc_fs().node() != null) {
            oc_fs().node().load(nbt.getCompoundTag("oc:fs"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        if(!isUpgrade)
            nbt = super.writeToNBT(nbt);
        if (node() != null && node().host() == this) {
            NBTTagCompound nodeNbt = new NBTTagCompound();
            node().save(nodeNbt);
            nbt.setTag("oc:node", nodeNbt);
        }
        if (oc_fs() != null && oc_fs().node() != null) {
            NBTTagCompound fsNbt = new NBTTagCompound();
            oc_fs().node().save(fsNbt);
            nbt.setTag("oc:fs", fsNbt);
        }
        return nbt;
    }

    @Override
    public Node node() {
        return node;
    }

    @Override
    public void onChunkUnload() {
        super.onChunkUnload();
        if (node != null)
            node.remove();
    }

    @Override
    public void invalidate() {
        super.invalidate();
        if (node != null)
            node.remove();
    }

    @Override
    public void onConnect(Node arg0) {}

    @Override
    public void onDisconnect(final Node node) {}

    @Override
    public void onMessage(Message arg0) {}

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(super.getUpdateTag());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(getPos(), 1, getUpdateTag());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        readFromNBT(tag);
    }


    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }

    // methods used for upgrades

    public void setupNode() {
        if (this.node() == null) {
            this.node = API.network.newNode(this, Visibility.Neighbors).withConnector().withComponent(this.getComponentName()).create();
        }
    }

    @Override
    public boolean canUpdate(){ return false; }

    @Override
    public void load(NBTTagCompound nbt) {
        this.setupNode();
        readFromNBT(nbt);
    }

    @Override
    public void save(NBTTagCompound nbt) {
        this.setupNode();
        writeToNBT(nbt);
    }

    @Override
    public World getWorld(){
        if(isUpgrade)
            return container.world();

        return super.getWorld();
    }


    @Override
    public BlockPos getPos(){
        if(isUpgrade)
            return new BlockPos(container.xPosition(), container.yPosition(), container.zPosition());

        return super.getPos();
    }
}
