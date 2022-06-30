package check;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDB {
    public void makeDb(Connection connection){
        try {
            String sql = "CREATE TABLE REGISTRATION " +
                    "(id INTEGER not NULL, " +
                    " first VARCHAR(255), " +
                    " last VARCHAR(255), " +
                    " age INTEGER, " +
                    " PRIMARY KEY ( id ))";
//            "CREATE TABLE if not exists ETO_25(SEQ numeric primary key ,URL varchar(254) not null, CHANNEL varchar(254) not null, TITLE varchar(4096) not null, CONTENT varchar(65536) not null, WRITE_DATE varchar(254) not null, WRITE_TIME varchar(254) null, WRITER_NAME varchar(254) null, WRITER_ACCOUNT varchar(254) null, CONTACT varchar(65536) null);"
            Statement stmt = connection.createStatement();
            stmt.executeQuery(sql);
//            stmt.close();
//            connection.close();
        }catch (SQLException e){
            System.out.println(e);
            System.out.println("DB 생성 실패");
        }
    }
}
