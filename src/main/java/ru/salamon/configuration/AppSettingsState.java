package ru.salamon.configuration;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

@State(
        name = "ru.salamon.configuration.AppSettingsState",
        storages = @Storage("AnalyzerHelperStorage.xml")
)
public class AppSettingsState implements PersistentStateComponent<AppSettingsState> {

    private final Set<String> projectIds = new HashSet<>();

    public static AppSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(AppSettingsState.class);
    }

    @Nullable
    @Override
    public AppSettingsState getState() {
        return this;
    }

    @NotNull
    public Set<String> getProjectIds() {
        return projectIds;
    }

    @Override
    public void loadState(@NotNull AppSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

}
