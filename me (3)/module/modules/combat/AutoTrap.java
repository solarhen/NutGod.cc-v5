// 
// Decompiled by Procyon v0.5.36
// 

package me.zeroeightsix.kami.module.modules.combat;

import java.util.concurrent.TimeUnit;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3i;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import java.util.Iterator;
import net.minecraft.client.entity.EntityPlayerSP;
import me.zeroeightsix.kami.util.Friends;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.Entity;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import me.zeroeightsix.kami.module.Module;

@Info(name = "AutoTrap ", category = Category.COMBAT)
public class AutoTrap extends Module
{
    BlockPos abovehead;
    BlockPos aboveheadpartner;
    BlockPos aboveheadpartner2;
    BlockPos aboveheadpartner3;
    BlockPos aboveheadpartner4;
    BlockPos side1;
    BlockPos side2;
    BlockPos side3;
    BlockPos side4;
    BlockPos side11;
    BlockPos side22;
    BlockPos side33;
    BlockPos side44;
    int delay;
    public static EntityPlayer target;
    public static List<EntityPlayer> targets;
    public static float yaw;
    public static float pitch;
    
    public boolean isInBlockRange(final Entity target) {
        return target.func_70032_d((Entity)AutoTrap.mc.field_71439_g) <= 4.0f;
    }
    
    public static boolean canBeClicked(final BlockPos pos) {
        return AutoTrap.mc.field_71441_e.func_180495_p(pos).func_177230_c().func_176209_a(AutoTrap.mc.field_71441_e.func_180495_p(pos), false);
    }
    
