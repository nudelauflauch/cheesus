package net.stehschnitzel.cheesus.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.stehschnitzel.cheesus.common.blocks.entities.CheeseCoverBlockEntity;
import net.stehschnitzel.cheesus.init.BlockInit;
import net.stehschnitzel.cheesus.init.CheesusTags;

public class CheeseCoverEntityRenderer implements BlockEntityRenderer<CheeseCoverBlockEntity> {
    public CheeseCoverEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(CheeseCoverBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = pBlockEntity.inventory.getStackInSlot(0);
        BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();

        pPoseStack.pushPose();

        if (stack.is(CheesusTags.Items.CHEESE) && stack.getItem() instanceof BlockItem blockItem) {
            pPoseStack.translate(0.1f, 0.15f, 0.1f);
            pPoseStack.scale(0.8f, 0.8f, 0.8f);

            blockRenderer.renderSingleBlock(blockItem.getBlock().defaultBlockState(),
                    pPoseStack, pBufferSource, getLightLevel(pBlockEntity.getLevel(),
                            pBlockEntity.getBlockPos()), pPackedOverlay);

        } else if (stack.getItem() instanceof BlockItem blockItem) {
            pPoseStack.translate(0.25f, 0.15f, 0.25f);
            pPoseStack.scale(0.5f, 0.5f, 0.5f);
            blockRenderer.renderSingleBlock(blockItem.getBlock().defaultBlockState(),
                    pPoseStack, pBufferSource, getLightLevel(pBlockEntity.getLevel(),
                            pBlockEntity.getBlockPos()), pPackedOverlay);

        } else {
            pPoseStack.scale(0.6f, 0.6f, 0.6f);
            pPoseStack.translate(0.8f, 0.3f, 0.8f);
            pPoseStack.mulPose(Axis.XN.rotationDegrees((float) -90));
            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(pBlockEntity.getLevel(),
                    pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 1);
        }

        pPoseStack.popPose();
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }

}
