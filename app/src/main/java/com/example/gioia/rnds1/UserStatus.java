package com.example.gioia.rnds1;

public class UserStatus {
    private String userName;
    private String currentStatus;
    private String availableStates;
    private int noiseLevel;
    private boolean isDisturbing;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getAvailableStates() {
        return availableStates;
    }

    public void setAvailableStates(String availableStates) {
        this.availableStates = availableStates;
    }

    public int getNoiseLevel() {
        return noiseLevel;
    }

    public void setNoiseLevel(int noiseLevel) {
        this.noiseLevel = noiseLevel;
    }

    public boolean isDisturbing() {
        return isDisturbing;
    }

    public void setDisturbing(boolean disturbing) {
        isDisturbing = disturbing;
    }
}
