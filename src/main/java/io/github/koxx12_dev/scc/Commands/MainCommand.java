package io.github.koxx12_dev.scc.Commands;

import io.github.koxx12_dev.scc.SCC;
import io.github.koxx12_dev.scc.Utils.HTTPstuff;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class MainCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "scc";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "scc";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1) {
            SCC.displayScreen = SCC.config.gui();
        } else if (args[0].equalsIgnoreCase("reload")) {
            HTTPstuff.reloadTags();
        } else if (args[0].equalsIgnoreCase("debug")) {
            if (args[1].equalsIgnoreCase("on")) {
                SCC.Debug = true;
            } else {
                SCC.Debug = false;
            }
        } else {
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED+"Unknown command\n"+EnumChatFormatting.RED+"\"/scc\" for config gui\n"+EnumChatFormatting.RED+"\"/scc reload\" to reload tags"));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return -1;
    }
}
