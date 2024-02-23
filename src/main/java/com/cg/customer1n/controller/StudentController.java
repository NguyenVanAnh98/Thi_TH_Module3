package com.cg.customer1n.controller;

import com.cg.customer1n.dao.StudentDAO;
import com.cg.customer1n.dao.IStudentDAO;
import com.cg.customer1n.dao.IClassRoomDAO;
import com.cg.customer1n.dao.ClassRoomDAO;
import com.cg.customer1n.model.Student;
import com.cg.customer1n.model.ClassRoom;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UserServlet", urlPatterns = "/students")
public class StudentController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IStudentDAO studentDAO;
    private IClassRoomDAO classRoomDAO;

    public void init() {

        studentDAO = new StudentDAO();
        classRoomDAO = new ClassRoomDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                insertStudent(request, response);
                break;
            case "edit":
                updateStudent(request, response);
                break;
        }

    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("address");
        String address = request.getParameter("email");
        String phone = request.getParameter("phone");
        int classroom_id = Integer.parseInt(request.getParameter("classrooms"));
        ClassRoom classRoom = classRoomDAO.findClassRoomById(classroom_id);


        Student student = new Student(id,name,address,email,phone, classRoom);
        studentDAO.updateStudent(student);

        response.sendRedirect("/students");

    }

    private void insertStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        int classroomId = Integer.parseInt(request.getParameter("classrooms"));

        ClassRoom classroom = classRoomDAO.findClassRoomById(classroomId);

        Student student = new Student(name, address, email, phone, classroom);
        studentDAO.insertStudent(student);

        response.sendRedirect("/students");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                showNewForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteStudent(request, response);
                break;
            default:
                listStudent(request, response);
                break;
        }

    }

    private void listStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String kw = request.getParameter("kw");
        if (kw == null) {
            kw = "";
        }

        List<Student> studentList = new ArrayList<>();
        if (kw.equals("")) {
            studentList = studentDAO.selectAllStudent();
        }else{
           studentList =studentDAO.searchByKw(kw);
        }
        request.setAttribute("liststudents", studentList);
        request.getRequestDispatcher("/WEB-INF/students.jsp").forward(request, response);

    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        studentDAO.deleteStudent(id);
        response.sendRedirect("/students");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idStudent = Integer.parseInt(request.getParameter("id"));

        Student student = studentDAO.selectStudent(idStudent);
        List<ClassRoom> classrooms = classRoomDAO.selectClassRoom();

        request.setAttribute("student", student);
        request.setAttribute("classrooms", classrooms);
        request.getRequestDispatcher("/WEB-INF/edit.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ClassRoom> classrooms = classRoomDAO.selectClassRoom();
        request.setAttribute("classrooms", classrooms);
        request.getRequestDispatcher("/WEB-INF/create.jsp").forward(request, response);
    }

}
