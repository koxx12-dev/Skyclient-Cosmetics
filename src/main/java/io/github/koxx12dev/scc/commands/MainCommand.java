package io.github.koxx12dev.scc.commands;

import gg.essential.universal.ChatColor;
import io.github.koxx12dev.scc.SkyclientCosmetics;
import io.github.koxx12dev.scc.gui.Settings;
import io.github.koxx12dev.scc.utils.Chat;
import io.github.koxx12dev.scc.utils.Requests;
import io.github.koxx12dev.scc.utils.Sidebar;
import io.github.koxx12dev.scc.utils.cache.UserCache;
import io.github.koxx12dev.scc.utils.exceptions.APIException;
import io.github.koxx12dev.scc.utils.exceptions.CacheException;
import io.github.koxx12dev.scc.utils.managers.CosmeticsManager;
import io.github.koxx12dev.scc.utils.types.User;
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
        if (args.length < 1) {
            SkyclientCosmetics.displayScreen = SkyclientCosmetics.config.gui();
        } else if (args[0].equalsIgnoreCase("reload")) {
            if (args[1].equalsIgnoreCase("force")) {
                UserCache.invalidateCache();
                Chat.sendSystemMessage(ChatColor.GREEN+"Invalidated Cache");
            }
            try {
                SkyclientCosmetics.api = Requests.getApiData();
                if (SkyclientCosmetics.apiConnectionSuccess) {
                    Requests.reloadTags();
                }
            } catch (IOException | CacheException | APIException e) {
                e.printStackTrace();
            }
            Requests.setRankColor();
        } else if (args[0].equalsIgnoreCase("test") && Settings.showDebug) {
            Chat.sendSystemMessage("\nBits: "+Sidebar.getBits()+"\nPurse: "+Sidebar.getPurse()+"\nSBDate: "+Sidebar.getSBDate()+"\nSBTime: "+Sidebar.getSBTime()+"\nSBLoc: "+Sidebar.getSBLoc()+"\nServer: "+Sidebar.getServer()+"\nObjective: "+Sidebar.getObjective()+"\nScoreBoardName: "+Sidebar.getSidebarTitle());
        } else if (args[0].equalsIgnoreCase("displaytag")) {
            if (CosmeticsManager.isUserAdded(args[1])) {
                User user = CosmeticsManager.getUser(args[1]);

                String name = user.getName();
                String tag = user.getTag();

                Chat.sendClientMessage(tag+ChatColor.RESET+" "+name+ChatColor.GRAY+": test");
            } else {
                Chat.sendSystemMessage(ChatColor.RED+"User \""+args[1]+"\" is doesn't have a tag");
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
