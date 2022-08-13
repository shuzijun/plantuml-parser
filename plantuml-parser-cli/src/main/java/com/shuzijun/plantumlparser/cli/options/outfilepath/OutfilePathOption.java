package com.shuzijun.plantumlparser.cli.options.outfilepath;

import com.shuzijun.plantumlparser.cli.options.CliOption;
import com.shuzijun.plantumlparser.core.ParserConfig;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import java.util.Arrays;

public class OutfilePathOption implements CliOption {

    protected final String optName = "o";
    protected final String longOptName = "outfile";
    protected final Option option;

    public OutfilePathOption() {
        this.option = new Option(this.optName,  this.longOptName, true, "Set the output file");
    }

    @Override
    public ParserConfig updateConfig(final ParserConfig parserConfig, final CommandLine cmd) {
        return Arrays.stream(cmd.getOptions()).map(Option::getOpt)
                .filter(option -> option.equalsIgnoreCase(this.optName))
                .findAny()
                .map(option -> {
                    final String path = cmd.getOptionValue(option);
                    parserConfig.setOutFilePath(path);
                    return parserConfig;
                })
                .orElse(parserConfig);
    }

    @Override
    public Option getOption() {
        return this.option;
    }
}
