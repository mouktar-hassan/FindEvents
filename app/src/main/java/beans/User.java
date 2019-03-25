package beans;

public class User {

    private int u_id;
    private String u_pseudo;
    private String u_datecreated;

    public User(){
        super();
    }

    public User(int u_id, String u_pseudo, String u_datecreated) {
        this.u_id = u_id;
        this.u_pseudo = u_pseudo;
        this.u_datecreated= u_datecreated;
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

    public String getU_datecreated() {
        return u_datecreated;
    }

    public void setU_datecreated(String u_datecreated) {
        this.u_datecreated = u_datecreated;
    }
}
