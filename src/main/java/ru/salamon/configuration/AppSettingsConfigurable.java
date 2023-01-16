package ru.salamon.configuration;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

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
        var settings = AppSettingsState.getInstance();
        return !settings.getState().getProjectIds().equals(mySettingsComponent.getProjectIds());
    }

    @Override
    public void apply() {
        var settings = AppSettingsState.getInstance();
        settings.getState().getProjectIds().clear();
        settings.getState().getProjectIds().addAll(mySettingsComponent.getProjectIds());
    }

    @Override
    public void reset() {
        var settings = AppSettingsState.getInstance();
        mySettingsComponent.addProjectIds(settings.getProjectIds());
    }

    @Override
    public void disposeUIResources() {
        mySettingsComponent = null;
    }

}
