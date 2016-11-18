/**
 * Coded By Yulong Fang #100471536
 */

package csci4100.uoit.ca.a2pricebrowsing;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

public class BrowseProductsActivity extends AppCompatActivity {

    EditText name, description, price_dollar, price_bit;
    Button btnNext, btnPrev, btnDelete;

    ProductDBHelper dbHelper;
    ArrayList<Product> list;

    int currentProductIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_products_activity);

        //Hide the keyboard input method
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        init();

    }

    private void init(){

        dbHelper = new ProductDBHelper(this);

        name = (EditText)findViewById(R.id.editName);
        description = (EditText)findViewById(R.id.editDescription);
        price_dollar = (EditText)findViewById(R.id.editPriceDollar);
        price_bit = (EditText)findViewById(R.id.editPriceBitCoin);

        btnNext = (Button)findViewById(R.id.btnNext);
        btnPrev = (Button)findViewById(R.id.btnPrev);
        btnDelete = (Button)findViewById(R.id.btnDelete);

        list = new ArrayList<>();
        currentProductIndex = 0;

    }

    /**
     * Deal with the button events:
     * @param view
     */
    public void process(View view){
        switch (view.getId()){
            case R.id.btnPrev:
                showPrevProduct();
                break;
            case R.id.btnNext:
                showNextProduct();
                break;
            case R.id.btnDelete:
                deleteCurrentProduct();
                break;
            default:
                break;
        }
    }


    /**
     * Create the options menu on the action bar
     * @param menu
     * @return true;
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_add:
                Intent intent = new Intent(this,AddProductActivity.class);
                intent.putExtra("id",list.size());
                startActivityForResult(intent,1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1) {
            addNewProduct(data.getExtras().getString("newProduct"));
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        retriveFromDatabase();

    }

    @Override
    protected void onStop() {
        super.onStop();
        syncToDatabase();
        list.clear();

    }


    private void showNextProduct(){
        int count = list.size();
        if(count>0){
            if(count==1){
                showProduct(list.get(0));
                Toast.makeText(getApplicationContext(),"Only One Product Found!",Toast.LENGTH_SHORT).show();
            } else {
                if(currentProductIndex < count-1){
                    btnPrev.setEnabled(true);
                    currentProductIndex++;
                showProduct(list.get(currentProductIndex));
                }
                else {
                    btnNext.setEnabled(false);
                    Toast.makeText(getApplicationContext(),"This Is The Last Product!",Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            btnNext.setEnabled(false);
            btnPrev.setEnabled(false);
            Toast.makeText(getApplicationContext(),"No Product Has Been Added!",Toast.LENGTH_SHORT).show();
        }

        Log.i("[Debug]", "[Count]:"+String.valueOf(count)+"[Index]:"+String.valueOf(currentProductIndex));
    }

    private void showPrevProduct(){
        int count = list.size();

        if(count>0){
            if(count==1){
                showProduct(list.get(0));
                Toast.makeText(getApplicationContext(),"Only One Product Found!",Toast.LENGTH_SHORT).show();
            } else {
                if(currentProductIndex > 0){
                    btnNext.setEnabled(true);
                    currentProductIndex--;
                    showProduct(list.get(currentProductIndex));
                } else {
                    btnPrev.setEnabled(false);
                    Toast.makeText(getApplicationContext(),"This Is The First Product!",Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            btnNext.setEnabled(false);
            btnPrev.setEnabled(false);
            Toast.makeText(getApplicationContext(),"No Product Has Been Added!",Toast.LENGTH_SHORT).show();
        }
        Log.i("[Debug]", "[Count]:"+String.valueOf(count)+"[Index]:"+String.valueOf(currentProductIndex));
    }

    private void addNewProduct(String productStr){

        String data[] = productStr.split(",");
        int id              = Integer.parseInt(data[0]);
        String name         = data[1];
        String description  = data[2];
        float price         = Float.parseFloat(data[3]);

        list.add(new Product(id,name,description,price));
        showProduct(list.get(0));
        currentProductIndex = 0;

    }

    private void deleteCurrentProduct(){
        int count = list.size();
        if(count > 0){
            if( count == 1 ) {
                clearFields();
                list.clear();
                currentProductIndex =-1;
            }
            else if(count == 2){
                if(currentProductIndex==0){
                    showProduct(list.get(1));
                    list.remove(0);
                } else {
                    // only the last element in two.
                    showProduct(list.get(0));
                    list.remove(1);

                }
            } else {
                if(currentProductIndex==0){
                    showProduct(list.get(1));
                    list.remove(0);
                } else if(currentProductIndex==count-1){
                    showProduct(list.get(currentProductIndex-1));
                    list.remove(count-1);
                    currentProductIndex--;
                } else {
                    list.remove(currentProductIndex);
                    showProduct(list.get(currentProductIndex));
                }
            }
        } else {
            btnNext.setEnabled(false);
            btnPrev.setEnabled(false);
           Toast.makeText(getApplicationContext(),"Nothing Could Be Deleted!",Toast.LENGTH_SHORT).show();
        }

        Log.i("[Debug]", "[Count]:"+String.valueOf(count)+"[Index]:"+String.valueOf(currentProductIndex));
    }




    private void showProduct(Product product){
        if(list.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Nothing Could Be Displayed!",Toast.LENGTH_SHORT).show();
        } else {
            float price_cad = product.getPrice();
            name.setText(product.getName());
            description.setText(product.getDescription());
            price_dollar.setText(String.valueOf(price_cad));
            //conversion
            float price = convertToBitCoin(price_cad);
            price_bit.setText(String.valueOf(price));
        }
    }

    private void clearFields(){
        name.setText("");
        description.setText("");
        price_dollar.setText("");
        price_bit.setText("");
    }


    private float convertToBitCoin(float CAD){
        String url = "https://blockchain.info/tobtc?currency=CAD&value="+String.valueOf(CAD);
        CADtoBitCoinTask task = new CADtoBitCoinTask();
        task.execute(url);
        float bitcoin = -1f;
        try {
            bitcoin = task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return bitcoin;
    }



    private void retriveFromDatabase(){

        list.addAll(dbHelper.getAllProducts());
        if(!list.isEmpty()){
            showProduct(list.get(0));
            currentProductIndex=0;
            if(list.size()>1){
                btnNext.setEnabled(true);
            }
        } else {
            currentProductIndex=-1;
            btnNext.setEnabled(false);
        }
        btnPrev.setEnabled(false);
    }

    private void syncToDatabase(){

        dbHelper.deleteAllProducts();

        Iterator<Product> itr = list.iterator();

        while(itr.hasNext()){
            Product pro = itr.next();
            dbHelper.addNewProduct(pro.getProductId(),pro.getName(),pro.getDescription(),pro.getPrice());
        }
    }

    private class CADtoBitCoinTask extends AsyncTask<String ,Integer,Float>{

        private Exception exception = null;

        @Override
        protected Float doInBackground(String... urls) {
            float bitcoin = -1f;
            try{
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.connect();
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                if (br != null) {
                     String line = br.readLine();
                     bitcoin = Float.parseFloat(line);
                     br.close();
                 }
            } catch (IOException e){
                exception = e;
                e.printStackTrace();
            }
            return bitcoin;
        }

        @Override
        protected void onPostExecute(Float bitcoin) {
            if(exception != null){
                exception.printStackTrace();
                return;
            }
        }
    }


}


