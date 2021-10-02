package upce.nnpda.sema.DTO;

public class ResetPasswordTokenDTO {
    private String token;
    private String pass;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
