package io.github.koxx12_dev.scc.GUI;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;

import java.io.File;

public class SCCConfig extends Vigilant {

    @Property(type = PropertyType.SWITCH, name = "Show Debug Options", description = "SHOWS DEBUG STUFF USELESS FOR EVERYONE BUT ME -koxx12", category = "DEBUG", subcategory = "DEBUG", hidden = true)
    public static boolean ShowDebug = false;

    @Property(type = PropertyType.SWITCH, name = "Show custom tags", description = "Show all custom tags in new messages", category = "Main", subcategory = "Tags")
    public static boolean TagsShow = true;

    @Property(type = PropertyType.SWITCH, name = "Shorten custom tag", description = "Shortens all custom tags in new messages", category = "Main", subcategory = "Tags")
    public static boolean ShortenTags = false;

    @Property(type = PropertyType.CHECKBOX, name = "Reload Tags", description = "Reloads custom tags", category = "Main", subcategory = "Tags")
    public static boolean reloadTags = false;

    @Property(type = PropertyType.SWITCH, name = "Debug Tags", description = "Replaces every message with your tag", category = "DEBUG", subcategory = "DEBUG")
    public static boolean DebugTags = false;

    @Property(type = PropertyType.SWITCH, name = "Debug Display Tags", description = "Changes player names to your tag", category = "DEBUG", subcategory = "DEBUG")
    public static boolean DebugDisplayTags = false;

    @Property(type = PropertyType.TEXT, name = "Discord RPC Second Line", description = "Allows you to set second line of the Discord RPC", category = "Main", subcategory = "RPC")
    public static String RPCLineTwo = "SBE bad";

    @Property(type = PropertyType.TEXT, name = "Discord RPC First Line", description = "Allows you to set first line of the Discord RPC", category = "Main", subcategory = "RPC")
    public static String RPCLineOne = "%player% is very cool";

    @Property(type = PropertyType.TEXT, name = "Discord RPC Img Text", description = "Allows you to set text of the img", category = "Main", subcategory = "RPC")
    public static String RPCImgText = "Skyclient is cool";

    @Property(type = PropertyType.SWITCH, name = "Discord RPC", description = "Enables Discord RPC", category = "Main", subcategory = "RPC")
    public static boolean RPC = true;

    @Property(type = PropertyType.CHECKBOX, name = "First time message", description = "Get \"First time message\" when u join next time", category = "Main", subcategory = "Other")
    public static boolean JoinMessage = true;

    @Property(type = PropertyType.SWITCH, name = "Sbe sucks Mode", description = "Do i need to explain this?", category = "Main", subcategory = "RPC")
    public static boolean BadSbeMode = false;

    @Property(type = PropertyType.SWITCH, name = "Tags in Display Names", description = "Shows tags above player names (May crash)", category = "Main", subcategory = "Tags")
    public static boolean DisplayTags = false;

    //@Property(type = PropertyType.TEXT, name = "Skyclient Cosmetics API key", description = "SCC Api key is used for every feature of this mod", category = "Main", subcategory = "Main", protectedText = true)
    //public static String SCCApiKey = "";

    public SCCConfig() {
        super(new File("./config/skyclientcosmetics.toml"));

        initialize();

        addDependency("DebugTags","ShowDebug");
        addDependency("DebugDisplayTags","ShowDebug");

        addDependency("BadSbeMode","RPC");
        addDependency("RPCLineTwo","RPC");
        addDependency("RPCLineOne","RPC");
        addDependency("RPCImgText","RPC");

        addDependency("ShortenTags","TagsShow");
        addDependency("reloadTags","TagsShow");
        addDependency("DisplayTags","TagsShow");




    }

}
