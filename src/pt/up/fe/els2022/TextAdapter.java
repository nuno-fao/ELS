package pt.up.fe.els2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import java.nio.file.*;
;

//Position class to help build the headers
class Position{
    public int start;
    public int end;
    Position(int start, int end){
        this.start=start;
        this.end=end;
    }

    public boolean sameCol(Position pos){
        if(pos.start>=start && pos.start<=end)
            return true;
        if(pos.end>=start && pos.end<=end)
            return true;
        if(pos.start<=this.start && pos.end>=this.end)
            return true;

        return false;
    }
};

public class TextAdapter{
    //help function that removers non alphanumeric chars from word
    private static String cleanWord(String word)
    {
        return word.replaceAll("[^a-zA-Z0-9]", "");
    }

    //takes a file and returns the it's contents in a string
    private static String readFileAsString(String fileName)
    {
        try{
            String data = "";
            data = new String(Files.readAllBytes(Paths.get(fileName)));
            return data;
        }catch(Exception e){
            return "";
        }

    }

    //split words by spaces (takes into account multiple spaces)
    private static String[] splitSpaces(String text)
    {
        return  text.replaceAll("\\s+", " ").trim().split(" ");

    }

    //returns the word number "num" of line "line" in the file in "filename"
    public static String wordLineNum(String filename, int line, int num) {
        line-=1;
        num-=1;

        String text = TextAdapter.readFileAsString(filename);

        String[] lines = text.split("\n");
        if(line<lines.length){
            String[] words = TextAdapter.splitSpaces(lines[line]);
            if(num<=words.length)
                return TextAdapter.cleanWord(words[num]);
        }

        return "_ERROR_";
    }

    //returns the char number "num" of line "line" in the file in "filename"
    public static String charLineNum(String filename, int line, int num) {
        line-=1;
        num-=1;

        String text = TextAdapter.readFileAsString(filename);

        String[] lines = text.split("\n");
        String[] chars = lines[line].split("");

        return chars[num];
    }

    //returns the word number "num" of the first line starting with "startsWith" in the file in "filename"
    public static String wordSwNum(String filename, String startsWith, int num) {
        num-=1;
        String text = TextAdapter.readFileAsString(filename);
        String[] lines = text.split("\n");

        for(int i=0; i< lines.length;i++){
            String[] words = TextAdapter.splitSpaces(lines[i]);
            if(TextAdapter.cleanWord(words[0]).equals(startsWith) || lines[i].startsWith(startsWith))
                return TextAdapter.cleanWord(words[num]);
        }

        return "_ERROR_";
    }

    //returns the char number "num" of the first line starting with "startsWith" in the file in "filename"
    public static String charSwNum(String filename, String startsWith, int num) {
        num-=1;
        String text = TextAdapter.readFileAsString(filename);
        String[] lines = text.split("\n");

        for(int i=0; i< lines.length;i++){
            String[] chars = lines[i].split("");
            if(chars[0].equals(startsWith))
                return chars[num];
        }

        return "_ERROR_";
    }

    //returns the char in collumn "col" of the first line starting with "startsWith" in the file in "filename"
    public static String charSwCol(String filename, String startsWith, int num) {

        return TextAdapter.charSwNum(filename, startsWith, num);
    }

    //returns the word that contains the char in collumn "col" of the first line starting with "startsWith" in the file in "filename"
    public static String wordSwCol(String filename, String startsWith, int col) {
        col-=1;
        String text = TextAdapter.readFileAsString(filename);
        String[] lines = text.split("\n");

        for(int i=0; i< lines.length;i++){
            String[] words = TextAdapter.splitSpaces(lines[i].trim());
            if(words.length>0 && TextAdapter.cleanWord(words[0]).equals(startsWith) || lines[i].startsWith(startsWith)){
                String[] ch = lines[i].split("");

                if(ch[col].equals(" "))
                    return ch[col];

                String word = ch[col];
                for (int j=1; j<=col;j++){
                    if(!ch[col-j].equals(" ")){
                        word = ch[col-j] + word;
                    }
                    else break;
                }
                for (int j=col+1; j<ch.length;j++){
                    if(!ch[j].equals(" ")){
                        word += ch[j] ;
                    }
                    else break;
                }
                return TextAdapter.cleanWord(word);
            }
        }

        return "_ERROR_";
    }

