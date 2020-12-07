package com.shuzijun.plantumlparser.core;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * PUml视图
 *
 * @author shuzijun
 */
public class PUmlView {

    private List<PUmlClass> pUmlClassList = new ArrayList<>();

    private List<PUmlRelation> pUmlRelationList = new ArrayList<>();

    public void addPUmlClass(PUmlClass pUmlClass) {
        pUmlClassList.add(pUmlClass);
    }

    public void addPUmlRelation(PUmlRelation pUmlRelation) {
        pUmlRelationList.add(pUmlRelation);
    }

    @Override
    public String toString() {
        return "@startuml\n" +
                (pUmlClassList.isEmpty() ? "" : pUmlClassList.stream().map(pUmlClass -> pUmlClass.toString()).collect(Collectors.joining("\n")) + "\n") +
                (pUmlRelationList.isEmpty() ? "" : "\n\n" + pUmlRelationList.stream().map(pUmlRelation -> pUmlRelation.toString()).collect(Collectors.joining("\n")) + "\n") +
                "@enduml"
                ;
    }
}
