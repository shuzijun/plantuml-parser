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

    private String classComment;

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

    public String getClassComment() {
        return classComment;
    }

    public void setClassComment(String classComment) {
        this.classComment = classComment;
    }

    public void addPUmlFieldList(PUmlField pUmlField) {
        this.pUmlFieldList.add(pUmlField);
    }


    public void addPUmlMethodList(PUmlMethod pUmlMethod) {
        this.pUmlMethodList.add(pUmlMethod);
    }

    @Override
    public String toString() {

        String fullClassName = ((packageName == null || packageName.trim().equals("")) ? "" : (packageName + ".")) + className;

        String classStr = classType + " " + fullClassName + " {\n" +
                (pUmlFieldList.isEmpty() ? "" : pUmlFieldList.stream().map(pUmlField -> pUmlField.toString()).collect(Collectors.joining("\n")) + "\n") +
                (pUmlMethodList.isEmpty() ? "" : pUmlMethodList.stream().map(pUmlField -> pUmlField.toString()).collect(Collectors.joining("\n")) + "\n") +
                "}";

        if(getClassComment() != null && getClassComment().length() > 0){
            classStr += "\nnote top of "+fullClassName+"\n"+getClassComment()+"\nend note\n";
        }

        StringBuilder fieldComment = new StringBuilder();

        if(!pUmlFieldList.isEmpty()){
            int commentIdx = 0;
            for (PUmlField pUmlField : pUmlFieldList) {
                if(pUmlField.getComment() != null && pUmlField.getComment().length() > 0){
                    fieldComment.append("note ");
                    if(commentIdx % 2 != 0){
                        fieldComment.append("right");
                    }else{
                        fieldComment.append("left");
                    }
                    fieldComment.append(" of ").append(fullClassName).append("::").append(pUmlField.getName()).append("\n");
                    fieldComment.append(pUmlField.getComment()).append("\n");
                    fieldComment.append("end note\n");
                    commentIdx++;
                }
            }
        }


        if(!pUmlMethodList.isEmpty()){
            int commentIdx = 0;
            for (PUmlMethod pUmlMethod : pUmlMethodList) {
                if(pUmlMethod.getComment() != null && pUmlMethod.getComment().length() > 0){
                    fieldComment.append("note ");
                    if(commentIdx % 2 != 0){
                        fieldComment.append("right");
                    }else{
                        fieldComment.append("left");
                    }
                    fieldComment.append(" of ").append(fullClassName).append("::").append(pUmlMethod.getFullName()).append("\n");
                    fieldComment.append(pUmlMethod.getComment()).append("\n");
                    fieldComment.append("end note\n");
                    commentIdx++;
                }
            }
        }

        classStr += fieldComment.toString();
        return classStr;
    }
}
