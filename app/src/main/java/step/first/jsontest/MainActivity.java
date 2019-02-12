package step.first.jsontest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText et1,et2,et3;
    TextView tv1;
    Button bt ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = (EditText)findViewById(R.id.et1);
        et2 =(EditText)findViewById(R.id.eT2);
        et3 = (EditText)findViewById(R.id.eT3);
        bt = (Button)findViewById(R.id.bt);
        tv1 =(TextView)findViewById(R.id.tV5);

                final Gson js = new Gson();

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if((!et1.getText().toString().isEmpty()) ||(!et2.getText().toString().isEmpty()) || (!et3.getText().toString().isEmpty()))
                {
                    try {

                        person p = new person();
                        p.setName(et1.getText().toString().trim());
                        p.setAge(Integer.parseInt(et2.getText().toString().trim()));
                        p.setSal(Integer.parseInt(et3.getText().toString().trim()));

                        String json = js.toJson(p, person.class);

                        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo ni = cm.getActiveNetworkInfo();
                        if (ni != null && cm != null) {


                            mytask mt = new mytask();
                            mt.execute(json);


                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Please check Your Connection", Toast.LENGTH_LONG).show();

                        }
                    }
                    catch(Throwable ex)
                        {
                            Toast.makeText(MainActivity.this, "error" + ex, Toast.LENGTH_LONG).show();
                        }


                }
                else
                {
                    Toast.makeText(MainActivity.this,"Please Enter Right Data", Toast.LENGTH_LONG).show();

                }



            }
        });







    }

    class mytask extends AsyncTask<String,Void,String>
    {


        @Override
        protected String doInBackground(String... strings) {
            Looper.prepare();
            String ans ="ok";
            try {
                String jsonstr = strings[0];
                URL url = new URL("http://192.168.1.214:2030/Mywhatsapp/gsontest.jsp");
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type","application/json");
                con.setRequestProperty("Accept","application/json");
                PrintWriter pw = new PrintWriter(con.getOutputStream());
                pw.write(jsonstr);
                pw.close();
                InputStream in = con.getInputStream();

                BufferedReader bff = new BufferedReader(new InputStreamReader(in));
                StringBuffer buf = new StringBuffer();
                String line ;
                while((line =bff.readLine())!=null)
                    buf.append(line+"\n");

                ans = buf.toString();






            }
            catch(Throwable th)
            {
                Toast.makeText(MainActivity.this,"error"+th, Toast.LENGTH_LONG).show();
            }



            return ans;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            tv1.setText(s.trim());
        }
    }
}
