package com.shuzijun.plantumlparser.cli.options.showconstructors;

import com.shuzijun.plantumlparser.cli.options.CliOption;
import com.shuzijun.plantumlparser.core.ParserConfig;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import java.util.Arrays;

public class ShowConstructorsOption implements CliOption {

    protected final String optName = "sctr";
    protected final String longOptName = "show_constructors";
    protected final Option option;

    public ShowConstructorsOption() {
        this.option = new Option(this.optName,  this.longOptName, false, "Show constructors");
    }

    @Override
    public ParserConfig updateConfig(final ParserConfig parserConfig, final CommandLine cmd) {
        return Arrays.stream(cmd.getOptions()).map(Option::getOpt)
                .filter(option -> option.equalsIgnoreCase(this.optName))
                .findAny()
                .map(option -> {
                    parserConfig.setShowConstructors(true);
                    return parserConfig;
                })
                .orElse(parserConfig);
    }

    @Override
    public Option getOption() {
        return this.option;
    }
}
