package com.cg.customer1n.dao;


import com.cg.customer1n.model.ClassRoom;
import com.cg.customer1n.model.Student;

import java.util.List;

public interface IClassRoomDAO {
    List<ClassRoom> selectClassRoom();



    ClassRoom findClassRoomById(int id);

    void insertClassRoom(ClassRoom classRoom);

    void updateClassRoom(ClassRoom classRoom);

    void deleteClassRoom(int id);


}
