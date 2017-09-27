package com.weather.eastwind.testorientation;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Test extends Activity {

    private static final String STATE_C = "string";
    TextView t1;
    Button b1;
    ProgressBar pb;
    Mytask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        t1 = (TextView) findViewById(R.id.txt);
        t1.setMovementMethod(new ScrollingMovementMethod());

        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);

        b1 = (Button) findViewById(R.id.btn);
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                task = new Mytask();
                task.execute("p1", "p2", "p3");
            }
        });

        if (savedInstanceState != null) {

        }

        if (savedInstanceState != null) {
            t1.setText(savedInstanceState.getString(STATE_C));
            if (savedInstanceState.getBoolean(STATE_C, false)) {
                task = new Mytask();
                task.execute("p1", "p2", "p3");
            }
        }
    }

    protected void updateDisplay(String massege) {
        t1.append(massege + "\n");
    }

    private boolean isTaskRunning() {
        return (task != null) && (task.getStatus() == AsyncTask.Status.RUNNING);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_C, t1.getText().toString());
        if (isTaskRunning()) {
            outState.putBoolean(STATE_C, true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (isTaskRunning()) {
            task.cancel(false);
        }
    }

    private class Mytask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            updateDisplay("Starting task");
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {

            for (int i = 0; i < strings.length; i++) {
                publishProgress("Working with  " + strings[i]);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            return "Task Complete";
        }

        @Override
        protected void onPostExecute(String s) {
            updateDisplay(s);
            pb.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            updateDisplay(values[0]);
        }
    }
}

