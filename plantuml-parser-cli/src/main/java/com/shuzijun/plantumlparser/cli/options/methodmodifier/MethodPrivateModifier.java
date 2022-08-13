package com.shuzijun.plantumlparser.cli.options.methodmodifier;

import com.shuzijun.plantumlparser.cli.ParserModifiers;

public class MethodPrivateModifier extends MethodModifierWrapper {

    @Override
    protected String getOptName() {
        return "mpri";
    }

    @Override
    protected String getLongOptName() {
        return "method_private";
    }

    @Override
    protected String getModifier() {
        return ParserModifiers.PRIVATE;
    }

}
