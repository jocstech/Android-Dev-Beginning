/**
 * Coded By Yulong Fang #100471536
 */

package csci4100.uoit.ca.a2pricebrowsing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddProductActivity extends AppCompatActivity {

    private int CurrentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product_activity);
        this.setTitle(R.string.addnewproduct);

        CurrentID = getIntent().getIntExtra("id",-1);

        //Toast.makeText(getApplicationContext(),String.valueOf(CurrentID),Toast.LENGTH_LONG).show();

    }

    public void process(View view){
        switch(view.getId()){
            case R.id.btnSave:
                saveData();
                break;
            case R.id.btnCancel:
                Cancel();
                break;
            default:
                break;
        }
    }

    private void saveData(){
        String result = getFieldsValue();

        if(result.split(",").length == 4){
            Intent backIntent = new Intent();
            backIntent.putExtra("newProduct", result);
            setResult(Activity.RESULT_OK, backIntent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(),"You must complete the form!",Toast.LENGTH_LONG).show();
        }

    }

    private void Cancel(){
        startActivity(new Intent(this,BrowseProductsActivity.class));
    }

    private String getFieldsValue(){
        String name = ((EditText)findViewById(R.id.editName_add)).getText().toString();
        String des = ((EditText)findViewById(R.id.editDescription_add)).getText().toString();
        String price = ((EditText)findViewById(R.id.editPriceDollar_add)).getText().toString();

        return CurrentID+","+name+","+des+","+price;
    }

}