    //returns the char in collumn "col" of the line "line" in the file in "filename"
    public static String charLineCol(String filename, int line, int col) {

        return TextAdapter.charLineNum(filename, line, col);
    }

    //returns the word that contains the char in collumn "col" of the line "line" in the file in "filename"
    public static String wordLineCol(String filename, int line, int col) {
        line-=1;
        col-=1;
        String text = TextAdapter.readFileAsString(filename);
        String[] lines = text.split("\n");

        String[] words = TextAdapter.splitSpaces(lines[line].trim());
        if(words.length>0){
            String[] ch = lines[line].split("");

            if(ch[col].equals(" "))
                return ch[col];

            String word = ch[col];
            for (int j=1; j<=col;j++){
                if(!ch[col-j].equals(" ")){
                    word = ch[col-j] + word;
                }
                else break;
            }
            for (int j=col+1; j<ch.length;j++){
                if(!ch[j].equals(" ")){
                    word += ch[j] ;
                }
                else break;
            }
            return TextAdapter.cleanWord(word);
        }

        return "_ERROR_";
    }

    //Creates a position array (left most position of a char in the collumn, right most position of a char in the collumn) of the headers
    private static ArrayList<Position> getHeaderPositions(String[] tableHeader,int numberCols, int headerLength){
        ArrayList<Position> headerPos = new ArrayList<>();

        // Each iteration compiles an header
        for(int i= 0; i<numberCols;i++){
            Position pos;
            //First header always starts at (0,0)
            if(i==0)
                pos = new Position(0, 0);
            else
                pos = new Position(headerPos.get(i-1).start,headerPos.get(i-1).end);
            // Each iteration compiles an header line
            for(int j=headerLength-1; j>=0; j--){
                String line = tableHeader[j];
                String[] chars = line.split("");
                Position tempPos;
                if(i==0)
                    tempPos = new Position(0, 0);
                else
                    tempPos = new Position(headerPos.get(i-1).start,headerPos.get(i-1).end);

                boolean started =false;
                //Compiles the line
                for(int k=tempPos.end+1; k<chars.length;k++){
                    if(chars[k].equals(" ")){
                        if(started){
                            tempPos.end=k-1;
                            break;
                        }
                        continue;
                    }
                    if(!started){
                        started=true;
                        tempPos.start=k;
                    }
                }
                if(j==headerLength-1){
                    pos.start=tempPos.start;
                    pos.end=tempPos.end;
                }
                else if(pos.sameCol(tempPos)){
                    pos.start=Math.min(pos.start, tempPos.start);
                    pos.end=Math.max(pos.end, tempPos.end);
                }
            }
            headerPos.add(pos);
        }
        if(headerPos.size() >= 2){
            var last = headerPos.get(headerPos.size()-1);
            var before_last = headerPos.get(headerPos.size()-2);
            if(last.start == before_last.start && last.end == before_last.end){
                last.start = before_last.end +1;
                last.end = before_last.end +1;
            }
        }
        return headerPos;
    }

