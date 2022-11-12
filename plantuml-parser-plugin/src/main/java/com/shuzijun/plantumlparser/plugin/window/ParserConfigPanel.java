package com.shuzijun.plantumlparser.plugin.window;

import com.github.javaparser.ParserConfiguration;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserFactory;
import com.intellij.openapi.fileChooser.PathChooserDialog;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.DocumentAdapter;
import com.shuzijun.plantumlparser.plugin.utils.PropertiesUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.*;

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
    private JButton chooseFilePath;
    private JCheckBox commentCheckBox;
    private JTextField excludeClass;
    private PathChooserDialog pathChooserDialog;

    public ParserConfigPanel(Project project) {
        this.basePath = project.getBasePath();
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

        FileChooserDescriptor fileChooserDescriptor = new FileChooserDescriptor(
                false, true, false,
                false, false, false);

        chooseFilePath.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pathChooserDialog = FileChooserFactory.getInstance().createPathChooser(fileChooserDescriptor,project,jpanel);

                pathChooserDialog.choose(project.getProjectFile(), choose -> {
                    if(choose.size() > 0){
                        VirtualFile file = choose.get(0);
                        fileDirectory.setText(file.getPath());
                        filePath.setText(fileDirectory.getText() + File.separator + fileName.getText() + ".puml");
                    }
                });
            }
        });
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

    public boolean getShowComment() {
        return commentCheckBox.isSelected();
    }

    public List<String> getExcludeClass(){
        if (excludeClass.getText() == null || excludeClass.getText().trim().length() == 0) {
            return new ArrayList<>();
        }
        return Arrays.asList(excludeClass.getText().trim().split(","));
    }
}
