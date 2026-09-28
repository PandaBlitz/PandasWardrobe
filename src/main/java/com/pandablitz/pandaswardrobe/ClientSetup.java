package com.pandablitz.pandaswardrobe;

import com.pandablitz.pandaswardrobe.item.ModItems;
import com.pandablitz.pandaswardrobe.screen.custom.WardrobeScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.bus.api.SubscribeEvent;

@EventBusSubscriber(modid = PandasWardrobe.MOD_ID, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModItems.Wardrobe_Container.get(), WardrobeScreen::new);
    }
}