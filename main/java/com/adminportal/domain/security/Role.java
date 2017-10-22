package com.adminportal.domain.security;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Role {

    @Id
    private int roleId;
    private String name;

    /*java collections and set cannot have duplicate values with no order as opposed to a list that has order*/
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY) /*1 role to many users*/
    private Set<UserRole> userRoles = new HashSet<>(); /*map to a set of user roles*/

    /*generate getters and setters*/
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
