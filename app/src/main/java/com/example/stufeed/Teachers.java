package com.example.stufeed;

public class Teachers {

    private String tid;
    private String username;
    private String department;
    private String gender;
    private String image;
    private String designation;
    private String email;
    private String date_joined;

    public Teachers(String tid, String username, String department, String gender, String image, String designation, String email, String date_joined) {
        this.tid = tid;
        this.username = username;
        this.department = department;
        this.gender = gender;
        this.image = image;
        this.designation = designation;
        this.email = email;
        this.date_joined = date_joined;
    }


    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate_joined() {
        return date_joined;
    }

    public void setDate_joined(String date_joined) {
        this.date_joined = date_joined;
    }


    @Override
    public String toString() {
        return "Teachers{" +
                "tid='" + tid + '\'' +
                ", username='" + username + '\'' +
                ", department='" + department + '\'' +
                ", gender='" + gender + '\'' +
                ", image='" + image + '\'' +
                ", designation='" + designation + '\'' +
                ", email='" + email + '\'' +
                ", date_joined='" + date_joined + '\'' +
                '}';
    }
}
