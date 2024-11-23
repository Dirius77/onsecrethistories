package net.dirius.onsecrethistories.block;

import net.dirius.onsecrethistories.OnSecretHistories;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class OSHBlocks {
    public static final Block ACACIA_PLANK_TABLE = registerBlock("acacia_plank_table", createTableBlock(Blocks.ACACIA_PLANKS));
    public static final Block BAMBOO_PLANK_TABLE = registerBlock("bamboo_plank_table", createTableBlock(Blocks.BAMBOO_PLANKS));
    public static final Block BIRCH_PLANK_TABLE = registerBlock("birch_plank_table", createTableBlock(Blocks.BIRCH_PLANKS));
    public static final Block CHERRY_PLANK_TABLE = registerBlock("cherry_plank_table", createTableBlock(Blocks.CHERRY_PLANKS));
    public static final Block DARK_OAK_PLANK_TABLE = registerBlock("dark_oak_plank_table", createTableBlock(Blocks.DARK_OAK_PLANKS));
    public static final Block JUNGLE_PLANK_TABLE = registerBlock("jungle_plank_table", createTableBlock(Blocks.JUNGLE_PLANKS));
    public static final Block MANGROVE_PLANK_TABLE = registerBlock("mangrove_plank_table", createTableBlock(Blocks.MANGROVE_PLANKS));
    public static final Block OAK_PLANK_TABLE = registerBlock("oak_plank_table", createTableBlock(Blocks.OAK_PLANKS));
    public static final Block SPRUCE_PLANK_TABLE = registerBlock("spruce_plank_table", createTableBlock(Blocks.SPRUCE_PLANKS));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(OnSecretHistories.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(OnSecretHistories.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerBlocks() {
        OnSecretHistories.LOGGER.info("Registering Blocks.");
    }

    public static TableBlock createTableBlock(Block base) {
        return new TableBlock(base.getDefaultState(), AbstractBlock.Settings.copy(base));
    }
}
