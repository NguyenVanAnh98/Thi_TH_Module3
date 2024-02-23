package com.cg.customer1n.model;

public class Student {
    private int id;
    private String name;
    private String address;
    private String email;
    private String phone;

    private ClassRoom classRoom;

    public Student() {
    }

    public Student(String name, String address, String email, String phone, ClassRoom classRoom) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.classRoom = classRoom;
    }


    public Student(int id, String name, String address, String email, String phone, ClassRoom classRoom) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.classRoom = classRoom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }
}