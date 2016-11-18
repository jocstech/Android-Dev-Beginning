package csci4100.uoit.ca.lab07_database_access;

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

public class DeleteGradeActivity extends AppCompatActivity {

    private TextView txtview ;
    private Spinner spinner;
    private ArrayAdapter<Grade> adapter;
    private ArrayList<Grade> GradeList;
    private int positionSelected;
    private int sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_grade);

        txtview = (TextView) findViewById(R.id.textResult);
        spinner = (Spinner) findViewById(R.id.spinner);
        positionSelected = -1;

        // Get the list
        GradeList = (ArrayList<Grade>) getIntent().getSerializableExtra("list");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, GradeList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
        spinner.setVisibility(View.VISIBLE);

        updateSpinner();

    }

    @Override
    protected void onStart(){
        updateSpinner();
        super.onStart();
    }

    public void delete(View view){
        if(positionSelected != -1){
            Intent backIntent = new Intent();
            backIntent.putExtra("position",this.positionSelected);
            setResult(Activity.RESULT_OK,backIntent);
            finish();
        }else{
            Toast.makeText(this,"Nothing Selected!",Toast.LENGTH_LONG).show();
        }
    }

    public void updateSpinner(){
        spinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
            positionSelected = position;
            sid = GradeList.get(position).getStudentId();
            String selectedID = String.valueOf(sid);
            String selectedName = GradeList.get(position).getCourseComponent();
            String selectedMark = String.valueOf(GradeList.get(position).getMark());
            txtview.setText("[Selected]\n\nID:"+ selectedID+" Comp:"+selectedName+" Mark:"+selectedMark);
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            sid =  -1;
            positionSelected = -1;
            txtview.setText("There is nothing you can delete now!Add some contacts first!");
        }
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
