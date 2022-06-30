import lombok.Data;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
@Data
public class Properties {
    private static Properties instance;
    private Postgres postgres;

    public static void initialize(){
        try{
            Yaml yaml = new Yaml();
            instance = yaml.loadAs(new FileInputStream("conf/application.yml"), Properties.class);
        }catch (FileNotFoundException e){
            System.out.println("error:"+e);
        }
    }
    public static Properties getInstance() {
        return instance;
    }

    @Data
    public static class Postgres{
        private String host;
        private int port;
        private String db;
        private String id;
        private String pw;
    }
}
