package com.shuzijun.plantumlparser.cli;

import com.shuzijun.plantumlparser.cli.options.CliOptionsHolder;
import com.shuzijun.plantumlparser.core.ParserConfig;
import com.shuzijun.plantumlparser.core.ParserProgram;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

public class CliRunner {

    public static void main(String[] args) {

        try {
            final Options options = CliOptionsHolder.createOptions();
            final CommandLineParser parser = new DefaultParser();
            final CommandLine cmd = parser.parse(options, args);

            // Updated ParserConfig
            final ParserConfig config = new ParserConfig();
            CliOptionsHolder.getParserConfigUpdater()
                            .forEach(parserConfigUpdater -> parserConfigUpdater.updateConfig(config, cmd));

            // config.addFilePath("/Users/kevinwallis/projects/plantuml-parser/plantuml-parser-cli/src/main/java/com/shuzijun/plantumlparser/cli");
            // config.setOutFilePath("/Users/kevinwallis/projects/plantuml-parser/plantuml-parser-cli/src/main/java/com/shuzijun/plantumlparser/cli/out.puml");

            final ParserProgram program = new ParserProgram(config);
            program.execute();
        } catch (final Exception ex) {
            ex.printStackTrace();
        }

    }

}
