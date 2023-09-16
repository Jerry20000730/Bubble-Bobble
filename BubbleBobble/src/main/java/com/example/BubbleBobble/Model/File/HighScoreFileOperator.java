package com.example.BubbleBobble.Model.File;

import com.example.BubbleBobble.Model.User.User;

import java.io.*;
import java.util.*;

/**
 * The class to read and write high score into the file, as well as sorting the list after the file is generated or updated.
 * So that the high score is permanently stored.
 */
public class HighScoreFileOperator {
    private static Map<String, Integer> map = new HashMap<String, Integer>();

    /**
     * Methods handling writing player name and score to local text file
     * @param playerName the string format of player name
     * @param score the int format of score
     */
    public static void WriteScoreToTextFile(String playerName, int score) {
        try {
            File file =new File("high_score.txt");
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getName(), true);
            fw.write(playerName + "," + score);
            fw.write("\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The Method to load information into a hashmap, from the local high-score text file
     * @return the hashmap containing all the information regarding player name(String) and score(Integer)
     * @throws IOException
     */
    public static Map<String, Integer> LoadHashMapFromTextFile() throws IOException {
        BufferedReader br = null;
        try {
            File file = new File("high_score.txt");
            br = new BufferedReader(new FileReader(file));
            String line = null;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
                String[] info = line.split(",");

                String name = info[0].trim();
                String number_string = info[1].trim();
                Integer score = Integer.valueOf(number_string);

                if (!name.equals("") && !number_string.equals("")) {
                    map.put(name, score);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try{
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    /**
     * The Method to sort the hashmap, from the previous reading process
     * @return the hashmap of sorted sequence
     */
    public static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap) {
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        HashMap<String, Integer> sortedList = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            sortedList.put(aa.getKey(), aa.getValue());
        }
        return sortedList;
    }

    /**
     * Methods to load map into a temp list for future use to display data in the table view, store the data in User class
     * @param map the hashmap containing player name(String)(Key) and score(Integer)(Value)
     * @return a temp list of User type containing all the information of the user
     */
    public static List<User> LoadMapIntoTempList(Map<String, Integer> map) {
        List<User> tempHighScoreList = new LinkedList<User>();
        Set<Map.Entry<String, Integer>> set = map.entrySet();
        for (Map.Entry<String, Integer> entryset : set) {
            User user = new User(entryset.getKey(), entryset.getValue());
            tempHighScoreList.add(user);
        }
        return tempHighScoreList;
    }
}
