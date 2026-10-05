package com.pandablitz.pandaswardrobe.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.pandablitz.pandaswardrobe.PandasWardrobe;
import com.pandablitz.pandaswardrobe.container.WardrobeContainer;
import com.pandablitz.pandaswardrobe.item.custom.Wardrobe;
import com.pandablitz.pandaswardrobe.screen.button.HoverOnlyImageButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class WardrobeScreen extends AbstractContainerScreen<WardrobeContainer> {

    //gui and button and slot texture loading
    private final ResourceLocation GUI_TEXTURE =
                ResourceLocation.fromNamespaceAndPath(PandasWardrobe.MOD_ID, "textures/gui/wardrobe/wardrobe_gui.png");
    private static final WidgetSprites BUTTON_SPRITES = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(PandasWardrobe.MOD_ID, "buttons/button"),
            ResourceLocation.fromNamespaceAndPath(PandasWardrobe.MOD_ID, "buttons/button_hovering")
    ); // button sprites are 18x18
    private static final ResourceLocation[] SLOT_BACKGROUND = {
                ResourceLocation.fromNamespaceAndPath(PandasWardrobe.MOD_ID, "armor/helmet_slot"),
                ResourceLocation.fromNamespaceAndPath(PandasWardrobe.MOD_ID, "armor/chestplate_slot"),
                ResourceLocation.fromNamespaceAndPath(PandasWardrobe.MOD_ID, "armor/pants_slot"),
                ResourceLocation.fromNamespaceAndPath(PandasWardrobe.MOD_ID, "armor/boots_slot")
    }; // slot backgrounds sprites are 16x16


    private WardrobeContainer container;
    private ItemStack wardrobe;

    public WardrobeScreen(WardrobeContainer container, Inventory playerInventory, Component title) {
        super(container ,playerInventory, title);
        this.container = container;
        this.wardrobe = container.playerEntity.getMainHandItem();
        this.imageWidth = 176;
        this.imageHeight = 250;

        // inventory label placement
        this.inventoryLabelX = 8;
        this.inventoryLabelY = this.imageHeight - 130;
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

        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        int dX = 23;

        // paints the buttons onto the gui
        for (int i = 0; i < 7; i++) {
            final int columnID = i;
            this.addRenderableWidget(new HoverOnlyImageButton(relX + 13 + ( dX * i) - i, relY + 96, 18, 18, BUTTON_SPRITES,
                    button -> { // logic for button click
                    if(this.minecraft != null && this.minecraft.gameMode != null) {
                        this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, columnID); //
                    }}));
        }
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

        for(int i = 0; i < WardrobeContainer.SLOTS; i++) {
            Slot slot = this.menu.slots.get(i);
            if (!slot.hasItem()) {
                int row = i / 7;
                guiGraphics.blitSprite(SLOT_BACKGROUND[row], relX + slot.x , relY + slot.y, 16, 16);
            }
        }
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
