import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import org.apache.commons.lang3.StringUtils;

public class Main extends Thread {

  //public static HashMap<String, ArrayList<UserRepos>> finalData;
  public static HashMap<String, ModelFileClass1> map1 = null;
  public static HashMap<String, ModelFileClass2> map2 = null;

  public void run() {

    ArrayList<String> user = new ArrayList<String>();
    map1 = CSVExtraction.readFile1();
    map2 = CSVExtraction.readFile2(user);

    initailizeCSV(map1, map2);

    ArrayList<String> userName = new ArrayList<String>(user.subList(0, 5));

    //finalData = new HashMap<String, ArrayList<UserRepos>>();
    repoExtractor(userName);
    //writeToCSV(map1,map2,finalData);

  }

  //private static final String MESSAGE="ConnectAgain";

  public static void main(String[] args) {

    Main prog = new Main();
    prog.start();

  }


  private static void repoExtractor(ArrayList<String> userName) {

    UserRepos[] uR;

    int cnt = 0;

    for (String str : userName) {

      //HashMap<String, ArrayList<UserRepos>> finalData = new HashMap<String, ArrayList<UserRepos>>();
      System.out.println("user Num: " + cnt++);
      System.out.println("UserID: " + str);
      uR = getUserRepo(str);
      //finalData.put(str, new ArrayList<UserRepos>(Arrays.asList(uR)));
      writeOneUserToCSV(map1, map2, str, uR);
      System.out.println("Data Written");

      System.out.println("**Sleep**\n");
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    }

  }


  private static UserRepos[] getUserRepo(String username) {

    String sURL = "https://api.github.com/users/" + username + "/repos";

    ObjectMapper omR = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    UserRepos[] uR = new UserRepos[0];

    try {

      String json = JsonString(sURL);
      if (StringUtils.isBlank(json)) {
        return uR;
      }

      uR = omR.readValue(json, UserRepos[].class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return uR;
  }


  private static String JsonString(String url) {

    String repos = null;

    try {
      URL obj = new URL(url);
      HttpURLConnection con = (HttpURLConnection) obj.openConnection();
      con.setConnectTimeout(2000);

      int responseCode = con.getResponseCode();

      if (responseCode == HttpURLConnection.HTTP_OK) { //successful connection

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String input;
        StringBuffer response = new StringBuffer();

        while ((input = in.readLine()) != null) {
          response.append(input);
        }

        in.close();
        repos = response.toString();

      } else {
        System.out.println("Connection Unsuccessful");
        System.out.println("Sleep");
        Thread.sleep(5000);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return repos;

  }

  private static void initailizeCSV(HashMap<String, ModelFileClass1> map1,
      HashMap<String, ModelFileClass2> map2) {
    String csv = "finaldata.csv";
    CSVWriter writer = null;

    try {
      writer = new CSVWriter(new FileWriter(csv, false));
      String heading = "BOlogin,empid,boempid,reponame,repourl,description,language,fork,giturl,sshurl,cloneurl,size,createdate,updatedate";
      writer.writeNext(heading.split(","));
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

//  private static void writeToCSV(HashMap<String, ModelFileClass1> map1, HashMap<String, ModelFileClass2> map2, HashMap<String,ArrayList<UserRepos>> finalData)
//  {
//    String csv = "finaldata.csv";
//    CSVWriter writer = null;
//    try {
//
//      writer = new CSVWriter(new FileWriter(csv, true));
//
//      for (Map.Entry<String, ModelFileClass1> entry : map1.entrySet()) {
//        String key = entry.getKey();
//        ModelFileClass1 value = entry.getValue();
//
//
//        String finalKey = map2.get(key).getGitUserName();
//
//        if(finalData.keySet().contains(finalKey))
//        {
//          ArrayList<UserRepos> userRepos = finalData.get(finalKey);
//          for(UserRepos rep : userRepos){
//
//            String result = value.toString() + "," + rep.toString();
//            writer.writeNext(result.split(","));
//          }
//        }
//        else {
//          String result = value.toString() + ",,,,,,,,";
//          writer.writeNext(result.split(","));
//        }
//      }
//      writer.close();
//    }catch (IOException e){
//      e.printStackTrace();
//    }
//  }

  private static void writeOneUserToCSV(HashMap<String, ModelFileClass1> map1,
      HashMap<String, ModelFileClass2> map2, String userId, UserRepos[] uR) {
    String csv = "finaldata.csv";
    CSVWriter writer = null;
    try {

      writer = new CSVWriter(new FileWriter(csv, true));

      String key1 = map2.get(userId).getBoLogin();

      for (UserRepos u : uR) {
        String result = map1.get(key1).toString() + "," + u.toString();
        writer.writeNext(result.split(","));
      }
      writer.close();

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}