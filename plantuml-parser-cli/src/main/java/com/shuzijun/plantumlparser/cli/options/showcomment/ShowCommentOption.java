package com.shuzijun.plantumlparser.cli.options.showcomment;

import com.shuzijun.plantumlparser.cli.options.CliOption;
import com.shuzijun.plantumlparser.core.ParserConfig;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import java.util.Arrays;

public class ShowCommentOption implements CliOption {

    protected final String optName = "scom";
    protected final String longOptName = "show_comment";
    protected final Option option;

    public ShowCommentOption() {
        this.option = new Option(this.optName,  this.longOptName, false, "Show comment");
    }

    @Override
    public ParserConfig updateConfig(final ParserConfig parserConfig, final CommandLine cmd) {
        return Arrays.stream(cmd.getOptions()).map(Option::getOpt)
                .filter(option -> option.equalsIgnoreCase(this.optName))
                .findAny()
                .map(option -> {
                    parserConfig.setShowComment(true);
                    return parserConfig;
                })
                .orElse(parserConfig);
    }

    @Override
    public Option getOption() {
        return this.option;
    }
}
