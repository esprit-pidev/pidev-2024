package tn.esprit.entities.User;

import java.time.LocalDateTime;

public class PasswordResetRequest {

    private int id, resetCode;
    private LocalDateTime createdAt,expiresAt;
    boolean isValid;
    private User user;

    public PasswordResetRequest(int id, int resetCode, LocalDateTime createdAt, LocalDateTime expiresAt, boolean isValid, User user) {
        this.id = id;
        this.resetCode = resetCode;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.isValid = isValid;
        this.user = user;
    }

    public PasswordResetRequest(int id, int resetCode, LocalDateTime createdAt, LocalDateTime expiresAt, boolean isValid) {
        this.id = id;
        this.resetCode = resetCode;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.isValid = isValid;
    }

    public PasswordResetRequest(int resetCode, LocalDateTime createdAt, LocalDateTime expiresAt, boolean isValid, User user) {
        this.resetCode = resetCode;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.isValid = isValid;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResetCode() {
        return resetCode;
    }

    public void setResetCode(int resetCode) {
        this.resetCode = resetCode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
