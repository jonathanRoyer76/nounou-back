package com.nounou.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * User
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String userName;
    private String password;
    private String email;
    private boolean isActive = true; // NOPMD by jonathan on 20/07/2019 15:53
    private boolean isAdmin; // NOPMD by jonathan on 20/07/2019 15:53

    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonIgnoreProperties({"users"})
    private Role role;

    public User(final User user) {
        this.setActive(user.isActive());
        this.userName = user.getUserName();
        this.password = user.getPassword();    
        this.email = user.getEmail();
        this.id = user.getId();
        this.role = user.getRole();
    }
    
    /**
     * Default constructor
     */
    public User(){

    }

	/**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(final String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * @return the isActive
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setActive(final boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * @return the isAdmin
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * @param isAdmin the isAdmin to set
     */
    public void setAdmin(final boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(final Role role) {
        this.role = role;
    }

}