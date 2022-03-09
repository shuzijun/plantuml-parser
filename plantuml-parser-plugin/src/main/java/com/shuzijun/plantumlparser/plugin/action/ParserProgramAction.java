package com.shuzijun.plantumlparser.plugin.action;

import com.intellij.ide.BrowserUtil;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.shuzijun.plantumlparser.core.ParserConfig;
import com.shuzijun.plantumlparser.core.ParserProgram;
import com.shuzijun.plantumlparser.plugin.utils.MTAUtils;
import com.shuzijun.plantumlparser.plugin.utils.PropertiesUtils;
import com.shuzijun.plantumlparser.plugin.window.ParserConfigPanel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

/**
 * 解析动作
 *
 * @author shuzijun
 */
public class ParserProgramAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        MTAUtils.click(e.getActionManager().getId(this));
        ParserConfig parserConfig = new ParserConfig();
        VirtualFile[] virtualFiles = e.getData(CommonDataKeys.VIRTUAL_FILE_ARRAY);
        for (VirtualFile virtualFile : virtualFiles) {
            parserConfig.addFilePath(virtualFile.getPath());
        }
        if (parserConfig.getFilePaths().isEmpty()) {
            Notifications.Bus.notify(new Notification("plantuml-parser", "", PropertiesUtils.getInfo("select.empty"), NotificationType.WARNING), e.getProject());
            return;
        }
        ParserConfigDialog parserConfigDialog = new ParserConfigDialog(e.getProject(), parserConfig);
        if (parserConfigDialog.showAndGet()) {
            try {
                parserConfig = parserConfigDialog.getParserConfig();
            }catch (NullPointerException nullPointerException){
                Notifications.Bus.notify(new Notification("plantuml-parser", "", nullPointerException.getMessage(), NotificationType.ERROR), e.getProject());
                throw nullPointerException;
            }
            ParserProgram parserProgram = new ParserProgram(parserConfig);
            try {
                parserProgram.execute();
                Notifications.Bus.notify(new Notification("plantuml-parser", "", PropertiesUtils.getInfo("success", parserConfig.getOutFilePath()), NotificationType.INFORMATION), e.getProject());
                VirtualFile vf = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(new File(parserConfig.getOutFilePath()));
                OpenFileDescriptor descriptor = new OpenFileDescriptor(e.getProject(), vf);
                FileEditorManager.getInstance(e.getProject()).openTextEditor(descriptor, false);
            } catch (NullPointerException n) {
                Notifications.Bus.notify(new Notification("plantuml-parser", "", n.getMessage(), NotificationType.WARNING), e.getProject());
            } catch (IOException ioException) {
                Notifications.Bus.notify(new Notification("plantuml-parser", "", PropertiesUtils.getInfo("io.exception", ioException.getMessage()), NotificationType.WARNING), e.getProject());
            }
        }

    }

    class ParserConfigDialog extends DialogWrapper {

        private ParserConfigPanel parserConfigPanel;

        private ParserConfig parserConfig;

        public ParserConfigDialog(@Nullable Project project, ParserConfig parserConfig) {
            super(project, true);
            parserConfigPanel = new ParserConfigPanel(project);
            this.parserConfig = parserConfig;
            setModal(true);
            init();
            setTitle("ParserConfig");
        }

        @Override
        protected @Nullable JComponent createCenterPanel() {
            return parserConfigPanel.getJpanel();
        }

        @Override
        protected @NotNull Action getOKAction() {
            Action action = super.getOKAction();
            action.putValue(Action.NAME, "generate");
            return action;
        }

        @Override
        protected @NotNull Action getCancelAction() {
            return super.getCancelAction();
        }

        @Override
        protected @NotNull Action getHelpAction() {
            Action action = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    BrowserUtil.browse("https://github.com/shuzijun/plantuml-parser");
                }
            };
            action.putValue(Action.NAME, "help");
            return action;
        }

        public ParserConfig getParserConfig() {
            parserConfig.setOutFilePath(parserConfigPanel.getFilePath());
            parserConfigPanel.getField().forEach(s -> parserConfig.addFieldModifier(s));
            parserConfigPanel.getMethod().forEach(s -> parserConfig.addMethodModifier(s));
            parserConfig.setLanguageLevel(parserConfigPanel.getLanguageLevel());
            parserConfig.setShowPackage(parserConfigPanel.getShowPackage());
            parserConfig.setShowConstructors(parserConfigPanel.getConstructors());
            parserConfig.setShowComment(parserConfigPanel.getShowComment());
            return parserConfig;
        }
    }
}
