import java.sql.*;


public class PostgresConnection {
    private static final Properties properties = Properties.getInstance();
    private static Connection connection;
    public static void initialize() {
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try{
            System.out.println("connect:"+"jdbc:postgresql://"+properties.getPostgres().getHost()+":"+properties.getPostgres().getPort()+"/"+properties.getPostgres().getDb());
            connection = DriverManager.getConnection("jdbc:postgresql://"+properties.getPostgres().getHost()+":"+properties.getPostgres().getPort()+"/"+properties.getPostgres().getDb(),properties.getPostgres().getId(),properties.getPostgres().getPw());
            connection.setAutoCommit(false);
//            Statement stmt = connection.createStatement();
//            DatabaseMetaData databaseMetaData = connection.getMetaData();
//            ResultSet resultSet = databaseMetaData.getColumns(null,"smart","test","test");
//            ResultSet resultSet = stmt.executeQuery("SELECT seq FROM test.test");
//            while (resultSet.next()){
//                System.out.println(resultSet.getString("seq"));
//            }
        }catch (SQLException e){
            System.out.println(e);
        }


    }
    public static Connection getPostgres(){
        return connection;
    }
}
