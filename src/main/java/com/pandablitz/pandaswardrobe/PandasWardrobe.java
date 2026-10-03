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
    Last updated 10.3.2026.(time)
    Fixed: Hover button has been created and slots now swap, store, and unequip current armor and ONLY accepts MC armor
    ToDo: Fix Hover button to revert back after click. Update gui OR armor slot for armor background to turn transparent.
 */

// IMPORTANT LOOK AT JDT GITHUB REPO VERSION 1.21.1
// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(PandasWardrobe.MOD_ID)
public class PandasWardrobe {

    // mod_id identifier, must be unique and lowercased, should be defined in a common place to be referenced easily
    public static final String MOD_ID = "pandaswardrobe";

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public PandasWardrobe(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (PandasWardrobe) to respond directly to events.
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
