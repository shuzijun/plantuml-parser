# plantuml-parser ![Gradle Package](https://github.com/shuzijun/plantuml-parser/workflows/Gradle%20Package/badge.svg) ![plugin](https://github.com/shuzijun/plantuml-parser/workflows/plugin/badge.svg)

This is a CLI for the plantuml-parser-core. It supports easy usage of the `ParserProgram`by setting the `ParserConfig` via CLI.  

**Possible options are:**

```bash
usage: plantuml-parser-cli [-f <arg>] [-fdef] [-fpri] [-fpro] [-fpub] [-h]
       [-l <arg>] [-mdef] [-mpri] [-mpro] [-mpub] [-o <arg>] [-scom]
       [-sctr] [-spkg]
 -f,--filepath <arg>         Set the input file/directory
 -fdef,--field_default       Add default fields
 -fpri,--field_private       Add private fields
 -fpro,--field_protected     Add protected fields
 -fpub,--field_public        Add public fields
 -h,--help                   Show the help
 -l,--language_level <arg>   Set language level
 -mdef,--method_default      Add default methods
 -mpri,--method_private      Add private methods
 -mpro,--method_protected    Add protected methods
 -mpub,--method_public       Add public methods
 -o,--outfile <arg>          Set the output file
 -scom,--show_comment        Show comment
 -sctr,--show_constructors   Show constructors
 -spkg,--show_package        Show package
```

## plantuml-parser-cli

Example usage:

```Bash
java -jar plantuml-parser-cli.jar -o
"˜/projects/plantuml-parser/plantuml-parser-cli/src/main/java/com/shuzijun/plantumlparser/cli/out.puml"
-f
"˜/projects/plantuml-parser/plantuml-parser-cli/src/main/java/com/shuzijun/plantumlparser/"
-sctr -spkg -fpub -mpub -fpro -mpro
```
