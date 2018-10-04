package com.sliit.ssd.csrfapp.models;

import java.util.HashMap;

/**
 * Stores user data such as sessionIDs, CSRF Tokens, username, password against username
 *
 * Created by rkavushica on 9/5/18.
 */
public class CredentialsStore {

    private HashMap<String, Credentials> credentialsStore;
    private static volatile CredentialsStore userCredentialsStore;

    private CredentialsStore(){
        credentialsStore = new HashMap<>();
    }

    public static CredentialsStore getUserCredentialsStore(){
        if (userCredentialsStore == null){
            synchronized (CredentialsStore.class){
                if (userCredentialsStore == null) {
                    userCredentialsStore = new CredentialsStore();
                }
            }

        }
        return userCredentialsStore;
    }

    public void addInitialData(){
        // Create initial user in store
        Credentials credentials = new Credentials();

        String username = "admin";
        String password = "admin";
        credentials.setUsername(username);
        credentials.setPassword(password);
        CredentialsStore.getUserCredentialsStore().saveCredentials(username, credentials);
    }

    public Credentials findCredentials(String username){
        return credentialsStore.get(username);
    }

    public void saveCredentials(String username, Credentials credentials){
        if (credentials!= null && credentialsStore.get(username) != null){
            credentialsStore.replace(username, credentials);
        } else {
            credentialsStore.put(username, credentials);
        }
    }

    @Override
    public String toString() {
        return "UserCredentialsStore{" +
                "credentialsStore=" + credentialsStore +
                '}';
    }

}
