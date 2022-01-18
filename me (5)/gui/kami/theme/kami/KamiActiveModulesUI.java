// 
// Decompiled by Procyon v0.5.36
// 

package me.zeroeightsix.kami.gui.kami.theme.kami;

import me.zeroeightsix.kami.gui.rgui.component.Component;
import java.util.function.Function;
import me.zeroeightsix.kami.command.Command;
import java.awt.Color;
import me.zeroeightsix.kami.gui.rgui.component.AlignedComponent;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Comparator;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.module.Module;
import java.util.List;
import me.zeroeightsix.kami.util.Wrapper;
import org.lwjgl.opengl.GL11;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;
import me.zeroeightsix.kami.gui.kami.component.ActiveModules;
import me.zeroeightsix.kami.gui.rgui.render.AbstractComponentUI;

public class KamiActiveModulesUI extends AbstractComponentUI<ActiveModules>
{
    @Override
    public void renderComponent(final ActiveModules component, final FontRenderer f) {
        GL11.glDisable(2884);
        GL11.glEnable(3042);
        GL11.glEnable(3553);
        final FontRenderer renderer = Wrapper.getFontRenderer();
        String string;
        final FontRenderer fontRenderer;
        final StringBuilder sb;
        final List<Module> mods = ModuleManager.getModules().stream().filter(Module::isEnabled).sorted(Comparator.comparing(module -> {
            new StringBuilder().append(module.getName());
            if (module.getHudInfo() == null) {
                string = "";
            }
            else {
                string = module.getHudInfo() + " ";
            }
            return Integer.valueOf(fontRenderer.getStringWidth(sb.append(string).toString()) * (component.sort_up ? -1 : 1));
        })).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
        final int[] y = { 2 };
        if (component.getParent().getY() < 26 && Wrapper.getPlayer().func_70651_bq().size() > 0 && component.getParent().getOpacity() == 0.0f) {
            y[0] = Math.max(component.getParent().getY(), 26 - component.getParent().getY());
        }
        final float[] hue = { System.currentTimeMillis() % 11520L / 11520.0f };
        final boolean lAlign = component.getAlignment() == AlignedComponent.Alignment.LEFT;
        switch (component.getAlignment()) {
            case RIGHT: {
                final Function<Integer, Integer> xFunc = (Function<Integer, Integer>)(i -> component.getWidth() - i);
                break;
            }
            case CENTER: {
                final Function<Integer, Integer> xFunc = (Function<Integer, Integer>)(i -> component.getWidth() / 2 - i / 2);
                break;
            }
            default: {
                final Function<Integer, Integer> xFunc = (Function<Integer, Integer>)(i -> 0);
                break;
            }
        }
        final Object o;
        final int rgb;
        final String s;
        String string2;
        final StringBuilder sb2;
        final String text;
        final FontRenderer fontRenderer2;
        final int textwidth;
        final int textheight;
        final int red;
        final int green;
        final int blue;
        final Function<Integer, Integer> function;
        final Object o2;
        final int n;
        final int n2;
        mods.stream().forEach(module -> {
            rgb = Color.HSBtoRGB(o[0], 1.0f, 1.0f);
            s = module.getHudInfo();
            new StringBuilder().append(module.getName());
            if (s == null) {
                string2 = "";
            }
            else {
                string2 = " " + Command.SECTIONSIGN() + "7" + s;
            }
            text = sb2.append(string2).toString();
            textwidth = fontRenderer2.getStringWidth(text);
            textheight = fontRenderer2.getFontHeight() + 1;
            red = 255;
            green = 0;
            blue = 255;
            fontRenderer2.drawStringWithShadow((int)function.apply(textwidth), o2[0], red, green, blue, text);
            o[n] += 0.02f;
            o2[n2] += textheight;
            return;
        });
        component.setHeight(y[0]);
        GL11.glEnable(2884);
        GL11.glDisable(3042);
    }
    
    @Override
    public void handleSizeComponent(final ActiveModules component) {
        component.setWidth(100);
        component.setHeight(100);
    }
}
