package models;

import lombok.*;

//@Data
//@AllArgsConstructor
@NoArgsConstructor
@ToString
//@Builder
public class UserBuilder {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private int userStatus;

    public UserBuilder(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public UserBuilder id(long id) {
        this.id = id;
        return this;
    }

    public UserBuilder username(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder build() {
        return new UserBuilder(id, username);
    }

}
