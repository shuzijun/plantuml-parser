package com.shuzijun.plantumlparser.core;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * plantUml类
 *
 * @author shuzijun
 */
public class PUmlClass implements PUml{
    private String packageName;

    private String className;

    private String classType;

    private List<PUmlField> pUmlFieldList = new LinkedList<>();

    private List<PUmlMethod> pUmlMethodList = new LinkedList<>();

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }


    public void addPUmlFieldList(PUmlField pUmlField) {
        this.pUmlFieldList.add(pUmlField);
    }


    public void addPUmlMethodList(PUmlMethod pUmlMethod) {
        this.pUmlMethodList.add(pUmlMethod);
    }

    @Override
    public String toString() {
        return classType + " " + ((packageName == null || packageName.trim().equals("")) ? "" : (packageName + ".")) + className + " {\n" +
                (pUmlFieldList.isEmpty() ? "" : pUmlFieldList.stream().map(pUmlField -> pUmlField.toString()).collect(Collectors.joining("\n")) + "\n") +
                (pUmlMethodList.isEmpty() ? "" : pUmlMethodList.stream().map(pUmlField -> pUmlField.toString()).collect(Collectors.joining("\n")) + "\n") +
                "}";
    }
}
