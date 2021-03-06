package apl.updater;

public class UpdaterConstants {
   public static final String WINDOWS_UPDATE_SCRIPT_PATH = "update.vbs";
   public static final String LINUX_UPDATE_SCRIPT_PATH = "update.sh";
   public static final String OSX_UPDATE_SCRIPT_PATH = "update.sh";
   public static final String WINDOWS_RUN_TOOL_PATH = "wscript.exe";
   public static final String LINUX_RUN_TOOL_PATH = "";
   public static final String OSX_RUN_TOOL_PATH = "";
   public static final int DOWNLOAD_ATTEMPTS = 10;
   public static final int NEXT_ATTEMPT_TIMEOUT = 60;
   public static final String TEMP_DIR_PREFIX = "Apollo-update";
   public static final String DOWNLOADED_FILE_NAME = "Apollo-newVersion.jar";

//   Certificate constants
   public static final String CERTIFICATE_DIRECTORY = "conf/certs";
   public static final String FIRST_DECRYPTION_CERTIFICATE_PREFIX = "1_";
   public static final String SECOND_DECRYPTION_CERTIFICATE_PREFIX = "2_";
   public static final String CERTIFICATE_SUFFIX = ".cert.pem";
   public static final String INTERMEDIATE_CERTIFICATE_NAME = "intermediate" + CERTIFICATE_SUFFIX;
   public static final String CA_CERTIFICATE_NAME = "ca-chain" + CERTIFICATE_SUFFIX;
   public static final String CA_CERTIFICATE_URL = "https://raw.githubusercontent.com/ApolloFoundation/Apollo/master/conf/certs/" + CA_CERTIFICATE_NAME;

   private UpdaterConstants() {}
}
