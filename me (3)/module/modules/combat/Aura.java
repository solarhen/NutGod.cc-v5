// 
// Decompiled by Procyon v0.5.36
// 

package me.zeroeightsix.kami.module.modules.combat;

import net.minecraft.util.math.Vec3d;
import me.zeroeightsix.kami.util.LagCompensator;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.item.ItemStack;
import java.util.Iterator;
import me.zeroeightsix.kami.module.modules.misc.AutoTool;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.util.Friends;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import me.zeroeightsix.kami.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumHand;
import net.minecraft.init.Items;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.module.Module;

@Info(name = "Aura", category = Category.COMBAT, description = "Hits entities around you")
public class Aura extends Module
{
    private Setting<Boolean> players;
    private Setting<Boolean> animals;
    private Setting<Boolean> mobs;
    private Setting<Double> range;
    private Setting<Boolean> wait;
    private Setting<Boolean> walls;
    private Setting<Boolean> sharpness;
    
    public Aura() {
        this.players = this.register(Settings.b("Players", true));
        this.animals = this.register(Settings.b("Animals", false));
        this.mobs = this.register(Settings.b("Mobs", false));
        this.range = this.register(Settings.d("Range", 5.5));
        this.wait = this.register(Settings.b("Wait", true));
        this.walls = this.register(Settings.b("Walls", false));
        this.sharpness = this.register(Settings.b("32k Switch", false));
    }
    
    @Override
    public void onUpdate() {
        if (Aura.mc.field_71439_g.field_70128_L) {
            return;
        }
        final boolean shield = Aura.mc.field_71439_g.func_184592_cb().func_77973_b().equals(Items.field_185159_cQ) && Aura.mc.field_71439_g.func_184600_cs() == EnumHand.OFF_HAND;
        if (Aura.mc.field_71439_g.func_184587_cr() && !shield) {
            return;
        }
        if (this.wait.getValue()) {
            if (Aura.mc.field_71439_g.func_184825_o(this.getLagComp()) < 1.0f) {
                return;
            }
            if (Aura.mc.field_71439_g.field_70173_aa % 2 != 0) {
                return;
            }
        }
        for (final Entity target : Minecraft.func_71410_x().field_71441_e.field_72996_f) {
            if (!EntityUtil.isLiving(target)) {
                continue;
            }
            if (target == Aura.mc.field_71439_g) {
                continue;
            }
            if (Aura.mc.field_71439_g.func_70032_d(target) > this.range.getValue()) {
                continue;
            }
            if (((EntityLivingBase)target).func_110143_aJ() <= 0.0f) {
                continue;
            }
            if (((EntityLivingBase)target).field_70737_aN != 0 && this.wait.getValue()) {
                continue;
            }
            if (!this.walls.getValue() && !Aura.mc.field_71439_g.func_70685_l(target) && !this.canEntityFeetBeSeen(target)) {
                continue;
            }
            if (this.players.getValue() && target instanceof EntityPlayer && !Friends.isFriend(target.func_70005_c_())) {
                this.attack(target);
                return;
            }
            Label_0382: {
                if (EntityUtil.isPassive(target)) {
                    if (this.animals.getValue()) {
                        break Label_0382;
                    }
                    continue;
                }
                else {
                    if (EntityUtil.isMobAggressive(target) && this.mobs.getValue()) {
                        break Label_0382;
                    }
                    continue;
                }
                continue;
            }
            if (ModuleManager.isModuleEnabled("AutoTool")) {
                AutoTool.equipBestWeapon();
            }
            this.attack(target);
        }
    }
    
    private boolean checkSharpness(final ItemStack stack) {
        if (stack.func_77978_p() == null) {
            return false;
        }
        final NBTTagList enchants = (NBTTagList)stack.func_77978_p().func_74781_a("ench");
        if (enchants == null) {
            return false;
        }
        int i = 0;
        while (i < enchants.func_74745_c()) {
            final NBTTagCompound enchant = enchants.func_150305_b(i);
            if (enchant.func_74762_e("id") == 16) {
                final int lvl = enchant.func_74762_e("lvl");
                if (lvl >= 16) {
                    return true;
                }
                break;
            }
            else {
                ++i;
            }
        }
        return false;
    }
    
    private void attack(final Entity e) {
        if (this.sharpness.getValue() && !this.checkSharpness(Aura.mc.field_71439_g.func_184614_ca())) {
            int newSlot = -1;
            for (int i = 0; i < 9; ++i) {
                final ItemStack stack = Aura.mc.field_71439_g.field_71071_by.func_70301_a(i);
                if (stack != ItemStack.field_190927_a) {
                    if (this.checkSharpness(stack)) {
                        newSlot = i;
                        break;
                    }
                }
            }
            if (newSlot != -1) {
                Aura.mc.field_71439_g.field_71071_by.field_70461_c = newSlot;
            }
        }
        Aura.mc.field_71442_b.func_78764_a((EntityPlayer)Aura.mc.field_71439_g, e);
        Aura.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
    }
    
    private float getLagComp() {
        if (this.wait.getValue()) {
            return -(20.0f - LagCompensator.INSTANCE.getTickRate());
        }
        return 0.0f;
    }
    
    private boolean canEntityFeetBeSeen(final Entity entityIn) {
        return Aura.mc.field_71441_e.func_147447_a(new Vec3d(Aura.mc.field_71439_g.field_70165_t, Aura.mc.field_71439_g.field_70165_t + Aura.mc.field_71439_g.func_70047_e(), Aura.mc.field_71439_g.field_70161_v), new Vec3d(entityIn.field_70165_t, entityIn.field_70163_u, entityIn.field_70161_v), false, true, false) == null;
    }
}
