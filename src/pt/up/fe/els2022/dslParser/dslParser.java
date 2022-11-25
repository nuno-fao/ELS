package pt.up.fe.els2022.dslParser;

import com.google.inject.Inject;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.*;
import org.eclipse.xtext.parser.*;
import org.xtext.example.mydsl.MyDslStandaloneSetup;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class dslParser {
    @Inject
    IParser parser;

    FileReader configFile;

    public dslParser(String filename) throws FileNotFoundException {
        configFile = new FileReader(filename);
        var injector = new MyDslStandaloneSetup().createInjectorAndDoEMFRegistration();
        injector.injectMembers(this);
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
        TreeIterator<EObject> tree = ast.eAllContents();

        while(tree.hasNext()) {
            EObject command = tree.next();

            System.out.println("Not implemented yet: " + command);
        }
    }
}
