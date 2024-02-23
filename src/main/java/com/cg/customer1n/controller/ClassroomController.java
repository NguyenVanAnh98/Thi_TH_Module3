package com.cg.customer1n.controller;

import com.cg.customer1n.dao.IClassRoomDAO;
import com.cg.customer1n.dao.ClassRoomDAO;
import com.cg.customer1n.model.ClassRoom;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ClassroomController", urlPatterns = "/classrooms")
public class ClassroomController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IClassRoomDAO classRoomDAO;

    public void init() {
        classRoomDAO = new ClassRoomDAO();
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
                deleteClassroom(request, response);
                break;
            default:
                listClassroom(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                insertClassRoom(request, response);
                break;
            case "edit":
                updateClassroom(request, response);
                break;
        }
    }

    private void listClassroom(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {



        List<ClassRoom> ClassRoomList = classRoomDAO.selectClassRoom();

        request.setAttribute("listClassRooms", ClassRoomList);
        request.getRequestDispatcher("/WEB-INF/classrooms.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int classRoomId = Integer.parseInt(request.getParameter("id"));
        ClassRoom classRoom = classRoomDAO.findClassRoomById(classRoomId);
        request.setAttribute("classrooms", classRoom);
        request.getRequestDispatcher("/WEB-INF/edit_classrooms.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/create_classrooms.jsp").forward(request, response);
    }

    private void insertClassRoom(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        ClassRoom classRoom = new ClassRoom(name);
        classRoomDAO.insertClassRoom(classRoom);
        response.sendRedirect("/classroom");
    }

    private void updateClassroom(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        ClassRoom classRoom = new ClassRoom(id, name);
       classRoomDAO.updateClassRoom(classRoom);
        response.sendRedirect("/classroom");
    }

    private void deleteClassroom(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        classRoomDAO.deleteClassRoom(id);
        response.sendRedirect("/classroom");
    }
}