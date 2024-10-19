package net.dirius.onsecrethistories;

import net.dirius.onsecrethistories.block.OSHBlocks;
import net.dirius.onsecrethistories.item.OSHItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OnSecretHistories implements ModInitializer {
	public static final String MOD_ID = "onsecrethistories";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		OSHItems.registerItems();
		OSHBlocks.registerBlocks();
	}
}