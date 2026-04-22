package com.example.shoprime;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatManager {
    private static ChatManager instance;
    private Map<String, List<ChatMessage>> chats; // Key: orderId
    private static final String PREF_NAME = "ShoprimeChatPrefs";
    private static final String KEY_CHATS = "chats";
    private SharedPreferences sharedPreferences;
    private Gson gson;

    private ChatManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
        loadChats();
    }

    public static synchronized ChatManager getInstance(Context context) {
        if (instance == null) {
            instance = new ChatManager(context.getApplicationContext());
        }
        return instance;
    }

    private void loadChats() {
        String json = sharedPreferences.getString(KEY_CHATS, null);
        if (json != null) {
            Type type = new TypeToken<HashMap<String, List<ChatMessage>>>() {}.getType();
            chats = gson.fromJson(json, type);
        } else {
            chats = new HashMap<>();
        }
    }

    private void saveChats() {
        String json = gson.toJson(chats);
        sharedPreferences.edit().putString(KEY_CHATS, json).apply();
    }

    public void sendMessage(String orderId, ChatMessage message) {
        if (!chats.containsKey(orderId)) {
            chats.put(orderId, new ArrayList<>());
        }
        chats.get(orderId).add(message);
        saveChats();
    }

    public List<ChatMessage> getMessages(String orderId) {
        return chats.getOrDefault(orderId, new ArrayList<>());
    }
}