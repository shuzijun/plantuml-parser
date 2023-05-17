package com.shuzijun.plantumlparser.cli.options.showdefaultconstructors;

import com.shuzijun.plantumlparser.cli.options.CliOption;
import com.shuzijun.plantumlparser.core.ParserConfig;
import java.util.Arrays;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

public class ShowDefaultConstructorsOption implements CliOption {

    protected final String optName = "sdctr";
    protected final String longOptName = "show_default_constructors";
    protected final Option option;

    public ShowDefaultConstructorsOption() {
        this.option = new Option(this.optName,  this.longOptName, false, "Show default constructors");
    }

    @Override
    public ParserConfig updateConfig(final ParserConfig parserConfig, final CommandLine cmd) {
        return Arrays.stream(cmd.getOptions()).map(Option::getOpt)
            .filter(option -> option.equalsIgnoreCase(this.optName))
            .findAny()
            .map(option -> {
                parserConfig.setShowDefaultConstructors(true);
                return parserConfig;
            })
            .orElse(parserConfig);
    }

    @Override
    public Option getOption() {
        return this.option;
    }
}
