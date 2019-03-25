package beans;

public class Message {

    private int m_id;
    private String m_user;
    private String m_event;
    private String m_message;

    public Message() {
        super();
    }

    public Message(int m_id, String m_user, String m_event, String m_message) {
        this.m_id = m_id;
        this.m_user = m_user;
        this.m_event = m_event;
        this.m_message = m_message;
    }

    public int getM_id() {
        return m_id;
    }

    public void setM_id(int m_id) {
        this.m_id = m_id;
    }

    public String getM_user() {
        return m_user;
    }

    public void setM_user(String m_user) {
        this.m_user = m_user;
    }

    public String getM_event() {
        return m_event;
    }

    public void setM_event(String m_event) {
        this.m_event = m_event;
    }

    public String getM_message() {
        return m_message;
    }

    public void setM_message(String m_message) {
        this.m_message = m_message;
    }
}