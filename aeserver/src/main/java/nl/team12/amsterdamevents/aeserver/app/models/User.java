package nl.team12.amsterdamevents.aeserver.app.models;

import javax.persistence.*;

@Entity
public class User implements Identifiable {
    @Id
    @GeneratedValue
    public long id;
    public String name;
    public String eMail;
    private String hashedPassWord;
    public boolean admin;

    public User(long id, String name, String eMail, String hashedPassWord, boolean admin) {
        this.id = id;
        this.name = name;
        this.eMail = eMail;
        this.hashedPassWord = hashedPassWord;
        this.admin = admin;
    }

    public User(long id, String name, String eMail, boolean admin) {
        this.id = id;
        this.name = name;
        this.eMail = eMail;
        this.admin = admin;
    }

    public User() {
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String geteMail() {
        return eMail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getHashedPassWord() {
        return hashedPassWord;
    }

    public void setHashedPassWord(String hashedPassWord) {
        this.hashedPassWord = hashedPassWord;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean validateEncodedPassword(String hashedPassWord) {
        return this.name.equals(hashedPassWord);
    }
}
