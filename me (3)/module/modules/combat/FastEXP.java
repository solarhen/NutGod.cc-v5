// 
// Decompiled by Procyon v0.5.36
// 

package me.zeroeightsix.kami.module.modules.combat;

import net.minecraft.item.ItemExpBottle;
import me.zeroeightsix.kami.module.Module;

@Info(name = "Fast Exp", category = Category.COMBAT)
public class FastEXP extends Module
{
    @Override
    public void onUpdate() {
        if (FastEXP.mc.field_71439_g.field_71071_by.func_70448_g().func_77973_b() instanceof ItemExpBottle) {
            FastEXP.mc.field_71467_ac = 0;
        }
    }
}
