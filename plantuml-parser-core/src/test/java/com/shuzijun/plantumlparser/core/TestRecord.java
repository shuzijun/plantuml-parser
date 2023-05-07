package com.shuzijun.plantumlparser.core;

import java.io.*;


public class TestRecord {
    public static void testFile(String fileName, String expectedOutput) throws IOException {
        ParserConfig parserConfig=new ParserConfig();
        File file = new File(TestRecord.class.getClassLoader().getResource(fileName).getFile());
        parserConfig.addFilePath(file.getAbsolutePath());
        ParserProgram parserProgram=new ParserProgram(parserConfig);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        parserProgram.execute();
        String result = baos.toString();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        if (!result.equals(expectedOutput)) {
            throw new RuntimeException("Unexpected output: " + result);
        }
    }

    public static void main(String[]args)throws IOException {
        testFile("TestClass.java", "@startuml\nclass TestClass {\n}\n@enduml\r\n");
        System.out.println("Tests passed.");
    }

}
