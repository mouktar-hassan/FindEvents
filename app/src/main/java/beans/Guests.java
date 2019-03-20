package beans;

public class Guests {

    private int g_id;
    private int g_user;
    private int g_event;
    private String g_created_at;
    private String g_updated_at;

    public Guests() {
        super();
    }

    public Guests(int g_id, int g_user, int g_event) {
        super();
        this.g_id = g_id;
        this.g_user = g_user;
        this.g_event = g_event;
    }

    public int getG_id() {
        return g_id;
    }

    public void setG_id(int g_id) {
        this.g_id = g_id;
    }

    public int getG_user() {
        return g_user;
    }

    public void setG_user(int g_user) {
        this.g_user = g_user;
    }

    public int getG_event() {
        return g_event;
    }

    public void setG_event(int g_event) {
        this.g_event = g_event;
    }
}
