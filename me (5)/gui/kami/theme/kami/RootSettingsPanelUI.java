// 
// Decompiled by Procyon v0.5.36
// 

package me.zeroeightsix.kami.gui.kami.theme.kami;

import me.zeroeightsix.kami.gui.rgui.component.Component;
import me.zeroeightsix.kami.gui.kami.RenderHelper;
import org.lwjgl.opengl.GL11;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;
import me.zeroeightsix.kami.gui.kami.component.SettingsPanel;
import me.zeroeightsix.kami.gui.rgui.render.AbstractComponentUI;

public class RootSettingsPanelUI extends AbstractComponentUI<SettingsPanel>
{
    @Override
    public void renderComponent(final SettingsPanel component, final FontRenderer fontRenderer) {
        GL11.glColor4f(1.0f, 0.33f, 0.33f, 0.2f);
        RenderHelper.drawOutlinedRoundedRectangle(0, 0, component.getWidth(), component.getHeight(), 6.0f, 0.14f, 0.14f, 0.14f, component.getOpacity(), 1.0f);
    }
}
