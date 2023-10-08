package com.shuzijun.plantumlparser.core;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class KtTester {

    public static void main(String[]args)throws IOException {
        File file = new File(Objects.requireNonNull(
                KtTester.class.getClassLoader().getResource("KtExample.kt")).getFile());

        ParserConfig parserConfig=new ParserConfig();
        parserConfig.addFilePath(file.getAbsolutePath());

        parserConfig.addFieldModifier(Constant.VisibilityAll);
        parserConfig.addMethodModifier(Constant.VisibilityAll);

        ParserProgram parserProgram=new ParserProgram(parserConfig);
        parserProgram.execute();
    }

}
