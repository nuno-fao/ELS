package pt.up.fe.els2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import java.nio.file.*;
;

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
    private static String cleanWord(String word)
    {
        return word.replaceAll("[^a-zA-Z0-9]", "");
    }


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

    private static String[] splitSpaces(String text)
    {
        return  text.replaceAll("\\s+", " ").trim().split(" ");

    }

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

    public static String charLineNum(String filename, int line, int num) {
        line-=1;
        num-=1;

        String text = TextAdapter.readFileAsString(filename);

        String[] lines = text.split("\n");
        String[] chars = lines[line].split("");

        return chars[num];
    }

    public static String wordSwNum(String filename, String startsWith, int num) {
        num-=1;
        String text = TextAdapter.readFileAsString(filename);
        String[] lines = text.split("\n");

        for(int i=0; i< lines.length;i++){
            String[] words = TextAdapter.splitSpaces(lines[i]);
            if(TextAdapter.cleanWord(words[0]).equals(startsWith))
                return TextAdapter.cleanWord(words[num]);
        }
       
        return "_ERROR_"; 
    }

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

    public static String charSwCol(String filename, String startsWith, int num) {
       
        return TextAdapter.charSwNum(filename, startsWith, num); 
    }

    public static String wordSwCol(String filename, String startsWith, int col) {
        col-=1;
        String text = TextAdapter.readFileAsString(filename);
        String[] lines = text.split("\n");

        for(int i=0; i< lines.length;i++){
            String[] words = TextAdapter.splitSpaces(lines[i].trim());
            if(words.length>0 && TextAdapter.cleanWord(words[0]).equals(startsWith)){
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

    public static String charLineCol(String filename, int line, int col) {

        return TextAdapter.charLineNum(filename, line, col); 
    }

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


    public static Table buildTable(String text, int tableStart, int headerLength){
        tableStart-=1;

        String[] lines = text.split("\n");
        if(headerLength==0)
            headerLength=2;

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
        

        ArrayList<Position> headerPos = new ArrayList<>();


        for(int i= 0; i<numberCols;i++){
            Position pos;
            if(i==0)
                pos = new Position(0, 0);
            else
                pos = new Position(headerPos.get(i-1).start,headerPos.get(i-1).end);
            for(int j=headerLength-1; j>=0; j--){
                String line = tableHeader[j];
                String[] chars = line.split("");
                Position tempPos;
                if(i==0)
                    tempPos = new Position(0, 0);
                else
                    tempPos = new Position(headerPos.get(i-1).start,headerPos.get(i-1).end);

                boolean started =false;
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

        for(int i=headerLength-2; i>=0; i--){
            String[] headerWords = TextAdapter.splitSpaces(tableHeader[i].trim());
            if(headerWords.length == numberCols){
                for(int j=0; j<numberCols;j++){
                    headers[j]=headerWords[j]+" "+headers[j];
                }
            }
            else{
                for(int j=0; j<numberCols;j++){
                    headers[j]=tableHeader[i].substring(headerPos.get(j).start, headerPos.get(j).end+1).trim()+" "+headers[j];
                }
            }
        }

        ArrayList<HashMap<String,String>> entries= new ArrayList<>();

        for(int i=0; i<tableLines.length;i++){
            HashMap<String,String> cols = new HashMap<>();
            if(numberCols>1)
                cols.put(headers[0].trim(), tableLines[i].substring(0,headerPos.get(1).start).trim());
            for(int j=1; j<numberCols-1;j++){
                cols.put(headers[j].trim(), tableLines[i].substring(headerPos.get(j-1).end+1, headerPos.get(j+1).start).trim());
            }

            cols.put(headers[numberCols-1].trim(), tableLines[i].substring(headerPos.get(numberCols-1).start, tableLines[i].length()-1).trim());
            entries.add(cols);
        }
        
        ArrayList<String> headerList = new ArrayList<String>();
        for(int i=0;i<headers.length;i++){
            headerList.add(headers[i].trim());
        }

        return new Table(headerList,entries);
    }



    public static String tableLineCol(String filename, int start, int headerLength, int line, String col) {

        String text = TextAdapter.readFileAsString(filename);
        Table table = TextAdapter.buildTable(text, start, headerLength);

        return table.getEntries().get(line-1).get(col); 
    }

    public static String tableLineCol(String filename, int start, int headerLength, int line, int col) {

        String text = TextAdapter.readFileAsString(filename);
        Table table = TextAdapter.buildTable(text, start, headerLength);


        return table.getEntries().get(line-1).get(table.getHeaders().get(col-1)); 
    }

    public static ArrayList<HashMap<String, String>> table(String filename, int start, int headerLength) {

        String text = TextAdapter.readFileAsString(filename);
        Table table = TextAdapter.buildTable(text, start, headerLength);


        return table.getEntries();
    }


}

