package net.dirius.onsecrethistories.util;

import net.dirius.onsecrethistories.OnSecretHistories;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class OSHTags {
    public static class Blocks {
        public static final TagKey<Block> TABLE_BLOCKS = createTag("table_blocks");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(OnSecretHistories.MOD_ID, name));
        }
    }

    public static class Items {
        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(OnSecretHistories.MOD_ID, name));
        }
    }
}