    private static void faceVectorPacket(final Vec3d vec) {
        final double diffX = vec.field_72450_a - AutoTrap.mc.field_71439_g.field_70165_t;
        final double diffY = vec.field_72448_b - AutoTrap.mc.field_71439_g.field_70163_u + AutoTrap.mc.field_71439_g.func_70047_e();
        final double diffZ = vec.field_72449_c - AutoTrap.mc.field_71439_g.field_70161_v;
        final double dist = MathHelper.func_76133_a(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, dist)));
        AutoTrap.mc.func_147114_u().func_147297_a((Packet)new CPacketPlayer.Rotation(AutoTrap.mc.field_71439_g.field_70177_z + MathHelper.func_76142_g(yaw - AutoTrap.mc.field_71439_g.field_70177_z), AutoTrap.mc.field_71439_g.field_70125_A + MathHelper.func_76142_g(pitch - AutoTrap.mc.field_71439_g.field_70125_A), AutoTrap.mc.field_71439_g.field_70122_E));
    }
    
    public boolean isValid(final EntityPlayer entity) {
        if (entity instanceof EntityPlayer) {
            final EntityPlayer animal = entity;
            if (this.isInBlockRange((Entity)animal) && animal.func_110143_aJ() > 0.0f && !animal.field_70128_L && !animal.func_70005_c_().startsWith("Body #") && !Friends.isFriend(animal.func_70005_c_())) {
                return true;
            }
        }
        return false;
    }
    
    public void loadTargets() {
        for (final EntityPlayer player : AutoTrap.mc.field_71441_e.field_73010_i) {
            if (!(player instanceof EntityPlayerSP)) {
                final EntityPlayer p = player;
                if (this.isValid(p)) {
                    AutoTrap.targets.add(p);
                }
                else {
                    if (!AutoTrap.targets.contains(p)) {
                        continue;
                    }
                    AutoTrap.targets.remove(p);
                }
            }
        }
    }
    
    private boolean isStackObby(final ItemStack stack) {
        return stack != null && stack.func_77973_b() == Item.func_150899_d(49);
    }
    
    private boolean doesHotbarHaveObby() {
        for (int i = 36; i < 45; ++i) {
            final ItemStack stack = AutoTrap.mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
            if (stack != null && this.isStackObby(stack)) {
                return true;
            }
        }
        return false;
    }
    
    public static Block getBlock(final BlockPos pos) {
        return getState(pos).func_177230_c();
    }
    
    public static IBlockState getState(final BlockPos pos) {
        return AutoTrap.mc.field_71441_e.func_180495_p(pos);
    }
    
    public static boolean placeBlockLegit(final BlockPos pos) {
        final Vec3d eyesPos = new Vec3d(AutoTrap.mc.field_71439_g.field_70165_t, AutoTrap.mc.field_71439_g.field_70163_u + AutoTrap.mc.field_71439_g.func_70047_e(), AutoTrap.mc.field_71439_g.field_70161_v);
        final Vec3d posVec = new Vec3d((Vec3i)pos).func_72441_c(0.5, 0.5, 0.5);
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.func_177972_a(side);
            if (canBeClicked(neighbor)) {
                final Vec3d hitVec = posVec.func_178787_e(new Vec3d(side.func_176730_m()).func_186678_a(0.5));
                if (eyesPos.func_72436_e(hitVec) <= 36.0) {
                    AutoTrap.mc.field_71442_b.func_187099_a(AutoTrap.mc.field_71439_g, AutoTrap.mc.field_71441_e, neighbor, side.func_176734_d(), hitVec, EnumHand.MAIN_HAND);
                    AutoTrap.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
                    try {
                        TimeUnit.MILLISECONDS.sleep(10L);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public void onUpdate() {
        if (AutoTrap.mc.field_71439_g.func_184587_cr()) {
            return;
        }
        if (!this.isValid(AutoTrap.target) || AutoTrap.target == null) {
            this.updateTarget();
        }
        for (final EntityPlayer player : AutoTrap.mc.field_71441_e.field_73010_i) {
            if (!(player instanceof EntityPlayerSP)) {
                final EntityPlayer e = player;
                if (this.isValid(e) && e.func_70032_d((Entity)AutoTrap.mc.field_71439_g) < AutoTrap.target.func_70032_d((Entity)AutoTrap.mc.field_71439_g)) {
                    AutoTrap.target = e;
                    return;
                }
                continue;
            }
        }
        if (this.isValid(AutoTrap.target) && AutoTrap.mc.field_71439_g.func_70032_d((Entity)AutoTrap.target) < 4.0f) {
            this.trap(AutoTrap.target);
        }
        else {
            this.delay = 0;
        }
    }
    
    public static double roundToHalf(final double d) {
        return Math.round(d * 2.0) / 2.0;
    }
    
    public void onEnable() {
        this.delay = 0;
    }
    
    private void trap(final EntityPlayer player) {
        if (player.field_191988_bg == 0.0 && player.field_70702_br == 0.0 && player.field_70701_bs == 0.0) {
            ++this.delay;
        }
        if (player.field_191988_bg != 0.0 || player.field_70702_br != 0.0 || player.field_70701_bs != 0.0) {
            this.delay = 0;
        }
        if (!this.doesHotbarHaveObby()) {
            this.delay = 0;
        }
        if (this.delay == 20 && this.doesHotbarHaveObby()) {
            this.abovehead = new BlockPos(player.field_70165_t, player.field_70163_u + 2.0, player.field_70161_v);
            this.aboveheadpartner = new BlockPos(player.field_70165_t + 1.0, player.field_70163_u + 2.0, player.field_70161_v);
            this.aboveheadpartner2 = new BlockPos(player.field_70165_t - 1.0, player.field_70163_u + 2.0, player.field_70161_v);
            this.aboveheadpartner3 = new BlockPos(player.field_70165_t, player.field_70163_u + 2.0, player.field_70161_v + 1.0);
            this.aboveheadpartner4 = new BlockPos(player.field_70165_t, player.field_70163_u + 2.0, player.field_70161_v - 1.0);
            this.side1 = new BlockPos(player.field_70165_t + 1.0, player.field_70163_u, player.field_70161_v);
            this.side2 = new BlockPos(player.field_70165_t, player.field_70163_u, player.field_70161_v + 1.0);
            this.side3 = new BlockPos(player.field_70165_t - 1.0, player.field_70163_u, player.field_70161_v);
            this.side4 = new BlockPos(player.field_70165_t, player.field_70163_u, player.field_70161_v - 1.0);
            this.side11 = new BlockPos(player.field_70165_t + 1.0, player.field_70163_u + 1.0, player.field_70161_v);
            this.side22 = new BlockPos(player.field_70165_t, player.field_70163_u + 1.0, player.field_70161_v + 1.0);
            this.side33 = new BlockPos(player.field_70165_t - 1.0, player.field_70163_u + 1.0, player.field_70161_v);
            this.side44 = new BlockPos(player.field_70165_t, player.field_70163_u + 1.0, player.field_70161_v - 1.0);
            for (int i = 36; i < 45; ++i) {
                final ItemStack stack = AutoTrap.mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
                if (stack != null && this.isStackObby(stack)) {
                    final int oldSlot = AutoTrap.mc.field_71439_g.field_71071_by.field_70461_c;
                    if (AutoTrap.mc.field_71441_e.func_180495_p(this.abovehead).func_185904_a().func_76222_j() || AutoTrap.mc.field_71441_e.func_180495_p(this.side1).func_185904_a().func_76222_j() || AutoTrap.mc.field_71441_e.func_180495_p(this.side2).func_185904_a().func_76222_j() || AutoTrap.mc.field_71441_e.func_180495_p(this.side3).func_185904_a().func_76222_j() || AutoTrap.mc.field_71441_e.func_180495_p(this.side4).func_185904_a().func_76222_j()) {
                        AutoTrap.mc.field_71439_g.field_71071_by.field_70461_c = i - 36;
                        if (AutoTrap.mc.field_71441_e.func_180495_p(this.side1).func_185904_a().func_76222_j()) {
                            placeBlockLegit(this.side1);
                        }
                        if (AutoTrap.mc.field_71441_e.func_180495_p(this.side2).func_185904_a().func_76222_j()) {
                            placeBlockLegit(this.side2);
                        }
                        if (AutoTrap.mc.field_71441_e.func_180495_p(this.side3).func_185904_a().func_76222_j()) {
                            placeBlockLegit(this.side3);
                        }
                        if (AutoTrap.mc.field_71441_e.func_180495_p(this.side4).func_185904_a().func_76222_j()) {
                            placeBlockLegit(this.side4);
                        }
                        if (AutoTrap.mc.field_71441_e.func_180495_p(this.side11).func_185904_a().func_76222_j()) {
                            placeBlockLegit(this.side11);
                        }
                        if (AutoTrap.mc.field_71441_e.func_180495_p(this.side22).func_185904_a().func_76222_j()) {
                            placeBlockLegit(this.side22);
                        }
                        if (AutoTrap.mc.field_71441_e.func_180495_p(this.side33).func_185904_a().func_76222_j()) {
                            placeBlockLegit(this.side33);
                        }
                        if (AutoTrap.mc.field_71441_e.func_180495_p(this.side44).func_185904_a().func_76222_j()) {
                            placeBlockLegit(this.side44);
                        }
                        if (AutoTrap.mc.field_71441_e.func_180495_p(this.aboveheadpartner).func_185904_a().func_76222_j()) {
                            placeBlockLegit(this.aboveheadpartner);
                        }
                        if (AutoTrap.mc.field_71441_e.func_180495_p(this.abovehead).func_185904_a().func_76222_j()) {
                            placeBlockLegit(this.abovehead);
                        }
                        if (AutoTrap.mc.field_71441_e.func_180495_p(this.aboveheadpartner2).func_185904_a().func_76222_j()) {
                            placeBlockLegit(this.aboveheadpartner2);
                        }
                        if (AutoTrap.mc.field_71441_e.func_180495_p(this.aboveheadpartner3).func_185904_a().func_76222_j()) {
                            placeBlockLegit(this.aboveheadpartner3);
                        }
                        if (AutoTrap.mc.field_71441_e.func_180495_p(this.aboveheadpartner4).func_185904_a().func_76222_j()) {
                            placeBlockLegit(this.aboveheadpartner4);
                        }
                        AutoTrap.mc.field_71439_g.field_71071_by.field_70461_c = oldSlot;
                        this.delay = 0;
                        break;
                    }
                    this.delay = 0;
                }
                this.delay = 0;
            }
        }
    }
    
    public void onDisable() {
        this.delay = 0;
        AutoTrap.yaw = AutoTrap.mc.field_71439_g.field_70177_z;
        AutoTrap.pitch = AutoTrap.mc.field_71439_g.field_70125_A;
        AutoTrap.target = null;
    }
    
    public void updateTarget() {
        for (final EntityPlayer player : AutoTrap.mc.field_71441_e.field_73010_i) {
            if (!(player instanceof EntityPlayerSP)) {
                final EntityPlayer entity = player;
                if (entity instanceof EntityPlayerSP || !this.isValid(entity)) {
                    continue;
                }
                AutoTrap.target = entity;
            }
        }
    }
    
    public EnumFacing getEnumFacing(final float posX, final float posY, final float posZ) {
        return EnumFacing.func_176737_a(posX, posY, posZ);
    }
    
    public BlockPos getBlockPos(final double x, final double y, final double z) {
        return new BlockPos(x, y, z);
    }
}
