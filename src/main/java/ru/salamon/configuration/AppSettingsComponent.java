package ru.salamon.configuration;

// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.components.*;
import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * Supports creating and managing a {@link JPanel} for the Settings Dialog.
 */
public class AppSettingsComponent {

    private final JPanel myMainPanel;
    private final JBTextField myUserNameText = new JBTextField();
    private final JPanel projectsIds;

    public AppSettingsComponent() {
        var dataModel = new AbstractTableModel() {
            public int getColumnCount() {
                return 1;
            }

            public int getRowCount() {
                return 9;
            }

            public Object getValueAt(int row, int col) {
                return row;
            }
        };
        var table = new JBTable(dataModel);
//        JScrollPane scrollpane = new JBScrollPane(table);

        projectsIds = ToolbarDecorator
                .createDecorator(table)
                .setAddAction((param) -> {


                    for (int i = 0; i < 10; i++) {
                        System.out.println("Data at row " + " " + i + " and col " + 0 + " " + table.getModel().getValueAt(i, 0));
                        table.getModel().setValueAt("project id " + i, i, 0);
                        System.out.println("After at row " + " " + i + " and col " + 0 + " " + table.getModel().getValueAt(i, 0));
                    }
                    //dataModel.fireTableDataChanged();
                    table.updateUI();
                    System.out.println("All rows were updated");
                })
                .setRemoveAction((param) -> System.out.println("Removing from table"))
                .createPanel();
        myMainPanel = FormBuilder.createFormBuilder()
//                .addLabeledComponent(new JBLabel("Enter user name: "), myUserNameText, 1, false)
                .addComponent(projectsIds, 1)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return myMainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return myUserNameText;
    }

    @NotNull
    public String getUserNameText() {
        return myUserNameText.getText();
    }

    public void setUserNameText(@NotNull String newText) {
        myUserNameText.setText(newText);
    }

    public String[] getProjectIds() {
        return new String[3];
    }
//
//    public void setIdeaUserStatus(boolean newStatus) {
//        myIdeaUserStatus.setSelected(newStatus);
//    }

}
