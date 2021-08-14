/*
 * SkyclientCosmetics - Cool cosmetics for a mod installer Skyclient!
 * Copyright (C) koxx12-dev [2021 - 2021]
 *
 * This program comes with ABSOLUTELY NO WARRANTY
 * This is free software, and you are welcome to redistribute it
 * under the certain conditions that can be found here
 * https://www.gnu.org/licenses/lgpl-3.0.en.html
 *
 * If you have any questions or concerns, please create
 * an issue on the github page that can be found under this url
 * https://github.com/koxx12-dev/Skyclient-Cosmetics
 *
 * If you have a private concern, please contact me on
 * Discord: Koxx12#8061
 */

package io.github.koxx12dev.scc.gui;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;
import io.github.koxx12dev.scc.SkyclientCosmetics;
import io.github.koxx12dev.scc.utils.Requests;
import io.github.koxx12dev.scc.utils.exceptions.APIException;
import io.github.koxx12dev.scc.utils.exceptions.CacheException;
import io.github.koxx12dev.scc.utils.managers.CacheManager;

import java.io.File;
import java.io.IOException;

public class Settings extends Vigilant {

    @Property(type = PropertyType.SWITCH, name = "Show Debug Options", description = "SHOWS DEBUG STUFF USELESS FOR EVERYONE BUT ME -koxx12\n\u00A7cANY CRASHES CAUSED BY DEBUG SETTINGS ARE YOUR FAULT AND NOT SCC FAULT", category = "DEBUG", subcategory = "DEBUG")
    public static boolean showDebug = false;

    @Property(type = PropertyType.SWITCH, name = "Show custom tags", description = "Show all custom tags in new messages", category = "Main", subcategory = "Tags")
    public static boolean showTags = true;

    @Property(type = PropertyType.SWITCH, name = "Shorten custom tag", description = "Shortens all custom tags in new messages", category = "Main", subcategory = "Tags")
    public static boolean shortenTags = false;

    @Property(type = PropertyType.BUTTON, name = "Reload Tags", description = "Reloads custom tags", category = "Main", subcategory = "Tags", placeholder = "Reload")
    public static void reloadTags() throws APIException, CacheException, IOException {
        SkyclientCosmetics.api = Requests.getApiData();
        if (SkyclientCosmetics.apiConnectionSuccess) {
            Requests.reloadTags();
        }
    }

    @Property(type = PropertyType.SWITCH, name = "Debug Tags", description = "(RN DOES NOTHING) Replaces every message with your tag", category = "DEBUG", subcategory = "Tags")
    public static boolean debugTags = false;

    @Property(type = PropertyType.SWITCH, name = "Debug Display Tags", description = "Changes player names to your tag", category = "DEBUG", subcategory = "Chat")
    public static boolean debugDisplayTags = false;

    @Property(type = PropertyType.SWITCH, name = "Show Debug info in the logs", description = "Spams your logs as fuck", category = "DEBUG", subcategory = "Logs")
    public static boolean debugLogs = false;

    @Property(type = PropertyType.TEXT, name = "Discord RPC Second Line", description = "Allows you to set second line of the Discord RPC\n\u00A7aAllows usage of Placeholders. More info on the wiki (https://github.com/koxx12-dev/Skyclient-Cosmetics/wiki/Discord-RPC)", category = "Main", subcategory = "Discord Rich Presence")
    public static String rpcLineTwo = "SBE bad";

    @Property(type = PropertyType.TEXT, name = "Discord RPC First Line", description = "Allows you to set the first line of the Discord RPC\n\u00A7aAllows usage of Placeholders. More info on the wiki (https://github.com/koxx12-dev/Skyclient-Cosmetics/wiki/Discord-RPC)", category = "Main", subcategory = "Discord Rich Presence")
    public static String rpcLineOne = "%player% is very cool";

    @Property(type = PropertyType.TEXT, name = "Discord RPC Img Text", description = "Allows you to set text of the img\n\u00A7aAllows usage of Placeholders. More info on the wiki (https://github.com/koxx12-dev/Skyclient-Cosmetics/wiki/Discord-RPC)", category = "Main", subcategory = "Discord Rich Presence")
    public static String rpcImgText = "SkyClient is cool";

    @Property(type = PropertyType.SWITCH, name = "Discord RPC", description = "Enables Discord RPC", category = "Main", subcategory = "Discord Rich Presence")
    public static boolean rpc = true;

    @Property(type = PropertyType.CHECKBOX, name = "First time message", description = "Get \"First time message\" when u join next time", category = "Misc", subcategory = "Chat")
    public static boolean joinMessage = true;

    @Property(type = PropertyType.SWITCH, name = "Sbe sucks Mode", description = "Do i need to explain this?", category = "Main", subcategory = "Discord Rich Presence")
    public static boolean sbeBadMode = false;

    @Property(type = PropertyType.SWITCH, name = "Tags in Display Names", description = "Shows tags above player names\n\u00A7c(May crash)", category = "Main", subcategory = "Tags")
    public static boolean displayTags = false;

    @Property(type = PropertyType.TEXT, name = "Hypixel API key", description = "Hypixel API key used for requests", category = "Main", subcategory = "Hypixel", protectedText = true)
    public static String hpApiKey = "";

    @Property(type = PropertyType.SWITCH, name = "Display Name fix", description = "Fixes your display name color\n\u00A7c(only useful if you use patcher and requires restart)", category = "Fixes", subcategory = "Main")
    public static boolean displayNameFix = false;

    @Property(type = PropertyType.SWITCH, name = "Debug Regex", description = "Sends debug regex info in the chat\n\u00A7c(Can break mods that read chat)", category = "DEBUG", subcategory = "Chat")
    public static boolean debugRegexChat = false;

    @Property(type = PropertyType.SWITCH, name = "Hide pet zord", description = "Why would you do that?", category = "Misc", subcategory = "Gui")
    public static boolean hidePetZord = false;

    //@Property(type = PropertyType.TEXT, name = "Skyclient Cosmetics API key", description = "SkyclientCosmetics Api key is used for every feature of this mod", category = "Main", subcategory = "Main", protectedText = true)
    //public static String SCCApiKey = "";

    public Settings()  {
        super(new File("./SkyclientCosmetics/skyclientcosmetics.toml"));

        //final Class<Settings> SettingsClass = Settings.class;

        initialize();

        addDependency("debugTags","showDebug");
        addDependency("debugDisplayTags","showDebug");
        addDependency("debugLogs","showDebug");
        addDependency("debugRegexChat","showDebug");

        addDependency("sbeBadMode","rpc");
        addDependency("rpcLineTwo","rpc");
        addDependency("rpcLineOne","rpc");
        addDependency("rpcImgText","rpc");

        addDependency("shortenTags","showTags");
        //addDependency("reloadTags","showTags");
        //addDependency(SettingsClass.getField("r"),SettingsClass.getField("showTags"));
        addDependency("displayTags","showTags");

        registerListener("hidePetZord",
                a -> {
            if ((boolean) a) {
                try {
                    new File(CacheManager.sccFolder,"HIDEPETZORD").createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                if (new File(CacheManager.sccFolder,"HIDEPETZORD").exists()) {
                    new File(CacheManager.sccFolder, "HIDEPETZORD").delete();
                }
            }
        });

    }

}
