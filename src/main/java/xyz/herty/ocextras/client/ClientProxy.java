package xyz.herty.ocextras.client;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.herty.ocextras.Config;
import xyz.herty.ocextras.OCExtras;
import xyz.herty.ocextras.common.CommonProxy;
import xyz.herty.ocextras.common.ContentRegistry;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @SubscribeEvent
    public void renderWorldLastEvent(RenderWorldLastEvent event) {

    }

    @SubscribeEvent
    public void colorHandlerEventBlock(ColorHandlerEvent.Block event) {

    }

    @Override
    public World getWorld(int dimID) {
        World world = Minecraft.getMinecraft().world;
        return world.provider.getDimension() == dimID ? world : null;
    }

    @Override
    public void preinit() {
        super.preinit();
        Config.clientPreInit();
        MinecraftForge.EVENT_BUS.register(this);
        if (OCExtras.debug)
            OCExtras.logger.info("Registered renderers/models");
    }

    @Override
    public void init() {
        super.init();
        Minecraft mc = Minecraft.getMinecraft();
    }

    @Override
    public void registerModels() {
        for (Block block : ContentRegistry.modBlocks)
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName().toString(), "inventory"));

        for (ItemStack itemStack : ContentRegistry.modBlocksWithItem.values())
            ModelLoader.setCustomModelResourceLocation(itemStack.getItem(), 0, new ModelResourceLocation(itemStack.getItem().getRegistryName().toString()));

        for (ItemStack itemStack : ContentRegistry.modItems)
            ModelLoader.setCustomModelResourceLocation(itemStack.getItem(), 0, new ModelResourceLocation(itemStack.getItem().getRegistryName().toString()));
    }



}
