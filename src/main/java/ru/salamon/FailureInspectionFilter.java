package ru.salamon;

import com.intellij.execution.filters.Filter;
import com.intellij.execution.filters.OpenFileHyperlinkInfo;
import com.intellij.notification.*;
import com.intellij.openapi.editor.markup.EffectType;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.ui.JBColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.regex.Pattern;

import static com.intellij.util.PathUtil.getLocalPath;

public class FailureInspectionFilter implements Filter {

    public static final Pattern PATTERN = Pattern.compile("[-+]\\s/([()a-zA-Z0-9/._-]*):([0-9]*)\\s.*");
    private final Project myProject;

    public FailureInspectionFilter(Project project) {
        myProject = project;
    }

    //Be careful with offset, because its entireLength - length of all document
    @Override
    public @Nullable Result applyFilter(@NotNull String line, int entireLength) {
        if (line.isBlank()) return null;
        var localLine = line.trim();
        var matcher = PATTERN.matcher(localLine);

        String partialFilePath;
        String rawNumber;
        if (matcher.find()) {
            partialFilePath = matcher.group(1);
            rawNumber = matcher.group(2);
        } else {
            return null;
        }
        var lineNumber = Integer.parseInt(rawNumber);
        var vf = LocalFileSystem.getInstance().findFileByPath(myProject.getBasePath() + "/" + getLocalPath(partialFilePath));

        Result result = null;
        if (vf != null) {
            result = new Result(
                    entireLength - localLine.length() + 1,
                    entireLength - (localLine.length() - (partialFilePath.length() + rawNumber.length() + 3)),
                    new OpenFileHyperlinkInfo(myProject, vf, lineNumber > 0 ? lineNumber - 1 : lineNumber),
                    null,
                    new TextAttributes(JBColor.GREEN, null, JBColor.GREEN, EffectType.SEARCH_MATCH, Font.PLAIN)
            );
        }
//        else {
//            Notifications.Bus.notify(new Notification("Plugin Error", "File " + partialFilePath + " wasn't found in project ", NotificationType.ERROR));
//        }

        return result;
    }

}
