// 
// Decompiled by Procyon v0.5.36
// 

package me.zeroeightsix.kami.module.modules.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.MathHelper;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.module.Module;

@Info(name = "ElytraFlight", description = "Allows infinite elytra flying", category = Category.MOVEMENT)
public class ElytraFlight extends Module
{
    private Setting<ElytraFlightMode> mode;
    
    public ElytraFlight() {
        this.mode = this.register(Settings.e("Mode", ElytraFlightMode.BOOST));
    }
    
    @Override
    public void onUpdate() {
        if (!ElytraFlight.mc.field_71439_g.func_184613_cA()) {
            return;
        }
        switch (this.mode.getValue()) {
            case BOOST: {
                if (ElytraFlight.mc.field_71439_g.func_70090_H()) {
                    ElytraFlight.mc.func_147114_u().func_147297_a((Packet)new CPacketEntityAction((Entity)ElytraFlight.mc.field_71439_g, CPacketEntityAction.Action.START_FALL_FLYING));
                    return;
                }
                if (ElytraFlight.mc.field_71474_y.field_74314_A.func_151470_d()) {
                    final EntityPlayerSP field_71439_g = ElytraFlight.mc.field_71439_g;
                    field_71439_g.field_70181_x += 0.08;
                }
                else if (ElytraFlight.mc.field_71474_y.field_74311_E.func_151470_d()) {
                    final EntityPlayerSP field_71439_g2 = ElytraFlight.mc.field_71439_g;
                    field_71439_g2.field_70181_x -= 0.04;
                }
                if (ElytraFlight.mc.field_71474_y.field_74351_w.func_151470_d()) {
                    final float yaw = (float)Math.toRadians(ElytraFlight.mc.field_71439_g.field_70177_z);
                    final EntityPlayerSP field_71439_g3 = ElytraFlight.mc.field_71439_g;
                    field_71439_g3.field_70159_w -= MathHelper.func_76126_a(yaw) * 0.05f;
                    final EntityPlayerSP field_71439_g4 = ElytraFlight.mc.field_71439_g;
                    field_71439_g4.field_70179_y += MathHelper.func_76134_b(yaw) * 0.05f;
                    break;
                }
                if (ElytraFlight.mc.field_71474_y.field_74368_y.func_151470_d()) {
                    final float yaw = (float)Math.toRadians(ElytraFlight.mc.field_71439_g.field_70177_z);
                    final EntityPlayerSP field_71439_g5 = ElytraFlight.mc.field_71439_g;
                    field_71439_g5.field_70159_w += MathHelper.func_76126_a(yaw) * 0.05f;
                    final EntityPlayerSP field_71439_g6 = ElytraFlight.mc.field_71439_g;
                    field_71439_g6.field_70179_y -= MathHelper.func_76134_b(yaw) * 0.05f;
                    break;
                }
                break;
            }
            case FLY: {
                ElytraFlight.mc.field_71439_g.field_71075_bZ.field_75100_b = true;
                break;
            }
        }
    }
    
    @Override
    protected void onDisable() {
        if (ElytraFlight.mc.field_71439_g.field_71075_bZ.field_75098_d) {
            return;
        }
        ElytraFlight.mc.field_71439_g.field_71075_bZ.field_75100_b = false;
    }
    
    private enum ElytraFlightMode
    {
        BOOST, 
        FLY;
    }
}
