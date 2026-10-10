package com.shuzijun.plantumlparser.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * PUml视图
 *
 * @author shuzijun
 */
public class PUmlView implements PUml {

    private List<PUmlClass> pUmlClassList = new ArrayList<>();

    private List<PUmlRelation> pUmlRelationList = new ArrayList<>();

    private List<PUmlRelation> pUmlFieldRelationList = new ArrayList<>();

    public void addPUmlClass(PUmlClass pUmlClass) {
        pUmlClassList.add(pUmlClass);
    }

    public void addPUmlRelation(PUmlRelation pUmlRelation) {
        pUmlRelationList.add(pUmlRelation);
    }

    public void addPUmlFieldRelation(PUmlRelation pUmlRelation) {
        pUmlFieldRelationList.add(pUmlRelation);
    }

    @Override
    public String toString() {
        Set<String> classNameSet = pUmlClassList.stream()
                .map(pUmlClass -> ((pUmlClass.getPackageName() == null || pUmlClass.getPackageName().trim().equals("")) ? "" : (pUmlClass.getPackageName() + ".")) + pUmlClass.getClassName())
                .collect(Collectors.toSet());
        List<String> relationList = pUmlRelationList.stream().map(pUmlRelation -> pUmlRelation.toString()).collect(Collectors.toList());
        pUmlFieldRelationList.stream()
                .filter(pUmlRelation -> classNameSet.contains(pUmlRelation.getTarget()))
                .map(pUmlRelation -> pUmlRelation.toString())
                .distinct()
                .forEach(relationList::add);
        return "@startuml\n" +
                (pUmlClassList.isEmpty() ? "" : pUmlClassList.stream().map(pUmlClass -> pUmlClass.toString()).collect(Collectors.joining("\n")) + "\n") +
                (relationList.isEmpty() ? "" : "\n\n" + String.join("\n", relationList) + "\n") +
                "@enduml"
                ;
    }
}
