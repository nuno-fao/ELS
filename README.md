# ELS Project

For this project, you need to [install Gradle](https://gradle.org/install/)

## Project setup

Copy your source files to the ``src`` folder, and your JUnit test files to the ``test`` folder.

## Compile and Running

To compile and install the program, run ``gradle installDist``. This will compile your classes and create a launcher script in the folder ``build/install/els2022-g2/bin``. For convenience, there are two script files, one for Windows (``els2022-g2.bat``) and another for Linux (``els2022-g2``), in the root of the repository, that call these scripts.

After compilation, tests will be automatically executed, if any test fails, the build stops. If you want to ignore the tests and build the program even if some tests fail, execute Gradle with flags "-x test".

When creating a Java executable, it is necessary to specify which class that contains a ``main()`` method should be entry point of the application. This can be configured in the Gradle script with the property ``mainClassName``, which by default has the value ``pt.up.fe.els2022.Main``.

## Test

To test the program, run ``gradle test``. This will execute the build, and run the JUnit tests in the ``test`` folder. If you want to see output printed during the tests, use the flag ``-i`` (i.e., ``gradle test -i``).
You can also see a test report by opening ``build/reports/tests/test/index.html``.

## Assignment 1


### Reading from an XML file
```
Read files\vitis-report_1.xml as f1 
    Parent Resources
    Col LUT => LUTs
    Col FF => FFs
    Col DSP48E => DSP48Es
    Col BRAM_18K => BRAM_18Ks
End
```
On the first line we specify what file we want to read from and, after keyword ``as``, give the resulting table an **identifier which is mandatory to use when operating on tables**.

If multiple files are read in the same way, our DSL allows for an easy configuration like: 

- ``Read files/vitis-report_1.xml,files/vitis-report_2.xml,files/vitis-report_3.xml as f1,f2,f3``

Inside the Read block we must first specify the parent from where we will read our values, in case there are multiple tags with the same name.
Then we indicate what tag we will extract the value and right after ``=>`` we can change the name of the resulting column on the table.
We decided that renaming columns right at table creation was more intuitive and user-friendly this way, opposed to having to rename them one by one after reading.

### Adding and removing columns
```
AddColumn f1 TESTCOL TESTE
AddColumn f1 TESTCOL TESTE as f4
RemoveColumn f1 LUTs
RemoveColumn f1 LUTs as f4
```

When adding, we first indicate the target table, then the name of the new column and the default value of that column for all entries on that table. 

When removing, we simply indicate the target table and then what column to remove. 

Similar to some other commands, these two **may** have ``as <identifier>`` at the end. When this is present the original table will remain unaltered and the results of the operations will be reflected on a new table will be identified as ``<identifier>``.

### Sorting a table
```
Sort f3 LUTs desc
Sort f3 LUTs desc as f4
```
Here we first indicate the target table, the column from which we take the values to sort the entries and the order (``asc``or ``desc``).
Again, we **may** have ``as <identifier>`` at the end so that the changes are reflected on a new table and leave the original intact.

**Important** - for this assignment, values read from XML's are all strings so sorting is done according to the unicode value of each character. For example, 432 < 54 is true because char '4' has smaller unicode value than '5'. 

### Merging
```
Merge f1,f2
Merge f1,f2,f3 as f4
Merge f1,f2,f3 with Name on File
Merge f1,f2,f3 with Name on File as f4
```
The merge command can be used in several ways. You can merge more than two tables at a time. If ``as <indentifier>`` is specified the combination of all tables will result in a new table, otherwise it will overwrite the first table in the command (not mandatory).

There is also a ``with <atribute> on <column_name>`` part where you can create a new column for all tables in this merge command preserving some attribute of the table on a column.
For example, in this assignment we want to know the file from where a table/entry originated. The filepath is an attribute of the table originated from the Read command and when merging 2 tables together, instead of adding the column manually to each table, we can use this Merge command to more easily add that column to all tables involved.
We chose this approach because in the future more metadata may be needed from the original file and this gives the language a lot of versatility.

### Writing to a file
```
SetOutput f4
    File
    LUTs
    FFs
    DSP48Es
    BRAM_18Ks
End

Write CSV f4 to test/pt/up/fe/els2022/outFiles/outTestFull.csv
```
To create a .csv from a table we use the Write command where we first specific the type of file (only 'CSV' supported for now), then the target table and finally, after ``to``, the output path.

Before using Write, we can use SetOutput to filter and order the columns for the output file. If SetOutput is done, only columns listed in it will be printed following the order they are presented in the command. Otherwise, everything will be printed, following to order in the Read command.