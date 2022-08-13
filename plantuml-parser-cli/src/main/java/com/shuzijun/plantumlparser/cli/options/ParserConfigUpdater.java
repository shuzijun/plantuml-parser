package com.shuzijun.plantumlparser.cli.options;

import com.shuzijun.plantumlparser.core.ParserConfig;
import org.apache.commons.cli.CommandLine;

public interface ParserConfigUpdater {

    ParserConfig updateConfig(final ParserConfig parserConfig, final CommandLine cmd);

}
