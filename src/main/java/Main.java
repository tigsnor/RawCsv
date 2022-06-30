import check.DataInspection;

public class Main {
    private Properties properties;
    DataInspection dataInspection = new DataInspection();
    public static void main(String[] arguments){
        new Main().start();
    }

    public void start(){
        Properties.initialize();
        properties = Properties.getInstance();

        PostgresConnection.initialize();

        dataInspection.inspectionStart(PostgresConnection.getPostgres());
    }


}
