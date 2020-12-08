# plantuml-parser ![Gradle Package](https://github.com/shuzijun/plantuml-parser/workflows/Gradle%20Package/badge.svg)

将Java源代码转换为plantuml  
Convert the Java source code to Plantuml

## plantuml-parser-core

```java
    public static void main(String[]args)throws IOException{
        ParserConfig parserConfig=new ParserConfig();
        parserConfig.addFilePath(filePath or fileDirectory);
        parserConfig.setOutFilePath(out file path);
        parserConfig.addMethodModifier(private or protected or default or public );
        parserConfig.addFieldModifier(private or protected or default or public );

        ParserProgram parserProgram=new ParserProgram(parserConfig);
        parserProgram.execute();
        }
```

## plantuml-parser-plugin

<p align="center">
  <img src="https://raw.githubusercontent.com/shuzijun/plantuml-parser/master/doc/demo.gif" alt="demo"/>
</p> 

## output
### demo  
```puml
@startuml
class com.shuzijun.plantumlparser.core.PUmlClass {
+ String getPackageName()
+ void setPackageName(String)
+ String getClassName()
+ void setClassName(String)
+ String getClassType()
+ void setClassType(String)
+ void addPUmlFieldList(PUmlField)
+ void addPUmlMethodList(PUmlMethod)
+ String toString()
}
class com.shuzijun.plantumlparser.core.PUmlField {
+ String getVisibility()
+ void setVisibility(String)
+ boolean isStatic()
+ void setStatic(boolean)
+ String getType()
+ void setType(String)
+ String getName()
+ void setName(String)
+ String toString()
}
class com.shuzijun.plantumlparser.core.ParserConfig {
+ String getOutFilePath()
+ void setOutFilePath(String)
+ Set<File> getFilePaths()
+ void addFilePath(String)
+ void addFieldModifier(String)
+ boolean isFieldModifier(String)
+ void addMethodModifier(String)
+ boolean isMethodModifier(String)
}
class com.shuzijun.plantumlparser.core.ClassVoidVisitor {
+ void visit(ClassOrInterfaceDeclaration,PUmlView)
}
class com.shuzijun.plantumlparser.core.PUmlMethod {
+ String getVisibility()
+ void setVisibility(String)
+ boolean isStatic()
+ void setStatic(boolean)
+ boolean isAbstract()
+ void setAbstract(boolean)
+ String getReturnType()
+ void setReturnType(String)
+ String getName()
+ void setName(String)
+ List<String> getParamList()
+ void addParam(String)
+ String toString()
}
class com.shuzijun.plantumlparser.core.PUmlView {
+ void addPUmlClass(PUmlClass)
+ void addPUmlRelation(PUmlRelation)
+ String toString()
}
class com.shuzijun.plantumlparser.core.PUmlRelation {
+ void setSource(String)
+ void setTarget(String)
+ void setRelation(String)
+ String toString()
}
class com.shuzijun.plantumlparser.core.VisibilityUtils {
+ {static} String toCharacter(String)
}
class com.shuzijun.plantumlparser.core.ParserProgram {
+ void execute(ParserConfig)
}


com.github.javaparser.ast.visitor.VoidVisitorAdapter <|-- com.shuzijun.plantumlparser.core.ClassVoidVisitor
@enduml
```
