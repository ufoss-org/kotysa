/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test;

import java.util.Objects;

/**
 * Basic Entity
 */
public class JavaUser {

    private String login;

    private String firstname;

    private String lastname;

    private boolean isAdmin;

    private String alias1;

    private String alias2;

    private String alias3;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @javax.annotation.Nullable
    public String getAlias1() {
        return alias1;
    }

    public void setAlias1(String alias1) {
        this.alias1 = alias1;
    }

    @org.jetbrains.annotations.Nullable
    public String getAlias2() {
        return alias2;
    }

    public void setAlias2(String alias2) {
        this.alias2 = alias2;
    }

    @org.springframework.lang.Nullable
    public String getAlias3() {
        return alias3;
    }

    public void setAlias3(String alias3) {
        this.alias3 = alias3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JavaUser javaUser = (JavaUser) o;

        if (isAdmin != javaUser.isAdmin) {
            return false;
        }
        if (!Objects.equals(login, javaUser.login)) {
            return false;
        }
        if (!Objects.equals(firstname, javaUser.firstname)) {
            return false;
        }
        if (!Objects.equals(lastname, javaUser.lastname)) {
            return false;
        }
        if (!Objects.equals(alias1, javaUser.alias1)) {
            return false;
        }
        if (!Objects.equals(alias2, javaUser.alias2)) {
            return false;
        }
        return Objects.equals(alias3, javaUser.alias3);

    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (isAdmin ? 1 : 0);
        result = 31 * result + (alias1 != null ? alias1.hashCode() : 0);
        result = 31 * result + (alias2 != null ? alias2.hashCode() : 0);
        result = 31 * result + (alias3 != null ? alias3.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JavaUser{" +
                "id='" + login + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", isAdmin=" + isAdmin +
                ", alias1='" + alias1 + '\'' +
                ", alias2='" + alias2 + '\'' +
                ", alias3='" + alias3 + '\'' +
                '}';
    }
}
