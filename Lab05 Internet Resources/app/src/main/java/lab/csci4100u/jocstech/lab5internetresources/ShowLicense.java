package lab.csci4100u.jocstech.lab5internetresources;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by jocs on 2016-10-21.
 */

public class ShowLicense extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_licenses);

        getLicenseResourceTask task = new getLicenseResourceTask();
        task.execute("https://www.gnu.org/licenses/gpl.txt");

    }


    private class getLicenseResourceTask extends AsyncTask<String, Integer, Void>{

        private String licenseText = "";
        private Exception exception = null;


        @Override
        protected Void doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);

                HttpURLConnection urlConnection =(HttpURLConnection)url.openConnection();

                int responseCode = urlConnection.getResponseCode();

                if(responseCode == 200){
                    InputStream in = urlConnection.getInputStream();
                    if ( in != null ) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                        String line ;
                        while ((line = bufferedReader.readLine()) != null)
                            licenseText += line;
                    }
                    in.close();
                }

            }
            catch (Exception e)
            {
                exception = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if(exception != null){
                exception.printStackTrace();
                return;
            }
            TextView field =  (TextView)findViewById(R.id.editText1);
            field.setText(licenseText);
        }


    }


    public void close(View view){
        finish();
    }

}




