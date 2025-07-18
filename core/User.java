package core;

import interfaces.Dashboard;

public abstract class User implements Dashboard{
    private String id;
    private String password;

    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public abstract boolean displayDashboard();
    
    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
    
}