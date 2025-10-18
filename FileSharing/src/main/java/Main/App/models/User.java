/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main.App.models;

public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private int departmentId;
    private String role; // admin, user, manager...

    public User() {}

    public User(int id, String username, String email, String password, int departmentId) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.departmentId = departmentId;
    }

    public User(String username, String email, String password, int departmentId, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.departmentId = departmentId;
        this.role = role;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", departmentId=" + departmentId +
                ", role='" + role + '\'' +
                '}';
    }
}
