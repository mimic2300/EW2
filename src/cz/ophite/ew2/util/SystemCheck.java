package cz.ophite.ew2.util;

public final class SystemCheck
{
    private static final String OS = System.getProperty("os.name").toLowerCase();

    private SystemCheck()
    {}

    public static boolean isWindows()
    {
        return (OS.indexOf("win") >= 0);
    }

    public static boolean isMac()
    {
        return (OS.indexOf("mac") >= 0);
    }

    public static boolean isUnix()
    {
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
    }

    public static boolean isSolaris()
    {
        return (OS.indexOf("sunos") >= 0);
    }
}
