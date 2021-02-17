package xyz.herty.ocextras.common.tileentity;

import li.cil.oc.api.Network;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.Visibility;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityBoomBox extends TileEntityOCEBase implements ITickable {

    public static final int SIZE = 2;

    private ItemStackHandler inventoryInput;
    private boolean redstone = false;
    private String password = "";


    public TileEntityBoomBox() {
        super("oce_boombox");
        node = Network.newNode(this, Visibility.Network).withComponent(getComponentName()).withConnector(32000).create();
        inventoryInput = new ItemStackHandler(1);
    }

    public boolean isRedstoneEnabled() {
        return redstone;
    }

    public boolean isPasswordSet() {
        return password.length() > 0;
    }

    public String getPassword() {
        return password;
    }

    @Callback(doc = "function():string; gets the currently held explosive's registry name", direct = true)
    public Object[] getExplosive(Context ctx, Arguments arg) {
        return new Object[]{ inventoryInput.getStackInSlot(0).getItem().getRegistryName() };
    }

    @Callback(doc = "function(string: new, string: old):boolean; sets the device password, returns true on success", direct = true)
    public Object[] setPassword(Context ctx, Arguments arg) {
        String newPass = arg.checkString(0);
        String oldPass = arg.optString(1, "");
        if (!oldPass.equals(this.password)) return new Object[]{ false };
        this.password = newPass;
        return new Object[]{ true };
    }

    @Callback(doc = "function(boolean: enabled, string: password):boolean; enables or disables redstone activation, returns true on success", direct = true)
    public Object[] setRedstone(Context ctx, Arguments arg) {
        boolean rsState = arg.checkBoolean(0);
        String pass = arg.optString(1, "");
        if (!pass.equals(this.password)) return new Object[]{ false };
        this.redstone = rsState;
        return new Object[]{ true };
    }

    @Callback(doc = "function(string password):String; detonates the explosive - returns false if none present", direct = true)
    public Object[] detonate(Context ctx, Arguments arg) {
        String pass = arg.optString(0, "");
        if (!pass.equals(this.password)) return new Object[]{ false };
        // if (inventoryInput.getStackInSlot(0).getCount() < 1) return new Object[]{ false };
        // inventoryInput.getStackInSlot(0).setCount(inventoryInput.getStackInSlot(0).getCount() - 1);
        EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(this.world, (double)((float)pos.getX() + 0.5F), (double)pos.getY(), (double)((float)pos.getZ() + 0.5F), (EntityLivingBase) null);
        this.world.spawnEntity(entitytntprimed);
        this.world.playSound((EntityPlayer)null, entitytntprimed.posX, entitytntprimed.posY, entitytntprimed.posZ, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
        return new Object[]{ true };
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        inventoryInput.deserializeNBT(getTileData().getCompoundTag("inv"));
        this.password = nbt.getString("password");
        this.redstone = nbt.getBoolean("redstone");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setTag("inv", inventoryInput.serializeNBT());
        nbt.setString("password", this.password);
        nbt.setBoolean("redstone", this.redstone);
        return nbt;
    }
}
