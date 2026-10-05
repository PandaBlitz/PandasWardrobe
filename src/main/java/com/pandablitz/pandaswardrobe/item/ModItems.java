package com.pandablitz.pandaswardrobe.item;

import com.pandablitz.pandaswardrobe.container.WardrobeContainer;
import com.pandablitz.pandaswardrobe.item.custom.Wardrobe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import java.util.function.Supplier;
import static com.pandablitz.pandaswardrobe.PandasWardrobe.MOD_ID;

public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(Registries.MENU, MOD_ID);
    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, MOD_ID);

    public static void init(IEventBus eventBus) {}

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MOD_ID);


    public static final DeferredHolder<Item, Wardrobe> wardrobeItem = ITEMS.registerItem("wardrobe", Wardrobe::new);

    public static final Supplier<AttachmentType<ItemStackHandler>> WARDROBE_STORAGE =
            ATTACHMENTS.register("wardrobe_storage", () -> AttachmentType.serializable(() -> new ItemStackHandler(28))
                            .copyOnDeath()
                            .build()
            );

    public static final DeferredHolder<MenuType<?>, MenuType<WardrobeContainer>> WARDROBE_CONTAINER =
            CONTAINERS.register("wardrobecontainer", () -> IMenuTypeExtension.create(
                    (windowId, inv, data) -> new WardrobeContainer(windowId, inv, inv.player, data))
            );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        CONTAINERS.register(eventBus);
        COMPONENTS.register(eventBus);
        ATTACHMENTS.register(eventBus);
    }
}


