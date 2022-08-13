package com.shuzijun.plantumlparser.cli.options.showpackage;

import com.shuzijun.plantumlparser.cli.options.CliOption;
import com.shuzijun.plantumlparser.core.ParserConfig;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import java.util.Arrays;

public class ShowPackageOption implements CliOption {

    protected final String optName = "spkg";
    protected final String longOptName = "show_package";
    protected final Option option;

    public ShowPackageOption() {
        this.option = new Option(this.optName,  this.longOptName, false, "Show package");
    }

    @Override
    public ParserConfig updateConfig(final ParserConfig parserConfig, final CommandLine cmd) {
        return Arrays.stream(cmd.getOptions()).map(Option::getOpt)
                .filter(option -> option.equalsIgnoreCase(this.optName))
                .findAny()
                .map(option -> {
                    parserConfig.setShowPackage(true);
                    return parserConfig;
                })
                .orElseGet(() -> {
                    parserConfig.setShowPackage(false);
                    return parserConfig;
                });
    }

    @Override
    public Option getOption() {
        return this.option;
    }
}
