package ru.salamon;

import com.intellij.execution.filters.Filter;
import com.intellij.execution.filters.HyperlinkInfo;
import com.intellij.execution.filters.OpenFileHyperlinkInfo;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.PathUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.regex.Pattern;

class FailureInspectionFilter implements Filter {

    private final Project myProject;
    private final Pattern PATTERN;

    public FailureInspectionFilter(Project project) {
        PATTERN = Pattern.compile("[-+] /([a-zA-Z0-9/.-_]*):([0-9]*)\\s.*");
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
        var vf = LocalFileSystem.getInstance().findFileByPath(getLocalPath(partialFilePath));

        if (vf == null) return null;
        return new Result(
                entireLength - localLine.length() + 1,
                entireLength - (localLine.length() - (partialFilePath.length() + rawNumber.length() + 3)),
                new OpenFileHyperlinkInfo(myProject, vf, lineNumber > 0 ? lineNumber - 1 : lineNumber)
        );
    }

    //Original code - PhpLocalPathMapper
    private String getLocalPath(@NotNull final String remoteFileUrlOrPath) {
        final String remoteFilePath = VirtualFileManager.extractPath(remoteFileUrlOrPath);
        final String remoteCanonicalFilePath = FileUtil.toCanonicalPath(remoteFilePath);

        final String fileName = PathUtil.getFileName(remoteFilePath);
        if (fileName.length() > 0) {
            Collection<VirtualFile> candidates = ReadAction
                    .compute(() -> FilenameIndex.getVirtualFilesByName(fileName, GlobalSearchScope.allScope(myProject)));
            int i = 0;
            for (VirtualFile candidate : candidates) {
                int MAX_RESOLVE_ATTEMPTS = 100;
                if (i++ > MAX_RESOLVE_ATTEMPTS) break;
                try {
                    final String candidateFilePath = candidate.getPath();
                    final String resolvedFilePath = new File(candidateFilePath).getCanonicalPath();
                    if (FileUtil.toCanonicalPath(resolvedFilePath).contains(remoteCanonicalFilePath)) {
                        return FileUtil.toCanonicalPath(candidateFilePath);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return remoteFilePath;
    }

}
