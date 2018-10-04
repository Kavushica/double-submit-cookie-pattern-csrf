package com.sliit.ssd.csrfapp.services;

import com.sliit.ssd.csrfapp.models.Credentials;
import com.sliit.ssd.csrfapp.models.CredentialsStore;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Handles authentication related tasks
 *
 * Created by rkavushica on 9/5/18.
 */

@Service
public class LoginService {

   
    CredentialsStore userCredentialsStore = CredentialsStore.getUserCredentialsStore();

    /**
     * Authenticates user using username and password
     *
     * @param username
     * @param password
     * @return
     */
    public boolean isUserAuthenticated(String username, String password){

            return (username.equalsIgnoreCase("admin")
                    && password.equalsIgnoreCase("admin"));
    }

    /**
     * Authenticates user's session and csrf token
     *
     * @param cookies
     * @param csrf
     *
     * @return
     */
    public boolean isAuthenticated(Cookie[] cookies, String csrf){
        Map<String, String> cookieStore = getCookies(cookies);
        // Check if the user session is valid and if the csrf token
        if(isUserSessionValid(cookieStore.get("username"), cookieStore.get("sessionID"))
                && validateCSRFToken(cookieStore.get("Csrf-token"), csrf)){
            return true;
        }

        return false;
    }




    /**
     * Get all the cookies from cookiestore
     * @param cookies
     * @return
     */

    public Map<String, String> getCookies(Cookie[] cookies){

        Map<String, String> cookieStore = new HashMap<>();
        if (null != cookies && cookies.length > 0){
            for (Cookie cookie : cookies) {
                cookieStore.put(cookie.getName(), cookie.getValue());
            }
        }
        return cookieStore;
    }


    /**
     * Authenticates user using cookies
     *
     * @param cookies
     * @return
     */
    /**
     * Authenticates users session
     *
     * @param cookies
     * @return
     */
    public boolean isAuthenticated(Cookie[] cookies){
        Map<String, String> cookieStore = getCookies(cookies);

        // Check if the user session is valid and if the csrf token
        if(isUserSessionValid(cookieStore.get("username"), cookieStore.get("sessionID"))){
            return true;
        }

        return false;
    }

    /**
     * Retrieves the session ID from cookies
     *
     * @param cookies
     * @return
     */
    public String sessionIdFromCookies(Cookie[] cookies){
        if (null != cookies && cookies.length > 0){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("sessionID")){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Checks if the sessionID is valid for the user
     *
     * @param username
     * @param sessionId
     * @return
     */
    public boolean isUserSessionValid(String username, String sessionId){
        if (CredentialsStore.getUserCredentialsStore().findCredentials(username) != null){
            return sessionId.equals(userCredentialsStore
                    .findCredentials(username)
                    .getSessionID());
        }
        return false;
    }

    /**
     * Generates a new session ID
     *
     * @param username
     * @return
     */
    public String generateSessionId(String username){

        // Generate new sessionID for user
        String sessionId = UUID.randomUUID().toString();

        // Store sessionID in UserCredentialsStore
        Credentials credentials = userCredentialsStore.findCredentials(username);
        credentials.setSessionID(sessionId);
        userCredentialsStore.saveCredentials(username, credentials);
        return sessionId;
    }

    /**
     * Generates a new anti-CSRF token
     *
     * @param session
     * @return
     */
    public String generateToken(String session){
       
        // Generate new token for user
        return UUID.randomUUID().toString();
    }

    /**
     * Validates if the CSRF token is valid
     *
     * @param sessionID
     * @param token
     * @return
     */
    public boolean validateCSRFToken(String tokenFromCookie, String tokenFromRequestForm){

            return tokenFromCookie.equals(tokenFromRequestForm);

    }

}
