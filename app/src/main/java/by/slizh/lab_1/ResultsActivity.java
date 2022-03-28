package by.slizh.lab_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import by.slizh.lab_1.entity.Result;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        List<Result> results = null;
        try {
            results = DataReader.readResults(MainActivity.user.getId(), this);
        } catch (IOException e) {
            Log.e("File error", "Cant read results file");
        }
        TableLayout resultsTableLayout = findViewById(R.id.resultsTableLayout);
        System.out.println(results);
        for (int i = 0; i < results.size(); i++) {
            addTableRow(resultsTableLayout, results.get(i), i);
        }
    }

    private void addTableRow(TableLayout tableLayout, Result result, int number) {
        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        addTextView(tableRow, result.getDateTime().toString(), 0);
        addTextView(tableRow, result.getUserName(), 1);
        addTextView(tableRow, Integer.toString(result.getScore()), 2);
        tableLayout.addView(tableRow, number);
    }

    private void addTextView(TableRow tableRow, String text, int number) {
        TextView textView = new TextView(this);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(5,5,5,5);
        textView.setText(text);
        tableRow.addView(textView, number);
    }

}