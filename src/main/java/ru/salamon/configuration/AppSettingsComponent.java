package ru.salamon.configuration;

import com.intellij.ui.components.*;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppSettingsComponent {

    private final JPanel myMainPanel;
    private final JLabel myLabel = new JBLabel("List of teamcity project identifiers. Each project id must be placed on a new line");
    private final JBTextArea projectsIds = new JBTextArea(15, 1);

    public AppSettingsComponent() {
        var settings = AppSettingsState.getInstance();
        projectsIds.setText(
                settings
                        .getState()
                        .getProjectIds()
                        .stream()
                        .map(Objects::toString)
                        .collect(Collectors.joining("\n"))
                        .trim()
        );
        myMainPanel = FormBuilder.createFormBuilder()
                .addComponent(myLabel)
                .addComponent(projectsIds, 1)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return myMainPanel;
    }

    public Set<String> getProjectIds() {
        return Stream
                .of(projectsIds.getText().split("\n"))
                .collect(Collectors.toSet());
    }

    public void addProjectIds(Set<String>projectIds) {
        projectsIds.setText(String.join("\n", projectIds).trim());
    }

    public JComponent getPreferredFocusedComponent() {
        return projectsIds;
    }
}
