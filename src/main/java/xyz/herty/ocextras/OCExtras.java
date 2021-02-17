package xyz.herty.ocextras;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.herty.ocextras.common.CommonProxy;
import xyz.herty.ocextras.common.ContentRegistry;
import xyz.herty.ocextras.manual.Manual;

@Mod.EventBusSubscriber
@Mod(
        modid = BuildInfo.modID,
        name = BuildInfo.modName,
        version = BuildInfo.majorNumber + "." + BuildInfo.minorNumber + "." + BuildInfo.buildNumber,
        dependencies = "required-after:opencomputers;",
        updateJSON = "https://oce.fla.herty.xyz/update",
        guiFactory = OCExtras.GUIFACTORY
)
public class OCExtras {
    @Mod.Instance(value = BuildInfo.modID)
    public static OCExtras instance;

    @SidedProxy(clientSide = "xyz.herty.ocextras.client.ClientProxy", serverSide = "xyz.herty.ocextras.common.CommonProxy")
    public static CommonProxy proxy;

    public static final String GUIFACTORY = "xyz.herty.ocextras.client.config.ConfigGUI";

    public static final String ASSETSPATH = "mods/OCExtras/assets/ocextras";

    public static boolean debug = false;

    public static final Logger logger = LogManager.getFormatterLogger(BuildInfo.modID);

    public static SimpleNetworkWrapper network;

    public static ContentRegistry contentRegistry = new ContentRegistry();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        long time = System.nanoTime();
        Config.preInit();
        Manual.preinit();
        //debug = Config.getConfig().getCategory("general").get("enableDebugMessages").getBoolean();
        ContentRegistry.preInit();
        MinecraftForge.EVENT_BUS.register(contentRegistry);
        network = NetworkRegistry.INSTANCE.newSimpleChannel(BuildInfo.modID);
        proxy.preinit();
        int packetID = 0;
        // network.registerMessage(Handler.class, Packet.class, packetID++, Side.CLIENT);
        if (OCExtras.debug) {
            logger.info("Registered " + packetID + " packets");
            logger.info("Finished pre-init in %d md", (System.nanoTime() - time) / 1000000);
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        long time = System.nanoTime();
        proxy.init();
        ContentRegistry.init();
        if (OCExtras.debug)
            logger.info("Finished init in %d ms", (System.nanoTime() - time) / 1000000);
    }

    @SubscribeEvent
    public static void onRegisterModels(ModelRegistryEvent event) {
        proxy.registerModels();
    }

}
