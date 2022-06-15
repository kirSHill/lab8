package essentials.elements;

import java.io.Serializable;

public class UserInfo implements Serializable {

    private final String login;
    private final String password;
    private String nickname;
    private int id;

    public UserInfo(String login, String password, String nickname, int id) {
        this.id = id;
        this.login = login;
        this.nickname = nickname;
        this.password = password;
    }

    public UserInfo(String login, String password, String nickname) {
        this.login = login;
        this.password = password;
        this.nickname = nickname;
    }

    public UserInfo(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() { return nickname; }

    public int getId() {
        return id;
    }
}
