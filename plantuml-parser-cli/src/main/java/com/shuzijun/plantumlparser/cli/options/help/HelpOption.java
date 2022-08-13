package com.shuzijun.plantumlparser.cli.options.help;

import com.shuzijun.plantumlparser.cli.options.CliOption;
import com.shuzijun.plantumlparser.core.ParserConfig;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.util.Arrays;
import java.util.List;

public class HelpOption implements CliOption {

    protected final String optName = "h";
    protected final String longOptName = "help";
    protected final Option option;

    protected final List<CliOption> cliOptions;

    public HelpOption(final List<CliOption> cliOptions) {
        this.option = new Option(this.optName,  this.longOptName, false, "Show the help");
        this.cliOptions = cliOptions;
    }

    @Override
    public ParserConfig updateConfig(final ParserConfig parserConfig, final CommandLine cmd) {
        Arrays.stream(cmd.getOptions()).map(Option::getOpt)
                .filter(option -> option.equalsIgnoreCase(this.optName))
                .findAny()
                .ifPresent(option -> {
                    final HelpFormatter formatter = new HelpFormatter();
                    final Options options = new Options();
                    cliOptions.forEach(cliOption -> options.addOption(cliOption.getOption()));
                    formatter.printHelp("plantuml-parser-cli", options, true);
                });
        return parserConfig;
    }

    @Override
    public Option getOption() {
        return this.option;
    }
}
