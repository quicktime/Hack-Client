package at.tiam.bolt.util;

/**
 * Created by quicktime on 5/24/17.
 */
public class ClientSession {

    private String username, sessionID;
    private boolean isPremium;

    public ClientSession(String username, String sessionID, boolean isPremium) {
        super();
        this.username = username;
        this.sessionID = sessionID;
        this.isPremium = isPremium;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getSessionID() { return sessionID; }
    public void setSessionID(String sessionID) { this.sessionID = sessionID; }
    public boolean isPremium() { return isPremium; }
}
