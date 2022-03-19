package io.github.koxx12dev.scc.cosmetics;

public class Tag {
    private final String fullTag;
    private final String shortTag;
    private final String identifier;

    Tag(String fullTag, String shortTag, String identifier) {
        this.fullTag = fullTag;
        this.shortTag = shortTag;
        this.identifier = identifier;
    }

    public String getFullTag() {
        return fullTag;
    }

    public String getShortTag() {
        return shortTag;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "fullTag='" + fullTag + '\'' +
                ", shortTag='" + shortTag + '\'' +
                ", identifier='" + identifier + '\'' +
                '}';
    }
}
