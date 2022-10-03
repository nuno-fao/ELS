package pt.up.fe.els2022.languageParser;

public interface Command {
    void addLine(String line);
    void close();

    void println();
}
