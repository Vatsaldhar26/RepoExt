import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class Main extends Thread{

  public static HashMap<String, ArrayList<UserRepos>> finalData;
  public static HashMap<String, ModelFileClass1> map1 = null;
  public static HashMap<String, ModelFileClass2> map2 = null;

  public void run(){

    ArrayList<String> user = new ArrayList<String>();
    map1 = CSVExtraction.readFile1();
    map2 = CSVExtraction.readFile2(user);

    ArrayList<String> userName = new ArrayList<String>(user.subList(0, 10));

    finalData = new HashMap<String, ArrayList<UserRepos>>();
    repoExtractor(userName);
    writeToCSV(map1,map2,finalData);

  }

  private static final String MESSAGE="ConnectAgain";

  public static void main(String[] args) {

    Main prog = new Main();
    prog.start();

  }


  private static void repoExtractor(ArrayList<String> userName) {

    UserRepos[] uR = new UserRepos[0];

    int cnt = 0;
    for(String str:userName) {
      System.out.println("user: " + cnt++);
      System.out.println("UserID: " + str);
      uR = getUserRepo(str);
      finalData.put(str, new ArrayList<UserRepos>(Arrays.asList(uR)));
    }

  }


  private static UserRepos[] getUserRepo(String username) {

    String sURL = "https://api.github.com/users/" + username + "/repos";

    ObjectMapper omR = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    UserRepos[] uR = new UserRepos[0];

    int tryAgain = 0;

    try {
      String dataFetched=null;
      dataFetched = JsonString(sURL);
      while(dataFetched==MESSAGE && tryAgain<3) {
        dataFetched=JsonString(sURL);
        tryAgain++;
      }

      uR = omR.readValue( dataFetched , UserRepos[].class);
    }
    catch (Exception e) {
      e.printStackTrace();
      writeToCSV(map1,map2,finalData);
    }
    tryAgain=0;
    System.out.println("**Sleep**\n");
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
      writeToCSV(map1,map2,finalData);
    }
    System.out.println("Repositories: ");

    String repo;

    for(UserRepos temp: uR) {
      System.out.println("Repo_name: " + temp.getName());
      System.out.println("Repo_Url: " + temp.getUrl() );
    }
    return uR;
  }


  private static String JsonString(String url) {

    String repos = null;
    int flag=0;
    try {
      URL obj = new URL(url);
      HttpURLConnection con = (HttpURLConnection) obj.openConnection();
      con.setConnectTimeout(8000);

      int responseCode = con.getResponseCode();
      //System.out.println(responseCode);

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
        System.out.print(".");
        return MESSAGE;
      }
    }
    catch (Exception e) {
      flag=1;
      e.printStackTrace();
      writeToCSV(map1,map2,finalData);
    }

    if(flag==1)
      return MESSAGE;
    else
      return repos;
  }


  private static void writeToCSV(HashMap<String, ModelFileClass1> map1, HashMap<String, ModelFileClass2> map2, HashMap<String,ArrayList<UserRepos>> finalData)
  {
    String csv = "finaldata.csv";
    CSVWriter writer = null;
    try {
      writer = new CSVWriter(new FileWriter(csv, false));
      String heading = "bologin,empid,boempid,reponame,repourl,description,language,fork,giturl,sshurl,cloneurl,size,createdate,updatedate";
      writer.writeNext(heading.split(","));


      for (Map.Entry<String, ModelFileClass1> entry : map1.entrySet()) {
        String key = entry.getKey();
        ModelFileClass1 value = entry.getValue();


        String finalKey = map2.get(key).getGitUserName();

        if(finalData.keySet().contains(finalKey))
        {
          ArrayList<UserRepos> userRepos = finalData.get(finalKey);
          for(UserRepos rep : userRepos){

            String result = value.toString() + "," + rep.toString();
            writer.writeNext(result.split(","));
          }
        }
        else {
          String result = value.toString() + ",,,,,,,,";
          writer.writeNext(result.split(","));
        }
      }
      writer.close();
    }catch (IOException e){
      e.printStackTrace();
    }
  }
}