package com.example.shoprime;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static UserManager instance;
    private Map<String, User> users;
    private User currentUser;
    private static final String PREF_NAME = "ShoprimePrefs";
    private static final String KEY_USERS = "users";
    private SharedPreferences sharedPreferences;
    private Gson gson;

    private UserManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
        loadUsers();
    }

    public static synchronized UserManager getInstance(Context context) {
        if (instance == null) {
            instance = new UserManager(context.getApplicationContext());
        }
        return instance;
    }

    private void loadUsers() {
        String json = sharedPreferences.getString(KEY_USERS, null);
        if (json != null) {
            Type type = new TypeToken<HashMap<String, User>>() {}.getType();
            users = gson.fromJson(json, type);
        } else {
            users = new HashMap<>();
            registerUser("Comprador Test", "comprador@test.com", "123", "BUYER");
            registerUser("Vendedor Test", "vendedor@test.com", "123", "SELLER");
        }
    }

    public void saveUsers() {
        String json = gson.toJson(users);
        sharedPreferences.edit().putString(KEY_USERS, json).apply();
    }

    public void registerUser(String name, String email, String password, String role) {
        User newUser = new User(name, email.toLowerCase().trim(), password, role);
        users.put(email.toLowerCase().trim(), newUser);
        saveUsers();
    }

    public User login(String email, String password) {
        if (email == null) return null;
        User user = users.get(email.toLowerCase().trim());
        if (user != null && user.getPassword().equals(password)) {
            this.currentUser = user;
            return user;
        }
        return null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void updateCurrentUser(User user) {
        this.currentUser = user;
        users.put(user.getEmail().toLowerCase().trim(), user);
        saveUsers();
    }

    public static class User {
        private String name;
        private String email;
        private String password;
        private String role;
        private String profileImageUri;

        public User(String name, String email, String password, String role) {
            this.name = name;
            this.email = email;
            this.password = password;
            this.role = role;
        }

        public String getRole() { return role; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public String getPassword() { return password; }
        public String getProfileImageUri() { return profileImageUri; }
        public void setProfileImageUri(String profileImageUri) { this.profileImageUri = profileImageUri; }
    }
}