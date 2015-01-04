package net.masterthought.dlanguage.jps.model;

import com.intellij.util.xmlb.annotations.Tag;

/**
 * Serialization object for communicating build settings with the build server.
 */
public class DLanguageBuildOptions {
    public static final String DEFAULT_DMD_PATH = "dmd";
    public static final String DEFAULT_RDMD_PATH = "rdmd";
    public static final String DEFAULT_DUB_PATH = "dub";
    public static final boolean DEFAULT_USE_DUB = true;
    public static final String DEFAULT_DUB_FLAGS = "";
    public static final boolean DEFAULT_INSTALL_DUB_DEPENDENCIES = false;

    public DLanguageBuildOptions() {
    }

    public DLanguageBuildOptions(DLanguageBuildOptions options) {
        myUseDub = options.myUseDub;
        myDubPath = options.myDubPath;
        myDubFlags = options.myDubFlags;
        myInstallDubDependencies = options.myInstallDubDependencies;
        myDmdPath = options.myDmdPath;
        myrDmdPath = options.myrDmdPath;
    }

    @Tag("dmdPath")
    public String myDmdPath = DEFAULT_DMD_PATH;

    @Tag("rdmdPath")
    public String myrDmdPath = DEFAULT_RDMD_PATH;

    @Tag("dubPath")
    public String myDubPath = DEFAULT_DUB_PATH;

    @Tag("dubFlags")
    public String myDubFlags = DEFAULT_DUB_FLAGS;

    @Tag("useDub")
    public boolean myUseDub = DEFAULT_USE_DUB;

    @Tag("installDependencies")
    public boolean myInstallDubDependencies = DEFAULT_INSTALL_DUB_DEPENDENCIES;


    @Override
    public String toString() {
//        return "DLanguageBuildOptions{" +
//                "myDmdPath=" + myDmdPath +
//                ", myrDmdPath=" + myrDmdPath +
//                '}';

        return "DLanguageBuildOptions{" +
                "myUseDub=" + myUseDub +
                ", myDubPath=" + myDubPath +
                ", myDubFlags=" + myDubFlags +
//                ", myDubFiles=" + myDubFiles +
                ", myInstallDubDependencies=" + myInstallDubDependencies +
                '}';
    }
}

