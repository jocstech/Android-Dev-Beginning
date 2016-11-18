package lab.csci4100u.jocstech.lab3_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by jocs on 2016-09-30.
 */

public class LoginActivity extends AppCompatActivity {

    private String username;
    private String password;
    private int resCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public void btnLoginLoginClicked(View view) {

        username = ((EditText) findViewById(R.id.editTextUsername)).getText().toString();
        password = ((EditText) findViewById(R.id.editTextPassword)).getText().toString();

        //Toast.makeText(getApplicationContext(), username, Toast.LENGTH_SHORT).show();

        // Sending the username and password
        Intent resultIntent = new Intent();
        resultIntent.putExtra("username", username);
        resultIntent.putExtra("password", password);
        setResult(resCode, resultIntent);

        finish();
    }


}
