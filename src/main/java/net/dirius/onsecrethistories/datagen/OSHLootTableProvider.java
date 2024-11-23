package net.dirius.onsecrethistories.datagen;

import net.dirius.onsecrethistories.block.OSHBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class OSHLootTableProvider extends FabricBlockLootTableProvider {
    public OSHLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(OSHBlocks.ACACIA_PLANK_TABLE);
        addDrop(OSHBlocks.BAMBOO_PLANK_TABLE);
        addDrop(OSHBlocks.BIRCH_PLANK_TABLE);
        addDrop(OSHBlocks.CHERRY_PLANK_TABLE);
        addDrop(OSHBlocks.DARK_OAK_PLANK_TABLE);
        addDrop(OSHBlocks.JUNGLE_PLANK_TABLE);
        addDrop(OSHBlocks.MANGROVE_PLANK_TABLE);
        addDrop(OSHBlocks.OAK_PLANK_TABLE);
        addDrop(OSHBlocks.SPRUCE_PLANK_TABLE);
    }
}
