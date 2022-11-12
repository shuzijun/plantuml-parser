package com.shuzijun.plantumlparser.core;

import java.io.IOException;

public class Test {
    public static void main(String[]args)throws IOException {
        ParserConfig parserConfig=new ParserConfig();
        parserConfig.addFilePath("/Users/shuzijun/plantuml-parser/plantuml-parser-core/src");
        parserConfig.addExcludeClassRegex("Test");

        ParserProgram parserProgram=new ParserProgram(parserConfig);
        parserProgram.execute();
    }

}
