package check;

import com.vdurmont.emoji.EmojiParser;

import javax.swing.text.StyledEditorKit;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;

public class DataInspection {

    public void inspectionStart(Connection connection){
        try {

            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM test.test");

            while (resultSet.next()){
                if(checkUrl(resultSet.getString("url"))){
//                    System.out.println("true");
                }else{
//                    System.out.println("false");
                }
                String removeEmoji = emoji(resultSet.getString("content"));
                changeDate(resultSet.getString("write_date"),resultSet.getString("write_time"));
            }
        }catch (SQLException e){
            System.out.println(e);
        }

    }

    public Boolean checkUrl(String url){
        Pattern p = Pattern.compile("^(?:https?:\\/\\/)?(?:www\\.)?[a-zA-Z0-9./]+$");
        Matcher m = p.matcher(url);
        if  (m.matches()){
            return true;
        }
        URL u = null;
        try {
            u = new URL(url);
        } catch (MalformedURLException e) {
            return false;
        }
        try {
            u.toURI();
        } catch (URISyntaxException e) {
            return false;
        }
        return true;
    }

    public String emoji(String data){
        String removeEmoji = EmojiParser.removeAllEmojis(data);
//        String removeEmoji = EmojiParser.replaceAllEmojis(data,"||||");

//        System.out.println(removeEmoji);
        return removeEmoji;
    }
    public String textLimit(String text){
        if(text.length() > 60){
           String limitText = text.substring(0,56);
           return limitText+"...";
        }
        return text;
    }

    public void changeDate(String Date, String Time){
//        System.out.println("date:"+Date);
//        System.out.println("time:"+Time);
        DateFormat dateParse = new SimpleDateFormat("yyyyMMdd");
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        DateFormat timeParse = new SimpleDateFormat("HHmm");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        try {
            switch (Time.length()) {
                case 1:
                    Time = "000" + Time;
                    break;
                case 2:
                    Time = "00" + Time;
                    break;
                case 3:
                    Time = "00" + Time;
                    break;
            }
        }catch (NullPointerException e){
            Time = "0000";
        }
        try {
            Date timePar = timeParse.parse(Time);
            String time = timeFormat.format(timePar);
           Date datePar = dateParse.parse(Date);
           String date = dateFormat.format(datePar);
            System.out.println(date);
            System.out.println(time);

        }
        catch (ParseException e){
            System.out.println(e);
        }

//        System.out.println(timeFormat.format(Time));
    }
}
