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

public class KtRelationTester {

    private static File getResource(String name) {
        return new File(Objects.requireNonNull(
            KtRelationTester.class.getClassLoader().getResource(name)).getFile());
    }

    private static String getUml(String directory, boolean showPackage, String... fieldModifiers) throws IOException {
        ParserConfig parserConfig = new ParserConfig();
        parserConfig.setShowPackage(showPackage);
        for (String fieldModifier : fieldModifiers) {
            parserConfig.addFieldModifier(fieldModifier);
        }
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

    private static void testDirectory(String directory, String expectedFile, boolean showPackage, String... fieldModifiers) throws IOException {
        String uml = getUml(directory, showPackage, fieldModifiers);
        String expected = FileUtils.readFileToString(getResource(expectedFile), StandardCharsets.UTF_8);
        if (!isUmlEquivalent(uml, expected)) {
            System.err.println(uml);
            throw new RuntimeException("UML not equal: " + expectedFile);
        }
    }

    public static void main(String[] args) throws IOException {
        // Property types link classes across packages, through imports and import aliases (issue #35)
        testDirectory("ktpackage", "ktpackage/PackageOutput.txt", true, Constant.VisibilityAll);
        testDirectory("ktpackage", "ktpackage/PackageHiddenOutput.txt", false, Constant.VisibilityAll);

        // Properties hidden by the field modifiers are not drawn
        testDirectory("ktpackage", "ktpackage/PublicOutput.txt", true, Constant.VisibilityPublic);
    }
}
