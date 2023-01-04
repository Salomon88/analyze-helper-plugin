package ru.salamon.configuration;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@State(
        name = "ru.salamon.configuration.AppSettingsState",
        storages = @Storage("AnalyzerHelperStorage.xml")
)
public class AppSettingsState implements PersistentStateComponent<AppSettingsState> {

    public String userId = "John Q. Public";
    public boolean ideaStatus = false;

    public static AppSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(AppSettingsState.class);
    }

    @Nullable
    @Override
    public AppSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull AppSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

}
