package tw.tcfarmgo.tcnrcloud110a01;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Scope;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Q0501c002 extends AppCompatActivity implements View.OnClickListener {

    private Intent intent = new Intent();
    private Intent intent02=new Intent();

    private EditText e101,e102,e103,e104,e111;
    private Button b101,b102,b103,b104,b105,b106,b107,b108,b109,b110;


    private Q0501DBhlper dbHelper;
    private static final String DB_File = "FarmTravel.db";
    private static final int DBversion = 1;
    private int tcount;

    // ------------------
    public static String myselection = "";
    public static String myorder = "id ASC"; // ????????????
    public static String myargs[] = new String[]{};
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private ArrayList<String> recSet;
    private int index=0;
    private String msg;
    private ListView lv001;
    private TextView tsub,t112,t114,t115;
    private RelativeLayout rl01;
    private LinearLayout ll32,ll34;
    private String e_id,e_name,e_tel,e_text1,e_text2;
    private int old_index;
    private String sqlctl;
    private int rowsAffected;
    private int servermsgcolor;
    private String ser_msg;
    private Spinner s002;
    private Intent intent023=new Intent();
    private String t_name,t_tel,t_text1,t_text2;
    private Button b111,b112;
    private int nowposition;
    private Uri uri;

    private Geocoder geocoder;
    private double latitude,longitude;
    private Uri utel;
    private Intent Utel;
    private Boolean account_state;
    private String g_Email;
    private String e_email;
    private Boolean flag_per_call;
    private TextView t111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        enableStrictMode(this);//use ????????????????????????????????????
        super.onCreate(savedInstanceState);
        setContentView(R.layout.q0501_c001);
        //----?????????page??????---
        Intent intent02=getIntent();
        setTitle(intent02.getStringExtra("class_title"));

        Intent intent032=getIntent();
        setTitle(intent032.getStringExtra("class_title"));

        Intent intent52=getIntent();
        setTitle(intent52.getStringExtra("class_title"));
        setupViewComponent();
    }


    private void setupViewComponent() {

        //--editview,????????????
        e101=(EditText)findViewById(R.id.q0501_e101);//??????
        e102=(EditText)findViewById(R.id.q0501_e102);//??????
        e103=(EditText)findViewById(R.id.q0501_e103);//??????1
        e104=(EditText)findViewById(R.id.q0501_e104);//??????2
        e111=(EditText)findViewById(R.id.q0501_e111);//ID

        t111=(TextView)findViewById(R.id.q0501_t111);        

        lv001 = (ListView) findViewById(R.id.q0501_lv001);//in the page of searching, as listview
        tsub = (TextView) findViewById(R.id.q0501_c001_subtitle);//in the page of searching, as subtitle
        t112 = (TextView) findViewById(R.id.q0501_t112); //textView, "total"
        t114 = (TextView)findViewById(R.id.q0501_t114); //server message, if connect well...
        t115 = (TextView)findViewById(R.id.q0501_t115); //title,
        rl01 = (RelativeLayout) findViewById(R.id.q0501_rl01); //the page of tying wonderlist
        ll32 = (LinearLayout) findViewById(R.id.q0501_ll32);//the page of searching
        ll34 = (LinearLayout) findViewById(R.id.q0501_ll34);//the page of after searching

        b101=(Button)findViewById(R.id.q0501_b101);//??????
        b102=(Button)findViewById(R.id.q0501_b102);//??????
        b103=(Button)findViewById(R.id.q0501_b103);//??????
        b104=(Button)findViewById(R.id.q0501_b104);//??????
        b105=(Button)findViewById(R.id.q0501_b105);//?????????
        b106=(Button)findViewById(R.id.q0501_b106);//?????????
        b107=(Button)findViewById(R.id.q0501_b107);//??????
        b108=(Button)findViewById(R.id.q0501_b108);//??????
        b109=(Button)findViewById(R.id.q0501_b109);//??????
        b110=(Button)findViewById(R.id.q0501_b110);//????????????
        b111=(Button)findViewById(R.id.q0501_b111);//??????????????????
        b112=(Button)findViewById(R.id.q0501_b112);//?????????

        b101.setOnClickListener(this);
        b102.setOnClickListener(this);
        b103.setOnClickListener(this);
        b104.setOnClickListener(this);
        b105.setOnClickListener(this);
        b106.setOnClickListener(this);
        b107.setOnClickListener(this);
        b108.setOnClickListener(this);
        b109.setOnClickListener(this);
        b110.setOnClickListener(this);
        b111.setOnClickListener(this);
        b112.setOnClickListener(this);

        //??????Geocorder??????,??????????????????????????????(most important)
        geocoder=new Geocoder(this, Locale.TAIWAN);

        //spinner???clicklistener
        s002 = (Spinner)findViewById(R.id.q0501_spinner);
        s002.setOnItemSelectedListener(mSpnNameOnItemSelLis);
        s002.setVisibility(View.INVISIBLE);//????????????????????????????????????
//        s002.setSelection(index, true);//..........................................

        // ?????????????????? ?????????????????????????????????
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int buttonwidth = displayMetrics.widthPixels /2;

        b111.getLayoutParams().width=buttonwidth;
        b112.getLayoutParams().width=buttonwidth;

        int buttonwidth2 = displayMetrics.widthPixels /4;

        b103.getLayoutParams().width=buttonwidth2;
        b102.getLayoutParams().width=buttonwidth2;
        //--

        u_layout_def2();// the first page layout appearance
        initDB();
        showRec(index);

        //?????????ID??????
        e111.setTextColor(ContextCompat.getColor(this, R.color.Red));
        e111.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow_softness));

        //--text1, text2 clean, ??????,???spinner?????????
        e103.setText("");
        e104.setText("");
        e101.setText("");
        e102.setText("");
        //tvtitle. ???????????? (????????????/?????????)
        t115.setTextColor(ContextCompat.getColor(this, R.color.Teal));
        t115.setText("??????????????? ???" + tcount + " ???");
        u_setspinner();
        rl01.setVisibility(View.VISIBLE);
        ll32.setVisibility(View.INVISIBLE);
    }

    private void u_setspinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item);
        for (int i = 0; i < recSet.size(); i++) {
            String[] fld = recSet.get(i).split("#");
           // adapter.add(fld[0] + " " + fld[1] + " " + fld[2]+ " " + fld[3]);
            adapter.add(fld[0] + " " + fld[1] + " " + fld[2] + " " + fld[3]+ " " + fld[4]);
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s002.setAdapter(adapter);
        s002.setOnItemSelectedListener(mSpnNameOnItemSelLis);
    }

    private void initDB() {
        if (dbHelper == null) {
            dbHelper = new Q0501DBhlper(this, DB_File, null, DBversion);//???????????????
        }
        dbmysql();
        recSet = dbHelper.getRecSet_Q0501();

    }

    private void u_layout_def2() {
        b101.setVisibility(View.INVISIBLE);//??????
        b102.setVisibility(View.VISIBLE);//??????
        b103.setVisibility(View.VISIBLE);//??????
        b108.setVisibility(View.INVISIBLE);//??????
        b109.setVisibility(View.INVISIBLE);//??????
        b104.setVisibility(View.INVISIBLE);
        b105.setVisibility(View.INVISIBLE);
        b106.setVisibility(View.INVISIBLE);
        b107.setVisibility(View.INVISIBLE);
        //t112.setVisibility(View.INVISIBLE);
        t115.setVisibility(View.INVISIBLE); //??????
        e111.setVisibility(View.INVISIBLE); //ID?????????
        t111.setVisibility(View.INVISIBLE);


        e111.setEnabled(false); //ID????????????
        s002.setEnabled(false); //spinner??????


    }




    private AdapterView.OnItemSelectedListener mSpnNameOnItemSelLis = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int iSelect = s002.getSelectedItemPosition();//???????????????
            String[] fld = recSet.get(iSelect).split("#");
            String s = "??????:???" + recSet.size() + "???," + "????????????" + String.valueOf(iSelect + 1) + "???";//?????????0
            t115.setText(s);
            //           b_id.setTextColor((ContextCompat.getColor(parent.getContext(), R.color.Red)));
