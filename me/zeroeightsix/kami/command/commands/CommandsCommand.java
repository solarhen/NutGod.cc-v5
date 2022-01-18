// 
// Decompiled by Procyon v0.5.36
// 

package me.zeroeightsix.kami.command.commands;

import java.util.Comparator;
import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.command.syntax.SyntaxChunk;
import me.zeroeightsix.kami.command.Command;

public class CommandsCommand extends Command
{
    public CommandsCommand() {
        super("commands", SyntaxChunk.EMPTY);
    }
    
    @Override
    public void call(final String[] args) {
        KamiMod.getInstance().getCommandManager().getCommands().stream().sorted(Comparator.comparing(command -> command.getLabel())).forEach(command -> Command.sendChatMessage("&7" + Command.getCommandPrefix() + command.getLabel() + "&r ~ &8" + command.getDescription()));
    }
}
