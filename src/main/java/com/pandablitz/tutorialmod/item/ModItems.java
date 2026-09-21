package com.pandablitz.tutorialmod.item;

import com.pandablitz.tutorialmod.TutorialMod;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    // this deferred register is basically a long list of items that we have and then registers them into mc
    // then we pass through our TutorialMod class and the MDO_ID string to tell the list that it belongs to OUR mod
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TutorialMod.MOD_ID);

    // this is where we create our new item as well as register it via ITEMS.register(), from there we pass into the
    // parameters a string that must be lowercased as well as a construct called a supplier which is the "() ->" that
    // we see. A supplier is a java built in functional interface that allows us to create the item when asked
    // rather than right away. So DeferredItem<Item> is a wrapper that holds the supplier then ONLY calls it (creates it)
    // when it's called. "()-> new Item(new Item.Properties()));" is a lambda expression. It's helpful for when we want
    // to load certain parts of code first before creating rather than creating the item off rip, in this case we
    // want to load up other elements of Minecraft first before we actually create this item.
    public static final DeferredItem<Item> WARDROBE = ITEMS.register("wardrobe",
            () -> new Item(new Item.Properties()));

    // we then use this method to properly register DeferredRegister
    public static void register(IEventBus eventBus) {
        // we pass in each item we want to register into our DeferredRegister ITEMS list
        ITEMS.register(eventBus);
    }
}
