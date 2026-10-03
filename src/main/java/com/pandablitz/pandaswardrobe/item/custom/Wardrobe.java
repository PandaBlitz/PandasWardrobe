package com.pandablitz.pandaswardrobe.item.custom;

import com.pandablitz.pandaswardrobe.container.WardrobeContainer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;


/**
 * Wardrobe class that links the {@link WardrobeContainer} class with our Wardrobe
 */

public class Wardrobe extends Item  {

    public Wardrobe(Properties properties) {
        super(properties);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
        // linking our wardrobe to its container and adding playerdata
            serverPlayer.openMenu(new SimpleMenuProvider(
                    (containerId, playerInventory, p) -> new WardrobeContainer(containerId, playerInventory,player, stack),
                    stack.getHoverName()), buf -> ItemStack.OPTIONAL_STREAM_CODEC.encode(buf, stack)
                    );
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

}
