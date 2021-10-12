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

package io.github.koxx12dev.scc.rpc;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.DiscordEventAdapter;
import de.jcm.discordgamesdk.GameSDKException;
import de.jcm.discordgamesdk.activity.Activity;
import io.github.koxx12dev.scc.SkyclientCosmetics;
import io.github.koxx12dev.scc.gui.Settings;
import io.github.koxx12dev.scc.utils.StringTransformers;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Random;

public class RPC extends Thread {

    private static final Instant timestamp = Instant.now();
    public static RPC INSTANCE = new RPC();
    private final Thread trd = new Thread(this);

    public static void update(Core core) {
        try (Activity activity = new Activity()) {

            String LineOne = StringTransformers.discordPlaceholder(Settings.rpcLineOne);
            String LineTwo = StringTransformers.discordPlaceholder(Settings.rpcLineTwo);

            if (LineOne.length() >= 2 && LineOne.length() <= 127) {
                activity.setDetails(LineOne);
            }

            if (LineTwo.length() >= 2 && LineTwo.length() <= 127) {
                activity.setState(LineTwo);
            }

            activity.timestamps().setStart(timestamp);

            //activity.party().size().setMaxSize(4);
            //activity.party().size().setCurrentSize(1);

            if (Settings.sbeBadMode) {
                activity.assets().setLargeImage("nosbe");
            } else {
                activity.assets().setLargeImage("skyclienticon");
            }

            activity.assets().setLargeText(StringTransformers.discordPlaceholder(Settings.rpcImgText));

            //activity.party().setID(SkyclientCosmetics.PartyID);
            //activity.secrets().setJoinSecret("Secret");

            core.activityManager().updateActivity(activity);
            SkyclientCosmetics.rpcOn = true;
        }
    }

    public static String generateID() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 127;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public void rpcManager() {
        if (!SkyclientCosmetics.rpcRunning) {
            SkyclientCosmetics.rpcRunning = true;
            trd.start();
        }
    }

    public void run() {

        try {
            File discordLibrary = DownloadSDK.downloadDiscordLibrary();
            if (discordLibrary == null) {
                System.err.println("Error downloading Discord SDK.");
                System.exit(-1);
            }
            // Initialize the Core
            Core.init(discordLibrary);

            // Set parameters for the Core
            try (CreateParams params = new CreateParams()) {
                params.setClientID(857240025288802356L);
                params.setFlags(CreateParams.Flags.NO_REQUIRE_DISCORD);
                // Create the Core

                params.registerEventHandler(new DiscordEventAdapter() {

                    @Override
                    public void onActivityJoin(String secret) {
                        SkyclientCosmetics.LOGGER.info("");
                    }

                });

                try (Core core = new Core(params)) {
                    // Run callbacks forever
                    SkyclientCosmetics.rpcCore = core;

                    while (SkyclientCosmetics.rpcRunning) {
                        try {
                            core.runCallbacks();
                        } catch (GameSDKException e) {
                            SkyclientCosmetics.LOGGER.warn("FAILED TO LAUNCH RPC");
                            SkyclientCosmetics.rpcRunning = false;
                        }
                        try {
                            if (SkyclientCosmetics.rpcRunning) {
                                Thread.sleep(16);
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (!Settings.rpc && SkyclientCosmetics.rpcOn && SkyclientCosmetics.rpcRunning) {
                            core.activityManager().clearActivity();
                            SkyclientCosmetics.rpcOn = false;
                        } else if (Settings.rpc && SkyclientCosmetics.rpcRunning) {
                            RPC.update(SkyclientCosmetics.rpcCore);
                            SkyclientCosmetics.rpcOn = true;
                        }

                    }
                } catch (GameSDKException e) {
                    SkyclientCosmetics.LOGGER.warn("FAILED TO LAUNCH RPC");
                    SkyclientCosmetics.rpcRunning = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error downloading Discord SDK.");
            System.exit(-1);
        }

    }
}