    //Builds the table starting at "tableStart" with a header with "headerLength" lines in "text"
    public static Table buildTable(String text, int tableStart, int headerLength){
        tableStart-=1;

        String[] lines = text.split("\n");
        if(headerLength==0)
            headerLength=2;
        //checks were the table ends (considers an empty line as the end)
        int tableEnd=0;
        for(int i=tableStart; i<lines.length;i++){
            tableEnd=i;
            if(lines[i].equals("\r") || lines[i].trim().length() == 0)
                break;
        }
        String[] tableLines=Arrays.copyOfRange(lines, tableStart, tableEnd);
        String[] tableHeader=Arrays.copyOfRange(lines, tableStart-headerLength, tableStart);

        String[] headerLastWords = TextAdapter.splitSpaces(tableHeader[headerLength-1].trim());
        int numberCols = headerLastWords.length;

        String[] headers = headerLastWords.clone();


        ArrayList<Position> headerPos =getHeaderPositions(tableHeader, numberCols,  headerLength);
        //Compiles the header one line at a time
        for(int i=headerLength-2; i>=0; i--){
            String[] headerWords = TextAdapter.splitSpaces(tableHeader[i].trim());
            //If each header is complete in the row it happends all
            if(headerWords.length == numberCols){
                for(int j=0; j<numberCols;j++){
                    headers[j]=headerWords[j]+" "+headers[j];
                }
            }
            //Otherwise it uses the header positions to happends the word
            else{
                for(int j=0; j<numberCols;j++) {
                    try {
                        headers[j] = tableHeader[i].substring(headerPos.get(j).start, headerPos.get(j).end + 1).trim() + " " + headers[j];
                    } catch (Exception ignored) {
                        try {
                            headers[j] = tableHeader[i].substring(headerPos.get(j).start, Math.min(headerPos.get(j).end + 1, tableHeader[i].length())).trim() + " " + headers[j];
                            System.out.println(headerPos.get(j).start + " "+headerPos.get(j).end + 1 +" "+ tableHeader[i].length()+" "+i+" "+j);
                        } catch (Exception ignored1) {
                            System.out.println(headerPos.get(j).start + " "+headerPos.get(j).end + 1 +" "+ tableHeader[i].length());
                        }
                    }
                }
            }
        }

        ArrayList<HashMap<String,String>> entries= new ArrayList<>();

        //Each iteration gets the values of a line of the table
        for(int i=0; i<tableLines.length;i++){
            HashMap<String,String> cols = new HashMap<>();
            String[] lineStrings = tableLines[i].trim().replaceAll("  +", " ").split(" ");
            //If each collumn in the line is complete it saves the value according to the word number
            if(lineStrings.length==numberCols){
                for(int j=0; j<numberCols; j++){
                    cols.put(headers[j].trim(),lineStrings[j]);
                }
            }
            //Otherwise it will use the header positions to get the word
            else{
                if(numberCols>1)
                    cols.put(headers[0].trim(), tableLines[i].substring(0,headerPos.get(1).start).trim());
                for(int j=1; j<numberCols-1;j++){
                    cols.put(headers[j].trim(), tableLines[i].substring(headerPos.get(j-1).end+2, headerPos.get(j+1).start).trim());
                }
                cols.put(headers[numberCols-1].trim(), tableLines[i].substring(headerPos.get(numberCols-1).start).trim());
            }
            entries.add(cols);
        }

        ArrayList<String> headerList = new ArrayList<String>();
        for(int i=0;i<headers.length;i++){
            headerList.add(headers[i].trim());
        }

        Table t = new Table(headerList,entries);
        t.setOutput(headerList);
        return t;
    }


    //Returns the value in line and in col (takes the name of the collumn)
    public static String tableLineCol(String filename, int start, int headerLength, int line, String col) {

        String text = TextAdapter.readFileAsString(filename);
        Table table = TextAdapter.buildTable(text, start, headerLength);

        return table.getEntries().get(line-1).get(col);
    }

    //Returns the value in line and in col
    public static String tableLineCol(String filename, int start, int headerLength, int line, int col) {

        String text = TextAdapter.readFileAsString(filename);
        Table table = TextAdapter.buildTable(text, start, headerLength);

        return table.getEntries().get(line-1).get(table.getHeaders().get(col-1));
    }

    //reads the file and calls buildTable
    public static Table table(String filename, int start, int headerLength) {

        String text = TextAdapter.readFileAsString(filename);
        Table table = TextAdapter.buildTable(text, start, headerLength);

        return table;
    }


}

