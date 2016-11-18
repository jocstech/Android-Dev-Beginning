package lab.csci4100u.jocstech.a1basicandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AskQuestion extends AppCompatActivity {

    private int currentQue;
    private String[] questionSet;
    private Intent oIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);
        //Toast.makeText(this, "Layout.AskQuestion",  Toast.LENGTH_LONG).show();

        questionSet = getResources().getStringArray(R.array.survey_questions);

        // get connection from main menu
        oIntent = getIntent();
        currentQue = oIntent.getIntExtra("currentQ",-1);
        setCurrentQueLayout(currentQue);
    }


    public void yesClicked(View view) {
        // return result to main menu.
        Intent returnIntent = new Intent(this, MainMenu.class);
        returnIntent.putExtra("result",1); // 1 means yes
        setResult(AskQuestion.RESULT_OK,returnIntent);
        finish();
    }

    public void noClicked(View view) {
        // return result to main menu.
        Intent returnIntent = new Intent(this, MainMenu.class);
        returnIntent.putExtra("result",0); // 0 means no
        setResult(AskQuestion.RESULT_OK,returnIntent);
        finish();
    }


    private void setCurrentQueLayout(int num){

        TextView question_display = (TextView) findViewById(R.id.textQuestions);
        question_display.setText(questionSet[num]);

    }

}
