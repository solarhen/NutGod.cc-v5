// 
// Decompiled by Procyon v0.5.36
// 

package me.zeroeightsix.kami.module.modules.combat;

import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.item.ItemBow;
import net.minecraft.client.Minecraft;
import me.zeroeightsix.kami.module.Module;

@Info(name = "Fast Bow", category = Category.COMBAT)
public class FastBow extends Module
{
    @Override
    public void onUpdate() {
        final Minecraft mc = Minecraft.func_71410_x();
        if (mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemBow && mc.field_71439_g.func_184587_cr() && mc.field_71439_g.func_184612_cw() >= 3) {
            mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, mc.field_71439_g.func_174811_aO()));
            mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(mc.field_71439_g.func_184600_cs()));
            mc.field_71439_g.func_184597_cx();
        }
    }
}
