package ru.salamon.configuration;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

public class AppSettingsConfigurable implements Configurable {

    private AppSettingsComponent mySettingsComponent;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Analyzer Helper Configuration";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return mySettingsComponent.getPreferredFocusedComponent();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        mySettingsComponent = new AppSettingsComponent();
        return mySettingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
//        AppSettingsState settings = AppSettingsState.getInstance();
//        boolean modified = !mySettingsComponent.getUserNameText().equals(settings.userId);
//        modified |= mySettingsComponent.getIdeaUserStatus() != settings.ideaStatus;
//        return modified;
        return false;
    }

    @Override
    public void apply() {
        AppSettingsState settings = AppSettingsState.getInstance();
        settings.projectIds = List.of(mySettingsComponent.getProjectIds());
    }

    @Override
    public void reset() {
//        AppSettingsState settings = AppSettingsState.getInstance();
//        mySettingsComponent.setUserNameText(settings.userId);
//        mySettingsComponent.setIdeaUserStatus(settings.ideaStatus);
    }

    @Override
    public void disposeUIResources() {
        mySettingsComponent = null;
    }

}
