


public class ServerInfo {

    // purpose of this class is to hold the data needed for server operations
    // to be used across multiple files
    // examples: starting and closing server

    // data members
    private static String ip;
    private static int port;
    private static int clientCount;
    private static boolean isRunning;
    private static Server server;
    private static StartController startController;
    private static ServerController serverController;

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

    // clientCount
    public static int getClientCount() {
        return ServerInfo.clientCount;
    }
    public static void setClientCount(int clientCount) {
        ServerInfo.clientCount = clientCount;
    }

    // isRunning
    public static boolean getIsRunning() {
        return ServerInfo.isRunning;
    }
    public static void setIsRunning(boolean isRunning) {
        ServerInfo.isRunning = isRunning;
    }

    // server
    public static Server getServer() {
        return ServerInfo.server;
    }
    public static void setServer(Server server) {
        ServerInfo.server = server;
    }

    // start controller
    public static StartController getStartController() {
        return ServerInfo.startController;
    }
    public static void setStartController(StartController startController) {
        ServerInfo.startController = startController;
    }

    // server controller
    public static ServerController getServerController() {
        return ServerInfo.serverController;
    }
    public static void setServerController(ServerController serverController) {
        ServerInfo.serverController = serverController;
    }
}
