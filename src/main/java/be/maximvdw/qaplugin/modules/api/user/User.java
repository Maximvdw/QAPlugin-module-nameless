package be.maximvdw.qaplugin.modules.api.user;

import org.json.simple.JSONObject;

/**
 * User
 *
 * @author Maxim Van de Wynckel
 * @version 1.0
 */
public class User {
    private String username = "";
    private String displayName = "";
    private String uuid = "";
    private int groupId = 0;
    private long registered = 0L;
    private boolean banned = false;
    private boolean validated = false;
    private int reputation = 0;

    protected User(){

    }

    protected User(JSONObject object){
        setUsername((String) object.get("username"));
        setDisplayName((String) object.get("displayname"));
        setUUID((String) object.get("uuid"));
        setReputation(Integer.parseInt((String) object.get("reputation")));
        setRegistered(Long.parseLong((String) object.get("registered")));
        setGroupId(Integer.parseInt((String) object.get("group_id")));
        setBanned(Boolean.valueOf((String) object.get("banned")));
        setValidated(Boolean.valueOf((String) object.get("validated")));
    }

    /**
     * Get username
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username
     *
     * @param username username
     */
    private void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the display name
     *
     * @return display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Set the display name
     *
     * @param displayName display name
     */
    private void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Get UUID
     *
     * @return UUID
     */
    public String getUUID() {
        return uuid;
    }

    /**
     * Set UUID
     *
     * @param uuid UUID
     */
    private void setUUID(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Get the group id
     *
     * @return group id
     */
    public int getGroupId() {
        return groupId;
    }

    /**
     * Set the group id
     *
     * @param groupId group id
     */
    private void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    /**
     * Get when registered
     *
     * @return registered unix timestamp
     */
    public long getRegistered() {
        return registered;
    }

    /**
     * Set when registered
     *
     * @param registered registered unix timestamp
     */
    private void setRegistered(long registered) {
        this.registered = registered;
    }

    /**
     * Check if banned
     *
     * @return banned
     */
    public boolean isBanned() {
        return banned;
    }

    /**
     * Set if banned
     *
     * @param banned banned
     */
    private void setBanned(boolean banned) {
        this.banned = banned;
    }

    /**
     * Check if validated
     *
     * @return validated
     */
    public boolean isValidated() {
        return validated;
    }

    /**
     * Set if validated
     *
     * @param validated validated
     */
    private void setValidated(boolean validated) {
        this.validated = validated;
    }

    /**
     * Get reputation
     *
     * @return reputation
     */
    public int getReputation() {
        return reputation;
    }

    /**
     * Set reputation
     *
     * @param reputation reputation
     */
    private void setReputation(int reputation) {
        this.reputation = reputation;
    }
}
