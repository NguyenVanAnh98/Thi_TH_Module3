package com.cg.customer1n.dao;

import com.cg.customer1n.model.Student;

import java.util.List;

public interface IStudentDAO {
    public void insertStudent(Student student);

    public Student selectStudent(int id);

    public List<Student> selectAllStudent();

    public boolean deleteStudent(int id) ;

    public boolean updateStudent(Student student);

    List<Student> searchByKw(String kw);
}