//--------------12
            index = position;
            showRec(index);
            iSelect = index;
//-----------12
//            e101.setText(keep01);??????
//            e102.setText(keep02);

            if (b101.getVisibility() == View.VISIBLE) { //b101?????????
                e111.setHint("???????????????");
                e101.setText("");
                e102.setText("");
                e103.setText("");
                e104.setText("");
            }

            if (b103.getVisibility() == View.VISIBLE) { //b103?????????

                //e111.setHint("???????????????");
                e101.setText("");
                e102.setText("");
                e103.setText("");
                e104.setText("");
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {  //parent,
            // TODO Auto-generated method stub
//            e101.setText(keep01);
//            e102.setText(keep02); ???????????????
            e101.setText("");
            e102.setText("");
            e103.setText("");
            e104.setText("");
        }
    };

    private void showRec(int index) {

        msg = "";
        if (recSet.size() != 0) {
            String stHead = "?????????????????? " + (index + 1) + " ??? / ??? " + recSet.size() + " ???";
            msg = getString(R.string.q0501_t112) + recSet.size() + "???";
            t115.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow_softness));
            t115.setTextColor(ContextCompat.getColor(this, R.color.Teal));
            t115.setText(stHead);

            String[] fld = recSet.get(index).split("#");
            e111.setTextColor(ContextCompat.getColor(this, R.color.Red));
            e111.setBackgroundColor(ContextCompat.getColor(this, R.color.Yellow));
            e111.setText(fld[0]);//ID
            e101.setText(fld[1]);//??????
            e102.setText(fld[2]);//??????
            e103.setText(fld[3]);//??????1
            e104.setText(fld[4]);//??????2

            if(index == -1){
                Toast.makeText(getApplicationContext(), "int index is null", Toast.LENGTH_SHORT).show();
            }else {
                s002.setSelection(index, true);
            }
           // s002.setSelection(index, true); //spinner ?????????????????????  ???????????????
