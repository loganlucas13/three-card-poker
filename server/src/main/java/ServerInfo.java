public class ServerInfo {

    // purpose of this class is to hold the data needed for server operations
    // to be used across multiple files
    // examples: starting and closing server

    // data members
    private static String ip;
    private static int port;
    private static boolean isRunning;

    // getters and setters

    // ip
    public static String getIp() {
        return ServerInfo.ip;
    }
    public static void setIp(String ip) {
        ServerInfo.ip = ip;
    }

    // port
    public static int getPort() {
        return ServerInfo.port;
    }
    public static void setPort(int port) {
        ServerInfo.port = port;
    }

    // isRunning
    public static boolean getIsRunning() {
        return ServerInfo.isRunning;
    }
    public static void setIsRunning(boolean isRunning) {
        ServerInfo.isRunning = isRunning;
    }
}
