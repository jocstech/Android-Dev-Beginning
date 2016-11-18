package lab.csci4100u.jocstech.lab3_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String usernameDB = "100471536";
    private String passwordDB = "100471536";
    private int resCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
    }

    @Override // When back to this activity, following code will execute.
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 0) {
            String username = data.getStringExtra("username");
            String password = data.getStringExtra("password");

            if ((username.equals(usernameDB)) && (password.equals(passwordDB))) {
                toastMsg(username + ": Login success!");
            } else {
                toastMsg(username + ": Login incorrect");
            }

        }
    }


    public void btnHomeAboutClicked(View view) {
        // New intent for aboutActivity
        Intent aboutIntent = new Intent(this, AboutActivity.class);
        startActivity(aboutIntent);
    }

    public void btnHomeLoginClicked(View view) {
        // New intent for intentActivity
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivityForResult(loginIntent, resCode);
    }

    public void toastMsg(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }


}