//            e101.setText(keep01);  ???????????????
//            e102.setText(keep02);

        } else {
            String stHead = "???????????????0 ???";
            t115.setText(stHead);
            e111.setText("");//ID
            e101.setText("");//??????
            e102.setText("");//??????
            e103.setText("");//??????1
            e104.setText("");//??????2

        }

        t112.setText(msg);
    }

    private void enableStrictMode(Context context) {

        //-------------????????????????????????????????????------------------------------
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().
                detectDiskReads().
                detectDiskWrites().
                detectNetwork().
                penaltyLog().
                build());
        StrictMode.setVmPolicy(
                new
                        StrictMode.
                                VmPolicy.
                                Builder().
                        detectLeakedSqlLiteObjects().
                        penaltyLog().
                        penaltyDeath().
                        build());


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.q0501_b102://??????
                //Toast.makeText(getApplicationContext(), "*?????????*", Toast.LENGTH_SHORT).show();
                //--??????????????????????????????
                e101.setText("");
                e102.setText("");
                e103.setText("");
                e104.setText("");
                //this.finish();
                break;

            case R.id.q0501_b103://??????
                if(account_state) {
                    //account_state=true
                    initDB();//???????????????????????????crash
                    t_name = e101.getText().toString().trim();
                    t_tel = e102.getText().toString().trim();
                    t_text1 = e103.getText().toString().trim();
                    t_text2 = e104.getText().toString().trim();
                    e_email=g_Email;
                    msg = null;
                    recSet = dbHelper.getRecSet_query_Q0501c002q(t_name,t_tel,t_text1,t_text2,e_email);
                    Log.d(TAG, "Q0501c003_b103()//"+recSet+"//");
                    //Toast.makeText(getApplicationContext(), "??????????????? ??? " + recSet.size() + " ???", Toast.LENGTH_SHORT).show();
                    //--------???SQLite ??????---------------
                    List<Map<String, Object>> mList;
                    mList = new ArrayList<Map<String, Object>>();
                    for (int i = 0; i < recSet.size(); i++) {
                        Map<String, Object> item = new HashMap<String, Object>();
                        String[] fld = recSet.get(i).split("#");
                        item.put("q0501_img020", R.drawable.t007);
                        item.put("txtView", "id:" + fld[0] + "\n??????:" + fld[1] + "\n??????:" + fld[2] + "\n??????1:" + fld[3] + "\n??????2:" + fld[4]);

                        mList.add(item);
                    }
                    //System.out: CCCCCCCCCC[{txtView=id:1
                    //    ??????:???????????????
                    //    ??????:0422228888
                    //    ??????1:????????????: ????????????????????????
                    //    ??????2:??????:??????, q0501_img020=2131165331}]
                    //=========??????listview========
                    rl01.setVisibility(View.INVISIBLE);
                    ll32.setVisibility(View.VISIBLE);
                    //Toast.makeText(getApplicationContext(), "*?????????*", Toast.LENGTH_SHORT).show();
                    //-----------------------------
                    SimpleAdapter adapter = new SimpleAdapter(
                            this,
                            mList,
                            R.layout.q0501_c002_list,
                            new String[]{"q0501_img020", "txtView"},
                            new int[]{R.id.q0501_img020, R.id.txtView}
                    );
                    lv001.setAdapter(adapter);
                    lv001.setTextFilterEnabled(true);
                    lv001.setOnItemClickListener(listviewOnItemClkLis);

                    //------------------------------------
                }else {
                    Toast.makeText(getApplicationContext(),getString(R.string.q0501_hint03),Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.q0501_b110://????????????
                initDB(); //???????????????????????????crash
                tsub.setText("");
                old_index=s002.getSelectedItemPosition();
                //dbmysql(); ??????????????????initDB();
                recSet=dbHelper.getRecSet_Q0501();
                rl01.setVisibility(View.VISIBLE);
                ll32.setVisibility(View.INVISIBLE);
                ll34.setVisibility(View.GONE);
                index=old_index;
                showRec(index);//??????spinner?????????????????????
                ctlLast();
                break;

            case R.id.q0501_b111: //??????????????????
                msg = null;
                recSet = dbHelper.getRecSet_query_Q0501(t_name,t_tel,t_text1,t_text2);
                System.out.println("DDDDDDDDDDDDDDDD"+recSet);
               // Log.d(TAG, t_name.toString());
                String addr=recSet.get(nowposition);
                String addressName = addr.trim();

                System.out.println("WWWWWWWWWWWWWWWWWWWW"+addressName);
               // Log.d(TAG, addressName.toString());
                try{
                    //??????????????????GPS??????
                    //??????????????????????????????list??????
                    List<Address> listGPSAddress=geocoder.getFromLocationName(addressName,1);
                    // ????????????????????????
                    if (listGPSAddress != null) {
                        latitude = listGPSAddress.get(0).getLatitude();
                        longitude = listGPSAddress.get(0).getLongitude();

                    }
                }catch (Exception ex) {
                    //Toast.makeText(getApplicationContext(),"??????:"+ex.toString(),Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),"???????????????????????????????????????!",Toast.LENGTH_SHORT).show();
                }

                //??????google?????????????????????????????????
                // ??????URI??????
                String uri = String.format("geo:%f,%f?z=17", latitude, longitude);
                // ??????Intent??????
                Intent geoMap = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(geoMap);  // ????????????

                break;

            case R.id.q0501_b112: //????????????????????????
                if(flag_per_call == true){
                    recSet = dbHelper.getRecSet_query_Q0501c002t(t_tel);
                    String call=recSet.get(nowposition);
                    System.out.println("PPPPPPPPPPPPPP"+call);
                    utel = Uri.parse("tel:"+call);
                    Utel = new Intent(Intent.ACTION_DIAL, utel);
                    startActivity(Utel);
                    }else{
                        //Toast.makeText(getApplicationContext(),getString(R.string.q0501_hint04),Toast.LENGTH_LONG).show();
                    makeACall();
                    }
                break;
        }

    }

    private void makeACall() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            // ??????????????????????????????????????????????????????????????????????????????????????????
            // ??????????????????????????????????????????????????????????????????request??????
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                Q0501_Util.showDialog(this, R.string.dialog_msg_call, android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestNeededPermission();
                    }
                });
            } else {
                // ???????????????request
                requestNeededPermission();
            }
        } else {
            recSet = dbHelper.getRecSet_query_Q0501c002t(t_tel);
            String call=recSet.get(nowposition);
            System.out.println("4044444444444"+call);
            utel = Uri.parse("tel:"+call);
            Utel = new Intent(Intent.ACTION_DIAL, utel);
            startActivity(Utel);
        }

    }

    /*** request???????????????*/
    private void requestNeededPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_ASK_PERMISSIONS);
        System.out.println("requestNeededPermission()");
    }

    private ListView.OnItemClickListener listviewOnItemClkLis = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String s = "???????????? " + Integer.toString(position + 1) + "???"
                    + ((TextView) view.findViewById(R.id.txtView))
                    .getText()
                    .toString();

            ll34.setVisibility(View.VISIBLE);
            tsub.setText(s);
            System.out.println("PPPPPPPPXXXXXXXXXXXXXXXXXXPPPPPP"+s);
            //??????listview?????????
            nowposition=position;
        }

    };
    private void dbmysql() {
         //???????????????spinner??????data?????????????????????????????????
        sqlctl = "SELECT * FROM farm02 WHERE email='"+ g_Email +"' ORDER BY id ASC";
        ArrayList<String> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(sqlctl);
        try {
            String result = Q0501DBConnector.executeQuery_Q0501(nameValuePairs);
//----------------
            chk_httpstate();  //?????? ????????????
//-------------------------------------
            JSONArray jsonArray = new JSONArray(result);
            // -------------------------------------------------------
            if (jsonArray.length() > 0) { // MySQL ?????????????????????
                rowsAffected = dbHelper.clearRec_Q0501();                 // ?????????,????????????SQLite??????
                // ??????JASON ????????????????????????
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonData = jsonArray.getJSONObject(i);
                    ContentValues newRow = new ContentValues();
                    // --(1) ?????????????????? --?????? jsonObject ????????????("key","value")-----------------------
                    Iterator itt = jsonData.keys();
                    while (itt.hasNext()) {
                        String key = itt.next().toString();
                        String value = jsonData.getString(key); // ??????????????????
                        if (value == null) {
                            continue;
                        } else if ("".equals(value.trim())) {
                            continue;
                        } else {
                            jsonData.put(key, value.trim());
                        }
                        // ------------------------------------------------------------------
                        newRow.put(key, value.toString()); // ???????????????????????????
                        // -------------------------------------------------------------------
                    }
                    // ---(2) ????????????????????????---------------------------
                    // newRow.put("id", jsonData.getString("id").toString());
                    // newRow.put("name",
                    // jsonData.getString("name").toString());
                    // newRow.put("grp", jsonData.getString("grp").toString());
                    // newRow.put("address", jsonData.getString("address")
                    // -------------------??????SQLite---------------------------------------
                    long rowID = dbHelper.insertRec_m_Q0501(newRow);
                    //Toast.makeText(getApplicationContext(), "????????? " + Integer.toString(jsonArray.length()) + " ?????????", Toast.LENGTH_SHORT).show();
                }
               // Toast.makeText(getApplicationContext(), "????????? " + Integer.toString(jsonArray.length()) + " ?????????", Toast.LENGTH_SHORT).show();
                // ---------------------------
            } else {
                Toast.makeText(getApplicationContext(), "????????????????????????", Toast.LENGTH_LONG).show();
            }
            recSet = dbHelper.getRecSet_Q0501();  //????????????SQLite
            u_setspinner();
            // --------------------------------------------------------
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }

    private void chk_httpstate() {
//**************************************************
//*       ??????????????????
//**************************************************
        //?????????????????? DBConnector01.httpstate ?????????????????? 200(??????????????????)
        servermsgcolor = ContextCompat.getColor(this, R.color.Navy);

        if (Q0501DBConnector.httpstate == 200) {
            ser_msg = "?????????????????????(code:" + Q0501DBConnector.httpstate + ") ";
            servermsgcolor = ContextCompat.getColor(this, R.color.Navy);
//                Toast.makeText(getBaseContext(), "???????????????????????? ",
//                        Toast.LENGTH_SHORT).show();
        } else {
            int checkcode = Q0501DBConnector.httpstate / 100;
            switch (checkcode) {
                case 1:
                    ser_msg = "????????????(code:" + Q0501DBConnector.httpstate + ") ";
                    servermsgcolor = ContextCompat.getColor(this, R.color.Red);
                    break;
                case 2:
                    ser_msg = "????????????????????????????????????(code:" + Q0501DBConnector.httpstate + ") ";
                    servermsgcolor = ContextCompat.getColor(this, R.color.Red);
                    break;
                case 3:
                    ser_msg = "??????????????????????????????????????????(code:" + Q0501DBConnector.httpstate + ") ";
                    servermsgcolor = ContextCompat.getColor(this, R.color.Red);
                    break;
                case 4:
                    ser_msg = "???????????????????????????????????????(code:" + Q0501DBConnector.httpstate + ") ";
                    servermsgcolor = ContextCompat.getColor(this, R.color.Red);
                    break;
                case 5:
                    ser_msg = "?????????error responses??????????????????(code:" + Q0501DBConnector.httpstate + ") ";
                    servermsgcolor = ContextCompat.getColor(this, R.color.Red);
                    break;
            }
//                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
        }

        if (Q0501DBConnector.httpstate == 0) {
            ser_msg = "?????????????????????(code:" + Q0501DBConnector.httpstate + ") ";
            servermsgcolor = ContextCompat.getColor(this, R.color.Red);
        }
        t114.setText(ser_msg);
        t114.setTextColor(servermsgcolor);
        //    servermsgcolor = ContextCompat.getColor(this, R.color.Red);
        //-------------------------------------------------------------------
    }

    private void ctlLast() {
        //???????????? origin recSet.size()-1
        index = recSet.size()-1 ;
        showRec(index);
    }
