package com.shuzijun.plantumlparser.core;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * plantUml方法
 *
 * @author mafayun
 */
public class PUmlMethod {

    private String visibility = "default";

    private boolean isStatic;

    private boolean isAbstract;

    private String returnType;

    private String name;

    private List<String> paramList = new LinkedList<>();

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean anAbstract) {
        isAbstract = anAbstract;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getParamList() {
        return paramList;
    }

    public void addParam(String param) {
        this.paramList.add(param);
    }

    @Override
    public String toString() {
        return VisibilityUtils.toCharacter(visibility) + " " + (isStatic ? "{static} " : "") + (isAbstract ? "{abstract}" : "")
                + returnType + " " + name + "("
                + (paramList.isEmpty() ? "" : paramList.stream().collect(Collectors.joining(",")))
                + ")";
    }
}
