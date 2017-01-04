package be.maximvdw.qaplugin.modules.api.user;

import be.maximvdw.qaplugin.modules.api.NamelessAPI;
import be.maximvdw.qaplugin.modules.api.http.HttpMethod;
import be.maximvdw.qaplugin.modules.api.http.HttpRequest;
import be.maximvdw.qaplugin.modules.api.http.HttpResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * UserManager
 *
 * @author Maxim Van de Wynckel
 * @version 1.0
 */
public class UserManager {
    private NamelessAPI api = null;

    public UserManager(NamelessAPI api) {
        this.api = api;
    }

    /**
     * Get user by username
     * @param username username
     * @return User if found
     */
    public User getUserByUsername(String username) {
        String url = api.getAPIURL() + "get";
        HttpResponse response = null;
        try {
            response = new HttpRequest(url)
                    .method(HttpMethod.POST)
                    .post("username", username)
                    .execute();
            JSONParser parser = new JSONParser();
            JSONObject jsonResponse = (JSONObject) parser.parse(response.getSource());
            if (jsonResponse.containsKey("error")) {
                if (((Boolean) jsonResponse.get("error"))) {
                    return null;
                }
            }
            if (jsonResponse.containsKey("success")) {
                if (((Boolean) jsonResponse.get("success"))) {
                    JSONObject userJson = (JSONObject) parser.parse((String) jsonResponse.get("message"));
                    return new User(userJson);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get user by username
     * @param uuid Minecraft UUID
     * @return User if found
     */
    public User getUserByUUID(String uuid) {
        String url = api.getAPIURL() + "get";
        HttpResponse response = null;
        try {
            response = new HttpRequest(url)
                    .method(HttpMethod.POST)
                    .post("uuid", uuid)
                    .execute();
            JSONParser parser = new JSONParser();
            JSONObject jsonResponse = (JSONObject) parser.parse(response.getSource());
            if (jsonResponse.containsKey("error")) {
                if (((Boolean) jsonResponse.get("error"))) {
                    return null;
                }
            }
            if (jsonResponse.containsKey("success")) {
                if (((Boolean) jsonResponse.get("success"))) {
                    JSONObject userJson = (JSONObject) parser.parse((String) jsonResponse.get("message"));
                    return new User(userJson);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Register to the site using the username and UUID
     *
     * @param username Username to register with
     * @param uuid     UUID of the minecraft user
     * @param email    Email to register with
     * @return success
     */
    public boolean register(String username, String uuid, String email) {
        String url = api.getAPIURL() + "register";
        HttpResponse response = null;
        try {
            response = new HttpRequest(url)
                    .method(HttpMethod.POST)
                    .post("username", username)
                    .post("uuid", uuid)
                    .post("email", email)
                    .execute();
            JSONParser parser = new JSONParser();
            JSONObject jsonResponse = (JSONObject) parser.parse(response.getSource());
            if (jsonResponse.containsKey("error")) {
                if (((Boolean) jsonResponse.get("error"))) {
                    return false;
                }
            }
            if (jsonResponse.containsKey("success")) {
                if (((Boolean) jsonResponse.get("success"))) {
                    return true;
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Set user group
     * @param user user to set group
     * @param groupId Group Identifier
     * @return success
     */
    public boolean setGroup(User user, int groupId){
        if (user == null){
            return false;
        }
        String url = api.getAPIURL() + "setGroup";
        HttpResponse response = null;
        try {
            response = new HttpRequest(url)
                    .method(HttpMethod.POST)
                    .post("username", user.getUsername())
                    .post("uuid", user.getUUID())
                    .post("group_id", String.valueOf(groupId))
                    .execute();
            JSONParser parser = new JSONParser();
            JSONObject jsonResponse = (JSONObject) parser.parse(response.getSource());
            if (jsonResponse.containsKey("error")) {
                if (((Boolean) jsonResponse.get("error"))) {
                    return false;
                }
            }
            if (jsonResponse.containsKey("success")) {
                if (((Boolean) jsonResponse.get("success"))) {
                    return true;
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
