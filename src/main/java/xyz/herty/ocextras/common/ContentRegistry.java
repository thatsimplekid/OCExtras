package xyz.herty.ocextras.common;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import xyz.herty.ocextras.BuildInfo;
import xyz.herty.ocextras.OCExtras;
import xyz.herty.ocextras.common.blocks.BlockBoomBox;
import xyz.herty.ocextras.common.blocks.BlockGPS;
import xyz.herty.ocextras.common.tileentity.TileEntityBoomBox;
import xyz.herty.ocextras.common.tileentity.TileEntityGPS;
import xyz.herty.ocextras.manual.Manual;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Mod.EventBusSubscriber
public class ContentRegistry {
    public static CreativeTabs ocetab = new CreativeTabs("tabOCExtras") {
        public @Nonnull ItemStack getTabIconItem() {
            return new ItemStack(Items.CHICKEN);
        }
        public @Nonnull String getTranslatedTabLabel() {
            return new TextComponentTranslation("itemGroup.OCExtras.tabOCExtras").getUnformattedText();
        }
    };

    public static final HashSet<Block> modBlocks = new HashSet<>();

    public static final HashMap<Block, ItemStack> modBlocksWithItem = new HashMap<>();

    public static final HashSet<ItemStack> modItems = new HashSet<>();

    static {
        modBlocks.add(BlockGPS.DEFAULTITEM = new BlockGPS());
        modBlocks.add(BlockBoomBox.DEFAULTITEM = new BlockBoomBox());
    }

    public static void preInit() {
        for (Item manualItem : Manual.items)
            modItems.add(new ItemStack(manualItem));
        registerEvents();
    }

    private static void registerEvents() {
        if (OCExtras.debug)
            OCExtras.logger.info("Registered Events");
    }

    public static void init() {

    }

    @SubscribeEvent
    public void registerEntities(RegistryEvent.Register<EntityEntry> event) {

    }

    @SubscribeEvent
    public static void addBlocks(RegistryEvent.Register<Block> event) {
        for (Block block : modBlocks)
            event.getRegistry().register(block);

        for (Block block : modBlocksWithItem.keySet())
            event.getRegistry().register(block);

        registerTileEntity(TileEntityGPS.class, BlockGPS.NAME);
        registerTileEntity(TileEntityBoomBox.class, BlockBoomBox.NAME);
    }

    private static void registerTileEntity(Class<? extends TileEntity> teclass, String key) {
        GameRegistry.registerTileEntity(teclass, new ResourceLocation(BuildInfo.modID, key));
    }

    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent
    public static void addItems(RegistryEvent.Register<Item> event) {
        for (Block block : modBlocks)
            event.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName()));

        for (Map.Entry<Block, ItemStack> entry : modBlocksWithItem.entrySet())
            event.getRegistry().register(entry.getValue().getItem());

        for (ItemStack itemStack : modItems)
            event.getRegistry().register(itemStack.getItem());
    }

}
