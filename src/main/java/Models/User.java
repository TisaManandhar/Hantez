package Models;

public class User {
    private int userId;
    private String name;
    private String email;
    private String password;
    private String profilePicture;
    private String bio;
    private String role;
    private java.sql.Timestamp joinDate;

    public User() {}

    public User(int userId, String name, String email, String password, String profilePicture, String bio, String role, java.sql.Timestamp joinDate) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.profilePicture = profilePicture;
        this.bio = bio;
        this.role = role;
        this.joinDate = joinDate;
    }

    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getProfilePicture() { return profilePicture; }
    public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public java.sql.Timestamp getJoinDate() { return joinDate; }
    public void setJoinDate(java.sql.Timestamp joinDate) { this.joinDate = joinDate; }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", name=" + name + ", email=" + email + ", role=" + role + "]";
    }
}
