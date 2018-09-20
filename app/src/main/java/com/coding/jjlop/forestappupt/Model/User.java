package com.coding.jjlop.forestappupt.Model;

public class User {
    private String id_u;
    private String nick;
    private String email;
    private String degree;
    private String quarter;
    private String t_points;
    private String expassw;

    public User(String id_u, String nick, String email, String degree, String quarter, String t_points, String expassw) {
        this.id_u = id_u;
        this.nick = nick;
        this.email = email;
        this.degree = degree;
        this.quarter = quarter;
        this.t_points = t_points;
        this.expassw=expassw;
    }

    public String getExpassw() {
        return expassw;
    }

    public void setExpassw(String expassw) {
        this.expassw = expassw;
    }

    public String getId_u() {
        return id_u;
    }

    public void setId_u(String id_u) {
        this.id_u = id_u;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public String getT_points() {
        return t_points;
    }

    public void setT_points(String t_points) {
        this.t_points = t_points;
    }
}
