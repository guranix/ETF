package dbService.DataSets;

import service.MD5;


/**
 * Created by guran on 2/8/17.
 */
public class User {
    private long id;
    private String username;
    private String password;
    private String name;
    private String email;


    public User(long id, String username, String password, String name, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public User(String username, String password, String name, String email) {
        this(-1, username,password,name, email);
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


    public boolean chackPassword (String password) {
        return this.password.equals(MD5.encode(password));
    }


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
