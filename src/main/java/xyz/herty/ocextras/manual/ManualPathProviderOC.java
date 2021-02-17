package xyz.herty.ocextras.manual;

import li.cil.oc.api.manual.PathProvider;
import li.cil.oc.api.prefab.TextureTabIconRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class ManualPathProviderOC  extends ManualPathProvider implements PathProvider {
    void initialize(ResourceLocation iconResourceLocation, String tooltip, String path) {
        if (FMLCommonHandler.instance().getEffectiveSide().equals(Side.CLIENT)) {
            li.cil.oc.api.Manual.addProvider(new ManualPathProviderOC());
            li.cil.oc.api.Manual.addProvider(new ManualContentProviderOC());
            li.cil.oc.api.Manual.addTab(new TextureTabIconRenderer(iconResourceLocation), tooltip, path);
        }
    }
}
