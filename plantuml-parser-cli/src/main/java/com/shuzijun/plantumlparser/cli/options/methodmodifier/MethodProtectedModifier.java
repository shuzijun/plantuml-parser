package com.shuzijun.plantumlparser.cli.options.methodmodifier;

import com.shuzijun.plantumlparser.cli.ParserModifiers;

public class MethodProtectedModifier extends MethodModifierWrapper {

    @Override
    protected String getOptName() {
        return "mpro";
    }

    @Override
    protected String getLongOptName() {
        return "method_protected";
    }

    @Override
    protected String getModifier() {
        return ParserModifiers.PROTECTED;
    }

}
