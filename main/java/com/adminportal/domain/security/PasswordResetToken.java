package com.adminportal.domain.security;

import com.adminportal.domain.User;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
public class PasswordResetToken {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    /*bind the token string to the user in a one to one relationship*/
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable=false, name="user_id")
    private User user;

    private Date expiryDate;

    public PasswordResetToken() {};

    public PasswordResetToken(final String token, final User user) {
        super();
        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }
    /*calculate the expiry date*/
    private Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public void updateToken(final String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }
    /*generate getters and setters*/

    public static int getEXPIRATION() {
        return EXPIRATION;
    }

    public java.lang.Long getId() {
        return id;
    }

    public void setId(java.lang.Long id) {
        this.id = id;
    }

    public java.lang.String getToken() {
        return token;
    }

    public void setToken(java.lang.String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
    /*generate toString methods using the fields*/

    @Override
    public java.lang.String toString() {
        return "PasswordResetToken{" +
                "id=" + id +
                ", token=" + token +
                ", user=" + user +
                ", expiryDate=" + expiryDate +
                '}';
    }
}

