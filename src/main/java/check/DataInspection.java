package check;

import com.opencsv.CSVWriter;
import com.vdurmont.emoji.EmojiParser;

import java.io.FileWriter;
import java.io.IOException;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;

public class DataInspection {

    public void inspectionStart(Connection connection) {

        try {
            Statement stmt = connection.createStatement();
            //SELECT 문을 사용할때 WHERE NOT 조건을 사용하여 WRITE_ACCOUNT(작성자계정)과 CONTACT(연락처) 둘다 없는 로우를 가져오지 않는다.
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM 금융_20220101_20220331 WHERE NOT (writer_account is null AND contact is null)");
            putData("resultSet", resultSet);

            ResultSet drugResultSet = stmt.executeQuery("SELECT * FROM 마약_20220101_20220331 WHERE NOT (writer_account is null AND contact is null)");
            putData("drugResultSet", drugResultSet);

        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void putData(String dataName, ResultSet resultSet){
        try{
            //카테고리
            List<String[]> testList = new ArrayList<String[]>();
            String[] data = {};
            String csvname = "";
            if (dataName == "resultSet"){
                csvname = "ETO_20";
            }
            if (dataName == "drugResultSet"){
                csvname = "ETO_19";
            }
            while (resultSet.next()){
                if(checkUrl(resultSet.getString("url"))){
                    String contact2 = "";
                    if (resultSet.getString("contact") != null) {
                        contact2 = resultSet.getString("contact");
                    }
                    int seqNum = resultSet.getRow();
                    String url= resultSet.getString("url");
                    String title = changeData(textLimit(emoji(resultSet.getString("title"))));
                    String content = changeData(emoji(resultSet.getString("content")));
                    String date=changeDate(resultSet.getString("write_date"));
                    String time=changeTime(resultSet.getString("write_time"));
                    String channel = resultSet.getString("channel");
                    String name = resultSet.getString("writer_name");
                    String account = resultSet.getString("writer_account");



                    data = new String[]{String.valueOf(seqNum), url, channel, title, date, time, name, account, contact2};
                    testList.add(data);

                    try{

                    }catch (NullPointerException e){

                    }
                }else{
                }
            }
            //csv파일 만들기
            makeCsv(csvname, testList);
    //            for (int i=0; i<testList.size(); i++) {
    //                System.out.println(data[i]);
    //            }
        }catch (SQLException | IOException e){
            System.out.println(e);
        }

    }

    //여기에 csv작성파일
    public void makeCsv(String name, List<String[]> data) throws IOException {
        DateFormat dateParse = new SimpleDateFormat("yyyyMMdd");
        Date nwDate = new Date();
        String tbDate = dateParse.format(nwDate);
        String path = "C:\\Users\\e2on\\Desktop\\csvTest\\"+name+"_"+tbDate+"_C_001.csv";
        CSVWriter writer = new CSVWriter(new FileWriter(path));

//        String[] category = {"seq", "url", "channel", "title", "write_date", "write_time", "writer_name", "write_account", "contact"};
//        writer.writeNext(category);
        for(int i=0; i<data.size(); i++){
            writer.writeNext(data.get(i));
        }

        writer.close();

    }

    //url 정규식을 확인
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
    // data 내부에있는 이모티콘을 제거
    public String emoji(String data){
        String removeEmoji = EmojiParser.removeAllEmojis(data);
//        System.out.println(removeEmoji);
        return removeEmoji;
    }
    //글길이를 60자로 제한
    public String textLimit(String text){
        if(text.length() > 56){
           String limitText = text.substring(0,56);
           return limitText+"...";
        }
        return text;
    }
    //db writeTime의 시간형태를 변형
    public String changeTime(String time){
        DateFormat timeParse = new SimpleDateFormat("HHmm");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String timeC=null;
        try {
            switch (time.length()) {
                case 1:
                    time = "000" + time;
                    break;
                case 2:
                    time = "00" + time;
                    break;
                case 3:
                    time = "00" + time;
                    break;
            }
        }catch (NullPointerException e){
//            System.out.println("널");
            return null;
        }
        try {
            Date timePar = timeParse.parse(time);
            timeC = timeFormat.format(timePar);
//            System.out.println(timeC);
        }
        catch (ParseException e){
            System.out.println(e);
        }finally {
            return timeC;
        }

    }
    //db WriteDate 형태 변환
    public String changeDate(String date){
        DateFormat dateParse = new SimpleDateFormat("yyyyMMdd");
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String dateC = null;

        try {
           Date datePar = dateParse.parse(date);
           dateC = dateFormat.format(datePar);
//            System.out.println(dateC);
        }
        catch (ParseException e){
            System.out.println(e);
        }finally {
            return dateC;
        }
    }

    public String changeData(String data){
       String replaceData = data.replace("\t"," ").replace("\r\n"," ").replace(",","^");
       return replaceData;
    }
}
