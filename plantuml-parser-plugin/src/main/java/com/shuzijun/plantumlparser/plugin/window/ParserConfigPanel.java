package com.shuzijun.plantumlparser.plugin.window;

import com.github.javaparser.ParserConfiguration;
import com.intellij.ui.DocumentAdapter;
import com.shuzijun.plantumlparser.plugin.utils.PropertiesUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * 解析配置
 *
 * @author shuzijun
 */
public class ParserConfigPanel {

    private String basePath;

    private JPanel jpanel;
    private JTextField fileName;
    private JLabel fileDirectory;
    private JLabel filePath;
    private JCheckBox fieldPrivateCheckBox;
    private JCheckBox fieldDefaultCheckBox;
    private JCheckBox fieldProtectedCheckBox;
    private JCheckBox fieldPublicCheckBox;
    private JCheckBox methodPrivateCheckBox;
    private JCheckBox methodProtectedCheckBox;
    private JCheckBox methodDefaultCheckBox;
    private JCheckBox methodPublicCheckBox;
    private JComboBox languageLevelComboBox;
    private JCheckBox showPackageCheckBox;
    private JCheckBox constructorsCheckBox;

    public ParserConfigPanel(String basePath) {
        this.basePath = basePath;
        fileDirectory.setText(basePath);
        filePath.setText(basePath + File.separator + fileName.getText() + ".puml");
        fileName.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(@NotNull DocumentEvent documentEvent) {
                filePath.setText(fileDirectory.getText() + File.separator + fileName.getText() + ".puml");
            }
        });
        for (ParserConfiguration.LanguageLevel value : ParserConfiguration.LanguageLevel.values()) {
            languageLevelComboBox.addItem(value);
        }
        languageLevelComboBox.setSelectedItem(ParserConfiguration.LanguageLevel.JAVA_8);

    }

    public JPanel getJpanel() {
        return jpanel;
    }

    public Set<String> getField() {
        Set<String> fieldModifier = new HashSet<>();
        if (fieldPrivateCheckBox.isSelected()) {
            fieldModifier.add(fieldPrivateCheckBox.getText());
        }
        if (fieldProtectedCheckBox.isSelected()) {
            fieldModifier.add(fieldProtectedCheckBox.getText());
        }

        if (fieldDefaultCheckBox.isSelected()) {
            fieldModifier.add(fieldDefaultCheckBox.getText());
        }

        if (fieldPublicCheckBox.isSelected()) {
            fieldModifier.add(fieldPublicCheckBox.getText());
        }
        return fieldModifier;
    }

    public Set<String> getMethod() {
        Set<String> methodModifier = new HashSet<>();
        if (methodPrivateCheckBox.isSelected()) {
            methodModifier.add(methodPrivateCheckBox.getText());
        }
        if (methodProtectedCheckBox.isSelected()) {
            methodModifier.add(methodProtectedCheckBox.getText());
        }

        if (methodDefaultCheckBox.isSelected()) {
            methodModifier.add(methodDefaultCheckBox.getText());
        }

        if (methodPublicCheckBox.isSelected()) {
            methodModifier.add(methodPublicCheckBox.getText());
        }
        return methodModifier;
    }

    public String getFilePath() {
        if (fileName.getText() == null || fileName.getText().trim().length() == 0) {
            throw new NullPointerException(PropertiesUtils.getInfo("fileName.empty"));
        }
        return filePath.getText();
    }

    public ParserConfiguration.LanguageLevel getLanguageLevel() {
        return (ParserConfiguration.LanguageLevel) languageLevelComboBox.getSelectedItem();
    }

    public boolean getShowPackage() {
        return showPackageCheckBox.isSelected();
    }

    public boolean getConstructors() {
        return constructorsCheckBox.isSelected();
    }
}
