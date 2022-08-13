package com.shuzijun.plantumlparser.cli.options.filepath;

import com.shuzijun.plantumlparser.cli.options.CliOption;
import com.shuzijun.plantumlparser.core.ParserConfig;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import java.util.Arrays;

public class FilePathOption implements CliOption {

    protected final String optName = "f";
    protected final String longOptName = "filepath";
    protected final Option option;

    public FilePathOption() {
        this.option = new Option(this.optName, this.longOptName, true, "Set the input file/directory");
    }

    @Override
    public ParserConfig updateConfig(final ParserConfig parserConfig, final CommandLine cmd) {
        Arrays.stream(cmd.getOptions()).map(Option::getOpt)
                .filter(option -> option.equalsIgnoreCase(this.optName))
                .forEach(option -> {
                    final String path = cmd.getOptionValue(option);
                    parserConfig.addFilePath(path);
                });
        return parserConfig;
    }

    @Override
    public Option getOption() {
        return this.option;
    }
}
