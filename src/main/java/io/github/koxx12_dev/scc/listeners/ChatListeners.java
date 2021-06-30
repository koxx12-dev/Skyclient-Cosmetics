package io.github.koxx12_dev.scc.listeners;

import io.github.koxx12_dev.scc.GUI.SCCConfig;
import io.github.koxx12_dev.scc.SCC;
import io.github.koxx12_dev.scc.Utils.DetectionStuff;
import io.github.koxx12_dev.scc.Utils.Transformers;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatListeners {

    @SubscribeEvent
    public void onChatMsg(ClientChatReceivedEvent event) {
        if (SCCConfig.TagsShow && DetectionStuff.isOnHypixel()) {

            if (SCCConfig.DebugLogs) {
                SCC.LOGGER.info(event.message.getFormattedText() + " , " + Transformers.cleanMessage(event.message.getUnformattedText()));
                SCC.LOGGER.info(Arrays.toString(event.message.getFormattedText().split(" ")) + " , " + Arrays.toString(Transformers.cleanMessage(event.message.getUnformattedText()).split(" ")));
            }

            List<String> msgAsList = new ArrayList<>(Arrays.asList(event.message.getFormattedText().split(" ")));
            List<String> cleanMsgAsList = new ArrayList<>(Arrays.asList(Transformers.cleanMessage(event.message.getUnformattedText()).split(" ")));


            for (int i = 0; i < (cleanMsgAsList.toArray().length-1); i++) {

                String val = cleanMsgAsList.get(i);
                String valClean = cleanMsgAsList.get(i).replaceAll(",","").replaceAll(":","");
                if (SCC.HypixelRanks.contains(val)) {


                    int j = (i+1);
                    try {

                        String val2;

                        if (!SCCConfig.ShortenTags) {
                            val2 = SCC.UUIDtags.get(cleanMsgAsList.get(j).replaceAll(":",""));
                        } else {
                            val2 = SCC.UUIDtagsShort.get(cleanMsgAsList.get(j).replaceAll(":",""));
                        }

                        if (val2 != null) {
                            msgAsList.add(i,val2);
                            event.message = new ChatComponentText(String.join(" ", msgAsList));
                        } else {
                            SCC.LOGGER.debug(cleanMsgAsList.get(j).replaceAll(":","") + " doesnt have a tag");
                        }
                    }  catch (Exception e) {
                        SCC.LOGGER.debug(cleanMsgAsList.get(j).replaceAll(":","") + " doesnt have a tag");
                    }
                    break;
                } else if(SCC.UUIDtags.containsKey(valClean) && val.contains(":")) {

                    String val2;

                    if (!SCCConfig.ShortenTags) {
                        val2 = SCC.UUIDtags.get(valClean);
                    } else {
                        val2 = SCC.UUIDtagsShort.get(valClean);
                    }

                    msgAsList.add(i,val2);

                    event.message = new ChatComponentText(String.join(" ", msgAsList));

                    break;

                }

            }



        }
        if (SCCConfig.DebugTags) {
            try {
                SCC.LOGGER.debug(event.message = new ChatComponentText(SCC.UUIDtags.get(Minecraft.getMinecraft().getSession().getUsername())));
            } catch (Exception e) {
                SCC.LOGGER.debug(Minecraft.getMinecraft().getSession().getUsername() + " doesnt have a tag");
            }

        }
    }
}
