package at.akunatur.cheesus.client.ter;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import at.akunatur.cheesus.common.te.CheeseStrainerTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CheeseStrainerTleEntityRenderer implements BlockEntityRenderer<CheeseStrainerTileEntity> {

	private Minecraft mc = Minecraft.getInstance();

	public CheeseStrainerTleEntityRenderer(BlockEntityRenderDispatcher rendererDispatcherIn) {
		super();
	}

	@Override
	public void render(CheeseStrainerTileEntity te, float partialTicks, PoseStack matrixStackIn,
			MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		if (te.getItemStack().equals(ItemStack.EMPTY))
			return;

		int lightLevel = getLightLevel(te.getLevel(), te.getBlockPos().above());
		renderItem(te.getItemStack(), new double[] { 0.5d, 1d, 0.5f }, Vector3f.YP.rotationDegrees(180f), matrixStackIn,
				bufferIn, partialTicks, combinedLightIn, lightLevel, 0.8f);
	}

	private void renderItem(ItemStack stack, double[] translation, Quaternion rotation, PoseStack matrixStack,
			MultiBufferSource buffer, float partialTicks, int combinedOverlay, int lightLevel, float scale) {
		matrixStack.pushPose();
		matrixStack.translate(translation[0], translation[1], translation[1]);
		matrixStack.scale(scale, scale, scale);

		BakedModel model = mc.getItemRenderer().getModel(stack, null, null, lightLevel);
		mc.getItemRenderer().render(stack, TransformType.GROUND, true, matrixStack, buffer, lightLevel, combinedOverlay,
				model);
	}

	private int getLightLevel(Level worldIn, BlockPos pos) {
		int bLight = worldIn.getLightEmission(pos);
		int sLight = worldIn.getLightEmission(pos);
		return LightTexture.pack(bLight, sLight);
	}

}