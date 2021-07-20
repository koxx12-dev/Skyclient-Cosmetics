package io.github.koxx12dev.scc.commands;

import io.github.koxx12dev.scc.SkyclientCosmetics;
import io.github.koxx12dev.scc.utils.Requests;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.io.IOException;

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
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length != 1) {
            SkyclientCosmetics.displayScreen = SkyclientCosmetics.config.gui();
        } else if (args[0].equalsIgnoreCase("reload")) {
            try {
                Requests.reloadTags();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Requests.setRankColor();
        } else {
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED+"Unknown command\n"+EnumChatFormatting.RED+"\"/scc\" for config gui\n"+EnumChatFormatting.RED+"\"/scc reload\" to reload tags"));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return -1;
    }
}
