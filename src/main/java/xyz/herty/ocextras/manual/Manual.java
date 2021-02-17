package xyz.herty.ocextras.manual;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import xyz.herty.ocextras.BuildInfo;
import xyz.herty.ocextras.common.ContentRegistry;

import java.util.HashSet;

public class Manual {
    private static ResourceLocation iconResourceLocation = new ResourceLocation(BuildInfo.modID, "textures/blocks/meta_block.png");
    private static String tooltip = "OCExtras";
    private static String homepage = "assets/" + BuildInfo.modID + "/doc/_sidebar";

    public static HashSet<Item> items = new HashSet<>();

    public static void preinit() {
        if (Loader.isModLoaded("opencomputers")) {
            new ManualPathProviderOC().initialize(iconResourceLocation, tooltip, homepage);
        }
    }
}
