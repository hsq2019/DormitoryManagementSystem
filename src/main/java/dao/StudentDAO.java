package dao;

import model.Page;
import model.Student;
import util.DBUtil;
import util.ThreadLocalHolder;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    //查询学生信息(最后返回是链表)
    //查询学生的详情信息（最后返回是一个对象，student）（用于修改学生面版）
    //插入学生信息
    //更新/修改学生信息（功能）
    //删除学生住宿信息


    //查询学生信息最后返回是链表
    public static List<Student> query(Page p) {
        //jdbc操作
        List<Student> list=new ArrayList<>();
        Connection c=null;//数据量连接
        PreparedStatement ps=null;//执行sql，可以执行带参数的sql
        ResultSet rs=null;//如果是查询，会有结果

        try {
            c= DBUtil.getConnection();
            StringBuilder sql=new StringBuilder("select s.id," +
                    "       s.student_name," +
                    "       s.student_graduate_year," +
                    "       s.student_major," +
                    "       s.student_email," +
                    "       s.dorm_id," +
                    "       s.create_time,"+
                    "       b.id building_id," +
                    "       d.dorm_no," +
                    "       b.building_name" +
                    "   from student s" +
                    "         join dorm d on d.id = s.dorm_id" +
                    "         join building b on d.building_id = b.id");
            if(p.getSearchText()!=null&&p.getSearchText().trim().length()>0){
                //去掉多余的空格后，搜索内容不是空的，有搜索内容，要进行搜索
                sql.append("    where s.student_name like ?");//使用模糊匹配
            }
            if(p.getSortOrder()!=null&&p.getSortOrder().trim().length()>0){
                //trim()方法是去掉两端多余的空格
                //有排序方式，升序或者降序，比如按照建立的时间去进行排序
                sql.append("    ORDER BY s.create_time "+p.getSortOrder());
            }
            StringBuilder countSQL=new StringBuilder("select count(0) count from (");
            countSQL.append(sql);
            countSQL.append(") tmp");//末尾加tmp是临时表的意思
            ps=c.prepareStatement(countSQL.toString());//预执行sql
            if(p.getSearchText()!=null&&p.getSearchText().trim().length()>0){
                //模糊匹配，中间有任何字段，包含了搜索的内容，就会查询到
                ps.setString(1,"%"+p.getSearchText()+"%");//设置占位符，搜索的东西
            }
            rs=ps.executeQuery();
            while (rs.next()){
                //设置到返回数据的total字段，当前方法是无法通过返回对象设置
                int count=rs.getInt("count");
                //使用threadLocal变量绑定到线程
                ThreadLocalHolder.get().set(count);//先获取到ThreadLocal这个变量，之后再调用本身的方法
                //与线程相关，与方法或者对象无关
                //绑定到线程thread类的ThreadLocalMap方法中
            }
            //处理业务
            sql.append("    limit ?,?");
            //sql的执行
            ps=c.prepareStatement(sql.toString());
            //页码转化成索引，第几页，每页的数量。（第几页-1）*每页有多少行。注意索引是从0开始
            int idx=(p.getPageNumber()-1)*p.getPageSize();
            int i=1;
            if(p.getSearchText()!=null&&p.getSearchText().trim().length()>0){
                ps.setString(i++,"%"+p.getSearchText()+"%");
            }
            ps.setInt(i++,idx);
            ps.setInt(i++,p.getPageSize());//每页的数量
            rs=ps.executeQuery();
            while (rs.next()){
                Student student=new Student();
                student.setId(rs.getInt("id"));
                student.setStudentName(rs.getString("student_name"));
                student.setStudentGraduateYear(rs.getString("student_graduate_year"));
                student.setStudentMajor(rs.getString("student_major"));
                student.setStudentEmail(rs.getString("student_email"));
                student.setDormId(rs.getInt("dorm_id"));
                student.setDormNo(rs.getString("dorm_no"));
                student.setBuildingId(rs.getInt("building_id"));
                student.setBuildingName(rs.getString("building_name"));
                student.setCreateTime(new Date(rs.getTimestamp("create_time").getTime()));
                list.add(student);
            }

        } catch (Exception e) {
            throw new RuntimeException("查询学生列表出错",e);
        }finally {
            DBUtil.close(c,ps,rs);
        }

        return list;
    }
    //查询学生的详情信息（最后返回是一个对象，student）（用于修改学生面版）前端自己调用
    public static Student queryById(int id) {
        Student student=new Student();
        Connection c=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            c=DBUtil.getConnection();
            String sql="select s.id," +
                    "       s.student_name," +
                    "       s.student_graduate_year," +
                    "       s.student_major," +
                    "       s.student_email," +
                    "       s.dorm_id," +
                    "       s.create_time," +
                    "       d.building_id," +
                    "       b.building_name," +
                    "       d.dorm_no" +
                    "   from student s" +
                    "         join dorm d on s.dorm_id = d.id" +
                    "         join building b on d.building_id = b.id" +
                    "   where s.id = ?";
            ps=c.prepareStatement(sql);
            ps.setInt(1,id);//替代?
            rs=ps.executeQuery();//执行
            while (rs.next()){
                student.setId(rs.getInt("id"));
                student.setStudentName(rs.getString("student_name"));
                student.setStudentGraduateYear(rs.getString("student_graduate_year"));
                student.setStudentMajor(rs.getString("student_major"));
                student.setStudentEmail(rs.getString("student_email"));
                student.setDormId(rs.getInt("dorm_id"));
                student.setCreateTime(new Date(rs.getTimestamp("create_time").getTime()));
                student.setBuildingId(rs.getInt("building_id"));
                student.setBuildingName(rs.getString("building_name"));
                student.setDormNo(rs.getString("dorm_no"));
            }
        } catch (Exception e) {
            throw new RuntimeException("查询学生住宿详情出错",e);
        } finally {
            DBUtil.close(c,ps,rs);
        }
        return student;

    }

    //插入学生信息
    public static int insert(Student s) {
        Connection c=null;
        PreparedStatement ps=null;
        //不是查找，所以没有resultSet
        try {
            c=DBUtil.getConnection();
            String sql="insert into student(student_name," +
                    "student_graduate_year," +
                    "student_major," +
                    "student_email," +
                    "dorm_id)" +
                    "   value (?,?,?,?,?)";
            ps=c.prepareStatement(sql);//预编译
            ps.setString(1,s.getStudentName());
            ps.setString(2,s.getStudentGraduateYear());
            ps.setString(3,s.getStudentMajor());
            ps.setString(4,s.getStudentEmail());
            ps.setInt(5,s.getDormId());
            return ps.executeUpdate();//返回是个整数呢，实际是执行了sql，返回了整数

        } catch (Exception e) {
            throw new RuntimeException("插入学生信息出错",e);
        } finally {
            DBUtil.close(c,ps);
        }
    }
    //更新/修改学生信息（功能）
    public static int update(Student s) {
        Connection c=null;
        PreparedStatement ps=null;

        try {
            c=DBUtil.getConnection();
            String sql="update student set student_name=?," +
                    "student_graduate_year=?," +
                    "student_major=?," +
                    "student_email=?," +
                    "dorm_id=?" +
                    "   where id=?";
            ps=c.prepareStatement(sql);
            ps.setString(1,s.getStudentName());
            ps.setString(2,s.getStudentGraduateYear());
            ps.setString(3,s.getStudentMajor());
            ps.setString(4,s.getStudentEmail());
            ps.setInt(5,s.getDormId());
            ps.setInt(6,s.getId());
            return ps.executeUpdate();//执行sql
        } catch (Exception e) {
            throw new RuntimeException("修改学生信息出错",e);
        } finally {
            DBUtil.close(c,ps);
        }
    }

    //删除学生住宿信息
    public static int delete(String[] ids) {
        Connection c=null;
        PreparedStatement ps=null;

        try {
            c=DBUtil.getConnection();
            //拼接，sql
            StringBuilder sql=new StringBuilder("delete from student where id in (");
            for(int i=0;i<ids.length;i++){
                if(i!=0){
                    sql.append(",");
                }
                sql.append("?");
            }
            sql.append(")");

            ps=c.prepareStatement(sql.toString());//预执行sql
            for(int i=0;i<ids.length;i++){
                ps.setInt(i+1,Integer.parseInt(ids[i]));
            }
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("删除学生住宿信息出错",e);
        } finally {
            DBUtil.close(c,ps);
        }

    }

    //分配宿舍
    public static int apply(Student s) {
        Connection c=null;
        PreparedStatement ps=null;
        List<Integer> ids=s.getIds();
        try {
            c=DBUtil.getConnection();
            StringBuilder sql=new StringBuilder("update student set dorm_id=? where id in (");
            for(int i=0;i<ids.size();i++){
                if(i!=0){
                    sql.append(",");
                }
                sql.append("?");
            }
            sql.append(")");
            ps=c.prepareStatement(sql.toString());
            ps.setInt(1,s.getDormId());
            for(int i=0;i<ids.size();i++){
                ps.setInt(i+2,ids.get(i));
            }
            return ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("宿舍分配失败",e);
        } finally {
            DBUtil.close(c,ps);
        }

    }
}
