package pt.up.fe.els2022.dslParser.commands;

public enum FileType {
    XML("XML"),
    JSON("JSON"),
    TEXT("TEXT");

    private String text;

    FileType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static FileType fromString(String text) {
        for (FileType b : FileType.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
