package com.shuzijun.plantumlparser.cli.options.fieldmodifier;

import com.shuzijun.plantumlparser.cli.ParserModifiers;

public class FieldProtectedModifier extends FieldModifierWrapper {

    @Override
    protected String getOptName() {
        return "fpro";
    }

    @Override
    protected String getLongOptName() {
        return "field_protected";
    }

    @Override
    protected String getModifier() {
        return ParserModifiers.PROTECTED;
    }

}
