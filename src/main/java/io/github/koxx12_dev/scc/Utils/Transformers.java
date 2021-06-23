package io.github.koxx12_dev.scc.Utils;

public class Transformers {
    public static String cleanMessage(String msg) {
        //if null, keep null (no gain or loss of safety)
        if (msg == null)
            return null;

        msg = msg.replaceAll("\u00A7k","")
                .replaceAll("\u00A7l","")
                .replaceAll("\u00A7m","")
                .replaceAll("\u00A7n","")
                .replaceAll("\u00A7o","")
                .replaceAll("\u00A7r","")
                .replaceAll("\u00A74","")
                .replaceAll("\u00A7c","")
                .replaceAll("\u00A76","")
                .replaceAll("\u00A7e","")
                .replaceAll("\u00A72","")
                .replaceAll("\u00A7a","")
                .replaceAll("\u00A7b","")
                .replaceAll("\u00A73","")
                .replaceAll("\u00A71","")
                .replaceAll("\u00A79","")
                .replaceAll("\u00A7d","")
                .replaceAll("\u00A75","")
                .replaceAll("\u00A7f","")
                .replaceAll("\u00A77","")
                .replaceAll("\u00A78","")
                .replaceAll("\u00A70","");

        return msg.toString();//build the string, and return
    }
}
