package com.pandablitz.pandaswardrobe.slots;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class ArmorSlot extends SlotItemHandler {
    private final EquipmentSlot REQUIRED_TYPE;

    public ArmorSlot(IItemHandler itemHandler, int index, int x, int y, EquipmentSlot REQUIRED_TYPE) {
        super(itemHandler, index, x, y);
        this.REQUIRED_TYPE = REQUIRED_TYPE;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        if(!(stack.getItem() instanceof ArmorItem armorItem)) { // if statement that checks if current slot is armor
            return false;
        }
        return armorItem.getEquipmentSlot() == REQUIRED_TYPE;
    }
}
