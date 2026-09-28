package com.pandablitz.pandaswardrobe.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.pandablitz.pandaswardrobe.PandasWardrobe;
import com.pandablitz.pandaswardrobe.container.WardrobeContainer;
import com.pandablitz.pandaswardrobe.item.custom.Wardrobe;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class WardrobeScreen extends AbstractContainerScreen<WardrobeContainer> {
    private final ResourceLocation GUI_TEXTURE =
                ResourceLocation.fromNamespaceAndPath(PandasWardrobe.MOD_ID, "textures/gui/wardrobe/wardrobe_gui.png");
    private WardrobeContainer container;
    private ItemStack wardrobe;

    public WardrobeScreen(WardrobeContainer container, Inventory playerInventory, Component title) {
        super(container ,playerInventory, title);
        this.container = container;
        this.wardrobe = container.playerEntity.getMainHandItem();
        this.imageWidth = 176;
        this.imageHeight = 250;

        // hardcoded label recalculation
        this.inventoryLabelX = 8;
        this.inventoryLabelY = this.imageHeight - 125;
        this.titleLabelX = 8;
        this.titleLabelY = 6;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderSlot(GuiGraphics pGuiGraphics, Slot pSlot) {
        super.renderSlot(pGuiGraphics, pSlot);
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(GUI_TEXTURE, relX, relY, 0, 0, this.imageWidth, this.imageHeight,
                this.imageWidth, this.imageHeight);

        this.wardrobe = container.playerEntity.getMainHandItem();
        if (wardrobe.isEmpty() || !(wardrobe.getItem() instanceof Wardrobe))
            return;
    }

    @Override
    public boolean isPauseScreen() {
        return  false;
    }

    @Override
    public void onClose() {
        super.onClose();
    }


}
