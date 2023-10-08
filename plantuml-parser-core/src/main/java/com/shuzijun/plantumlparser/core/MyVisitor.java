package com.shuzijun.plantumlparser.core;

public interface MyVisitor {

    default PUmlClass createUmlClass() {
        PUmlClass pUmlClass = new PUmlClass();
        if (getParserConfig().isShowPackage()) {
            pUmlClass.setPackageName(getPackageName());
        } else {
            pUmlClass.setPackageName("");
        }
        return pUmlClass;
    }
    default String getPackageNamePrefix(String packageName) {
        if (packageName == null || packageName.trim().equals("")) {
            return "";
        } else {
            return packageName + ".";
        }
    }

    String getPackageName();
    ParserConfig getParserConfig();

}
