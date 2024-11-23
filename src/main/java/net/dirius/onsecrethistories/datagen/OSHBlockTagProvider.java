package net.dirius.onsecrethistories.datagen;

import net.dirius.onsecrethistories.block.OSHBlocks;
import net.dirius.onsecrethistories.util.OSHTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class OSHBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public OSHBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
                .add(OSHBlocks.ACACIA_PLANK_TABLE)
                .add(OSHBlocks.BAMBOO_PLANK_TABLE)
                .add(OSHBlocks.BIRCH_PLANK_TABLE)
                .add(OSHBlocks.CHERRY_PLANK_TABLE)
                .add(OSHBlocks.DARK_OAK_PLANK_TABLE)
                .add(OSHBlocks.JUNGLE_PLANK_TABLE)
                .add(OSHBlocks.MANGROVE_PLANK_TABLE)
                .add(OSHBlocks.OAK_PLANK_TABLE)
                .add(OSHBlocks.SPRUCE_PLANK_TABLE);

        getOrCreateTagBuilder(OSHTags.Blocks.TABLE_BLOCKS)
                .add(OSHBlocks.ACACIA_PLANK_TABLE)
                .add(OSHBlocks.BAMBOO_PLANK_TABLE)
                .add(OSHBlocks.BIRCH_PLANK_TABLE)
                .add(OSHBlocks.CHERRY_PLANK_TABLE)
                .add(OSHBlocks.DARK_OAK_PLANK_TABLE)
                .add(OSHBlocks.JUNGLE_PLANK_TABLE)
                .add(OSHBlocks.MANGROVE_PLANK_TABLE)
                .add(OSHBlocks.OAK_PLANK_TABLE)
                .add(OSHBlocks.SPRUCE_PLANK_TABLE);
    }
}
