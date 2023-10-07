package com.shuzijun.plantumlparser.core;

public  class MyVisitor extends ParserConfig  {
    private final String packageName;

    private final ParserConfig parserConfig;

    public MyVisitor(String packageName, ParserConfig parserConfig) {
        this.packageName = packageName;
        this.parserConfig = parserConfig;
    }

    public PUmlClass createUmlClass() {
        PUmlClass pUmlClass = new PUmlClass();
        if (parserConfig.isShowPackage()) {
            pUmlClass.setPackageName(packageName);
        } else {
            pUmlClass.setPackageName("");
        }
        return pUmlClass;
    }
}
