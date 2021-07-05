package io.github.koxx12_dev.scc.Utils;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.DiscordEventAdapter;
import de.jcm.discordgamesdk.GameSDKException;
import de.jcm.discordgamesdk.activity.Activity;
import io.github.koxx12_dev.scc.GUI.SCCConfig;
import io.github.koxx12_dev.scc.SCC;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Random;

public class RPC extends Thread {

    public static RPC INSTANCE = new RPC();

    private Thread trd = new Thread(this);

    private static Instant timestamp = Instant.now();

    public void RPCManager() {
        if (!SCC.RPCRunning) {
            SCC.RPCRunning = true;
            trd.start();
        }
    }

    public void run() {

        try {
            File discordLibrary = DownloadSDK.downloadDiscordLibrary();
            if(discordLibrary == null) {
                System.err.println("Error downloading Discord SDK.");
                System.exit(-1);
            }
            // Initialize the Core
            Core.init(discordLibrary);

            // Set parameters for the Core
            try(CreateParams params = new CreateParams()) {
                params.setClientID(857240025288802356L);
                params.setFlags(CreateParams.Flags.NO_REQUIRE_DISCORD);
                // Create the Core

                params.registerEventHandler(new DiscordEventAdapter()
                {

                    @Override
                    public void onActivityJoin(String secret)
                    {
                        SCC.LOGGER.info("");
                    }

                });

                try(Core core = new Core(params)) {
                    // Run callbacks forever
                    SCC.RPCcore = core;

                    while(SCC.RPCRunning) {
                        try {
                            core.runCallbacks();
                        } catch (GameSDKException e) {
                            System.out.println("FAILED TO LAUNCH RPC");
                            SCC.RPCRunning = false;
                        }
                        try {
                            if (SCC.RPCRunning) {
                                Thread.sleep(16);
                            }

                        }
                        catch(InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (!SCCConfig.RPC && SCC.RPCon && SCC.RPCRunning) {
                            core.activityManager().clearActivity();
                            SCC.RPCon = false;
                        } else if(SCCConfig.RPC && SCC.RPCRunning) {
                            RPC.update(SCC.RPCcore);
                        }

                    }
                } catch (GameSDKException e) {
                    System.out.println("FAILED TO LAUNCH RPC");
                    SCC.RPCRunning = false;
                }
            }
        }
        catch(IOException e) {
            e.printStackTrace();
            System.err.println("Error downloading Discord SDK.");
            System.exit(-1);
        }

    }

    public static void update(Core core) {
        try(Activity activity = new Activity())  {

            String LineOne = Transformers.DiscordPlaceholder(SCCConfig.RPCLineOne);
            String LineTwo = Transformers.DiscordPlaceholder(SCCConfig.RPCLineTwo);

            if (!LineOne.equals("") && LineOne.length() >= 2 && LineOne.length() <= 127) {
                activity.setDetails(LineOne);
            }

            if (!LineTwo.equals("") && LineTwo.length() >= 2 && LineTwo.length() <= 127) {
                activity.setState(LineTwo);
            }

            activity.timestamps().setStart(timestamp);

            //activity.party().size().setMaxSize(4);
            //activity.party().size().setCurrentSize(1);

            if (SCCConfig.BadSbeMode) {
                activity.assets().setLargeImage("nosbe");
            } else {
                activity.assets().setLargeImage("skyclienticon");
            }

            activity.assets().setLargeText(Transformers.DiscordPlaceholder(SCCConfig.RPCImgText));

            //activity.party().setID(SCC.PartyID);
            //activity.secrets().setJoinSecret("Secret");

            core.activityManager().updateActivity(activity);
            SCC.RPCon = true;
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

}
