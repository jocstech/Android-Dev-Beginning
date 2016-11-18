package lab.csci4100u.jocstech.lab_06_storage;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DeleteContact extends AppCompatActivity {

    private TextView txtview ;
    private Spinner spinner;
    private ArrayAdapter<Contact> adapter;
    private ArrayList<Contact> contactList;
    private int positionSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_contact);


        positionSelected = -1;

        txtview = (TextView) findViewById(R.id.txtSelected);
        spinner = (Spinner) findViewById(R.id.spinner);


        // Get the list
        contactList = (ArrayList<Contact>) getIntent().getSerializableExtra("list");;

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, contactList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
        spinner.setVisibility(View.VISIBLE);
    }

    public void onClicked(View view){
        if(positionSelected != -1){
            Intent backIntent = new Intent();
            backIntent.putExtra("position",positionSelected);
            setResult(Activity.RESULT_OK,backIntent);
            finish();
        }else{
            msg("Nothing Selected!");
        }
    }

    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
            positionSelected = position;
            int selectedID = contactList.get(position).getID();
            String selectedName = contactList.get(position).getName();
            txtview.setText("Selected ID ："+ selectedID+" "+selectedName);
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            positionSelected = -1;
            txtview.setText("There is nothing you can delete now!Add some contacts first!");
        }
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


    private void msg(String m){
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(this, m, duration);
        toast.show();
    }
}

