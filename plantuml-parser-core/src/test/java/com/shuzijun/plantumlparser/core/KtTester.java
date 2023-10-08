package com.shuzijun.plantumlparser.core;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.config.CompilerConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class KtTester {

    public static void main(String[]args)throws IOException {
        String filePath = KtTester.class.getClassLoader().getResource("kt").getPath();

        ParserConfig parserConfig=new ParserConfig();
        parserConfig.addFilePath(filePath);
        parserConfig.setShowPackage(true);
        parserConfig.setShowComment(true);
        parserConfig.setShowConstructors(true);

        parserConfig.addFieldModifier(Constant.VisibilityAll);
        parserConfig.addMethodModifier(Constant.VisibilityAll);

        ParserProgram parserProgram=new ParserProgram(parserConfig);
        parserProgram.execute();
    }

}
