package check;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDB {
    Connection connection;
    public CreateDB(Connection c){
        connection = c;
    }
    public void caseDB(String dbName){
        if(dbName.contains("작업대출")){
            makeDb("eto_24");
            dbName = "1";
        }
        else if(dbName.contains("미등록대부")){
            makeDb("eto_21");
            dbName = "2";
        }
        else if(dbName.contains("대리입금")){
            makeDb("eto_20");
            dbName="3";
        }
        else if(dbName.contains("신용카드 현금화")){
            makeDb("eto_23");
            dbName="4";
        }
        else if(dbName.contains("휴대폰 소액결재")){
            makeDb("eto_26");
            dbName="5";
        }
        else if(dbName.contains("신용정보 매매")){
            makeDb("eto_22");
            dbName="6";
        }
        else if(dbName.contains("통장매매")){
            makeDb("eto_25");
            dbName="7";
        }
        else if(dbName.contains("마약판매")){
            makeDb("eto_19");
            dbName="8";
        }

    }
    public void makeDb(String name){
        try {
            String sql = "CREATE TABLE if not exists"+name +"(SEQ numeric primary key ,URL varchar(254) not null, CHANNEL varchar(254) not null, TITLE varchar(4096) not null, CONTENT varchar(65536) not null, WRITE_DATE varchar(254) not null, WRITE_TIME varchar(254) null, WRITER_NAME varchar(254) null, WRITER_ACCOUNT varchar(254) null, CONTACT varchar(65536) null);";
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            connection.commit();
            stmt.close();
            connection.close();
        }catch (SQLException e){
            System.out.println(e);
            System.out.println("DB 생성 실패");
        }
    }
}
