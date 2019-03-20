package beans;

public class User {

    private int u_id;
    private String u_pseudo;

    public User(){
        super();
    }

    public User(int u_id, String u_pseudo) {
        this.u_id = u_id;
        this.u_pseudo = u_pseudo;
    }

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public String getU_pseudo() {
        return u_pseudo;
    }

    public void setU_pseudo(String u_pseudo) {
        this.u_pseudo = u_pseudo;
    }
}
