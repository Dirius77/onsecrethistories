package net.dirius.onsecrethistories.item;

import net.dirius.onsecrethistories.OnSecretHistories;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class OSHItems {
    public static final Item JOURNAL = registerItem("journal", new Item(new Item.Settings().maxCount(1)));
    public static final Item MUNDANE_INK = registerItem("mundane_ink", new Item(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(OnSecretHistories.MOD_ID, name), item);
    }

    public static void registerItems() {
        OnSecretHistories.LOGGER.info("Registering Items.");

    }
}
