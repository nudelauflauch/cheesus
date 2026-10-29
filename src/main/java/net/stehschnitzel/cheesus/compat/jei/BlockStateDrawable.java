package net.stehschnitzel.cheesus.compat.jei;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;

import java.util.List;

//renders a block state the same way block items are drawn in inventories, just at any size
public class BlockStateDrawable implements IDrawable {

    private final BlockState state;
    private final int size;

    public BlockStateDrawable(BlockState state, int size) {
        this.state = state;
        this.size = size;
    }

    @Override
    public int getWidth() {
        return size;
    }

    @Override
    public int getHeight() {
        return size;
    }

    @Override
    public void draw(GuiGraphics guiGraphics, int xOffset, int yOffset) {
        draw(guiGraphics, state, xOffset, yOffset, size);
    }

    public static void draw(GuiGraphics guiGraphics, BlockState state, int x, int y, int size) {
        drawColumn(guiGraphics, List.of(state), x, y, size);
    }

    //draws the states stacked on top of each other (first one at the bottom), scaled down so the column fits into size
    public static void drawColumn(GuiGraphics guiGraphics, List<BlockState> column, int x, int y, int size) {
        //on screen a rotated block is cos(30) high per block plus sin(30) * sqrt(2) for the top face
        float blockScale = 0.625F * 1.573F / (0.866F * column.size() + 0.707F);

        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        pose.translate(x + size / 2F, y + size / 2F, 100);
        pose.scale(size, -size, size);

        //same rotation as the vanilla block item gui transform
        pose.mulPose(Axis.XP.rotationDegrees(30));
        pose.mulPose(Axis.YP.rotationDegrees(225));
        pose.scale(blockScale, blockScale, blockScale);
        pose.translate(-0.5F, -column.size() / 2F, -0.5F);

        Lighting.setupFor3DItems();
        for (BlockState state : column) {
            Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, pose, guiGraphics.bufferSource(),
                    LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, null);
            pose.translate(0, 1, 0);
        }
        guiGraphics.flush();
        pose.popPose();
    }
}
