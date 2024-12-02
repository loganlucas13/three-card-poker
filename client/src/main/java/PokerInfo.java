public class PokerInfo {
    // data members
    private String ip;
    private int port;

    // constructor
    public PokerInfo() {
        this.ip = "undefined";
        this.port = -1;
    }

    // getters and setters

    // ip
    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    // port
    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
