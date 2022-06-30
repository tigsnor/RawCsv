import check.CreateDB;
import check.DataInspection;

public class Main {
    private Properties properties;
    DataInspection dataInspection = new DataInspection();
    CreateDB createDB = new CreateDB();
    public static void main(String[] arguments){
        new Main().start();
    }

    public void start(){
        Properties.initialize();
        properties = Properties.getInstance();

        PostgresConnection.initialize();
        createDB.makeDb(PostgresConnection.getPostgres());
//        dataInspection.inspectionStart(PostgresConnection.getPostgres());
    }


}
