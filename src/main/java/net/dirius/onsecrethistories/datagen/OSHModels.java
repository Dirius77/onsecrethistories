package net.dirius.onsecrethistories.datagen;

import net.dirius.onsecrethistories.OnSecretHistories;
import net.minecraft.block.Block;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.TextureKey;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class OSHModels {
    public static final Model TEMPLATE_TABLE_TOP = new Model(Optional.of(Identifier.of(OnSecretHistories.MOD_ID, "block/table_top")), Optional.empty(), TextureKey.TEXTURE);
    public static final Model TEMPLATE_TABLE_LEG = new Model(Optional.of(Identifier.of(OnSecretHistories.MOD_ID, "block/table_leg")), Optional.empty(), TextureKey.TEXTURE);
    public static final Model TEMPLATE_TABLE_INVENTORY = new Model(Optional.of(Identifier.of(OnSecretHistories.MOD_ID, "block/table_inventory")), Optional.empty(), TextureKey.TEXTURE);

    public static Identifier setModelOutput(String path, Block block, String suffix) {
        return Identifier.of(OnSecretHistories.MOD_ID, path + Registries.BLOCK.getId(block).getPath() + suffix);
    }
}
