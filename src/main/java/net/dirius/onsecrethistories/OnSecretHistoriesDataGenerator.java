package net.dirius.onsecrethistories;

import net.dirius.onsecrethistories.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class OnSecretHistoriesDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(OSHBlockTagProvider::new);
		pack.addProvider(OSHItemTagProvider::new);
		pack.addProvider(OSHLootTableProvider::new);
		pack.addProvider(OSHModelProvider::new);
		pack.addProvider(OSHRecipeProvider::new);
	}
}
