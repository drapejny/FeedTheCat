package by.slizh.lab_1;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import by.slizh.lab_1.entity.Achievement;
import by.slizh.lab_1.entity.Result;
import by.slizh.lab_1.entity.User;

public final class DataReader {

    private static final String USERS_FILE_PATH = "users";
    private static final String RESULTS_FILE_PATH = "results";
    private static final String DELIMITER_REGEX = ",";

    public static List<Result> readResults(String userId, Context context) throws IOException {
        List<Result> results = new ArrayList<>();
        FileInputStream fis = context.openFileInput(RESULTS_FILE_PATH);
        InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
        try (BufferedReader br = new BufferedReader(inputStreamReader)) {
            String line = br.readLine();
            while (line != null) {
                String[] words = line.split(DELIMITER_REGEX);
                LocalDateTime dateTime = LocalDateTime.parse(words[0]);
                String id = words[1];
                String name = words[2];
                int score = Integer.parseInt(words[3]);
                if(id.equals(userId)){
                    results.add(new Result(dateTime, id, name, score));
                }
                line = br.readLine();
            }
        }
        Collections.reverse(results);
        return results;
    }

    public static List<User> readUsers(Context context) throws IOException {
        List<User> users = new ArrayList<>();
        FileInputStream fis = context.openFileInput(USERS_FILE_PATH);
        InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
        try (BufferedReader br = new BufferedReader(inputStreamReader)) {
            String line = br.readLine();
            while (line != null) {
                String[] words = line.split(DELIMITER_REGEX);
                String id = words[0];
                String name = words[1];
                int highScore = Integer.parseInt(words[2]);
                List<Achievement> achievements = new ArrayList<>();
                for (int i = 3; i < words.length; i++) {
                    achievements.add(Achievement.valueOf(words[i]));
                }
                users.add(new User(id, name, highScore, achievements));
                line = br.readLine();
            }
        }
        return users;
    }
}
