package dao;

import model.Page;
import model.Student;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StudentDAOTest {


    @Test
    public void queryById() {
        //查询学生的详情信息（最后返回是一个对象，student）（用于修改学生面版）前端自己调用
        StudentDAO studentDAO=new StudentDAO();
        Student student=studentDAO.queryById(5);
        System.out.println(student);
    }

    @Test
    public void insert() {
        //插入学生信息
        Student s=new Student();
        s.setStudentName("狗子");
        s.setStudentGraduateYear("000001002");
        s.setStudentMajor("000002003");
        s.setStudentEmail("999@163.com");
        s.setDormId(10);
        StudentDAO studentDAO=new StudentDAO();
        studentDAO.insert(s);
        Page p=new Page();
        p.setSearchText("狗子");
        p.setSortOrder("asc");
        p.setPageNumber(1);
        p.setPageSize(5);
        List<Student> l= studentDAO.query(p);
        System.out.println(l);
    }

    @Test
    public void update() {
        StudentDAO studentDAO=new StudentDAO();
        Student s=new Student();
        s.setId(68);
        s.setStudentName("猫子");
        s.setDormId(10);
        studentDAO.update(s);
        Student student=studentDAO.queryById(68);
        System.out.println(student);
    }

    @Test
    public void delete() {
        StudentDAO studentDAO=new StudentDAO();
        String[] s=new String[1];
        s[0]="68";
        studentDAO.delete(s);
        Page p=new Page();
        p.setSearchText("猫子");
        p.setSortOrder("asc");
        p.setPageNumber(1);
        p.setPageSize(5);
        List<Student> l= studentDAO.query(p);
        System.out.println(l);
    }


    @Test
    public void apply() {
        //给学生分配宿舍
        StudentDAO studentDAO=new StudentDAO();
        Student s=new Student();
        List<Integer> l=new ArrayList<>();
        l.add(1);
        l.add(2);
        l.add(3);
        s.setIds(l);
        s.setDormId(3);
        studentDAO.apply(s);
    }
}