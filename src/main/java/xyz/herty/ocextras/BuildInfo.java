package xyz.herty.ocextras;

/**
 * This class provides meta-info about the mod
 * It will use the following variables:
 * - ##MINOR##
 *     The minor version number
 * - ##BUILD##
 *     The build number from the CI system
 */

public class BuildInfo {
    public static final String modName = "OCExtras";
    public static final String modID = "ocextras";

    public static final int majorNumber = 0;
    public static final String minorNumber = "##MINOR##";
    public static final String buildNumber = "##BUILD##";

    public static int getBuildNumber() {
        if (buildNumber.equals("##BUILD##"))
            return 0;
        return Integer.parseInt(buildNumber);
    }

    public static int getMinorNumber() {
        if (minorNumber.equals("##MINOR##"))
            return 0;
        return Integer.parseInt(minorNumber);
    }

    public static String getFullVersion() {
        return majorNumber + "." + getMinorNumber() + "." + getBuildNumber();
    }

}
