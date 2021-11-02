package at.akunatur.cheesus.client.ter;

import com.mojang.blaze3d.matrix.MatrixStack;

import at.akunatur.cheesus.common.te.CheeseStrainerTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class CheeseStrainerTleEntityRenderer extends TileEntityRenderer<CheeseStrainerTileEntity> {

	private Minecraft mc = Minecraft.getInstance();

	public CheeseStrainerTleEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@Override
	public void render(CheeseStrainerTileEntity te, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		if (te.getItem().equals(ItemStack.EMPTY))
			return;

		int lightLevel = getLightLevel(te.getWorld(), te.getPos().up());
		renderItem(te.getItem(), new double[] { 0.5d, 1d, 0.5f }, Vector3f.YP.rotationDegrees(180f), matrixStackIn, bufferIn,
				partialTicks, combinedLightIn, lightLevel, 0.8f);
	}

	private void renderItem(ItemStack stack, double[] translation, Quaternion rotation, MatrixStack matrixStack,
			IRenderTypeBuffer buffer, float partialTicks, int combinedOverlay, int lightLevel, float scale) {
		matrixStack.push();
		matrixStack.translate(translation[0], translation[1], translation[1]);
		matrixStack.scale(scale, scale, scale);

		IBakedModel model = mc.getItemRenderer().getItemModelWithOverrides(stack, null, null);
		mc.getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.GROUND, true, matrixStack, buffer,
				lightLevel, combinedOverlay, model);
		matrixStack.pop();
	}

	private int getLightLevel(World worldIn, BlockPos pos) {
		int bLight = worldIn.getLightFor(LightType.BLOCK, pos);
		int sLight = worldIn.getLightFor(LightType.BLOCK, pos);
		return LightTexture.packLight(bLight, sLight);
	}
}
