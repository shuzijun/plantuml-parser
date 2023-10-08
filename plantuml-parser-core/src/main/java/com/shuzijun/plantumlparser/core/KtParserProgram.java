package com.shuzijun.plantumlparser.core;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.util.Disposer;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import org.apache.commons.io.FileUtils;
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.idea.KotlinFileType;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class KtParserProgram {

    public static void execute(ParserConfig parserConfig, File file,PUmlView pUmlView) throws IOException {
        if (parserConfig.getProject() == null) {
                Disposable disposable = Disposer.newDisposable();
                KotlinCoreEnvironment env = KotlinCoreEnvironment.createForProduction(
                        disposable, new CompilerConfiguration(), EnvironmentConfigFiles.JVM_CONFIG_FILES);
            parserConfig.setProject( env.getProject());

        }

        PsiFile ktFile = PsiFileFactory.getInstance(parserConfig.getProject()).createFileFromText(file.getName(), KotlinFileType.INSTANCE, FileUtils.readFileToString(file, Charset.defaultCharset()));
        if (!(ktFile instanceof KtFile)) {
            return;
        }
        FqName packageFqName =  ((KtFile)ktFile).getPackageFqName();
        KtClassVOidVisitor ktClassVOidVisitor = new KtClassVOidVisitor(packageFqName.asString(),parserConfig);
        ((KtFile) ktFile).accept(ktClassVOidVisitor,pUmlView);
    }
}
