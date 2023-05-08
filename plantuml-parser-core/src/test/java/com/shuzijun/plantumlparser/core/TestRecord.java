package com.shuzijun.plantumlparser.core;

import com.github.javaparser.ParserConfiguration;

import java.io.*;
import java.util.*;


public class TestRecord {

    private static ParserConfig makeParserConfig(String filename) {
        ParserConfig parserConfig = new ParserConfig();
        parserConfig.setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_16);
        parserConfig.setShowConstructors(true);
        parserConfig.addFieldModifier("private");
        parserConfig.addMethodModifier("public");
        parserConfig.addMethodModifier("default");
        File file = new File(TestRecord.class.getClassLoader().getResource(filename).getFile());
        parserConfig.addFilePath(file.getAbsolutePath());
        return parserConfig;
    }

    private static String getUml(String filename) throws IOException {
        ParserProgram parserProgram = new ParserProgram(makeParserConfig(filename));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(baos));
        parserProgram.execute();
        String result = baos.toString();
        System.setOut(oldOut);
        return result;
    }

    // Consider UML equivalent if it has the same lines, possibly in a
    // different order. This is not perfect but is good enough for our
    // purposes.
    private static boolean isUmlEquivalent(String uml1, String uml2) {
        String[] lines1 = uml1.split("\\n");
        List<String> lines2 = new ArrayList(Arrays.asList(uml2.split("\\n")));
        if (lines1.length != lines2.size()) {
            return false;
        }
        for (String line : lines1) {
            if (!lines2.contains(line)) {
                return false;
            }
            lines2.remove(line);
        }
        return lines2.isEmpty();
    }

    public static void testFiles(String filename1, String filename2) throws IOException {
        String uml1 = getUml(filename1);
        System.out.println(uml1);
        String uml2 = getUml(filename2);
        System.out.println(uml2);
        if (!isUmlEquivalent(uml1, uml2)) {
            System.err.println("UML unequal: ");
            System.err.println(uml1);
            System.err.println(uml2);
            throw new RuntimeException("UML unequal");
        }
    }

    public static void main(String[] args) throws IOException {
        for (int i = 1; i <= 6; i++) {
            testFiles(
                    String.format("Record%d.java", i),
                    String.format("Record%dDesugared.java", i));
        }
    }
}
