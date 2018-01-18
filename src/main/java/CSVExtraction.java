import com.opencsv.CSVReader;

    import java.io.FileNotFoundException;
    import java.io.FileReader;
    import java.io.IOException;
    import java.util.ArrayList;
    import java.util.HashMap;

    import com.opencsv.CSVReaderBuilder;

public class CSVExtraction {
  public static void main(String[] args) {

    HashMap<String, ModelFileClass1> map1 = readFile1();

    ArrayList<String> userNameList = new ArrayList<String>();
    HashMap<String, ModelFileClass2> map2 = readFile2(userNameList);
//        System.out.println(map1.get("aa@").getEmpID());

//        System.out.println(map2.get(""));
//        System.out.println(userNameList);

  }
  static HashMap<String, ModelFileClass1> readFile1()
  {
    HashMap<String, ModelFileClass1> map = new HashMap<String, ModelFileClass1>();
    try {
      CSVReader reader = new CSVReaderBuilder(new FileReader("csvfiles/psm_email_id_key_mapping.csv")).withSkipLines(1).build();

      String[] record = null;

      while ((record = reader.readNext()) != null) {
        ModelFileClass1 f1 = new ModelFileClass1();
//                System.out.println(record[1]);
        f1.setBoLogin(record[0]);
        f1.setEmpID(Integer.parseInt(record[1]));
        f1.setBoEmpID(Integer.parseInt(record[2]));
//                list.add(f1);
        map.put(record[0],f1);
      }
      reader.close();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    catch (IOException e){
      e.printStackTrace();
    }
    return map;
  }
  static HashMap<String, ModelFileClass2> readFile2(ArrayList<String> userNameList)
  {
    HashMap<String, ModelFileClass2> map = new HashMap<String, ModelFileClass2>();
    try {
      CSVReader reader = new CSVReaderBuilder(new FileReader("csvfiles/UBS_PSM_DATA_V4.csv")).withSkipLines(1).build();
      String[] record = null;

      while ((record = reader.readNext()) != null) {

        ModelFileClass2 f2 = new ModelFileClass2();
        f2.setBoLogin(record[0]);
        f2.setGitUserName(record[1]);
        f2.setGitUserProfile(record[2]);
        map.put(record[0], f2);
        if(!record[1].equals("")) {
          userNameList.add(record[1]);
        }
      }
      reader.close();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    catch (IOException e){
      e.printStackTrace();
    }
    return map;
  }
}