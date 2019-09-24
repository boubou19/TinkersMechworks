package slimeknights.tmechworks.client.model;

import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelProperty;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DisguiseBakedModel extends BakedModelWrapper<IBakedModel> {
    public static final ModelProperty<ItemStack> DISGUISE = new ModelProperty<>();

    public DisguiseBakedModel(IBakedModel originalModel) {
        super(originalModel);
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
        if(extraData.hasProperty(DISGUISE)) {
            ItemStack disguise = extraData.getData(DISGUISE);

            if(disguise != null && disguise.getItem() instanceof BlockItem) {
                BlockItem disguiseItem = (BlockItem) disguise.getItem();
                BlockState disguiseState = disguiseItem.getBlock().getDefaultState();
                if(disguiseState.has(DirectionalBlock.FACING))
                    disguiseState = disguiseState.with(DirectionalBlock.FACING, state.get(DirectionalBlock.FACING));

                if(disguiseState.canRenderInLayer(MinecraftForgeClient.getRenderLayer())) {
                    IBakedModel model = Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelShapes().getModel(disguiseState);
                    return model.getQuads(disguiseState, side, rand, extraData);
                } else {
                    return Collections.emptyList();
                }
            }
        }

        return super.getQuads(state, side, rand, extraData);
    }

    @Nonnull
    @Override
    public IModelData getModelData(@Nonnull IEnviromentBlockReader world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull IModelData tileData) {
        return super.getModelData(world, pos, state, tileData);
    }
}