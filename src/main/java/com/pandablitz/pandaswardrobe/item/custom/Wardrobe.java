package com.pandablitz.pandaswardrobe.item.custom;

import com.pandablitz.pandaswardrobe.container.WardrobeContainer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;


/**
 * Wardrobe class that links the {@link WardrobeContainer} class with our Wardrobe
 */

public class Wardrobe extends Item  {

    public Wardrobe(Properties properties) {
        super(properties.stacksTo(1));
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
        // linking our wardrobe to its container and adding playerdata
            serverPlayer.openMenu(new SimpleMenuProvider(
                    (containerId, playerInventory, p) -> new WardrobeContainer(containerId, playerInventory,player),
                    stack.getHoverName()
            ));
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.translatable("item.pandaswardrobe.mod_name")
                .withStyle(ChatFormatting.BLUE, ChatFormatting.ITALIC));
    }
}
