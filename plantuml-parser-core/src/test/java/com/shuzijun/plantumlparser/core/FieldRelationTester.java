package com.shuzijun.plantumlparser.core;

import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FieldRelationTester {

    private static File getResource(String name) {
        return new File(Objects.requireNonNull(
            FieldRelationTester.class.getClassLoader().getResource(name)).getFile());
    }

    private static String getUml(String directory, boolean showPackage) throws IOException {
        ParserConfig parserConfig = new ParserConfig();
        parserConfig.setShowPackage(showPackage);
        parserConfig.addFieldModifier("private");
        parserConfig.addMethodModifier("public");
        parserConfig.addFilePath(getResource(directory).getAbsolutePath());
        ParserProgram parserProgram = new ParserProgram(parserConfig);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(baos));
        try {
            parserProgram.execute();
        } finally {
            System.setOut(oldOut);
        }
        return baos.toString();
    }

    private static boolean isUmlEquivalent(String uml1, String uml2) {
        List<String> lines1 = new ArrayList<>(Arrays.asList(uml1.replaceAll("\r", "").split("\n")));
        List<String> lines2 = new ArrayList<>(Arrays.asList(uml2.replaceAll("\r", "").split("\n")));
        lines1.sort(String::compareTo);
        lines2.sort(String::compareTo);
        return lines1.equals(lines2);
    }

    private static void testDirectory(String directory, String expectedFile, boolean showPackage) throws IOException {
        String uml = getUml(directory, showPackage);
        String expected = FileUtils.readFileToString(getResource(expectedFile), StandardCharsets.UTF_8);
        if (!isUmlEquivalent(uml, expected)) {
            System.err.println(uml);
            throw new RuntimeException("UML not equal: " + directory);
        }
    }

    public static void main(String[] args) throws IOException {
        // A field typed with a parsed class is drawn as composition (issue #30)
        testDirectory("field/composition", "field/CompositionOutput.txt", true);

        // Arrays and type arguments are drawn as aggregation; static fields, hidden fields
        // and types that were not parsed are not drawn
        testDirectory("field/aggregation", "field/AggregationOutput.txt", true);

        // Imported types across packages use the full name when packages are shown
        testDirectory("field/pkg", "field/PackageOutput.txt", true);
        testDirectory("field/pkg", "field/PackageHiddenOutput.txt", false);
    }
}