//---------------------------------


    //------????????????-------
    @Override
    protected void onStop() {
        super.onStop();
        if (dbHelper != null) {
            dbHelper.close();
            dbHelper = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDB(); //???????????????????????????crash(for ???????????????????????????)
    }


    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        //account = GoogleSignIn.getLastSignedInAccount(this);
        //account_state = account != null; //true false
        Q0501_CheckUserState cu= new Q0501_CheckUserState(this); //??????
        account_state = Q0501_CheckUserState.getAccount_state();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null && GoogleSignIn.hasPermissions(account, new Scope(Scopes.DRIVE_APPFOLDER))) {
            updateUI(account);
        } else {
            updateUI(null);
        }

        //------????????????
        flag_per_call= Q0501.getCall_state();
        System.out.println("c0002onstart-------"+flag_per_call);
    }

    //---------------------------------
    private void updateUI(GoogleSignInAccount account) {
        GoogleSignInAccount aa = account;
        int aaa=1;
        if (account != null) {
            g_Email=account.getEmail();  //??????
            Log.d(TAG, "Q0501c002_onStart()//"+g_Email+"//");
        }
    }

    //------------------------------------------Menu--------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.q0501_menu_sub ,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.q0501_login_action_settings:
                this.finish();
                break;
            case R.id.q0501_msub_r001://?????????  with ??????
                Toast.makeText(getApplicationContext(), "??????????????????~", Toast.LENGTH_SHORT).show();
                break;

            case R.id.q0501_msub_u001://????????????
                intent023.putExtra("class_title",getString(R.string.q0501_t903));
                intent023.setClass(Q0501c002.this, Q0501c003.class);
                startActivity(intent023);
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}