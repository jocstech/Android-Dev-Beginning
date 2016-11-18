package lab.csci4100u.jocstech.lab_06_storage;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class AddContact extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        setAutoID();
    }

    private void setAutoID(){
        //msg(String.valueOf(getIntent().getIntExtra("id",-1)));
        EditText autoid = (EditText)findViewById(R.id.editTxtID);
        autoid.setText(String.valueOf(getIntent().getIntExtra("id",-1)));
    }

    public void addBtnClicked(View view){
        String result = getFieldValues();
        if(result.split(",").length == 4){
            Intent backIntent = new Intent();
            backIntent.putExtra("newContact", result);
            setResult(Activity.RESULT_OK, backIntent);
            finish();
        }else {
            msg("You must complete the form!");
        }

    }

    private String getFieldValues(){
        String ID = ((EditText)findViewById(R.id.editTxtID)).getText().toString();
        String FirstName = ((EditText)findViewById(R.id.editTxtFirstName)).getText().toString();
        String LastName = ((EditText)findViewById(R.id.editTxtLastName)).getText().toString();
        String Phone = ((EditText)findViewById(R.id.editTxtPhone)).getText().toString();

        return ID+","+FirstName+","+LastName+","+Phone;
    }

    private void msg(String m){
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(this, m, duration);
        toast.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            startActivity(new Intent(this,ShowContacts.class));
            finish();
        }
        return false;
    }
}
