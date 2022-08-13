package com.shuzijun.plantumlparser.cli.options;

import com.shuzijun.plantumlparser.cli.options.fieldmodifier.FieldDefaultModifier;
import com.shuzijun.plantumlparser.cli.options.fieldmodifier.FieldPrivateModifier;
import com.shuzijun.plantumlparser.cli.options.fieldmodifier.FieldProtectedModifier;
import com.shuzijun.plantumlparser.cli.options.fieldmodifier.FieldPublicModifier;
import com.shuzijun.plantumlparser.cli.options.filepath.FilePathOption;
import com.shuzijun.plantumlparser.cli.options.help.HelpOption;
import com.shuzijun.plantumlparser.cli.options.languagelevel.LanguageLevelOption;
import com.shuzijun.plantumlparser.cli.options.methodmodifier.MethodDefaultModifier;
import com.shuzijun.plantumlparser.cli.options.methodmodifier.MethodPrivateModifier;
import com.shuzijun.plantumlparser.cli.options.methodmodifier.MethodProtectedModifier;
import com.shuzijun.plantumlparser.cli.options.methodmodifier.MethodPublicModifier;
import com.shuzijun.plantumlparser.cli.options.outfilepath.OutfilePathOption;
import com.shuzijun.plantumlparser.cli.options.showcomment.ShowCommentOption;
import com.shuzijun.plantumlparser.cli.options.showconstructors.ShowConstructorsOption;
import com.shuzijun.plantumlparser.cli.options.showpackage.ShowPackageOption;
import org.apache.commons.cli.Options;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CliOptionsHolder {

    private CliOptionsHolder() {
        throw new UnsupportedOperationException();
    }

    private static final List<CliOption> cliOptions = new ArrayList<>();
    static {
        cliOptions.add(new FieldPublicModifier());
        cliOptions.add(new FieldProtectedModifier());
        cliOptions.add(new FieldDefaultModifier());
        cliOptions.add(new FieldPrivateModifier());

        cliOptions.add(new MethodPublicModifier());
        cliOptions.add(new MethodProtectedModifier());
        cliOptions.add(new MethodDefaultModifier());
        cliOptions.add(new MethodPrivateModifier());

        cliOptions.add(new OutfilePathOption());
        cliOptions.add(new FilePathOption());

        cliOptions.add(new LanguageLevelOption());
        cliOptions.add(new ShowCommentOption());
        cliOptions.add(new ShowConstructorsOption());
        cliOptions.add(new ShowPackageOption());

        // This is a special cli option for displaying the help
        cliOptions.add(new HelpOption(cliOptions));
    }

    public static Options createOptions() {
        final Options options = new Options();
        cliOptions.stream().map(CliOption::getOption).forEach(options::addOption);
        return options;
    }

    public static List<ParserConfigUpdater> getParserConfigUpdater(){
        return cliOptions.stream()
                .map(ParserConfigUpdater.class::cast)
                .collect(Collectors.toList());
    }
}
