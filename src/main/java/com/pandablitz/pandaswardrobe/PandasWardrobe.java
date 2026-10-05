package com.pandablitz.pandaswardrobe;

import com.pandablitz.pandaswardrobe.item.ModItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(PandasWardrobe.MOD_ID)
public class PandasWardrobe {

    // mod_id identifier, must be unique and lowercased, should be defined in a common place to be referenced easily
    public static final String MOD_ID = "pandaswardrobe";


    public PandasWardrobe(IEventBus modEventBus) {
        modEventBus.addListener(this::addCreative);
        NeoForge.EVENT_BUS.register(this);

        ModItems.register(modEventBus);
        ModItems.init(modEventBus);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept((ItemLike) ModItems.wardrobeItem);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}
