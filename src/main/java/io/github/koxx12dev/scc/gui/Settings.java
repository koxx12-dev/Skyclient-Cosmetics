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
import io.github.koxx12dev.scc.cosmetics.TagCosmetics;
import io.github.koxx12dev.scc.utils.Files;

import java.io.File;
import java.io.IOException;

public class Settings extends Vigilant {

    @Property(type = PropertyType.SWITCH, name = "Show Custom Tags", description = "Show the custom tags, which are the main focus of this mod.", category = "Main", subcategory = "Tags")
    public static boolean showTags = true;

    @Property(type = PropertyType.SWITCH, name = "Shorten Custom Tags", description = "Use shorter tags.\n[BOOSTER] becomes [B], for example.", category = "Main", subcategory = "Tags")
    public static boolean shortenTags = false;

    @Property(type = PropertyType.BUTTON, name = "Reload Tags", description = "Reloads the custom tags.", category = "Main", subcategory = "Tags", placeholder = "Reload")
    public static void reloadTags() {
        if (TagCosmetics.getInstance().isInitialized()) TagCosmetics.getInstance().reInitialize();
    }

    @Property(type = PropertyType.TEXT, name = "Discord RPC Second Line", description = "Allows you to edit the second line of the Discord RPC\n\u00A7aAllows usage of Placeholders. More info on the wiki (https://github.com/koxx12-dev/Skyclient-Cosmetics/wiki/Discord-RPC)", category = "Main", subcategory = "Discord Rich Presence")
    public static String rpcLineTwo = "SBE bad";

    @Property(type = PropertyType.TEXT, name = "Discord RPC First Line", description = "Allows you to edit the first line of the Discord RPC\n\u00A7aAllows usage of Placeholders. More info on the wiki (https://github.com/koxx12-dev/Skyclient-Cosmetics/wiki/Discord-RPC)", category = "Main", subcategory = "Discord Rich Presence")
    public static String rpcLineOne = "%player% is very cool";

    @Property(type = PropertyType.TEXT, name = "Discord RPC Img Text", description = "Allows you to set text of the img\n\u00A7aAllows usage of Placeholders. More info on the wiki (https://github.com/koxx12-dev/Skyclient-Cosmetics/wiki/Discord-RPC)", category = "Main", subcategory = "Discord Rich Presence")
    public static String rpcImgText = "SkyClient is cool";

    @Property(type = PropertyType.SWITCH, name = "Discord RPC", description = "Enables Discord RPC", category = "Main", subcategory = "Discord Rich Presence")
    public static boolean rpc = true;

    @Property(type = PropertyType.SWITCH, name = "First Time Message", description = "Get \"First time message\" when u join next time", category = "Misc", subcategory = "Chat", hidden = true)
    public static boolean joinMessage = true;

    @Property(type = PropertyType.SWITCH, name = "SBE Sucks Mode", description = "https://github.com/MicrocontrollersDev/Alternatives/blob/1e409e056e3e14ca874a2368c045de96787e8cbd/SkyblockExtras.md", category = "Main", subcategory = "Discord Rich Presence")
    public static boolean sbeBadMode = false;

    @Property(type = PropertyType.SWITCH, name = "Tags in Display Names", description = "Shows tags above player names\n\u00A7c(May crash)", category = "Main", subcategory = "Tags")
    public static boolean displayTags = false;

    @Property(type = PropertyType.TEXT, name = "Hypixel API key", description = "Hypixel API key used for requests", category = "Main", subcategory = "Hypixel", protectedText = true)
    public static String hpApiKey = "";

    @Property(type = PropertyType.SWITCH, name = "Hide Pet Lisena", description = "WARNING: TURNING THIS ON WILL BREAK EVERYTHING", category = "Misc", subcategory = "Gui")
    public static boolean hidePetLis = false;

    //@Property(type = PropertyType.TEXT, name = "Skyclient Cosmetics API key", description = "SkyclientCosmetics Api key is used for every feature of this mod", category = "Main", subcategory = "Main", protectedText = true)
    //public static String SCCApiKey = "";

    public Settings()  {
        super(new File("./SkyclientCosmetics/skyclientcosmetics.toml"));

        //final Class<Settings> SettingsClass = Settings.class;

        initialize();

        addDependency("sbeBadMode","rpc");
        addDependency("rpcLineTwo","rpc");
        addDependency("rpcLineOne","rpc");
        addDependency("rpcImgText","rpc");

        addDependency("shortenTags","showTags");
        //addDependency("reloadTags","showTags");
        //addDependency(SettingsClass.getField("r"),SettingsClass.getField("showTags"));
        addDependency("displayTags","showTags");

        registerListener("hidePetLis",
                a -> {
            if ((boolean) a) {
                try {
                    new File(Files.sccFolder,"HIDEPETLIS").createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                if (new File(Files.sccFolder,"HIDEPETLIS").exists()) {
                    new File(Files.sccFolder, "HIDEPETLIS").delete();
                }
            }
        });
    }
}
