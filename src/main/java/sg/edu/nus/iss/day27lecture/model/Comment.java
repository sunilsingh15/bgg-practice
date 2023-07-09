package sg.edu.nus.iss.day27lecture.model;

import java.util.UUID;

public class Comment {

    private String c_id;
    private String user;
    private Integer rating;
    private String c_text;
    private Integer gid;

    public Comment() {
    }

    public Comment(Integer gid) {
        this.gid = gid;
    }

    public Comment(String c_id, String user, Integer rating, String c_text, Integer gid) {
        this.c_id = UUID.randomUUID().toString().substring(0,8);
        this.user = user;
        this.rating = rating;
        this.c_text = c_text;
        this.gid = gid;
    }

    public String getId() {
        return c_id;
    }

    public void setId(String id) {
        this.c_id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getC_text() {
        return c_text;
    }

    public void setC_text(String c_text) {
        this.c_text = c_text;
    }

    public Integer getgID() {
        return gid;
    }

    public void setgID(Integer gID) {
        this.gid = gID;
    }

    @Override
    public String toString() {
        return "Comment [c_id=" + c_id + ", user=" + user + ", rating=" + rating + ", c_text=" + c_text + ", gid=" + gid
                + "]";
    }

}
