package com.shuzijun.plantumlparser.cli.options.fieldmodifier;

import com.shuzijun.plantumlparser.cli.ParserModifiers;

public class FieldPublicModifier extends FieldModifierWrapper {

    @Override
    protected String getOptName() {
        return "fpub";
    }

    @Override
    protected String getLongOptName() {
        return "field_public";
    }

    @Override
    protected String getModifier() {
        return ParserModifiers.PUBLIC;
    }

}
