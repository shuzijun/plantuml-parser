package com.shuzijun.plantumlparser.cli.options.methodmodifier;

import com.shuzijun.plantumlparser.cli.ParserModifiers;

public class MethodDefaultModifier extends MethodModifierWrapper {

    @Override
    protected String getOptName() {
        return "mdef";
    }

    @Override
    protected String getLongOptName() {
        return "method_default";
    }

    @Override
    protected String getModifier() {
        return ParserModifiers.DEFAULT;
    }

}
