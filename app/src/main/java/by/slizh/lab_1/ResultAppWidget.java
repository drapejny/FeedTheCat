package by.slizh.lab_1;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class ResultAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        final String USER_NAME_KEY = "USER_NAME";
        final String LAST_SCORE_KEY = "LAST_SCORE";
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        int score = sharedPrefs.getInt(LAST_SCORE_KEY, 0);
        String user = sharedPrefs.getString(USER_NAME_KEY, "");
        String text = user + ": " + score;
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.result_app_widget);
        views.setTextViewText(R.id.appwidget_text, text);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}