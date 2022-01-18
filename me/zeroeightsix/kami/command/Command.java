// 
// Decompiled by Procyon v0.5.36
// 

package me.zeroeightsix.kami.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.util.text.TextComponentBase;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.util.text.ITextComponent;
import me.zeroeightsix.kami.util.Wrapper;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.command.syntax.SyntaxChunk;

public abstract class Command
{
    protected String label;
    protected String syntax;
    protected String description;
    protected SyntaxChunk[] syntaxChunks;
    public static Setting<String> commandPrefix;
    
    public Command(final String label, final SyntaxChunk[] syntaxChunks) {
        this.label = label;
        this.syntaxChunks = syntaxChunks;
        this.description = "Descriptionless";
    }
    
    public static void sendChatMessage(final String message) {
        sendRawChatMessage("§8 [Nutgod.cc]" + message);
    }
    
    public static void sendStringChatMessage(final String[] messages) {
        sendChatMessage("");
        for (final String s : messages) {
            sendRawChatMessage(s);
        }
    }
    
    public static void sendRawChatMessage(final String message) {
        Wrapper.getPlayer().func_145747_a((ITextComponent)new ChatMessage(message));
    }
    
    protected void setDescription(final String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public static String getCommandPrefix() {
        return Command.commandPrefix.getValue();
    }
    
    public String getLabel() {
        return this.label;
    }
    
    public abstract void call(final String[] p0);
    
    public SyntaxChunk[] getSyntaxChunks() {
        return this.syntaxChunks;
    }
    
    protected SyntaxChunk getSyntaxChunk(final String name) {
        for (final SyntaxChunk c : this.syntaxChunks) {
            if (c.getType().equals(name)) {
                return c;
            }
        }
        return null;
    }
    
    public static char SECTIONSIGN() {
        return '§';
    }
    
    static {
        Command.commandPrefix = Settings.s("commandPrefix", ".");
    }
    
    public static class ChatMessage extends TextComponentBase
    {
        String text;
        
        public ChatMessage(final String text) {
            final Pattern p = Pattern.compile("&[0123456789abcdefrlosmk]");
            final Matcher m = p.matcher(text);
            final StringBuffer sb = new StringBuffer();
            while (m.find()) {
                final String replacement = "§" + m.group().substring(1);
                m.appendReplacement(sb, replacement);
            }
            m.appendTail(sb);
            this.text = sb.toString();
        }
        
        public String func_150261_e() {
            return this.text;
        }
        
        public ITextComponent func_150259_f() {
            return (ITextComponent)new ChatMessage(this.text);
        }
    }
}
