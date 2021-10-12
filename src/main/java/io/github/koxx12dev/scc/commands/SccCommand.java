package io.github.koxx12dev.scc.commands;

import gg.essential.api.EssentialAPI;
import gg.essential.api.commands.Command;
import gg.essential.api.commands.DefaultHandler;
import gg.essential.api.commands.SubCommand;
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
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

public class SccCommand extends Command {
    public SccCommand() {
        super("skyclientcosmetics", true);
    }

    @DefaultHandler
    public void handle() {
        EssentialAPI.getGuiUtil().openScreen(SkyclientCosmetics.config.gui());
    }

    @SubCommand(value = "reload", description = "Reload the SkyClient Cosmetics API")
    public void reload(@Nullable String force) {
        if (force.equalsIgnoreCase("force")) {
            UserCache.invalidateCache();
            Chat.sendSystemMessage(ChatColor.GREEN + "Invalidated Cache");
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
    }

    @SubCommand(value = "test", description = "Debug command.")
    public void test() {
        if (Settings.showDebug)
            Chat.sendSystemMessage("\nBits: " + Sidebar.getBits() + "\nPurse: " + Sidebar.getPurse() + "\nSBDate: " + Sidebar.getSBDate() + "\nSBTime: " + Sidebar.getSBTime() + "\nSBLoc: " + Sidebar.getSBLoc() + "\nServer: " + Sidebar.getServer() + "\nObjective: " + Sidebar.getObjective() + "\nScoreBoardName: " + Sidebar.getSidebarTitle());
    }

    @SubCommand(value = "tag", description = "Get the tag of a player.")
    public void tag(String username) {
        if (CosmeticsManager.isUserAdded(username)) {
            User user = CosmeticsManager.getUser(username);

            String name = user.getName();
            String tag = user.getTag();

            Chat.sendClientMessage(tag + ChatColor.RESET + " " + name + ChatColor.GRAY + ": test");
        } else {
            Chat.sendSystemMessage(ChatColor.RED + "User \"" + username + "\" is doesn't have a tag");
        }
    }

    @Override
    public Set<Alias> getCommandAliases() {
        return Collections.singleton(new Alias("scc"));
    }
}
