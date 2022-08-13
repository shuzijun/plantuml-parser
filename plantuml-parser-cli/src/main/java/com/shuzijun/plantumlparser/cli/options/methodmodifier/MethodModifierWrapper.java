package com.shuzijun.plantumlparser.cli.options.methodmodifier;

import com.shuzijun.plantumlparser.cli.options.CliOption;
import com.shuzijun.plantumlparser.core.ParserConfig;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import java.util.Arrays;

public abstract class MethodModifierWrapper implements CliOption {

    protected abstract String getOptName();
    protected abstract String getLongOptName();
    protected abstract String getModifier();

    protected final Option option;

    public MethodModifierWrapper() {
        this.option = new Option(this.getOptName(),  this.getLongOptName(), false, String.format("Add %s methods", this.getModifier()));
    }

    @Override
    public ParserConfig updateConfig(final ParserConfig parserConfig, final CommandLine cmd) {
        return Arrays.stream(cmd.getOptions()).map(Option::getOpt)
                .filter(option -> option.equalsIgnoreCase(this.getOptName()))
                .findAny()
                .map(option -> {
                    parserConfig.addMethodModifier(this.getModifier());
                    return parserConfig;
                })
                .orElse(parserConfig);
    }

    @Override
    public Option getOption() {
        return this.option;
    }
}
