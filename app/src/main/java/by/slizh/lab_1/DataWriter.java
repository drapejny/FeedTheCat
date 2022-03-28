package by.slizh.lab_1;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.util.List;

import by.slizh.lab_1.entity.Achievement;
import by.slizh.lab_1.entity.User;

public final class DataWriter {

    private static final String USERS_FILE_NAME = "users";
    private static final String RESULTS_FILE_NAME = "results";
    private static final String DELIMITER_REGEX = ",";

    public static void writeResultsFile(int score, Context context) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(RESULTS_FILE_NAME, Context.MODE_APPEND)))) {
            String resultLine = LocalDateTime.now().toString() + DELIMITER_REGEX
                    + MainActivity.user.getId() + DELIMITER_REGEX
                    + MainActivity.user.getName() + DELIMITER_REGEX
                    + score;
            bw.write(resultLine);
            bw.newLine();
        }
    }

    public static void writeUsersFile(List<User> users, Context context) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(USERS_FILE_NAME, Context.MODE_PRIVATE)))) {
            for (User user : users) {
                String line = user.getId() + DELIMITER_REGEX
                        + user.getName() + DELIMITER_REGEX
                        + user.getHighScore();
                for (Achievement achievement : user.getAchievements()) {
                    line += DELIMITER_REGEX + achievement;
                }
                bw.write(line);
                bw.newLine();
            }
        }
    }
}
