package com.shuzijun.plantumlparser.core;

import com.github.javaparser.ParserConfiguration;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.*;
import java.util.regex.PatternSyntaxException;

/**
 * 解析配置
 *
 * @author shuzijun
 */
public class ParserConfig {
    /**
     * 解析的文件路径
     */
    private Map<String, File> fileMap = new HashMap<>();

    /**
     * 输出文件路径
     */
    private String outFilePath;

    private Set<String> excludeClassRegex = new HashSet<>();

    private Set<String> fieldModifier = new HashSet<>();

    private Set<String> methodModifier = new HashSet<>();

    private boolean showPackage = true;

    private boolean showConstructors = false;

    private boolean showDefaultConstructors = false;

    private boolean showComment = false;

    private ParserConfiguration.LanguageLevel languageLevel = ParserConfiguration.LanguageLevel.JAVA_8;

    public String getOutFilePath() {
        return outFilePath;
    }

    public void setOutFilePath(String outFilePath) {
        this.outFilePath = outFilePath;
    }

    public Set<File> getFilePaths() {
        return new HashSet<>(fileMap.values());
    }

    public void addFilePath(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        } else if (file.isDirectory()) {
            Collection<File> files = FileUtils.listFiles(file, new String[]{"java"}, Boolean.TRUE);
            files.forEach(fileTemp -> fileMap.put(fileTemp.getPath(), fileTemp));
        } else if (filePath.endsWith("java")) {
            fileMap.put(file.getPath(), file);
        }
    }

    public void addFieldModifier(String modifier) {
        fieldModifier.add(modifier);
    }

    public boolean isFieldModifier(String modifier) {
        return fieldModifier.contains(modifier);
    }

    public void addMethodModifier(String modifier) {
        methodModifier.add(modifier);
    }

    public boolean isMethodModifier(String modifier) {
        return methodModifier.contains(modifier);
    }

    public boolean isShowPackage() {
        return showPackage;
    }

    public void setShowPackage(boolean showPackage) {
        this.showPackage = showPackage;
    }

    public boolean isShowConstructors() {
        return showConstructors;
    }

    public void setShowConstructors(boolean showConstructors) {
        this.showConstructors = showConstructors;
    }

    public boolean isShowDefaultConstructors() {
        return showDefaultConstructors;
    }

    public void setShowDefaultConstructors(boolean showDefaultConstructors) {
        this.showDefaultConstructors = showDefaultConstructors;
    }

    public ParserConfiguration.LanguageLevel getLanguageLevel() {
        return languageLevel;
    }

    public void setLanguageLevel(ParserConfiguration.LanguageLevel languageLevel) {
        this.languageLevel = languageLevel;
    }

    public boolean isShowComment() {
        return showComment;
    }

    public void setShowComment(boolean showComment) {
        this.showComment = showComment;
    }

    public Set<String> getExcludeClassRegex() {
        return excludeClassRegex;
    }

    public void addExcludeClassRegex(String excludeClassRegex) {
        this.excludeClassRegex.add(excludeClassRegex);
    }
    public boolean isExcludeClass(String className) {
        if(className == null || className.trim().length() == 0){
            return false;
        }
        for (String regex : excludeClassRegex) {
            try {
                if(className.matches(regex)){
                    return true;
                }
            }catch (PatternSyntaxException ignore){
            }
        }
        return false;
    }
}
