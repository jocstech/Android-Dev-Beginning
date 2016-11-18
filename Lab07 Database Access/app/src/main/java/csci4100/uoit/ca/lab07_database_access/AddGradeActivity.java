package csci4100.uoit.ca.lab07_database_access;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class AddGradeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grade);

    }

    public void addGrade(View view){
        String result = getFieldValues();
        if(result.split(",").length == 3){
            Intent backIntent = new Intent();
            backIntent.putExtra("newGrade", result);
            setResult(Activity.RESULT_OK, backIntent);
            finish();
        }else {
            Toast.makeText(getApplicationContext(),"You must complete the form!",Toast.LENGTH_LONG).show();
        }

    }

    private String getFieldValues(){
        String ID = ((EditText)findViewById(R.id.editId)).getText().toString();
        String Comp = ((EditText)findViewById(R.id.editComp)).getText().toString();
        String Mark = ((EditText)findViewById(R.id.editMark)).getText().toString();

        return ID+","+Comp+","+Mark;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            startActivity(new Intent(this,ShowGradesActivity.class));
            finish();
        }
        return false;
    }

}
