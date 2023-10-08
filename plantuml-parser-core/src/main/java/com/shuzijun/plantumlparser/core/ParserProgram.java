package com.shuzijun.plantumlparser.core;

import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
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
import java.util.Optional;
import java.util.Set;

/**
 * 解析程序
 *
 * @author shuzijun
 */
public class ParserProgram {

    private ParserConfig parserConfig;


    public ParserProgram(ParserConfig parserConfig) {
        this.parserConfig = parserConfig;
    }


    public void execute() throws IOException {
        if(parserConfig.getLanguageLevel()!=null) {
            StaticJavaParser.getConfiguration().setLanguageLevel(parserConfig.getLanguageLevel());
        }else {
            StaticJavaParser.getConfiguration().setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_8);
        }
        Set<File> files = this.parserConfig.getFilePaths();

        Disposable disposable = Disposer.newDisposable();
        KotlinCoreEnvironment env = KotlinCoreEnvironment.createForProduction(
                disposable, new CompilerConfiguration(), EnvironmentConfigFiles.JVM_CONFIG_FILES);



        PUmlView pUmlView = new PUmlView();
        for (File file : files) {
            if (file.getPath().endsWith("java")){
                CompilationUnit compilationUnit = StaticJavaParser.parse(file);
                Optional<PackageDeclaration> packageDeclaration = compilationUnit.getPackageDeclaration();
                VoidVisitor<PUml> classNameCollector = new ClassVoidVisitor(packageDeclaration.isPresent() ? packageDeclaration.get().getNameAsString() : "", parserConfig);
                classNameCollector.visit(compilationUnit, pUmlView);
            } else if (file.getPath().endsWith("kt")){
                PsiFile ktFile = PsiFileFactory.getInstance(env.getProject()).createFileFromText(file.getName(), KotlinFileType.INSTANCE, FileUtils.readFileToString(file,Charset.defaultCharset()));
                if (!(ktFile instanceof  KtFile)) {
                    break;
                }
                FqName packageFqName =  ((KtFile)ktFile).getPackageFqName();
                KtClassVOidVisitor ktClassVOidVisitor = new KtClassVOidVisitor(packageFqName != null?"":packageFqName.asString(),parserConfig);
                ((KtFile) ktFile).accept(ktClassVOidVisitor,pUmlView);
            }
        }

        if (this.parserConfig.getOutFilePath() == null) {
            System.out.println(pUmlView);
        } else {
            File outFile = new File(this.parserConfig.getOutFilePath());
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            if (!outFile.exists()) {
                outFile.createNewFile();
            }
            FileUtils.write(outFile, pUmlView.toString(), Charset.forName("UTF-8"));
        }

    }

}
