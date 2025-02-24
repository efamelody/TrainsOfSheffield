package com2008;

public class UserSession {
    private static String currentUserId;


    // Setter method to set the current user ID
    public static void setCurrentUserId(String userId) {
        currentUserId = userId;
    }

    // Getter method to retrieve the current user ID
    public static String getCurrentUserId() {
        return currentUserId;
    }

    public static void endSession() {
        currentUserId = String.valueOf(0); // Reset or clear the currentUserId or any other session-related variables
        // Perform any additional session clean-up tasks if needed
    }
}

