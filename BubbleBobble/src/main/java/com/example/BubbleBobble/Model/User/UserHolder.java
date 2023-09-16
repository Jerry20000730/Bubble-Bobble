package com.example.BubbleBobble.Model.User;

/**
 * UserHolder class is a class using singleton design pattern to pass the User Setting Class value across the controller.
 * The singleton design pattern makes sure that when passing the data, the instance containing that data is always the same one
 * by invoking the UserHolder.getInstance().
 */
public class UserHolder {
    private UserSetting userSetting;
    private final static UserHolder UserHolderInstance = new UserHolder();

    /**
     * Constructor for UserHolder
     */
    private UserHolder() {}

    /**
     * Method for getting the only instance of UserHolder class
     * @return the only instance of UserHolder class
     */
    public static UserHolder getInstance() {
        return UserHolderInstance;
    }

    /**
     * Setter Method for setting the user setting parameter inside the UserHolder class
     * @param uss the user setting of UserSetting class
     */
    public void setUserSetting(UserSetting uss) {
        this.userSetting = uss;
    }

    /**
     * Getter Method for getting the user setting parameter inside the UserHolder class
     * @return the user setting of UserSetting class
     */
    public UserSetting getUserSetting() {
        return this.userSetting;
    }
}
