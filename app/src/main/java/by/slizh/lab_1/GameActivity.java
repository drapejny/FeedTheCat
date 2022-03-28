package by.slizh.lab_1;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import by.slizh.lab_1.entity.Achievement;

public class GameActivity extends AppCompatActivity {

    private long TIMER_PERIOD = 5;
    private long START_POS_Y = 0;

    private AtomicBoolean isPlaying;

    private ImageView food;
    private ImageView cat;
    private View targetCircle;
    private Button shotButton;
    private TextView scoreText;

    private float targetPosY;
    private float finishPosY;

    private float currentPosY;

    private float speed;

    private boolean foodFlag;

    private Timer timer = new Timer();
    private Handler handler = new Handler();

    private int score = 0;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        food = findViewById(R.id.foodImageView);
        cat = findViewById(R.id.gameCatImageView);
        targetCircle = findViewById(R.id.gameTargetCircleView);
        shotButton = findViewById(R.id.shotButton);
        scoreText = findViewById(R.id.scoreTextView);
        isPlaying = new AtomicBoolean(false);
        shotButton.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    onTouchButton(view);
                    return true;
                }
                return false;
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    private void stopGame() {
        isPlaying.compareAndSet(true, false);
        timer.cancel();
        shotButton.setText(getResources().getString(R.string.play_button));
        updateWidget();
        writeResult();
    }

    private void resetGameRound() {
        timer.cancel();
        resetSpeed();
        resetFood();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(() -> {
                    currentPosY += speed;
                    food.setY(currentPosY);
                    if (currentPosY > finishPosY) {
                        if (foodFlag) {
                            score++;
                            checkScore();
                            if (score % 3 == 0) {
                                playCatAnimation();
                            }
                            showScore();
                            resetGameRound();
                        } else {
                            food.setVisibility(View.INVISIBLE);
                            if (isPlaying.get()) {
                                stopGame();
                            }
                        }
                    }
                });
            }
        }, 0, TIMER_PERIOD);
    }

    private void updateWidget() {
        final String USER_NAME_KEY = "USER_NAME";
        final String LAST_SCORE_KEY = "LAST_SCORE";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_NAME_KEY, MainActivity.user.getName());
        editor.putInt(LAST_SCORE_KEY, score);
        editor.apply();
        Intent intent = new Intent(this, ResultAppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(getApplication())
                .getAppWidgetIds(new ComponentName(getApplication(), ResultAppWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);
    }

    private void writeResult() {
        try {
            DataWriter.writeResultsFile(score, this);
        } catch (IOException e) {
            Log.e("File error", "Cant write results file");
        }
    }

    public void checkScore() {
        if (score > MainActivity.user.getHighScore()) {
            MainActivity.user.setHighScore(score);
            checkAchievements();
            try {
                DataWriter.writeUsersFile(MainActivity.users, this);
            } catch (IOException e) {
                Log.e("File error", "Cant write users file");
            }
        }
    }

    public void checkAchievements() {
        Achievement achievement;
        switch (score) {
            case 3:
                achievement = Achievement.BEGINNER;
                break;
            case 6:
                achievement = Achievement.JUNIOR;
                break;
            default:
                return;
        }
        List<Achievement> achievements = MainActivity.user.getAchievements();
        if (!achievements.contains(achievement)) {
            achievements.add(achievement);
            showToast(achievement.getTitle() + "\n(" + achievement.getDescription() + ")");
        }
    }

    private void showToast(String text) {
        Toast toast = Toast.makeText(getApplicationContext(),
                text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void onTouchButton(View view) {
        if (isPlaying.get()) {
            if (currentPosY > (int) targetPosY - 20
                    && (int) currentPosY + food.getHeight() < (int) targetPosY + targetCircle.getHeight() + 20) {
                if (foodFlag) {
                    if (isPlaying.get()) {
                        stopGame();
                    }
                } else {
                    resetGameRound();
                }
            }
        } else {
            initializePositions();
            food.setVisibility(View.VISIBLE);
            shotButton.setText(getResources().getString(R.string.shot_button));
            isPlaying.compareAndSet(false, true);
            resetScore();
            resetGameRound();
        }
    }

    private void resetSpeed() {
        int minSpeed = 5;
        int maxSpeed = 7;
        Random random = new Random();
        speed = random.nextInt(maxSpeed - minSpeed + 1) + minSpeed;
    }

    private void resetFood() {
        currentPosY = START_POS_Y;
        food.setY(START_POS_Y);
        int minFoodNum = 0;
        int maxFoodNum = 3;
        Random random = new Random();
        int foodNum = random.nextInt(maxFoodNum - minFoodNum) + minFoodNum;
        switch (foodNum) {
            case 0:
                foodFlag = true;
                food.setImageResource(R.drawable.fish);
                break;
            case 1:
                foodFlag = false;
                food.setImageResource(R.drawable.orange);
                break;
            case 2:
                foodFlag = false;
                food.setImageResource(R.drawable.carrot);
                break;
        }
    }

    private void playCatAnimation() {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.cat_animation);
        set.setTarget(cat);
        set.start();
    }

    private void showScore() {
        String text = getResources().getString(R.string.score_text);
        text = text + " " + score;
        scoreText.setText(text);
    }

    private void resetScore() {
        score = 0;
        showScore();
    }

    private void initializePositions() {
        targetPosY = targetCircle.getY();
        finishPosY = cat.getY();
    }
}