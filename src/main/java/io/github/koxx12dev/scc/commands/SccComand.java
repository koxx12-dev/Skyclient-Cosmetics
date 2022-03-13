package io.github.koxx12dev.scc.commands;

import cc.woverflow.onecore.utils.GuiUtils;
import gg.essential.api.commands.Command;
import gg.essential.api.commands.DefaultHandler;
import gg.essential.api.commands.DisplayName;
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

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class SccComand extends Command {
    private final Set<Alias> hashSet = new HashSet<>();

    public SccComand() {
        super("scc");
        hashSet.add(new Alias("skyclientcosmetics"));
    }

    @Override
    public Set<Alias> getCommandAliases() {
        return hashSet;
    }

    @DefaultHandler
    public void handle() {
        GuiUtils.openScreen(SkyclientCosmetics.config);
    }

    @SubCommand("reload")
    public void reload(Optional<Boolean> force) {
        if (force.isPresent() && force.get()) {
            UserCache.invalidateCache();
            Chat.sendSystemMessage(ChatColor.GREEN + "Successfully invalidated cache!");
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

    @SubCommand("test")
    public void test() {
        if (Settings.showDebug) {
            Chat.sendSystemMessage("\nBits: " + Sidebar.getBits() + "\nPurse: " + Sidebar.getPurse() + "\nSBDate: " + Sidebar.getSBDate() + "\nSBTime: " + Sidebar.getSBTime() + "\nSBLoc: " + Sidebar.getSBLoc() + "\nServer: " + Sidebar.getServer() + "\nObjective: " + Sidebar.getObjective() + "\nScoreBoardName: " + Sidebar.getSidebarTitle());
        }
    }

    @SubCommand("displaytag")
    public void displaytag(@DisplayName("Player name") String name) {
        if (CosmeticsManager.isUserAdded(name)) {
            User user = CosmeticsManager.getUser(name);

            String playerName = user.getName();
            String tag = user.getTag();

            Chat.sendClientMessage(tag + ChatColor.RESET + " " + playerName + ChatColor.GRAY + ": test");
        } else {
            Chat.sendSystemMessage(ChatColor.RED + "User \"" + name + "\" is doesn't have a tag");
        }
    }
}
