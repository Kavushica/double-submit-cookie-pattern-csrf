package com.sliit.ssd.csrfapp.models;

import java.util.HashMap;

/**
 * Stores CSRF tokens of each sessions
 *
 * Created by rkavushica on 9/6/18.
 */
public class Session {

    private HashMap<String, String> sessions;
    private static volatile Session sessionStore;

    private Session(){
        sessions = new HashMap<>();
    }

    public static Session getUserCredentialsStore(){
        if (sessionStore == null){
            synchronized (Session.class){
                if (sessionStore == null) {
                    sessionStore = new Session();
                }
            }

        }
        return sessionStore;
    }

    public void addSessionToken(String session, String token){

        if(sessions.get(session) != null){
            sessions.replace(session, token);
        } else {
            sessions.put(session, token);
        }
    }

    public String getTokenFromSession(String sessionId){
        return sessions.get(sessionId);
    }

}
