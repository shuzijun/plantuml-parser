package com.shuzijun.plantumlparser.cli.options.fieldmodifier;

import com.shuzijun.plantumlparser.cli.ParserModifiers;

public class FieldPrivateModifier extends FieldModifierWrapper {

    @Override
    protected String getOptName() {
        return "fpri";
    }

    @Override
    protected String getLongOptName() {
        return "field_private";
    }

    @Override
    protected String getModifier() {
        return ParserModifiers.PRIVATE;
    }

}
