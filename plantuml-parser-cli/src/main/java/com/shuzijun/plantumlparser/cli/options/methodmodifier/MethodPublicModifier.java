package com.shuzijun.plantumlparser.cli.options.methodmodifier;

import com.shuzijun.plantumlparser.cli.ParserModifiers;

public class MethodPublicModifier extends MethodModifierWrapper {

    @Override
    protected String getOptName() {
        return "mpub";
    }

    @Override
    protected String getLongOptName() {
        return "method_public";
    }

    @Override
    protected String getModifier() {
        return ParserModifiers.PUBLIC;
    }

}
