package com.shuzijun.plantumlparser.cli.options.languagelevel;

import com.github.javaparser.ParserConfiguration;
import com.shuzijun.plantumlparser.cli.options.CliOption;
import com.shuzijun.plantumlparser.core.ParserConfig;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import java.util.Arrays;

public class LanguageLevelOption implements CliOption {

    protected final String optName = "l";
    protected final String longOptName = "language_level";
    protected final Option option;

    public LanguageLevelOption() {
        this.option = new Option(this.optName,  this.longOptName, true, "Set language level");
    }

    @Override
    public ParserConfig updateConfig(final ParserConfig parserConfig, final CommandLine cmd) {
        return Arrays.stream(cmd.getOptions()).map(Option::getOpt)
                .filter(option -> option.equalsIgnoreCase(this.optName))
                .findAny()
                .map(option -> {
                    final String languageLevelStr = cmd.getOptionValue(option);
                    final ParserConfiguration.LanguageLevel languageLevel = ParserConfiguration.LanguageLevel.valueOf(languageLevelStr);
                    parserConfig.setLanguageLevel(languageLevel);
                    return parserConfig;
                })
                .orElse(parserConfig);
    }

    @Override
    public Option getOption() {
        return this.option;
    }
}
