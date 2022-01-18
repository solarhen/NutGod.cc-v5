// 
// Decompiled by Procyon v0.5.36
// 

package me.zeroeightsix.kami.module.modules.misc;

import java.util.function.Predicate;
import net.minecraft.network.play.client.CPacketChatMessage;
import me.zeroeightsix.kami.setting.Settings;
import me.zero.alpine.listener.EventHandler;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.module.Module;

@Info(name = "Nutgod.cc chat", category = Category.MISC, description = "Modifies your chat messages")
public class CustomChat extends Module
{
    private Setting<Boolean> commands;
    private final String KAMI_SUFFIX = " \u23d0 \u0274\u1d1c\u1d1b\u0262\u1d0f\u1d05.\u1d04\u1d04 \u0fc9";
    @EventHandler
    public Listener<PacketEvent.Send> listener;
    
    public CustomChat() {
        this.commands = this.register(Settings.b("Commands", false));
        String s;
        String s2;
        this.listener = new Listener<PacketEvent.Send>(event -> {
            if (event.getPacket() instanceof CPacketChatMessage) {
                s = ((CPacketChatMessage)event.getPacket()).func_149439_c();
                if (!s.startsWith("/") || this.commands.getValue()) {
                    s2 = s + " \u23d0 \u0274\u1d1c\u1d1b\u0262\u1d0f\u1d05.\u1d04\u1d04 \u0fc9";
                    if (s2.length() >= 256) {
                        s2 = s2.substring(0, 256);
                    }
                    ((CPacketChatMessage)event.getPacket()).field_149440_a = s2;
                }
            }
        }, (Predicate<PacketEvent.Send>[])new Predicate[0]);
    }
}
