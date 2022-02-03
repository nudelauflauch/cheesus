package at.akunatur.cheesus.core.init;

import at.akunatur.cheesus.Cheesus;
import at.akunatur.cheesus.common.container.CheeseStrainerContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ContainerTypesInit{
	public static final DeferredRegister<MenuType<?>> CONTAINER_TYPES = DeferredRegister
			.create(ForgeRegistries.CONTAINERS, Cheesus.MOD_ID);

//	public static final RegistryObject<MenuType<CheeseStrainerContainer>> CHEESE_CONTAINER = CONTAINER_TYPES
//			.register("cheese_container", () -> IForgeContainerType.create(CheeseStrainerContainer::new));
}