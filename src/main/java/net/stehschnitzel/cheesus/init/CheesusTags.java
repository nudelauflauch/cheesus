package net.stehschnitzel.cheesus.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.stehschnitzel.cheesus.Cheesus;

public class CheesusTags {

    public static class Blocks {
        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.tryBuild(Cheesus.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> CHEESE = createTag("cheese");
        public static final TagKey<Item> CHEESE_SLICE = createTag("cheese_slice");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.tryBuild(Cheesus.MOD_ID, name));
        }
    }

}
