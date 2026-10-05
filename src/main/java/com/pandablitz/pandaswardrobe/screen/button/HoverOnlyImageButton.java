package com.pandablitz.pandaswardrobe.screen.button;

import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;

public class HoverOnlyImageButton extends ImageButton  {

    public HoverOnlyImageButton(int x, int y, int width, int height, WidgetSprites widgetSprites, OnPress onPress) {
        super(x,y,width,height, widgetSprites, onPress);
    }

    @Override
    public boolean isHoveredOrFocused() {
        return this.isHovered();
    }
}
