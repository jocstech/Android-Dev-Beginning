package lab.csci4100u.jocstech.a1basicandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {

    private int currentQ = 0;
    private  int[] ans = {-1,-1,-1,-1,-1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void clickStart(View view){
        //Toast.makeText(MainMenu.this, "clickStart",  Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, AskQuestion.class);
        intent.putExtra("currentQ",currentQ);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == 0) {
            if(resultCode == MainMenu.RESULT_OK){
                Toast.makeText(this, "Question "+(currentQ+1)+" answered successful！",  Toast.LENGTH_SHORT).show();
                int res = intent.getIntExtra("result",-1);

                ans[currentQ]=res;
                currentQ++;

                if(currentQ < 5) {
                    Intent reintent = new Intent(this, AskQuestion.class);
                    reintent.putExtra("currentQ",currentQ);
                    startActivityForResult(reintent, 0);

                } else {
                    Toast.makeText(this, "All Questions Done!",  Toast.LENGTH_SHORT).show();
                    Intent SumIntent = new Intent(this, Summary.class);
                    SumIntent.putExtra("resluts",ans);
                    startActivity(SumIntent);
                }

            }
            if (resultCode == MainMenu.RESULT_CANCELED) {
                Toast.makeText(this, "Unsuccessful!",  Toast.LENGTH_SHORT).show();
            }
        }
    }//onActivityResult
}
