package com.varabei.ivan.model.entity;

import java.time.LocalDate;

public class User extends Identifiable {
    private Role role;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birth;

    public User() {
    }

    public User(String login, String firstName, String lastName, String email, LocalDate birth) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birth = birth;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
        if (role != null ? !role.equals(user.role) : user.role != null){
            return false;
        }
        if (login != null ? !login.equals(user.login) : user.login != null){
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
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (birth != null ? birth.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("role='").append(role).append('\'');
        sb.append(", login='").append(login).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", birth=").append(birth);
        sb.append('}');
        return sb.toString();
    }
}
