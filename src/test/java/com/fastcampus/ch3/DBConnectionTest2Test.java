package com.fastcampus.ch3;

import org.apache.tomcat.dbcp.dbcp2.DelegatingPreparedStatement;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.*;
import org.springframework.context.support.*;
import org.springframework.jdbc.datasource.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class DBConnectionTest2Test {
    @Autowired
    DataSource ds;

    @Test
    public void insertUserTest() throws Exception{
        User user = new User("asdf3","1234","abc","aaaa@aaa.com", new Date(), "fb", new Date());
        deleteAll();
        int rowCnt = insertUser(user);

        assertTrue(rowCnt==1);
    }

    @Test
    public void selectUserTest() throws Exception{
//        List<User>list = selectAll();

//        for(User user : list){
//            System.out.print(user.getId());
//            System.out.print(user.getPwd());
//            System.out.print(user.getName());
//            System.out.print(user.getEmail());
//            System.out.print(user.getBirth());
//            System.out.print(user.getSns());
//            System.out.print(user.getReg_date());
//            System.out.println();
//        }
//        assertTrue(list!=null);
        deleteAll();
        User user = new User("asdf3","1234","abc","aaaa@aaa.com", new Date(), "fb", new Date());
        insertUser(user);
        user = selectUser("asdf3");
        assertTrue(user.getId().equals("asdf3"));
    }

    @Test
    public void deleteUserTest() throws Exception{
        deleteAll();
        int rowCnt = deleteUser("asdf");
        assertTrue(rowCnt==0);
        User user = new User("asdf3","1234","abc","aaaa@aaa.com", new Date(), "fb", new Date());
        rowCnt = insertUser(user);
        assertTrue(rowCnt==1);
        rowCnt = deleteUser(user.getId());
        assertTrue(rowCnt==1);
        assertTrue(selectUser(user.getId())==null);
    }

    @Test
    public void updateUserTest() throws Exception{
        deleteAll();
        User user = new User("asdf3","1234","abc","aaaa@aaa.com", new Date(), "fb", new Date());
        insertUser(user);

        int rowCnt = updateUser(user);
        assertTrue(rowCnt==1);
    }
    // 매개변수로 받은 사용자 정보로 user_info 테이블을 update하는 메서드
    public int updateUser(User user) throws Exception{
        Connection conn = ds.getConnection();
        String sql = "update user_info set pwd=?,name=? where id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, "test");
        pstmt.setString(2, "test");
        pstmt.setString(3, user.getId());
        int rowCnt = pstmt.executeUpdate();
        return rowCnt;
    }
    public int deleteUser(String id) throws Exception{
        Connection conn = ds.getConnection();
        String sql = "delete from user_info where id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,id);

        return pstmt.executeUpdate();
    }
    private User selectUser(String id) throws Exception{
        Connection conn = ds.getConnection();
        String sql = "select * from user_info where id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,id);
        ResultSet rs = pstmt.executeQuery();

        if(rs.next()){
            User user = new User();
            user.setId(rs.getString(1));
            user.setPwd(rs.getString(2));
            user.setName(rs.getString(3));
            user.setEmail(rs.getString(4));
            user.setBirth(rs.getDate(5));
            user.setSns(rs.getString(6));
            user.setReg_date(rs.getDate(7));
            return user;
        }
        return null;
    }

    private List<User> selectAll() throws Exception{
        List<User> list = new ArrayList<>();
        Connection conn = ds.getConnection();
        String sql = "select * from user_info";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        while(rs.next()){
            User user = new User();
            user.setId(rs.getString(1));
            user.setPwd(rs.getString(2));
            user.setName(rs.getString(3));
            user.setEmail(rs.getString(4));
            user.setBirth(rs.getDate(5));
            user.setSns(rs.getString(6));
            user.setReg_date(rs.getDate(7));
            list.add(user);
        }
        return list;
    }
    private void deleteAll() throws Exception{
        Connection conn = ds.getConnection();

        String sql = "delete from user_info";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();
    }

    // 사용자 정보를 user_info 테이블에 저장하는 메서드
    public int insertUser(User user) throws Exception{
        Connection conn = ds.getConnection();

//        insert into user_info (id, pwd, name, email, birth, sns, reg_date)
//        values ('asdf1','1234','smith','aaa@aaa.com','2021-01-01','fascbook',now());

        String sql = "insert into user_info values (?,?,?,?,?,?,now())";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,user.getId());
        pstmt.setString(2,user.getPwd());
        pstmt.setString(3,user.getName());
        pstmt.setString(4,user.getEmail());
        pstmt.setDate(5,new java.sql.Date(user.getBirth().getTime()));
        pstmt.setString(6,user.getSns());

        int rowCnt = pstmt.executeUpdate();
        return rowCnt;
    }

    @Test
    public void transactionTest() throws Exception{

        Connection conn = null;
        try {
            deleteAll();
            conn = ds.getConnection();
//            conn.setAutoCommit(false);
            conn.setAutoCommit(true);

            String sql = "insert into user_info values (?,?,?,?,?,?,now())";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,"asdf");
            pstmt.setString(2,"1234");
            pstmt.setString(3,"abc");
            pstmt.setString(4,"aaa@aaa.com");
            pstmt.setDate(5,new java.sql.Date(new Date().getTime()));
            pstmt.setString(6,"fb");

            int rowCnt = pstmt.executeUpdate(); // 트랜잭션 1
            pstmt.setString(1,"asdf");
            rowCnt = pstmt.executeUpdate(); // 트랜잭션 2

            conn.commit();
        } catch (Exception e) {
            conn.rollback(); // 문제발생시 롤백.
            e.printStackTrace();
        } finally {
        }

    }

    @Test
    public void jdbcConnectionTest() throws Exception {
//        ApplicationContext ac = new GenericXmlApplicationContext("file:src/main/webapp/WEB-INF/spring/**/root-context.xml");
//        DataSource ds = ac.getBean(DataSource.class);

        Connection conn = ds.getConnection(); // 데이터베이스의 연결을 얻는다.

        System.out.println("conn = " + conn);
        assertTrue(conn!=null); // 괄호 안의 조건식이 true먄, 테스트 성공, 아니면 실패
    }
}