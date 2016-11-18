package lab.csci4100u.jocstech.a1basicandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Summary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);


        Intent resIntent = getIntent();

        int res[] = resIntent.getIntArrayExtra("resluts");

        TextView display  = (TextView) findViewById(R.id.textSummary);
        String output = "";

        for(int i = 0 ; i < res.length ; i++){
            String o = "";
            if(res[i]==1) o = "Yes";
                else o = "No";
            output+="Answer for Question "+(i+1)+" is: "+o+"\n";
        }

        display.setText(output);
    }
}
