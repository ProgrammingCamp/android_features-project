package com.programmingcamp.androidfeatures;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadTextFileActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnReadFromFile;
    EditText txtFileContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_text_file);

        txtFileContent = (EditText) findViewById(R.id.txtFileContent);
        btnReadFromFile = (Button) findViewById(R.id.btnReadFromFile);
        btnReadFromFile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        BufferedReader reader = null;
        StringBuilder returnString = new StringBuilder();

        try {
            reader = new BufferedReader(
                    new InputStreamReader(this.getAssets().open("sample_file.txt"), "UTF-8"));

            String mLine;
            while ((mLine = reader.readLine()) != null) {
                returnString.append(mLine);
            }

            txtFileContent.setText(returnString.toString());
        }
        catch (IOException e) {
            txtFileContent.setText("");
        }
        finally {
            if (reader != null) {
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {
                    //log the exception
                }
            }
        }
    }
}
