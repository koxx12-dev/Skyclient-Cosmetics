package io.github.koxx12dev.scc.gui;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;
import io.github.koxx12dev.scc.SkyclientCosmetics;

import java.io.File;

public class Settings extends Vigilant {

    @Property(type = PropertyType.SWITCH, name = "Show Debug Options", description = "SHOWS DEBUG STUFF USELESS FOR EVERYONE BUT ME -koxx12", category = "DEBUG", subcategory = "DEBUG")
    public static boolean showDebug = false;

    @Property(type = PropertyType.SWITCH, name = "Show custom tags", description = "Show all custom tags in new messages", category = "Main", subcategory = "Tags")
    public static boolean showTags = true;

    @Property(type = PropertyType.SWITCH, name = "Shorten custom tag", description = "Shortens all custom tags in new messages", category = "Main", subcategory = "Tags")
    public static boolean shortenTags = false;

    @Property(type = PropertyType.CHECKBOX, name = "Reload Tags", description = "Reloads custom tags", category = "Main", subcategory = "Tags")
    public static boolean reloadTags = false;

    @Property(type = PropertyType.SWITCH, name = "Debug Tags", description = "(RN DOES NOTHING) Replaces every message with your tag", category = "DEBUG", subcategory = "DEBUG")
    public static boolean debugTags = false;

    @Property(type = PropertyType.SWITCH, name = "Debug Display Tags", description = "Changes player names to your tag", category = "DEBUG", subcategory = "DEBUG")
    public static boolean debugDisplayTags = false;

    @Property(type = PropertyType.SWITCH, name = "Show Debug info in the logs", description = "(RN DOES NOTHING) Spams your logs as fuck", category = "DEBUG", subcategory = "DEBUG")
    public static boolean debugLogs = false;

    @Property(type = PropertyType.TEXT, name = "Discord RPC Second Line", description = "Allows you to set second line of the Discord RPC", category = "Main", subcategory = "RPC")
    public static String rpcLineTwo = "SBE bad";

    @Property(type = PropertyType.TEXT, name = "Discord RPC First Line", description = "Allows you to set the first line of the Discord RPC", category = "Main", subcategory = "RPC")
    public static String rpcLineOne = "%player% is very cool";

    @Property(type = PropertyType.TEXT, name = "Discord RPC Img Text", description = "Allows you to set text of the img", category = "Main", subcategory = "RPC")
    public static String rpcImgText = "SkyClient is cool";

    @Property(type = PropertyType.SWITCH, name = "Discord RPC", description = "Enables Discord RPC", category = "Main", subcategory = "RPC")
    public static boolean rpc = true;

    @Property(type = PropertyType.CHECKBOX, name = "First time message", description = "Get \"First time message\" when u join next time", category = "Main", subcategory = "Other")
    public static boolean joinMessage = true;

    @Property(type = PropertyType.SWITCH, name = "Sbe sucks Mode", description = "Do i need to explain this?", category = "Main", subcategory = "RPC")
    public static boolean sbeBadMode = false;

    @Property(type = PropertyType.SWITCH, name = "Tags in Display Names", description = "Shows tags above player names (May crash)", category = "Main", subcategory = "Tags")
    public static boolean displayTags = false;

    @Property(type = PropertyType.TEXT, name = "Hypixel API key", description = "Hypixel API key used for requests", category = "Main", subcategory = "Hypixel", protectedText = true)
    public static String hpApiKey = "";

    @Property(type = PropertyType.SWITCH, name = "Display Name fix", description = "Fixes your display name color (only useful if you use patcher)", category = "Fixes", subcategory = "Main")
    public static boolean displanameFix = false;

    //@Property(type = PropertyType.TEXT, name = "Skyclient Cosmetics API key", description = "SkyclientCosmetics Api key is used for every feature of this mod", category = "Main", subcategory = "Main", protectedText = true)
    //public static String SCCApiKey = "";

    public Settings() {
        super(new File("./config/skyclientcosmetics.toml"));

        initialize();

        addDependency("debugTags","showDebug");
        addDependency("debugDisplayTags","showDebug");
        addDependency("debugLogs","showDebug");

        addDependency("sbeBadMode","rpc");
        addDependency("rpcLineTwo","rpc");
        addDependency("rpcLineOne","rpc");
        addDependency("rpcImgText","rpc");

        addDependency("shortenTags","showTags");
        addDependency("reloadTags","showTags");
        addDependency("displayTags","showTags");

        hidePropertyIf("sbeBadMode",() -> !SkyclientCosmetics.rpcRunning);
        hidePropertyIf("rpcLineTwo",() -> !SkyclientCosmetics.rpcRunning);
        hidePropertyIf("rpcLineOne",() -> !SkyclientCosmetics.rpcRunning);
        hidePropertyIf("rpcImgText",() -> !SkyclientCosmetics.rpcRunning);
        hidePropertyIf("rpc",() -> !SkyclientCosmetics.rpcRunning);
    }

}
