package com.fogus.traveltopsis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;
import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity {

    private final double[] NY = {239,4.6,62,6};
    private final double[] Tokyo = {177,4.45,40,2};
    private final double[] Santorini = {109,4.45,53,27};
    private final double[] Rio = {77,4.55,161,26};
    private final double[] Bucharest = {127,4.4,42,63};
    private final double[] Cairo = {28,4.45,120,33};
    private final double[] Sydney = {154,4.5,19,5};

    private final double[][] destination_matrix = new double[7][4];
    private int des_size = 0;
    private final String[] check_des = new String[7];

    private int btn_text = 0;
    private Button main_btn;
    private TextView db_text;
    private TextView aa_text;
    private TextView si_text;
    private TextView p_text;
    private SeekBar db_seek, aa_seek, si_seek, p_seek;
    private CheckBox ny_c, tk_c, sn_c, rio_c, bc_c, c_c, s_c;
    private LinearLayout weights,destinations,lonely;
    private WebView dest_site;
    private double db_weight, aa_weight, si_weight, p_weight;

    public MainActivity() {
    }

    @SuppressLint({"SetTextI18n", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.app_name);

        main_btn = findViewById(R.id.start_button);
        weights = findViewById(R.id.Weights);
        weights.setVisibility(View.INVISIBLE);
        destinations = findViewById(R.id.Destinations);
        destinations.setVisibility(View.INVISIBLE);
        lonely = findViewById(R.id.Lonely);
        lonely.setVisibility(View.INVISIBLE);
        dest_site = findViewById(R.id.best_destination);
        dest_site.getSettings().setDomStorageEnabled(true);
        dest_site.getSettings().setJavaScriptEnabled(true);
        dest_site.getSettings().setSupportZoom(true);
        dest_site.getSettings().setBuiltInZoomControls(false);
        dest_site.getSettings().setUserAgentString("Android");
        dest_site.setForceDarkAllowed(false);

        db_text = findViewById(R.id.db_num);
        aa_text = findViewById(R.id.aa_num);
        si_text = findViewById(R.id.si_num);
        p_text = findViewById(R.id.p_num);

        db_seek = findViewById(R.id.db_seekBar);
        aa_seek = findViewById(R.id.aa_seekBar);
        si_seek = findViewById(R.id.si_seekBar);
        p_seek = findViewById(R.id.p_seekBar);

        db_text.setText(db_seek.getProgress()+"/"+db_seek.getMax() );
        aa_text.setText(aa_seek.getProgress()+"/"+aa_seek.getMax() );
        si_text.setText(si_seek.getProgress()+"/"+si_seek.getMax() );
        p_text.setText(p_seek.getProgress()+"/"+p_seek.getMax() );

        ny_c = findViewById(R.id.check_ny);
        tk_c = findViewById(R.id.check_tokyo);
        sn_c = findViewById(R.id.check_santorini);
        bc_c = findViewById(R.id.check_bucharest);
        rio_c = findViewById(R.id.check_rio);
        c_c = findViewById(R.id.check_cairo);
        s_c = findViewById(R.id.check_sydney);

        //-------------Seekbars Util----------------------
        db_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                db_text.setText(progress+"/"+db_seek.getMax() );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        aa_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                aa_text.setText(progress+"/"+aa_seek.getMax() );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        si_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                si_text.setText(progress+"/"+si_seek.getMax() );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        p_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                p_text.setText(progress+"/"+p_seek.getMax() );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //------------------------------------------------
        //-------------Main Button Util-------------------
        main_btn.setOnClickListener(v -> {
            switch (btn_text) {
                case 0:
                    //Weight Submit
                    Log.i(TAG,"TOPSIS: User defines weights for criteria");
                    btn_text = 1;
                    main_btn.setText(R.string.submit);
                    weights.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    //Destinations Submit
                    Log.i(TAG,"TOPSIS: User defines possible travel destinations");
                    weights.setVisibility(View.INVISIBLE);
                    double pro_sum = db_seek.getProgress() + aa_seek.getProgress() + si_seek.getProgress() + p_seek.getProgress();
                    db_weight = db_seek.getProgress()/pro_sum;
                    aa_weight = aa_seek.getProgress()/pro_sum;
                    si_weight = si_seek.getProgress()/pro_sum;
                    p_weight = p_seek.getProgress()/pro_sum;
                    destinations.setVisibility(View.VISIBLE);
                    btn_text = 2;
                    break;
                case 2:
                    //Finishing

                    if(ny_c.isChecked())
                    {
                        destination_matrix[des_size][0]= NY[0];
                        destination_matrix[des_size][1]= NY[1];
                        destination_matrix[des_size][2]= NY[2];
                        destination_matrix[des_size][3]= NY[3];
                        check_des[des_size] = "NY";
                        des_size++;
                    }
                    if(tk_c.isChecked())
                    {
                        destination_matrix[des_size][0]= Tokyo[0];
                        destination_matrix[des_size][1]= Tokyo[1];
                        destination_matrix[des_size][2]= Tokyo[2];
                        destination_matrix[des_size][3]= Tokyo[3];
                        check_des[des_size] = "Tokyo";
                        des_size++;
                    }
                    if(sn_c.isChecked())
                    {
                        destination_matrix[des_size][0]= Santorini[0];
                        destination_matrix[des_size][1]= Santorini[1];
                        destination_matrix[des_size][2]= Santorini[2];
                        destination_matrix[des_size][3]= Santorini[3];
                        check_des[des_size] = "Santorini";
                        des_size++;
                    }
                    if(bc_c.isChecked())
                    {
                        destination_matrix[des_size][0]= Bucharest[0];
                        destination_matrix[des_size][1]= Bucharest[1];
                        destination_matrix[des_size][2]= Bucharest[2];
                        destination_matrix[des_size][3]= Bucharest[3];
                        check_des[des_size] = "Bucharest";
                        des_size++;
                    }
                    if(rio_c.isChecked())
                    {
                        destination_matrix[des_size][0]= Rio[0];
                        destination_matrix[des_size][1]= Rio[1];
                        destination_matrix[des_size][2]= Rio[2];
                        destination_matrix[des_size][3]= Rio[3];
                        check_des[des_size] = "Rio";
                        des_size++;
                    }
                    if(c_c.isChecked())
                    {
                        destination_matrix[des_size][0]= Cairo[0];
                        destination_matrix[des_size][1]= Cairo[1];
                        destination_matrix[des_size][2]= Cairo[2];
                        destination_matrix[des_size][3]= Cairo[3];
                        check_des[des_size] = "Cairo";
                        des_size++;
                    }
                    if(s_c.isChecked())
                    {
                        destination_matrix[des_size][0]= Sydney[0];
                        destination_matrix[des_size][1]= Sydney[1];
                        destination_matrix[des_size][2]= Sydney[2];
                        destination_matrix[des_size][3]= Sydney[3];
                        check_des[des_size] = "Sydney";
                        des_size++;
                    }

                    if(des_size < 2)
                    {
                        Toast.makeText(getApplicationContext(),"Choose 2 or more destinations",Toast.LENGTH_SHORT).show();
                        des_size = 0;
                        break;
                    }
                    btn_text = 3;
                    Log.i(TAG,"TOPSIS: Showing results for optimal travel destination");
                    destinations.setVisibility(View.INVISIBLE);
                    main_btn.setText("Results");
                    int best = topsis(destination_matrix);

                    if(check_des!=null)
                    {
                        switch (check_des[best]) {
                            case "NY":
                                dest_site.loadUrl("https://www.lonelyplanet.com/usa/new-york-state");
                                break;
                            case "Tokyo":
                                dest_site.loadUrl("https://www.lonelyplanet.com/japan/tokyo");
                                break;
                            case "Santorini":
                                dest_site.loadUrl("https://www.lonelyplanet.com/greece/cyclades/santorini-thira");
                                break;
                            case "Bucharest":
                                dest_site.loadUrl("https://www.lonelyplanet.com/romania/bucharest");
                                break;
                            case "Rio":
                                dest_site.loadUrl("https://www.lonelyplanet.com/brazil/rio-de-janeiro");
                                break;
                            case "Cairo":
                                dest_site.loadUrl("https://www.lonelyplanet.com/egypt/cairo");
                                break;
                            case "Sydney":
                                dest_site.loadUrl("https://www.lonelyplanet.com/australia/sydney");
                                break;
                        }
                        lonely.setVisibility(View.VISIBLE);
                    }

                    break;
                case 3:
                    main_btn.setText("Again?");
                    lonely.setVisibility(View.INVISIBLE);
                    dest_site.loadUrl("");
                    des_size = 0;
                    btn_text = 0;
                    break;
            }
        });
        //------------------------------------------------
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu,menu);
        return true;
    }

    //---- Menu Items ----
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.Exit)
        {
            MainActivity.this.finish();
        }
        return true;
    }
    //-----------------------------------
    private int topsis(double[][] destination_matrix) {
        double db_sum = 0.0, aa_sum = 0.0, si_sum = 0.0, p_sum = 0.0;
        double[] db_norm = new double[des_size];
        double[] aa_norm = new double[des_size];
        double[] si_norm = new double[des_size];
        double[] p_norm = new double[des_size];
        double[] j_plus = {1000.0, 0.0 , 200.0, 250.0};
        double[] j_minus = {0.0, 6.0 , 0.0, 0.0};
        double[] s_plus = new double[des_size];
        double[] s_minus = new double[des_size];
        double[] results = new double[des_size];
        int best = 10;
        double max_res = 0.0;

        if(destination_matrix!=null)
        {

            for(int j=0; j<4; j++)
            {
                if(j == 0)
                {
                    for (int i = 0; i<des_size; i++) {
                        db_sum = destination_matrix[i][j]*destination_matrix[i][j] + db_sum;
                    }
                    db_sum = Math.sqrt(db_sum);
                    for (int i = 0; i<des_size; i++) {
                        db_norm[i] = destination_matrix[i][j]/db_sum;
                        db_norm[i] = db_norm[i]*db_weight;
                        if(j_plus[j] > db_norm[i]) j_plus[j] = db_norm[i];
                        if(j_minus[j] < db_norm[i]) j_minus[j] = db_norm[i];
                    }
                    for (int i = 0; i<des_size; i++) {
                        s_plus[i] = (db_norm[i]-j_plus[j])*(db_norm[i]-j_plus[j]) + s_plus[i];
                        s_minus[i] = (db_norm[i]-j_minus[j])*(db_norm[i]-j_minus[j]) + s_minus[i];
                    }
                }
                else if(j == 1)
                {
                    for (int i = 0; i<des_size; i++) {
                        aa_sum = destination_matrix[i][j]*destination_matrix[i][j] + aa_sum;
                    }
                    aa_sum = Math.sqrt(aa_sum);
                    for (int i = 0; i<des_size; i++) {
                        aa_norm[i] = destination_matrix[i][j]/aa_sum;
                        aa_norm[i] = aa_norm[i]*aa_weight;
                        if(j_plus[j] < aa_norm[i]) j_plus[j] = aa_norm[i];
                        if(j_minus[j] > aa_norm[i]) j_minus[j] = aa_norm[i];
                    }
                    for (int i = 0; i<des_size; i++) {
                        s_plus[i] = (aa_norm[i]-j_plus[j])*(aa_norm[i]-j_plus[j]) + s_plus[i];
                        s_minus[i] = (aa_norm[i]-j_minus[j])*(aa_norm[i]-j_minus[j]) + s_minus[i];
                    }
                }
                else if(j == 2)
                {
                    for (int i = 0; i<des_size; i++) {
                        si_sum = destination_matrix[i][j]*destination_matrix[i][j] + si_sum;
                    }
                    si_sum = Math.sqrt(si_sum);
                    for (int i = 0; i<des_size; i++) {
                        si_norm[i] = destination_matrix[i][j]/si_sum;
                        si_norm[i] = si_norm[i]*si_weight;
                        if(j_plus[j] > si_norm[i]) j_plus[j] = si_norm[i];
                        if(j_minus[j] < si_norm[i]) j_minus[j] = si_norm[i];
                    }
                    for (int i = 0; i<des_size; i++) {
                        s_plus[i] = (si_norm[i]-j_plus[j])*(si_norm[i]-j_plus[j]) + s_plus[i];
                        s_minus[i] = (si_norm[i]-j_minus[j])*(si_norm[i]-j_minus[j]) + s_minus[i];
                    }
                }
                else
                {
                    for (int i = 0; i<des_size; i++) {
                        p_sum = destination_matrix[i][j]*destination_matrix[i][j] + p_sum;
                    }
                    p_sum = Math.sqrt(p_sum);
                    for (int i = 0; i<des_size; i++) {
                        p_norm[i] = destination_matrix[i][j]/p_sum;
                        p_norm[i] = p_norm[i]*p_weight;
                        if(j_plus[j] > p_norm[i]) j_plus[j] = p_norm[i];
                        if(j_minus[j] < p_norm[i]) j_minus[j] = p_norm[i];
                    }
                    for (int i = 0; i<des_size; i++) {
                        s_plus[i] = (p_norm[i]-j_plus[j])*(p_norm[i]-j_plus[j]) + s_plus[i];
                        s_minus[i] = (p_norm[i]-j_minus[j])*(p_norm[i]-j_minus[j]) + s_minus[i];
                        s_plus[i] = Math.sqrt(s_plus[i]);
                        s_minus[i] = Math.sqrt(s_minus[i]);
                        results[i] = s_minus[i]/(s_minus[i]+s_plus[i]);
                        if(results[i] > max_res)
                        {
                            max_res = results[i];
                            best = i;
                        }
                    }
                }
            }

        }
        return best;
    }
}