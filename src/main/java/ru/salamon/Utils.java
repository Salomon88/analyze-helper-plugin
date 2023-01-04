package ru.salamon;

import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.PathUtil;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

final public class Utils {

    //Original code - PhpLocalPathMapper
    public static String getLocalPath(Project project , @NotNull final String remoteFileUrlOrPath) {
        final String remoteFilePath = VirtualFileManager.extractPath(remoteFileUrlOrPath);
        final String remoteCanonicalFilePath = FileUtil.toCanonicalPath(remoteFilePath);

        final String fileName = PathUtil.getFileName(remoteFilePath);
        if (fileName.length() > 0) {
            Collection<VirtualFile> candidates = ReadAction
                    .compute(() -> FilenameIndex.getVirtualFilesByName(fileName, GlobalSearchScope.allScope(project)));
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
