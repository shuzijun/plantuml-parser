package com.shuzijun.plantumlparser.core;

import com.github.javaparser.ParserConfiguration;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class UmlGenerationTester {

    private static ParserConfig makeParserConfig(String filename, boolean showDefaultCon) {
        ParserConfig parserConfig = new ParserConfig();
        parserConfig.setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_17);
        parserConfig.setShowConstructors(true);
        parserConfig.setShowDefaultConstructors(showDefaultCon);
        parserConfig.addFieldModifier("private");
        parserConfig.addMethodModifier("public");
        parserConfig.addMethodModifier("default");
        File file = new File(Objects.requireNonNull(
            UmlGenerationTester.class.getClassLoader().getResource(filename)).getFile());
        parserConfig.addFilePath(file.getAbsolutePath());
        return parserConfig;
    }

    private static String getUml(String filename, boolean showDefaultCon) throws IOException {
        ParserProgram parserProgram = new ParserProgram(makeParserConfig(filename, showDefaultCon));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(baos));
        parserProgram.execute();
        String result = baos.toString();
        System.setOut(oldOut);
        return result;
    }

    private static boolean isUmlEquivalent(String uml1, String uml2) {
        String[] lines1 = uml1.replaceAll("\r", "").split("\n");
        List<String> lines2 = new ArrayList<>(Arrays.asList(uml2.replaceAll("\r", "").split("\n")));
        if (lines1.length != lines2.size()) {
            return false;
        }
        for (String line : lines1) {
            if (!lines2.contains(line)) {
                System.out.println(line);
                return false;
            }
            lines2.remove(line);
        }
        return lines2.isEmpty();
    }

    private static boolean isUmlEquivalent(String uml, File umlFile) {
        StringBuilder sb = new StringBuilder();
        try (Scanner sc = new Scanner(umlFile)) {
            while (sc.hasNextLine()) {
                String cur = sc.nextLine();
                sb.append(cur).append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return isUmlEquivalent(uml, sb.toString());
    }

    private static void testFilesRecord(String filename1, String filename2)
        throws IOException {
        String uml1 = getUml(filename1, false);
        String uml2 = getUml(filename2, false);
        if (!isUmlEquivalent(uml1, uml2)) {
            System.err.println("UML unequal: ");
            System.err.println(uml1);
            System.err.println(uml2);
            throw new RuntimeException("UML unequal");
        }
    }

    private static void testFilesDefaultConstructor(
        String fileNameUmlExpected, String fileNameSrc, boolean showDefaultCon)
        throws IOException {
        String uml = getUml(fileNameSrc, showDefaultCon);
        File file = new File(Objects.requireNonNull(
            UmlGenerationTester.class.getClassLoader().getResource(fileNameUmlExpected)).getFile());
        if (!isUmlEquivalent(uml, file)) {
            System.err.println(uml);
            throw new RuntimeException("UML not equal");
        }
    }

    private static void testRecord() throws IOException {
        for (int i = 1; i <= 6; i++) {
            testFilesRecord(
                String.format("Record%d.java", i),
                String.format("Record%dDesugared.java", i));
        }
    }

    private static void testDefaultConstructor() throws IOException {
        // A class without a constructor should contain a default constructor in the output
        // when showDefaultConstructor is true
        testFilesDefaultConstructor("ClassWOConstructorOutput.txt", "ClassWOConstructor.java", true);
        testFilesDefaultConstructor("ClassWithConstructorOutput.txt", "ClassWithConstructor.java", true);

        // A class without a constructor should not contain a default constructor in the output
        // when showDefaultConstructor is false
        testFilesDefaultConstructor("ClassWOConstructorNoConstructorOutput.txt", "ClassWOConstructor.java", false);
        testFilesDefaultConstructor("ClassWithConstructorOutput.txt", "ClassWithConstructor.java", false);
    }

    public static void main(String[] args) throws IOException {
        testRecord();
        testDefaultConstructor();
    }
}
