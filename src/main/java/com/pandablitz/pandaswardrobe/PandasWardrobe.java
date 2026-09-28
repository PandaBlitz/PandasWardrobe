package com.pandablitz.pandaswardrobe;

import com.pandablitz.pandaswardrobe.item.ModItems;
import net.minecraft.world.level.ItemLike;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;




/*
    Last updated 9.29.2026.1138
    Fixed: Wardrobe now displays menu correctly
    ToDo: Match up item slots with menu gui in WardrobeContainer

 */
// IMPORTANT LOOK AT JDT GITHUB REPO VERSION 1.21.1
// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(PandasWardrobe.MOD_ID)
public class PandasWardrobe {
    // Define mod id in a common place for everything to reference
    // mod_id is THE identifier for your mod so it must be unique, can only contain lowercase and no space
    // can be changed to pandablitzWardrobe later on just make sure to change in gradle.properties as well
    public static final String MOD_ID = "pandaswardrobe";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public PandasWardrobe(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // here we call the register method from ModItems into our main method
        // then pass through modEventBus thus registering/loading in our mod stuff
        ModItems.register(modEventBus);
        ModItems.init(modEventBus);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

    }


    private void commonSetup(FMLCommonSetupEvent event) {

    }

    // Add the example block item to the building blocks creative menu tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept((ItemLike) ModItems.wardrobeItem);
        }

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}
