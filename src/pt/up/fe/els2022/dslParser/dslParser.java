package pt.up.fe.els2022.dslParser;

import com.google.inject.Inject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.IParser;
import org.xtext.example.mydsl.MyDslStandaloneSetup;
import org.xtext.example.mydsl.myDsl.Command;
import org.xtext.example.mydsl.myDsl.*;
import pt.up.fe.els2022.BuilderExecutor;
import pt.up.fe.els2022.builders.*;
import pt.up.fe.els2022.dslParser.commands.FileType;
import pt.up.fe.specs.util.classmap.FunctionClassMap;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class dslParser {
    @Inject
    IParser parser;

    FileReader configFile;
    private final FunctionClassMap<EObject, Object> nodeFunctions;
    private CMDHolder builder = new BuilderExecutor();

    public dslParser(String filename) throws FileNotFoundException {
        configFile = new FileReader(filename);

        var injector = new MyDslStandaloneSetup().createInjectorAndDoEMFRegistration();
        injector.injectMembers(this);

        nodeFunctions = new FunctionClassMap<>();
        initNodeFunctions();
    }

    private void initNodeFunctions() {
        nodeFunctions.put(Model.class, this::parseModel);
        nodeFunctions.put(Read.class, this::parseRead);
        nodeFunctions.put(Sort.class, this::parseSort);
        nodeFunctions.put(RemoveColumn.class, this::parseRemoveColumn);
        nodeFunctions.put(AddColumn.class, this::parseAddColumn);
        nodeFunctions.put(SetOutput.class, this::parseSetOutput);
        nodeFunctions.put(Merge.class, this::parseMerge);
        nodeFunctions.put(Join.class, this::parseJoin);
        nodeFunctions.put(Write.class, this::parseWrite);
        nodeFunctions.put(Extract.class, this::parseExtract);
        nodeFunctions.put(Average.class, this::parseAverage);
        nodeFunctions.put(Sum.class, this::parseSum);
        nodeFunctions.put(ReadDir.class, this::parseReadDir);
        nodeFunctions.put(Rename.class, this::parseRename);
        nodeFunctions.put(Append.class, this::parseAppend);
        nodeFunctions.put(Compress.class, this::parseCompress);
    }

    public BuilderExecutor getBuilder() {
        return (BuilderExecutor) builder;
    }

    public void parse() {
        IParseResult result = parser.parse(configFile);

        if (result.hasSyntaxErrors()) {
            for(INode error: result.getSyntaxErrors()) {
                System.out.println("Syntax error in configuration file: " + error.getSyntaxErrorMessage());
            }
            return;
        }

        EObject ast = result.getRootASTElement();
        nodeFunctions.apply(ast);
    }

    private Object parseModel(Model model) {
        for (Command command: model.getCommands()) {
            nodeFunctions.apply(command);
        }
        return null;
    }

    private Object parseRead(Read read) {
        BuilderRead builderRead = builder.read();

        List<String> filePaths = read.getFilepaths();
        FileType fileType = FileType.fromString(read.getFileType());
        List<String> tableNames = read.getTableNames();

        if (!tableNames.isEmpty() && (filePaths.size() != tableNames.size())) {
            throw new Error("Read command incorrect, number of files must be equal to number of identifiers");
        }

        List<String> includes = new ArrayList<>();
        if (read.isIncludeFolder()) {
            includes.add("folder");
        }
        if (read.isIncludeFile()) {
            includes.add("file");
        }

        builderRead
                .setFilesPaths(filePaths)
                .setFileType(fileType)
                .setFilesIds(tableNames.isEmpty() ? filePaths : tableNames)
                .setIncludes(includes);

        if (FileType.TEXT == fileType) {
            // parse text read
            for (Table table : read.getTables()) {
                parseTable(table, builderRead);
            }

            for (Word word : read.getWords()) {
                parseWord(word, builderRead);
            }
        } else {
            // parse xml or json read
            List<String> parents = read.getParents();
            if (read.isRoot()) parents.add("ROOT");
            if (!parents.isEmpty()) builderRead.setParentElements(parents);

            for (ColRename colRename : read.getCols()) {
                if (colRename.isRename()) {
                    builderRead.addColumn(colRename.getOriginalName(), colRename.getNewName());
                } else {
                    builderRead.addColumn(colRename.getOriginalName(), colRename.getOriginalName());
                }
            }
        }

        builderRead.close();

        return null;
    }

    private void parseTable(Table table, BuilderRead builderRead) {
        builderRead.addTable(table.getLines(), table.getHeaderHeight());
    }

    private void parseWord(Word word, BuilderRead builderRead) {
        EObject lineStarter = word.getLine();
        EObject wordOrColumn = word.getWordOrColumn();

        if (lineStarter instanceof Line) {
            int lineNumber = ((Line) lineStarter).getLineNumber();

            if (wordOrColumn instanceof WordNumber) {
                int wordNumber = ((WordNumber) wordOrColumn).getWordNumber();
                builderRead.addWordByWord(lineNumber, wordNumber, word.getWordName());
            } else {
                int columnNumber = ((ColumnNumber) wordOrColumn).getColumnNumber();
                builderRead.addWordByCol(lineNumber, columnNumber, word.getWordName());
            }
        } else {
            String startsWith = ((StartsWith) lineStarter).getStarter();

            if (wordOrColumn instanceof WordNumber) {
                int wordNumber = ((WordNumber) wordOrColumn).getWordNumber();
                builderRead.addWordByWord(startsWith, wordNumber, word.getWordName());
            } else {
                int columnNumber = ((ColumnNumber) wordOrColumn).getColumnNumber();
                builderRead.addWordByCol(startsWith, columnNumber, word.getWordName());
            }
        }
    }

    private Object parseSort(Sort sort) {
        builder.sort()
                .setFileId(sort.getTableName())
                .setCol(sort.getColumn())
                .setDirection(sort.getOrder() != null ? sort.getOrder() : "asc")
                .setNewFileId(sort.getNewTableName())
                .close();

        return null;
    }

    private Object parseRemoveColumn(RemoveColumn removeColumn) {
        builder.removeColumn()
                .setFileId(removeColumn.getTableName())
                .setColumn(removeColumn.getColumnName())
                .setNewFileId(removeColumn.getNewTableName())
                .close();

        return null;
    }

    private Object parseAddColumn(AddColumn addColumn) {
        builder.addColumn()
                .setFileId(addColumn.getTableName())
                .setColumn(addColumn.getColumnName())
                .setDefault(addColumn.getDefaultValue())
                .setNewFileId(addColumn.getNewTableName())
                .close();

        return null;
    }

    private Object parseSetOutput(SetOutput setOutput) {
        BuilderSetOutput builderSetOutput = builder.setOutput();

        builderSetOutput.setFilesId(setOutput.getTableName());

        for (String col : setOutput.getCols()) {
            builderSetOutput.addColumn(col);
        }

        builderSetOutput.close();

        return null;
    }

    private Object parseMerge(Merge merge) {
        BuilderMerge builderMerge = builder.merge();

        for (String tableName : merge.getTableNames()) {
            builderMerge.addFileId(tableName);
        }

        builderMerge
                .setAggregate(merge.getAttribute())
                .setDestinyColumn(merge.getColumnName())
                .setNewFileId(merge.getNewTableName())
                .close();

        return null;
    }

    private Object parseJoin(Join join) {
        BuilderJoin builderJoin = builder.join();

        for (String tableName : join.getTableNames()) {
            builderJoin.addFileId(tableName);
        }

        builderJoin
                .setNewFileId(join.getNewTableName())
                .close();

        return null;
    }

    private Object parseWrite(Write write) {
        BuilderWrite builderWrite = builder.write();

        builderWrite
                .setType("CSV")
                .setFileId(write.getTableName())
                .setFilePath(write.getFilePath())
                .close();

        return null;
    }

    private Object parseExtract(Extract extract) {
        BuilderExtract builderExtract = builder.extract();

        builderExtract
                .setFileId(extract.getTableName())
                .setColumns(extract.getCols())
                .setNewFileId(extract.getNewTableName())
                .close();

        HashSet<Integer> lines = new HashSet<>();

        if (extract.getLines() != null) {
            for (int i = extract.getInitialLine(); i > extract.getFinalLine(); i++) {
                lines.add(i);
            }
        } else {
            lines = new HashSet<>(extract.getLines());
        }

        builderExtract
                .setLines(new HashSet<>(extract.getLines()))
                .close();

        return null;
    }

    private Object parseAverage(Average average) {
        builder.average()
                .setFileId(average.getTableName())
                .setNewFileId(average.getNewTableName())
                .close();

        return null;
    }

    private Object parseSum(Sum sum) {
        builder.sum()
                .setFileId(sum.getTableName())
                .setNewFileId(sum.getNewTableName())
                .close();

        return null;
    }

    private Object parseReadDir(ReadDir readDir) {
        BuilderReadDir builderReadDir = builder.readDir();

        builderReadDir.setFolderPath(readDir.getFolderpath());

        if (readDir.getTableName() != null) {
            if (readDir.getPileTableName() != null) {
                builderReadDir
                        .setFileId(readDir.getTableName())
                        .setPileId(readDir.getPileTableName());
            } else {
                throw new Error("Error in configuration file: if ReadDir has table identifier, it must have pile identifier");

            }
        } else if (readDir.getPileTableName() != null) {
            throw new Error("Error in configuration file: if ReadDir has pile identifier, it must have table identifier");
        }

        CMDHolder mainBuilder = builder;
        builder = builderReadDir;

        for (Command command : readDir.getCommands()) {
            nodeFunctions.apply(command);
        }

        builder = mainBuilder;

        return null;
    }

    private Object parseRename(Rename rename) {
        builder.rename()
                .setFileId(rename.getTableName())
                .setColList(rename.getColNames())
                .setNewColllist(rename.getNewColNames())
                .setNewFileId(rename.getNewTableName())
                .close();

        return null;
    }

    private Object parseAppend(Append append) {
        builder.append()
                .setFileId(append.getTableName())
                .setSuffix(append.getSuffix())
                .setNewFileId(append.getNewTableName())
                .close();

        return null;
    }

    private Object parseCompress(Compress compress) {
        builder.compress()
                .setFileId(compress.getTableName())
                .setSuffix(compress.getSuffix())
                .setNewFileId(compress.getNewTableName())
                .close();

        return null;
    }
}
