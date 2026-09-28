package com.pandablitz.pandaswardrobe.container;

import com.pandablitz.pandaswardrobe.item.ModItems;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ComponentItemHandler;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;

/*
    This is a container class for our wardrobe, a container allows us to store specific items and keep them there.
    container classes are used to only set up menu slots
    The goal of this class is to 1. add our player inventory and hotbar 2. add the armor slots

 */

 /**
 *  This is the WardrobeContainer class, which adds and adjusts both the wardrobe and player inventory slots.
  *  We use a container since it allows us to store and keep items within it.
 */

public class WardrobeContainer extends AbstractContainerMenu {
    public static final int SLOTS = 28;
    public ComponentItemHandler handler;
    public ItemStack wardrobeItemStack;
    public Player playerEntity;


    public WardrobeContainer(int windowId, Inventory inv, Player player, RegistryFriendlyByteBuf extraData) {
        this(windowId, inv, player, ItemStack.OPTIONAL_STREAM_CODEC.decode(extraData));
    }

    public WardrobeContainer(int windowid, Inventory playerInventory, Player player, ItemStack wardrobe) {
        super(ModItems.Wardrobe_Container.get(), windowid);
        playerEntity = player;
        this.wardrobeItemStack = wardrobe;
        handler = new ComponentItemHandler(wardrobe, ModItems.ITEMSTACK_HANDLER.get(), SLOTS);

        // wardrobe slots x/y variables can be moved to match gui slots
        addSlotBox(handler, 0, 8, 18, 7, 18, 4, 18); // 7 wide x 4 tall = 28 slots

        // adding player inventory + hotbar
        addPlayerSlots(playerInventory, 8, 140);
    }

    /**
     * Method for adding player slots
     * @param playerInventory is the player's inventory, which is wrapped as {@link net.minecraft.world.Container},
     *                        allowing for inventory logic (get, remove) to be called
     * @param inX is the distance from the left of our inventory
     * @param inY is the distance from the top of our inventory
     */
    protected void addPlayerSlots(Inventory playerInventory, int inX, int inY) {
        // player inventory slots
        addSlotBox(new InvWrapper(playerInventory), 9, inX, inY, 9, 18, 3, 18);

        // hotbar slots
        inY += 58;
        addSlotRange(new InvWrapper(playerInventory), 0, inX, inY, 9, 18);
    }


     /**
      * Method for adding slot range. Loops through a passed amount and adds slots per iteration
      * @param handler The Item Handler
      * @param index The slot position to be linked in {@link SlotItemHandler}
      * @param x Starting x-axis position in our inventory for our slots
      * @param y Starting y-axis position in our inventory for our slots
      * @param amount # of slots to be added (Or times to be looped through)
      * @param dx The amount to space out each slot
      * @return Returns the updated index allowing for the next call to know where to continue from
      */
    protected int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            // calls neoforge super class addSlot method which creates each slot
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

     /**
      * Method for laying out a full grid of slots
      * @param handler The Item Handler
      * @param index The slot position to be linked in {@link SlotItemHandler}
      * @param x Starting x-axis position in our inventory for our slots
      * @param y Starting y-axis position in our inventory for our slots
      * @param horAmount number of rows to add
      * @param dx The amount to space out each slot
      * @param verAmount Number of columns to add
      * @param dy The amount to space out each row
      * @return Returns the updated index allowing the next call to know where to continue from
      */

    protected int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }


    // taken from JustDireThings
    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack currentStack = slot.getItem();
            if (index < SLOTS) { //Slot to Player Inventory
                if (!this.moveItemStackTo(currentStack, SLOTS, Inventory.INVENTORY_SIZE + SLOTS, true)) {
                    return ItemStack.EMPTY;
                }
            }
            if (index >= SLOTS) { //Player Inventory to Slots
                if (!this.moveItemStackTo(currentStack, 0, SLOTS, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (currentStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.set(currentStack);
                slot.setChanged();
            }

            if (currentStack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, currentStack);
        }
        return itemstack;
    }

    @Override
    public boolean stillValid(Player playerInv) {
        return playerInv.getMainHandItem().equals(wardrobeItemStack);
    }
}
