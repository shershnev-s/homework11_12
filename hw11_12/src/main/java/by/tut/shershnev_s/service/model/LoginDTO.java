package by.tut.shershnev_s.service.model;

public class LoginDTO {
    private String logn;
    private String password;

    public String getLogin() {
        return logn;
    }

    public void setLogin(String login) {
        this.logn = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
