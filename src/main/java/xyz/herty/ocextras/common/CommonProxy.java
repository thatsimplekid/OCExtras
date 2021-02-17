package xyz.herty.ocextras.common;

import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import xyz.herty.ocextras.OCExtras;

public class CommonProxy {

    public World getWorld(int dimId) {
        return null;
    }

    public void init() {
        NetworkRegistry.INSTANCE.registerGuiHandler(OCExtras.instance, new GuiHandler());
    }

    public void preinit() {

    }

    protected void registerRenderers() {

    }

    public void registerModels() {

    }

}
