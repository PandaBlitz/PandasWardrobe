package com.pandablitz.pandaswardrobe.item;

import com.pandablitz.pandaswardrobe.PandasWardrobe;
import com.pandablitz.pandaswardrobe.container.WardrobeContainer;
import com.pandablitz.pandaswardrobe.item.custom.Wardrobe;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.pandablitz.pandaswardrobe.PandasWardrobe.MOD_ID;

public class ModItems {
    // this deferred register is basically a long list of items that we have and then registers them into mc
    // then we pass through our TutorialMod class and the MDO_ID string to tell the list that it belongs to OUR mod
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(Registries.MENU, MOD_ID);
    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister
            .createDataComponents(Registries.DATA_COMPONENT_TYPE, MOD_ID);

    public static void init(IEventBus eventBus) {
        // ITEMS.register(eventBus);
    }


    // this is where we create our new item as well as register it via ITEMS.register(), from there we pass into the
    // parameters a string that must be lowercased as well as a construct called a supplier which is the "() ->" that
    // we see. A supplier is a java built in functional interface that allows us to create the item when asked
    // rather than right away. So DeferredItem<Item> is a wrapper that holds the supplier then ONLY calls it (creates it)
    // when it's called. "()-> new Item(new Item.Properties()));" is a lambda expression. It's helpful for when we want
    // to load certain parts of code first before creating rather than creating the item off rip, in this case we
    // want to load up other elements of Minecraft first before we actually create this item.
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(BuiltInRegistries.MENU, PandasWardrobe.MOD_ID);

    public static final DeferredHolder<Item, Wardrobe> wardrobeItem = ITEMS.registerItem("wardrobe", Wardrobe::new);

    public static final DeferredHolder<MenuType<?>, MenuType<WardrobeContainer>> Wardrobe_Container =
            CONTAINERS.register("wardrobecontainer", () -> IMenuTypeExtension.create(((windowId, inv, data)
                    -> new WardrobeContainer(windowId, inv, inv.player, data) )));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemContainerContents>> ITEMSTACK_HANDLER
            = COMPONENTS.register("itemstack_handler", () -> DataComponentType.<ItemContainerContents>builder()
            .persistent(ItemContainerContents.CODEC)
            .networkSynchronized(ItemContainerContents.STREAM_CODEC)
            .cacheEncoding()
            .build());



    // we then use this method to properly register DeferredRegister
    public static void register(IEventBus eventBus) {
        // we pass in each item we want to register into our DeferredRegister ITEMS list
        ITEMS.register(eventBus);
        CONTAINERS.register(eventBus);
        COMPONENTS.register(eventBus);
    }
}


