package ch.bzz.videothek.model;

/**
 * The user for the authentication
 */
public class User {
    private String userUUID;
    private String username;
    private String password;
    private String role;

    public User(){
        setRole("guest");
    }

    /**
     * gets userUUID
     *
     * @return value of userUUID
     */

    public String getUserUUID() {
        return userUUID;
    }

    /**
     * sets userUUID
     *
     * @param userUUID the value to set
     */

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    /**
     * gets username
     *
     * @return value of username
     */

    public String getUsername() {
        return username;
    }

    /**
     * sets username
     *
     * @param username the value to set
     */

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * gets password
     *
     * @return value of password
     */

    public String getPassword() {
        return password;
    }

    /**
     * sets password
     *
     * @param password the value to set
     */

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * gets role
     *
     * @return value of role
     */

    public String getRole() {
        return role;
    }

    /**
     * sets role
     *
     * @param role the value to set
     */

    public void setRole(String role) {
        this.role = role;
    }
}
