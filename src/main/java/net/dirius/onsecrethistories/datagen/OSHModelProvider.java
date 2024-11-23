package net.dirius.onsecrethistories.datagen;

import net.dirius.onsecrethistories.block.OSHBlocks;
import net.dirius.onsecrethistories.item.OSHItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

import static net.dirius.onsecrethistories.datagen.OSHModels.*;

public class OSHModelProvider extends FabricModelProvider {

    public OSHModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        createTable(generator, OSHBlocks.ACACIA_PLANK_TABLE, Blocks.ACACIA_PLANKS);
        createTable(generator, OSHBlocks.BAMBOO_PLANK_TABLE, Blocks.BAMBOO_PLANKS);
        createTable(generator, OSHBlocks.BIRCH_PLANK_TABLE, Blocks.BIRCH_PLANKS);
        createTable(generator, OSHBlocks.CHERRY_PLANK_TABLE, Blocks.CHERRY_PLANKS);
        createTable(generator, OSHBlocks.DARK_OAK_PLANK_TABLE, Blocks.DARK_OAK_PLANKS);
        createTable(generator, OSHBlocks.JUNGLE_PLANK_TABLE, Blocks.JUNGLE_PLANKS);
        createTable(generator, OSHBlocks.MANGROVE_PLANK_TABLE, Blocks.MANGROVE_PLANKS);
        createTable(generator, OSHBlocks.OAK_PLANK_TABLE, Blocks.OAK_PLANKS);
        createTable(generator, OSHBlocks.SPRUCE_PLANK_TABLE, Blocks.SPRUCE_PLANKS);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(OSHItems.JOURNAL, Models.GENERATED);
        itemModelGenerator.register(OSHItems.MUNDANE_INK, Models.GENERATED);
    }

    public void createTable(BlockStateModelGenerator generator, Block table, Block base) {
        TextureMap textureMap = TextureMap.texture(base);
        Identifier tableInv = TEMPLATE_TABLE_INVENTORY.upload(setModelOutput("block/", table, "_inventory"), textureMap, generator.modelCollector);
        Identifier tableTop = TEMPLATE_TABLE_TOP.upload(setModelOutput("block/", table, "_top"), textureMap, generator.modelCollector);
        Identifier tableLeg = TEMPLATE_TABLE_LEG.upload(setModelOutput("block/", table, "_leg"), textureMap, generator.modelCollector);
        generator.blockStateCollector.accept(
                MultipartBlockStateSupplier.create(table)
                        .with(BlockStateVariant.create().put(VariantSettings.MODEL, tableTop))
                        .with(When.create().set(Properties.NORTH, false).set(Properties.WEST, false),
                                BlockStateVariant.create().put(VariantSettings.MODEL, tableLeg).put(VariantSettings.UVLOCK, true))
                        .with(When.create().set(Properties.NORTH, false).set(Properties.EAST, false),
                                BlockStateVariant.create().put(VariantSettings.MODEL, tableLeg).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .with(When.create().set(Properties.SOUTH, false).set(Properties.EAST, false),
                                BlockStateVariant.create().put(VariantSettings.MODEL, tableLeg).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .with(When.create().set(Properties.SOUTH, false).set(Properties.WEST, false),
                                BlockStateVariant.create().put(VariantSettings.MODEL, tableLeg).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R270))
        );
        generator.registerParentedItemModel(table, tableInv);
    }
}
