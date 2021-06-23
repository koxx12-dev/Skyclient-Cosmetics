package io.github.koxx12_dev.scc.GUI;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;
import io.github.koxx12_dev.scc.Utils.HTTPstuff;

import java.io.File;

public class SCCConfig extends Vigilant {

    @Property(type = PropertyType.SWITCH, name = "Hide custom tags", description = "Hides all custom tags in new messages", category = "Main", subcategory = "Tags")
    public static boolean TagsHidden = false;

    @Property(type = PropertyType.BUTTON, name = "Reload Tags", description = "Reloads custom tags", category = "Tags", subcategory = "Main")
    private void reload() {
        HTTPstuff.reloadTags();
    }

    @Property(type = PropertyType.SWITCH, name = "DEBUG MODE", description = "FOR DEV ONLY", category = "DEBUG", subcategory = "DEBUG",hidden = true)
    public static boolean DEBUG = false;

    //@Property(type = PropertyType.TEXT, name = "Skyclient Cosmetics API key", description = "SCC Api key is used for every feature of this mod", category = "Main", subcategory = "Main", protectedText = true)
    //public static String SCCApiKey = "";

    public SCCConfig() {
        super(new File("./config/skyclientcosmetics.toml"));
        initialize();
    }

}
