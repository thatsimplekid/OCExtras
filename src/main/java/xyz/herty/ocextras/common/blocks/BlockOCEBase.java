package xyz.herty.ocextras.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import scala.actors.threadpool.Arrays;
import xyz.herty.ocextras.BuildInfo;
import xyz.herty.ocextras.common.ContentRegistry;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public abstract class BlockOCEBase extends Block implements ITileEntityProvider {
    protected Random random;

    public static final PropertyDirection PROPERTYFACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public String lore = "";

    protected BlockOCEBase(String name, Material material, float hardness) {
        super(material);
        setUnlocalizedName(BuildInfo.modID + "." + name);
        setRegistryName(BuildInfo.modID, name);
        setHardness(hardness);
        setCreativeTab(ContentRegistry.ocetab);
        random = new Random();
    }

    @Override
    @Deprecated
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.getHorizontal(meta);
        return getDefaultState().withProperty(PROPERTYFACING, facing);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        if (GuiScreen.isShiftKeyDown()) {
            String formatted = new TextComponentTranslation("lore." + this.getRegistryName()).getFormattedText();
            tooltip.addAll(Arrays.asList(formatted.split("\\|")));
        } else {
            tooltip.add(new TextComponentTranslation("lore.ocextras.toolong").getFormattedText());
        }
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int facingbits = getFacing(state).getHorizontalIndex();
        return facingbits;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, PROPERTYFACING);
    }

    @Override
    @Deprecated
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing blockFaceClickedOn, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        EnumFacing enumfacing = (placer == null) ? EnumFacing.NORTH : EnumFacing.fromAngle(placer.rotationYaw);
        return getDefaultState().withProperty(PROPERTYFACING, enumfacing);
    }

    public static EnumFacing getFacing(IBlockState state){
        return state.getValue(PROPERTYFACING);
    }

}
