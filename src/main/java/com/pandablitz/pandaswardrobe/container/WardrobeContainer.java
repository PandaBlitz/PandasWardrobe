package com.pandablitz.pandaswardrobe.container;

import com.pandablitz.pandaswardrobe.item.ModItems;
import com.pandablitz.pandaswardrobe.slots.ArmorSlot;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ComponentItemHandler;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;

 /**
  *  This is the WardrobeContainer class, which adds and adjusts both the wardrobe and player inventory slots.
  *  We use an {@link AbstractContainerMenu} since it allows us to store and keep items within it.
 */

public class WardrobeContainer extends AbstractContainerMenu {
    public static final int SLOTS = 28;
    public ComponentItemHandler handler;
    public ItemStack wardrobeItemStack;
    public Player playerEntity;


    public WardrobeContainer(int windowId, Inventory inv, Player player, RegistryFriendlyByteBuf extraData) {
        this(windowId, inv, player, ItemStack.OPTIONAL_STREAM_CODEC.decode(extraData));
    }

    // must update our menu when an item is slotted in and only allow armor slots
    public WardrobeContainer(int windowid, Inventory playerInventory, Player player, ItemStack wardrobe) {
        super(ModItems.Wardrobe_Container.get(), windowid);
        playerEntity = player;
        this.wardrobeItemStack = wardrobe;
        handler = new ComponentItemHandler(wardrobe, ModItems.ITEMSTACK_HANDLER.get(), SLOTS);

        // wardrobe slots x/y variables can be moved to match gui slots
        int index = 0;
        index = addArmorSlotRow(handler, index, 14, 17, 7, 22, EquipmentSlot.HEAD);   // row 1 — helmets
        index = addArmorSlotRow(handler, index, 14, 35, 7, 22, EquipmentSlot.CHEST);  // row 2 — chestplates
        index = addArmorSlotRow(handler, index, 14, 53, 7, 22, EquipmentSlot.LEGS);   // row 3 — leggings
        index = addArmorSlotRow(handler, index, 14, 71, 7, 22, EquipmentSlot.FEET);   // row 4 - boots

        // adding player inventory + hotbar x/y variables can be moved to match slots
        addPlayerSlots(playerInventory, 9, 131);
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

     /**
      * Method for creating ONLY armor item slots using our {@link ArmorSlot} class - might need to fix
      * to be compatible with ATM10 armor
      * @param handler The Item Handler
      * @param index The slot position to be linked in {@link SlotItemHandler}
      * @param x Starting x-axis position in our inventory for our slots
      * @param y Starting y-axis position in our inventory for our slots
      * @param amount amount of slots to add
      * @param dx amount to space out each slot
      * @param type type of armor(equipment) slot
      * @return
      */
    protected int addArmorSlotRow(IItemHandler handler, int index, int x, int y, int amount, int dx, EquipmentSlot type) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new ArmorSlot(handler, index, x, y, type));
            x += dx;
            index++;
        }
        return  index;
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

    @Override
    public boolean clickMenuButton(Player player, int id) {
        EquipmentSlot[] rows = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

        for (int row = 0 ; row < 4; row++) {
            int wardrobeIndex = id + (row * 7); // loops 4 times for each column and multiplies by 7 to get each item within that column
            EquipmentSlot equipSlot = rows[row];

            ItemStack wardrobeStack = handler.getStackInSlot(wardrobeIndex);
            ItemStack equippedStack = player.getItemBySlot(equipSlot);

            handler.setStackInSlot(wardrobeIndex, equippedStack);
            player.setItemSlot(equipSlot, wardrobeStack);
        }
        return true;
    }
}
