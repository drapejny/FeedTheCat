package by.slizh.lab_1;

import static com.google.android.gms.common.GooglePlayServicesUtilLight.isGooglePlayServicesAvailable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import by.slizh.lab_1.entity.Achievement;
import by.slizh.lab_1.entity.User;

public class MainActivity extends AppCompatActivity {

    public static List<User> users;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        initializeUsers(account.getId(), account.getDisplayName());
        TextView userNameTextView = (TextView) findViewById(R.id.userNameTextView);
        userNameTextView.setText(user.getName());
    }

    private void initializeUsers(String userId, String userName) {
        try {
            users = DataReader.readUsers(this);
        } catch (IOException e) {
            Log.e("File error", "Cant read file");
        }
        for (User u : users) {
            if (u.getId().equals(userId)) {
                user = u;
                return;
            }
        }
        user = new User(userId, userName, 0, new ArrayList<Achievement>());
        users.add(user);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.item_about_developer:
                intent = new Intent(this, AboutDevActivity.class);
                break;
            case R.id.item_rules:
                intent = new Intent(this, RulesActivity.class);
                break;
            case R.id.item_results:
                intent = new Intent(this, ResultsActivity.class);
                break;
            case R.id.item_share_result:
                shareResultInSocialMedia();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        startActivity(intent);
        return true;
    }

    private boolean isUserExist() {
        for (User u : users) {
            if (u.getId().equals(user.getId())) {
                return true;
            }
        }
        return false;
    }

    private void shareResultInSocialMedia() {
        Intent myIntent = new Intent(Intent.ACTION_SEND);
        myIntent.setType("text/plain");
        String text = getResources().getString(R.string.shared_in_social_media_msg);
        text += " ";
        text += user.getHighScore();
        myIntent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(myIntent, "Share Using"));
    }

    public void feed(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
//        ImageView catImageView = (ImageView) findViewById(R.id.catImageView);
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        posX += 5;
//                        catImageView.setX(posX);
//                    }
//                });
//            }
//        }, 0, 20);
//        TextView satietyNumberTextView = (TextView) findViewById(R.id.numberTextView);
//        Integer satietyNumber = Integer.parseInt(satietyNumberTextView.getText().toString());
//        satietyNumber++;
//        satietyNumberTextView.setText(satietyNumber.toString());
//        if (satietyNumber % 3 == 0) {
//            ImageView catImageView = (ImageView) findViewById(R.id.catImageView);
//            AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.cat_animation);
//            set.setTarget(catImageView);
//            set.start();
//        }
    }
}