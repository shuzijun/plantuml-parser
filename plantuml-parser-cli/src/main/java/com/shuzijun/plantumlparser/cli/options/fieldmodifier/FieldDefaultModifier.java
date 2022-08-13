package com.shuzijun.plantumlparser.cli.options.fieldmodifier;

import com.shuzijun.plantumlparser.cli.ParserModifiers;

public class FieldDefaultModifier extends FieldModifierWrapper {

    @Override
    protected String getOptName() {
        return "fdef";
    }

    @Override
    protected String getLongOptName() {
        return "field_default";
    }

    @Override
    protected String getModifier() {
        return ParserModifiers.DEFAULT;
    }

}
