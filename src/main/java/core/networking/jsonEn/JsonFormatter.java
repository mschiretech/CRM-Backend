package core.networking.jsonEn;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class JsonFormatter {

    // Method to format a single user to JSON
    public static JSONObject formatUser(Map<String, Object> userData) {
        JSONObject user = new JSONObject();
        user.put("user_id", userData.get("user_id"));
        user.put("first_name", userData.get("first_name"));
        user.put("last_name", userData.get("last_name"));
        user.put("email", userData.get("email"));
        user.put("phone", userData.get("phone"));
        user.put("address", userData.get("address"));
        user.put("company", userData.get("company"));
        user.put("position", userData.get("position"));
        user.put("created_at", userData.get("created_at"));
        user.put("notes", userData.get("notes"));
        return user;
    }

    // Method to format a list of users to JSON
    public static JSONObject formatUsers(List<Map<String, Object>> usersList) {
        JSONArray usersArray = new JSONArray();
        for (Map<String, Object> userData : usersList) {
            usersArray.put(formatUser(userData));
        }
        JSONObject result = new JSONObject();
        result.put("users", usersArray);
        return result;
    }

    // Method to update a user (returns only the updatable fields)
    public static JSONObject updateUserFormat(int userId, Map<String, Object> updatedFields) {
        JSONObject updateObject = new JSONObject(updatedFields);
        updateObject.put("user_id", userId);
        return updateObject;
    }

    // Decode JSON to raw user data
    public static List<Map<String, Object>> decodeUsers(String jsonData) {
        List<Map<String, Object>> usersList = new ArrayList<>();
        JSONObject root = new JSONObject(jsonData);
        JSONArray usersArray = root.getJSONArray("users");

        for (int i = 0; i < usersArray.length(); i++) {
            JSONObject userJson = usersArray.getJSONObject(i);
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("user_id", userJson.getInt("user_id"));
            userMap.put("first_name", userJson.getString("first_name"));
            userMap.put("last_name", userJson.getString("last_name"));
            userMap.put("email", userJson.getString("email"));
            userMap.put("phone", userJson.getString("phone"));
            userMap.put("address", userJson.getString("address"));
            userMap.put("company", userJson.getString("company"));
            userMap.put("position", userJson.getString("position"));
            userMap.put("created_at", userJson.getString("created_at"));
            userMap.put("notes", userJson.getString("notes"));
            usersList.add(userMap);
        }

        return usersList;
    }
}
