package com2008.model;

import java.util.List;

public class CurrentUser {
    private String userId;
    private String email;
    private String username;
    private Role role;
    private static CurrentUser currentUser;

    /**
     * Constructor for creating a User object.
     *
     * @param userId   The unique identifier for the user.
     * @param email    The email address of the user.
     * @param username The username chosen by the user.
     * @param roles     The role assigned to the user.
     */
    public CurrentUser(String userId, String email, String username, Role role) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.role = role;
    }

    /**
     * Gets the user's unique identifier.
     *
     * @return The user's unique identifier.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user's unique identifier.
     *
     * @param userId The user's unique identifier.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the user's email address.
     *
     * @return The user's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     *
     * @param email The user's email address.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's chosen username.
     *
     * @return The user's chosen username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user's chosen username.
     *
     * @param username The user's chosen username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the list of roles associated with the user.
     *
     * @return The list of roles.
     */
    public Role getRoles() {
        return role;
    }

    /**
     * Sets the list of roles associated with the user.
     *
     * @param roles The list of roles to be set.
     */
    public void setRoles(Role role) {
        this.role = role;
    }

    /**
     * Gets the current user of the application.
     *
     * @return The current user.
     */
    public static CurrentUser getCurrentUser() {
        return currentUser;
    }
    
    /**
     * Sets the current user of the application.
     *
     * @param user The user to set as the current user.
     */
    public static void setCurrentUser(CurrentUser user) {
        currentUser = user;
    }

    /**
     * Overrides the toString method to provide a string representation of the User object.
     *
     * @return A string representation of the User object.
     */
    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", role='" + role.toString() + '\'' +
                '}';
    }
}
