package com.varabei.ivan.model.entity;

import java.time.LocalDate;

public class User extends Identifiable {
    private String roleName;
    private String login;
    private String password;
    private String salt;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birth;

    public User() {
    }

    public User(String login, String password, String firstName, String lastName, String email, LocalDate birth) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birth = birth;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        User user = (User) o;
        if (roleName != null ? !roleName.equals(user.roleName) : user.roleName != null){
            return false;
        }
        if (login != null ? !login.equals(user.login) : user.login != null){
            return false;
        }
        if (password != null ? !password.equals(user.password) : user.password != null){
            return false;
        }
        if (salt != null ? !salt.equals(user.salt) : user.salt != null){
            return false;
        }
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null){
            return false;
        }
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null){
            return false;
        }
        if (email != null ? !email.equals(user.email) : user.email != null){
            return false;
        }
        return birth != null ? birth.equals(user.birth) : user.birth == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (roleName != null ? roleName.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (salt != null ? salt.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (birth != null ? birth.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("roleName='").append(roleName).append('\'');
        sb.append(", login='").append(login).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", salt='").append(salt).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", birth=").append(birth);
        sb.append('}');
        return sb.toString();
    }
}
