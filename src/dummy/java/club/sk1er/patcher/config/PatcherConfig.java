package club.sk1er.patcher.config;

import gg.essential.vigilance.Vigilant;

import java.io.File;

public class PatcherConfig extends Vigilant {
    public static boolean hudCaching;
    public static boolean cullingFix;
    public static boolean separateResourceLoading;
    public static boolean disableAchievements;
    public static boolean autoTitleScale;
    public static boolean unfocusedFPS;
    public static boolean cleanProjectiles;
    public static boolean numericalEnchants;
    public static boolean staticItems;
    public static boolean limitChunks;
    public static boolean playerBackFaceCulling;
    public static int openToLanReplacement;

    public static PatcherConfig INSTANCE = new PatcherConfig();

    public PatcherConfig() {
        super(new File(""));
    }
}
