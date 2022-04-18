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

package io.github.koxx12dev.scc.utils;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SidebarUtils {

    private static List<String> getSidebarLines() {

        List<String> lines = new ArrayList<>();

        Scoreboard scoreboard = Minecraft.getMinecraft().theWorld.getScoreboard();
        if (scoreboard == null) {
            return lines;
        }

        ScoreObjective objective = scoreboard.getObjectiveInDisplaySlot(1);

        if (objective == null) {
            return lines;
        }

        Collection<Score> scores = scoreboard.getSortedScores(objective);
        List<Score> list = scores.stream().filter(s -> s.getPlayerName() != null).collect(Collectors.toList());

        if (list.size() > 15) {
            scores = Lists.newArrayList(Iterables.skip(list, scores.size() - 15));
        } else {
            scores = list;
        }

        for (Score score : scores) {
            ScorePlayerTeam team = scoreboard.getPlayersTeam(score.getPlayerName());
            lines.add(StringUtils.cleanMessage(StringUtils.cleanEmojis(ScorePlayerTeam.formatPlayerName(team, score.getPlayerName()))));
        }

        return lines;
    }

    public static String getPurse() {
        try {
            List<String> sidebar = getSidebarLines();

            for (String a : sidebar) {
                if (a.matches("Purse: [0-9,]+")) {
                    return a.replace("Purse: ", "").trim();
                }
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getBits() {
        try {
            List<String> sidebar = getSidebarLines();

            for (String a : sidebar) {
                if (a.matches("Bits: [0-9,]+")) {
                    return a.replace("Bits: ", "").trim();
                }
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getServer() {
        try {
            List<String> sidebar = getSidebarLines();

            Pattern regex = Pattern.compile("([0-9]+/){2,}");

            for (String a : sidebar) {
                if (regex.matcher(a).find()) {
                    return Array.get(a.split(" "), 1).toString().trim();
                }
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getSBTime() {
        try {
            List<String> sidebar = getSidebarLines();

            Pattern regex = Pattern.compile("([0-9][0-9]|[0-9]):[0-9][0-9](pm|am)");

            for (String a : sidebar) {
                if (regex.matcher(a).find()) {
                    return a.trim();
                }
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getSBDate() {
        try {
            List<String> sidebar = getSidebarLines();

            Pattern regex = Pattern.compile("(Spring|Summer|Winter|Autumm|Fall) ([0-9][0-9]|[0-9])(rd|nd|st|th)");

            for (String a : sidebar) {

                if (regex.matcher(a).find()) {
                    return a.trim();
                }
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getSBLoc() {
        try {
            List<String> sidebar = getSidebarLines();

            Pattern regex = Pattern.compile("([0-9][0-9]|[0-9]):[0-9][0-9](pm|am)");

            int i = -1;

            for (String a : sidebar) {
                if (regex.matcher(a).find()) {
                    return sidebar.get(i).trim();
                }
                i++;
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getObjective() {
        try {

            List<String> sidebar = getSidebarLines();

            Pattern regex = Pattern.compile("Objective");

            int i = -1;

            for (String a : sidebar) {
                if (regex.matcher(a).find()) {
                    return sidebar.get(i).trim();
                }
                i++;
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getSidebarTitle() {

        Scoreboard scoreboard = Minecraft.getMinecraft().theWorld.getScoreboard();
        if (scoreboard == null) {
            return null;
        }

        ScoreObjective objective = scoreboard.getObjectiveInDisplaySlot(1);

        if (objective == null) {
            return null;
        }

        return StringUtils.cleanMessage(objective.getDisplayName());
    }

}
