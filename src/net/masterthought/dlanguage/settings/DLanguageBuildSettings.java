package net.masterthought.dlanguage.settings;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import net.masterthought.dlanguage.jps.model.DLanguageBuildOptions;
import net.masterthought.dlanguage.jps.model.JpsDLanguageBuildOptionsSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name = JpsDLanguageBuildOptionsSerializer.DLANGUAGE_BUILD_OPTIONS_COMPONENT_NAME,
        storages = {
                @Storage(file = StoragePathMacros.PROJECT_FILE),
                @Storage(file = StoragePathMacros.PROJECT_CONFIG_DIR + "/compiler.xml",
                        scheme = StorageScheme.DIRECTORY_BASED)
        }
)
public class DLanguageBuildSettings implements PersistentStateComponent<DLanguageBuildOptions> {
    private DLanguageBuildOptions myBuildOptions = new DLanguageBuildOptions();

    @Nullable
    @Override
    public DLanguageBuildOptions getState() {
        return myBuildOptions;
    }

    @Override
    public void loadState(DLanguageBuildOptions state) {
        myBuildOptions = state;
    }

    @NotNull
    public String getDmdPath() {
        return myBuildOptions.myDmdPath;
    }

    @NotNull
    public String getrDmdPath() {
        return myBuildOptions.myrDmdPath;
    }

    public void setDmdPath(@NotNull String path) {
        myBuildOptions.myDmdPath = path;
    }

    public void setrDmdPath(@NotNull String path) {
        myBuildOptions.myrDmdPath = path;
    }

    public boolean isDubEnabled() {
        return myBuildOptions.myUseDub;
    }

    public void setUseDub(boolean useDub) {
        myBuildOptions.myUseDub = useDub;
    }

    public boolean isInstallDubDependenciesEnabled() {
        return myBuildOptions.myInstallDubDependencies;
    }

    public void setInstallDubDependencies(boolean install) {
        myBuildOptions.myInstallDubDependencies = install;
    }

    @NotNull
    public String getDubPath() {
        return myBuildOptions.myDubPath;
    }

    public void setDubPath(@NotNull String path) {
        myBuildOptions.myDubPath = path;
    }

    @NotNull
    public String getDubFlags() {
        return myBuildOptions.myDubFlags;
    }

    public void setDubFlags(@NotNull String flags) {
        myBuildOptions.myDubFlags = flags;
    }

    @NotNull
    public static DLanguageBuildSettings getInstance(@NotNull Project project) {
        final DLanguageBuildSettings persisted = ServiceManager.getService(project, DLanguageBuildSettings.class);
        return persisted != null ? persisted : new DLanguageBuildSettings();
    }
}