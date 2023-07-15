package com.fontsize.demo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextPaint;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.activelook.activelooksdk.Glasses;
import com.activelook.activelooksdk.types.ImgStreamFormat;
import com.activelook.activelooksdk.types.Rotation;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private Glasses connectedGlasses;
    public String langCode= Locale.getDefault().getLanguage();
    private boolean clockON=true, imageON=true, glassesSetting=false;
    private int fontSize=16, lineSpace=0, gbattery=0, topmrg=0, botmrg=0, lftmrg=0, rgtmrg=0;
    private final String[] poem_fr = {"Sous le pont Mirabeau coule la Seine",
            "Et nos amours",
            "Faut-il qu’il m’en souvienne",
            "La joie venait toujours après la peine",
            "Vienne la nuit sonne l’heure",
            "Les jours s’en vont je demeure",
            "Les mains dans les mains restons face à face",
            "Tandis que sous",
            "Le pont de nos bras passe",
            "Des éternels regards l’onde si lasse",
            "Vienne la nuit sonne l’heure",
            "Les jours s’en vont je demeure",
            "L’amour s’en va comme cette eau courante",
            "L’amour s’en va",
            "Comme la vie est lente",
            "Et comme l’Espérance est violente",
            "Vienne la nuit sonne l’heure",
            "Les jours s’en vont je demeure",
            "Passent les jours et passent les semaines",
            "Ni temps passé",
            "Ni les amours reviennent",
            "Sous le pont Mirabeau coule la Seine",
            "Vienne la nuit sonne l’heure",
            "Les jours s’en vont je demeure"};
    private final String[] poem_en = {"Once upon a midnight dreary,",
            "While I pondered, weak and weary,",
            "Over many a quaint and curious",
            "Volume of forgotten lore—",
            "While I nodded, nearly napping,",
            "Suddenly there came a tapping,",
            "As of some one gently rapping,",
            "Rapping at my chamber door.",
            "'Tis some visitor,' I muttered,",
            "'Tapping at my chamber door",
            "Only this, and nothing more.'",
            "Ah, distinctly I remember,",
            "It was in the bleak December,",
            "And each separate dying ember",
            "Wrought its ghost upon the floor.",
            "Eagerly I wished the morrow;",
            "Vainly I had sought to borrow",
            "From my books surcease of sorrow",
            "Sorrow for the lost Lenore—",
            "For the rare and radiant maiden",
            "Whom the angels name Lenore—",
            "Nameless here for evermore.",
            "And the silken, sad, uncertain",
            "Rustling of each purple curtain",
            "Thrilled me,—filled me with fantastic",
            "Terrors, never felt before;",
            "So that now, to still the beating",
            "Of my heart, I stood repeating,",
            "'Tis some visitor entreating",
            "Entrance at my chamber door",
            "Some late visitor entreating",
            "Entrance at my chamber door;",
            "This it is, and nothing more.'",
            "Merely this, and nothing more."};
    private String[] poem_sv ={"Morgonfåglar",
            "Jag väcker bilen",
            "som har vindrutan överdragen med frömjöl.",
            "Jag sätter på mig solglasögonen.",
            "Fågelsången mörknar.",
            "Medan en annan man köper en tidning",
            "på järnvägsstationen",
            "i närheten av en stor godsvagn",
            "som är alldeles röd av rost",
            "och står flimrande i solen.",
            "Inga tomrum någonstans här.",
            "Tvärs genom vårvärmen en kall korridor",
            "där någon kommer skyndande",
            "och berättar att man förtalat honom",
            "ända upp i styrelsen.",
            "Genom en bakdörr i landskapet",
            "kommer skatan",
            "svart och vit, Hels fågel.",
            "Och koltrasten som rör sig kors och tvärs",
            "tills allt blir kolteckning,",
            "utom de vita kläderna på tvättstrecket:",
            "en palestrinakör.",
            "Inga tomrum någonstans här.",
            "Fantastiskt att känna hur min dikt växer",
            "medan jag själv krymper.",
            "Den växer, den tar min plats.",
            "Den tränger undan mig.",
            "Den kastar mig ur boet.",
            "Dikten är färdig."};
    private String[] poem_zh ={
//            "一二三四五六七巴九十一二三四五六七巴九十一二三四五六七巴九十一二三四五六七巴九十",
            "你问我爱你有多深",
            "我爱你有几分",
            "我的情也真",
            "我的爱也真",
            "月亮代表我的心",
            "你问我爱你有多深",
            "我爱你有几分",
            "我的情不移",
            "我的爱不变",
            "月亮代表我的心",
            "轻轻的一个吻",
            "已经打动我的心",
            "深深的一段情",
            "教我思念到如今",
            "你问我爱你有多深",
            "我爱你有几分",
            "你去想一想",
            "你去看一看",
            "月亮代表我的心",
            "轻轻的一个吻",
            "已经打动我的心",
            "深深的一段情",
            "教我思念到如今",
            "你问我爱你有多深",
            "我爱你有几分",
            "你去想一想 你去看一看",
            "月亮代表我的心",
            "你去想一想 你去看一看",
            "月亮代表我的心"};
    private String[] poem=poem_fr;
//    Handler clockHandler = new Handler();
//    Runnable clockRunnable;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch sensorSwitch, clockSwitch, textImage;
    private SeekBar luminanceSeekBar, fontSizeSeekBar, spaceSizeSeekBar;
    private TextView largeText, GlassesBattery, fontSizeTextView, spaceSizeTextView;
    private Spinner LangChoice;
    private ToggleButton adjusmentSet;

    @RequiresApi(api = Build.VERSION_CODES.S)
    @SuppressLint({"BatteryLife", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        /* Check location permission (needed for BLE scan)
         */
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_SCAN},0);

        if (savedInstanceState != null && ((DemoApp) this.getApplication()).isConnected()) {
            this.connectedGlasses = savedInstanceState.getParcelable("connectedGlasses");
            this.connectedGlasses.setOnDisconnected(glasses -> {glasses.disconnect();
                MainActivity.this.disconnect();     });
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = this.findViewById(R.id.toolbar);
        largeText = findViewById(R.id.largeText);
        luminanceSeekBar = this.findViewById(R.id.luminanceSeekBar);
        fontSizeSeekBar = this.findViewById(R.id.fontSizeSeekBar);
        spaceSizeSeekBar = this.findViewById(R.id.spaceSizeSeekBar);
        sensorSwitch = this.findViewById(R.id.sensorSwitch);
        GlassesBattery = this.findViewById(R.id.GlassesBattery);
        clockSwitch = this.findViewById(R.id.clockSwitch);
        adjusmentSet = this.findViewById(R.id.adjusment_set);

        textImage = this.findViewById(R.id.textImage);
        langCode=Locale.getDefault().getLanguage();
        LangChoice = this.findViewById(R.id.lang_choice);
        String[] Lang_Choice = getResources().getStringArray(R.array.langChoice);
        ArrayAdapter<String> adapter_lang = new ArrayAdapter<>(this, R.layout.list_item, Lang_Choice);
        LangChoice.setAdapter(adapter_lang);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        topmrg = sharedPreferences.getInt("topmrg", 0);
        botmrg = sharedPreferences.getInt("botmrg", 0);
        lftmrg = sharedPreferences.getInt("lftmrg", 0);
        rgtmrg = sharedPreferences.getInt("rgtmrg", 0);

        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());
        this.updateVisibility();
        this.bindActions();
    }

//---------------------------------------------------------------------------------

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void updateText(int fs, int ls) {
        final Glasses g = this.connectedGlasses;
        int line0 = 255-topmrg, poemLin = 0, y;
        if (gbattery !=0 ) {GlassesBattery.setText("Glasses battery : "+String.format("%d",gbattery)+"%");}
        if(imageON){line0 -= fs; }
        if (clockON) {displayClock(); line0 -= 30;}
        y = line0;
        if (g!=null && glassesSetting) {g.clear(); displayClock();
            g.rect((short) rgtmrg, (short) botmrg, (short) (303-lftmrg),(short) (255-topmrg));}
        if (g != null && !glassesSetting) { g.cfgSet("cfgLaurent8"); g.clear();
//            g.shift((short) lftmrg, (short) topmrg);
            while (y > fs+botmrg) {
                if(imageON){g.imgStream(textAsBitmap(poem[poemLin], fs), ImgStreamFormat.MONO_4BPP_HEATSHRINK,
                        (short) (300-lftmrg-(textAsBitmap(poem[poemLin], fs)).getWidth()), (short) y);}
                else{g.txt((short) (300-lftmrg), (short) y, Rotation.TOP_LR, (byte) fs, (byte) 0x0F,
                 international_accent(poem[poemLin]));}
                poemLin ++; y = y-fs-ls;
            }
        }
    }

//---------------------------------------------------------------------------------

    @SuppressLint("DefaultLocale")
    private void updateVisibility() {
        final Glasses g = this.connectedGlasses;
        if (g == null) {
            this.findViewById(R.id.connected_content).setVisibility(View.GONE);
            this.findViewById(R.id.disconnected_content).setVisibility(View.VISIBLE);
        } else {
            this.findViewById(R.id.connected_content).setVisibility(View.VISIBLE);
            this.findViewById(R.id.disconnected_content).setVisibility(View.GONE);
            g.clear();
            g.txt(new Point(250, 204), Rotation.TOP_LR, (byte) 2, (byte) 0x0F, "ActiveLook");
            g.txt(new Point(245, 154), Rotation.TOP_LR, (byte) 2, (byte) 0x0F, "Font Sizes");
            g.txt(new Point(240, 100), Rotation.TOP_LR, (byte) 1, (byte) 0x0F, "Please, wait for");
            g.txt(new Point(242, 70), Rotation.TOP_LR, (byte) 1, (byte) 0x0F, "the fonts upload");

            try {g.loadConfiguration(new BufferedReader(new
                    InputStreamReader(getAssets().open("fonts8.txt"))));}
            catch (IOException e) {e.printStackTrace();}

            updateText(fontSize, lineSpace);
//            clockRunnable = new Runnable() {
//                @Override
//                public void run() { displayClock();
//                    clockHandler.postDelayed(this,60000);
//                }
//            };
//            clockHandler.removeCallbacks(clockRunnable);
//            clockHandler.postDelayed(clockRunnable,60000); // every minute
        }
    }

    @SuppressLint("DefaultLocale")
    private void displayClock(){
        BatteryManager bm = (BatteryManager) getSystemService(BATTERY_SERVICE);
        int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String clock = sdf.format(new Date());
        int top=255-topmrg;
        final Glasses g = connectedGlasses;
        if (g != null) {
            g.battery(r1 -> { gbattery=r1;
                connectedGlasses.cfgSet("ALooK");
                if (r1 < 25) {connectedGlasses.imgDisplay((byte) 1, (short) (272-lftmrg), (short) (top-26));}
                else {connectedGlasses.imgDisplay((byte) 0, (short) (272-lftmrg), (short) (top-26));}
                connectedGlasses.txt(new Point((263-lftmrg), top), Rotation.TOP_LR, (byte) 1, (byte) 0x0F,
                        String.format("%d", r1) + "% / " + String.format("%d", batLevel) + "%  ");
                connectedGlasses.txt(new Point(100+rgtmrg, top), Rotation.TOP_LR, (byte) 1, (byte) 0x0F, clock);
            });//Glasses Battery
        }
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void bindActions() {
        // If BT is not on, request that it be enabled.
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            Toast.makeText(getApplicationContext(),
                    "Your BLUETOOTH is not open !!!\n>>>relaunch the application", Toast.LENGTH_LONG).show();
            largeText.setText("Your BlueTooth is not open !!\n\n" +
                    "Please open BlueTooth and\n\n relaunch the application.");
            largeText.setTextColor(Color.parseColor("#FF0000"));
            largeText.setTypeface(largeText.getTypeface(), Typeface.BOLD);
        }
        this.findViewById(R.id.scan).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ScanningActivity.class);
            MainActivity.this.startActivityForResult(intent, Activity.RESULT_FIRST_USER);
        });

        this.findViewById(R.id.button_disconnect).setOnClickListener(view -> {
            MainActivity.this.sensorSwitch(true);
            connectedGlasses.sensor(true);
            MainActivity.this.disconnect();
        });

        sensorSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                MainActivity.this.sensorSwitch(isChecked));

        luminanceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                MainActivity.this.lumaButton(progressChangedValue);}
            public void onStartTrackingTouch(SeekBar seekBar) {}
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        clockSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                MainActivity.this.clockSwitch(isChecked));

        textImage.setOnCheckedChangeListener((buttonView, isChecked) ->
                MainActivity.this.textImage(isChecked));

        fontSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("DefaultLocale")
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                fontSize = progress+10;
                fontSizeTextView = MainActivity.this.findViewById(R.id.fontSizeTextView);
                fontSizeTextView.setText(String.format("%2d", fontSize));
                MainActivity.this.updateText(fontSize, lineSpace);}
            public void onStartTrackingTouch(SeekBar seekBar) {}
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        spaceSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("DefaultLocale")
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                lineSpace = progress;
                spaceSizeTextView = MainActivity.this.findViewById(R.id.spaceSizeTextView);
                spaceSizeTextView.setText(String.format("%1d", lineSpace));
                MainActivity.this.updateText(fontSize, lineSpace);}
            public void onStartTrackingTouch(SeekBar seekBar) {}
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        LangChoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected (AdapterView < ? > parent, View view,int position, long id){
                String Choice = (String) parent.getItemAtPosition(position);
                if (Choice.equals("Français")) {poem=poem_fr;}
                if (Choice.equals("English")) {poem=poem_en;}
                if (Choice.equals("Svenska")) {poem=poem_sv;}
                if (Choice.equals("中文-Chinese")) {poem=poem_zh;}
                MainActivity.this.updateText(fontSize, lineSpace);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {poem=poem_fr;}
        });

        adjusmentSet.setOnCheckedChangeListener((buttonView, isChecked) -> {glassesSetting=isChecked;
            if(isChecked) {this.findViewById(R.id.adjusment_content).setVisibility(View.VISIBLE);
                Glasses g=connectedGlasses;
                if (g!=null) {g.clear(); displayClock();
                    g.rect((short) rgtmrg, (short) botmrg, (short) (303-lftmrg),(short) (255-topmrg));}
            }
            else {this.findViewById(R.id.adjusment_content).setVisibility(View.GONE);
                updateText(fontSize, lineSpace);}
        });

        this.findViewById(R.id.topMinus).setOnClickListener(view -> { topmrg--; if (topmrg<0) {topmrg=0;}
            Glasses g=connectedGlasses;
            TextView topMarginTV=this.findViewById(R.id.topMargin);
            topMarginTV.setText("Top\n-"+String.format("%d",topmrg)+"px");
            savePreferences();
            if (g!=null) { g.clear(); displayClock();
            g.rect((short) rgtmrg, (short) botmrg, (short) (303-lftmrg),(short) (255-topmrg));}});
        this.findViewById(R.id.topPlus).setOnClickListener(view ->  { topmrg++; if (topmrg>50) {topmrg=50;}
            Glasses g=connectedGlasses;
            TextView topMarginTV=this.findViewById(R.id.topMargin);
            topMarginTV.setText("Top\n-"+String.format("%d",topmrg)+"px");
            savePreferences();
            if (g!=null) { g.clear(); displayClock();
                g.rect((short) rgtmrg, (short) botmrg, (short) (303-lftmrg),(short) (255-topmrg));}});
        this.findViewById(R.id.bottomMinus).setOnClickListener(view -> { botmrg--; if (botmrg<0) {botmrg=0;}
            Glasses g=connectedGlasses;
            TextView bottomMarginTV=this.findViewById(R.id.bottomMargin);
            bottomMarginTV.setText("Bottom\n-"+String.format("%d",botmrg)+"px");
            savePreferences();
            if (g!=null) { g.clear(); displayClock();
                g.rect((short) rgtmrg, (short) botmrg, (short) (303-lftmrg),(short) (255-topmrg));}});
        this.findViewById(R.id.bottomPlus).setOnClickListener(view ->  { botmrg++; if (botmrg>50) {botmrg=50;}
            Glasses g=connectedGlasses;
            TextView bottomMarginTV=this.findViewById(R.id.bottomMargin);
            bottomMarginTV.setText("Bottom\n-"+String.format("%d",botmrg)+"px");
            savePreferences();
            if (g!=null) { g.clear(); displayClock();
                g.rect((short) rgtmrg, (short) botmrg, (short) (303-lftmrg),(short) (255-topmrg));}});
        this.findViewById(R.id.leftMinus).setOnClickListener(view -> { lftmrg--; if (lftmrg<0) {lftmrg=0;}
            Glasses g=connectedGlasses;
            TextView leftMarginTV=this.findViewById(R.id.leftMargin);
            leftMarginTV.setText("Left\n-"+String.format("%d",lftmrg)+"px");
            savePreferences();
            if (g!=null) { g.clear(); displayClock();
                g.rect((short) rgtmrg, (short) botmrg, (short) (303-lftmrg),(short) (255-topmrg));}});
        this.findViewById(R.id.leftPlus).setOnClickListener(view ->  { lftmrg++; if (lftmrg>50) {lftmrg=50;}
            Glasses g=connectedGlasses;
            TextView leftMarginTV=this.findViewById(R.id.leftMargin);
            leftMarginTV.setText("Left\n-"+String.format("%d",lftmrg)+"px");
            savePreferences();
            if (g!=null) { g.clear(); displayClock();
                g.rect((short) rgtmrg, (short) botmrg, (short) (303-lftmrg),(short) (255-topmrg));}});
        this.findViewById(R.id.rightMinus).setOnClickListener(view -> { rgtmrg--; if (rgtmrg<0) {rgtmrg=0;}
            Glasses g=connectedGlasses;
            TextView rightMarginTV=this.findViewById(R.id.rightMargin);
            rightMarginTV.setText("Right\n-"+String.format("%d",rgtmrg)+"px");
            savePreferences();
            if (g!=null) { g.clear(); displayClock();
                g.rect((short) rgtmrg, (short) botmrg, (short) (303-lftmrg),(short) (255-topmrg));}});
        this.findViewById(R.id.rightPlus).setOnClickListener(view ->  { rgtmrg++; if (rgtmrg>50) {rgtmrg=50;}
            Glasses g=connectedGlasses;
            TextView rightMarginTV=this.findViewById(R.id.rightMargin);
            rightMarginTV.setText("Right\n-"+String.format("%d",rgtmrg)+"px");
            savePreferences();
            if (g!=null) { g.clear(); displayClock();
                g.rect((short) rgtmrg, (short) botmrg, (short) (303-lftmrg),(short) (255-topmrg));}});

    }

    private void savePreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("topmrg",topmrg);
        editor.putInt("botmrg",botmrg);
        editor.putInt("lftmrg",lftmrg);
        editor.putInt("rgtmrg",rgtmrg);
        editor.apply();
    }

    public Bitmap textAsBitmap(String text, int textSize) {
        TextPaint tp = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        tp.setTextSize(textSize);
        tp.setColor(Color.WHITE); // white for text
        tp.setTextAlign(Paint.Align.LEFT);
        float baseline = -tp.ascent(); // ascent() is negative
        int width = (int) (tp.measureText(text) + 0.5f); // round
        int height = (int) (baseline + tp.descent() + 0.5f);
        Paint bp = new Paint(Paint.ANTI_ALIAS_FLAG);
        bp.setStyle(Paint.Style.FILL);
        bp.setColor(Color.BLACK); // black for background
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(image);
        c.drawPaint(bp);
        c.drawText(text, 0, baseline, tp);
        return image;
    }


    /////////  LUMINANCE  bar and switch
    private void lumaButton(int luma) {this.connectedGlasses.luma((byte) luma);}
    private void sensorSwitch(boolean on) {this.connectedGlasses.sensor(on);}

    private void clockSwitch(boolean on) {clockON = on; MainActivity.this.updateText(fontSize, lineSpace);}

    private void textImage(boolean on) {imageON = on; MainActivity.this.updateText(fontSize, lineSpace);}

    @SuppressLint("SetTextI18n")
    private void setUIGlassesInformations() {
        final Glasses glasses = this.connectedGlasses;
        glasses.settings(r -> sensorSwitch.setChecked(r.isGestureEnable()));
        glasses.settings(r -> luminanceSeekBar.setProgress(r.getLuma()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == requestCode && requestCode == Activity.RESULT_FIRST_USER) {
            if (data != null && data.hasExtra("connectedGlasses")) {
                this.connectedGlasses = data.getExtras().getParcelable("connectedGlasses");
                this.connectedGlasses.setOnDisconnected(glasses -> MainActivity.this.disconnect());
                runOnUiThread(MainActivity.this::setUIGlassesInformations);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        if (this.connectedGlasses != null) {savedInstanceState.putParcelable("connectedGlasses",
                this.connectedGlasses);}
        super.onSaveInstanceState(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        // If BT is not on, request that it be enabled.
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            Toast.makeText(getApplicationContext(), "Your BlueTooth is not open !!!",
                    Toast.LENGTH_LONG).show();
            largeText.setText("Your BlueTooth is not open !!\n\n" +
                    "Please open BlueTooth and\n\n relaunch the application.");
            largeText.setTextColor(Color.parseColor("#FF0000"));
            largeText.setTypeface(largeText.getTypeface(), Typeface.BOLD);
        }
        if (!((DemoApp) this.getApplication()).isConnected()) {this.connectedGlasses = null;}
        this.updateVisibility();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        super.onStart();
        // If BT is not on, request that it be enabled.
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            Toast.makeText(getApplicationContext(), "Your BlueTooth is not open !!!",
                    Toast.LENGTH_LONG).show();
            largeText.setText("Your BlueTooth is not open !!\n\n" +
                    "Please open BlueTooth and\n\n relaunch the application.");
            largeText.setTextColor(Color.parseColor("#FF0000"));
            largeText.setTypeface(largeText.getTypeface(), Typeface.BOLD);
        }
        if (!((DemoApp) this.getApplication()).isConnected()) {this.connectedGlasses = null;}
    }

    protected void onPause() {super.onPause();}

    protected void onStop() {super.onStop();
//        if(clockHandler != null)
//            clockHandler.removeCallbacks(clockRunnable); // We stop the callback
    }

    protected void onDestroy() {
        super.onDestroy();
//        if(clockHandler != null)
//            clockHandler.removeCallbacks(clockRunnable); // We stop the callback
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        final Glasses g = this.connectedGlasses;

        //noinspection SimplifiableIfStatement
        if (id == R.id.about_app) {Toast.makeText(this.getApplicationContext(),
                getString(R.string.app_name) + "\nVersion " + getString(R.string.app_version),
                Toast.LENGTH_LONG).show();
            return true;}
        if (id == R.id.about_glasses) {
            if( g!=null) {Toast.makeText(this.getApplicationContext(),
                    "Glasses Name : " + g.getName() + "\n"
                            + "Firmware : " + g.getDeviceInformation().getFirmwareVersion(),
                    Toast.LENGTH_LONG).show();}
            else {Toast.makeText(this.getApplicationContext(),
                    "No connected glasses found yet!",
                    Toast.LENGTH_LONG).show();}
            return true;}
        return super.onOptionsItemSelected(item);
    }

    private void disconnect() {
        runOnUiThread(() -> {
            ((DemoApp) this.getApplication()).onDisconnected();
            MainActivity.this.connectedGlasses.disconnect();
            MainActivity.this.connectedGlasses = null;
            MainActivity.this.updateVisibility();
        });
    }

    public static String international_accent(String text) {
        text = text.replaceAll("Ă","A");
        text = text.replaceAll("Ā","A");
        text = text.replaceAll("Ė","E");
        text = text.replaceAll("Ē","E");
        text = text.replaceAll("Ī","I");
        text = text.replaceAll("Ō","O");
        text = text.replaceAll("Ő","O");
        text = text.replaceAll("Ū","U");
        text = text.replaceAll("Ű","U");
        text = text.replaceAll("Ų","U");
        text = text.replaceAll("Ŷ","Y");
        text = text.replaceAll("Č","C");
        text = text.replaceAll("Ċ","C");
        text = text.replaceAll("Ð","đ");
        text = text.replaceAll("Ď","D");
        text = text.replaceAll("Ľ","L'");
        text = text.replaceAll("Ĺ","L");
        text = text.replaceAll("Ğ","G");
        text = text.replaceAll("Ģ","G");
        text = text.replaceAll("Ġ","G");
        text = text.replaceAll("Ħ","H");
        text = text.replaceAll("Į","I");
        text = text.replaceAll("Ķ","K");
        text = text.replaceAll("Ļ","L");
        text = text.replaceAll("Ň","N");
        text = text.replaceAll("Ņ","N");
        text = text.replaceAll("Ŗ","R");
        text = text.replaceAll("Ŕ","R'");
        text = text.replaceAll("Ș","S");
        text = text.replaceAll("Ś","S'");
        text = text.replaceAll("Ť","T'");
        text = text.replaceAll("Ț","T");
        text = text.replaceAll("Ŵ","W");
        text = text.replaceAll("Ź","Z");
        text = text.replaceAll("Ż","Z");
        text = text.replaceAll("Œ","OE");
        text = text.replaceAll("Æ","AE");
        text = text.replaceAll("Á","á");
        text = text.replaceAll("À","à");
        text = text.replaceAll("Â","â");
        text = text.replaceAll("Ä","ä");
        text = text.replaceAll("Ã","ã");
        text = text.replaceAll("Å","å");
        text = text.replaceAll("É","é");
        text = text.replaceAll("È","è");
        text = text.replaceAll("Ê","ê");
        text = text.replaceAll("Ë","ë");
        text = text.replaceAll("Í","í");
        text = text.replaceAll("Ì","ì");
        text = text.replaceAll("Î","î");
        text = text.replaceAll("Ï","ï");
        text = text.replaceAll("Ó","ó");
        text = text.replaceAll("Ò","ò");
        text = text.replaceAll("Ô","ô");
        text = text.replaceAll("Ö","ö");
        text = text.replaceAll("Õ","õ");
        text = text.replaceAll("Ø","ø");
        text = text.replaceAll("Ú","ú");
        text = text.replaceAll("Ù","ù");
        text = text.replaceAll("Û","û");
        text = text.replaceAll("Ü","ü");
        text = text.replaceAll("Ý","ý");
        text = text.replaceAll("Ÿ","ÿ");
        text = text.replaceAll("Þ","þ");
        text = text.replaceAll("Ç","ç");
        text = text.replaceAll("Ñ","ñ");
        text = text.replaceAll("Č","č");
        text = text.replaceAll("Ć","ć");
        text = text.replaceAll("Đ","đ");
        text = text.replaceAll("Š","š");
        text = text.replaceAll("Ž","ž");
        text = text.replaceAll("Ą","ą");
        text = text.replaceAll("Ę","ę");
        text = text.replaceAll("Ş","ş");
        text = text.replaceAll("Ł","ł");
        text = text.replaceAll("ẞ","ß");

        text = text.replaceAll("ā","ä");
        text = text.replaceAll("ă","ä");
        text = text.replaceAll("ė","e");
        text = text.replaceAll("ē","ë");
        text = text.replaceAll("ī","ï");
        text = text.replaceAll("ō","ö");
        text = text.replaceAll("ő","ö");
        text = text.replaceAll("ū","ü");
        text = text.replaceAll("ű","ü");
        text = text.replaceAll("ų","u");
        text = text.replaceAll("ŷ","ÿ");
        text = text.replaceAll("č","c");
        text = text.replaceAll("ċ","c");
        text = text.replaceAll("ð","đ");
        text = text.replaceAll("ď","d'");
        text = text.replaceAll("ľ","l'");
        text = text.replaceAll("ĺ","l");
        text = text.replaceAll("ğ","g");
        text = text.replaceAll("ģ","g'");
        text = text.replaceAll("ġ","g");
        text = text.replaceAll("ħ","h");
        text = text.replaceAll("į","j");
        text = text.replaceAll("ķ","k");
        text = text.replaceAll("ļ","l");
        text = text.replaceAll("ň","ñ");
        text = text.replaceAll("ņ","n");
        text = text.replaceAll("ŗ","r");
        text = text.replaceAll("ŕ","r'");
        text = text.replaceAll("ș","s");
        text = text.replaceAll("ś","s'");
        text = text.replaceAll("ť","t'");
        text = text.replaceAll("ț","t");
        text = text.replaceAll("ŵ","w");
        text = text.replaceAll("ź","z'");
        text = text.replaceAll("ż","z");

        text = text.replaceAll("¿","?");
        text = text.replaceAll("¡","!");
        text = text.replaceAll("·",".");
        text = text.replaceAll("…","...");
        text = text.replaceAll("¼","1/4");
        text = text.replaceAll("½","1/2");
        text = text.replaceAll("¾","3/4");
        text = text.replaceAll("'","'");
        text = text.replaceAll("’","'");
        text = text.replaceAll("‘","'");
        text = text.replaceAll("‚","'");
        text = text.replaceAll("«","\"");
        text = text.replaceAll("»","\"");
        text = text.replaceAll("“","\"");
        text = text.replaceAll("„","\"");
        text = text.replaceAll("”","\"");
        text = text.replaceAll("String.valueOf((char) 0xAD)","-");
        text = text.replaceAll("String.valueOf((char) 0x2010)","-");
        text = text.replaceAll("String.valueOf((char) 0x2011)","-");
        text = text.replaceAll("String.valueOf((char) 0x2012)","-");
        text = text.replaceAll("String.valueOf((char) 0x2013)","-");
        text = text.replaceAll("String.valueOf((char) 0x2014)","-");
        text = text.replaceAll("String.valueOf((char) 0x2015)","-");
        text = text.replaceAll("_","-");
        text = text.replaceAll("~","-");
        text = text.replaceAll("\\|","I");
        text = text.replaceAll("\\[","(");
        text = text.replaceAll("]",")");
        text = text.replaceAll("\\{","(");
        text = text.replaceAll("\\}",")");
        text = text.replaceAll("œ","oe");
        text = text.replaceAll("æ","ae");
        text = text.replaceAll(String.valueOf((char) 0xA0)," "); // Non-breaking space

        text = text.replaceAll("á",String.valueOf((char) 2));
        text = text.replaceAll("à",String.valueOf((char) 3));
        text = text.replaceAll("â",String.valueOf((char) 4));
        text = text.replaceAll("ä",String.valueOf((char) 5));
        text = text.replaceAll("ã",String.valueOf((char) 6));
        text = text.replaceAll("å",String.valueOf((char) 7));
        text = text.replaceAll("é",String.valueOf((char) 8));
        text = text.replaceAll("è",String.valueOf((char) 9));
        text = text.replaceAll("ê",String.valueOf((char) 11));
        text = text.replaceAll("ë",String.valueOf((char) 12));
        text = text.replaceAll("í",String.valueOf((char) 13));
        text = text.replaceAll("ì",String.valueOf((char) 14));
        text = text.replaceAll("î",String.valueOf((char) 15));
        text = text.replaceAll("ï",String.valueOf((char) 16));
        text = text.replaceAll("ó",String.valueOf((char) 17));
        text = text.replaceAll("ò",String.valueOf((char) 18));
        text = text.replaceAll("ô",String.valueOf((char) 19));
        text = text.replaceAll("ö",String.valueOf((char) 20));
        text = text.replaceAll("õ",String.valueOf((char) 21));
        text = text.replaceAll("ø",String.valueOf((char) 22));
        text = text.replaceAll("ú",String.valueOf((char) 23));
        text = text.replaceAll("ù",String.valueOf((char) 24));
        text = text.replaceAll("û",String.valueOf((char) 25));
        text = text.replaceAll("ü",String.valueOf((char) 26));
        text = text.replaceAll("ý",String.valueOf((char) 27));
        text = text.replaceAll("ÿ",String.valueOf((char) 28));
        text = text.replaceAll("þ",String.valueOf((char) 29));
        text = text.replaceAll("ç",String.valueOf((char) 30));
        text = text.replaceAll("ñ",String.valueOf((char) 31));
        text = text.replaceAll("č",String.valueOf((char) 35)); // #
        text = text.replaceAll("ć",String.valueOf((char) 35)); // #
        text = text.replaceAll("đ",String.valueOf((char) 91)); // [
        text = text.replaceAll("š",String.valueOf(
                (char) 92)+String.valueOf((char) 92)); // \
        text = text.replaceAll("ž",String.valueOf((char) 93)); // ]
        text = text.replaceAll("ą",String.valueOf((char) 94)); // ^
        text = text.replaceAll("ę",String.valueOf((char) 95)); // _
        text = text.replaceAll("ş",String.valueOf((char) 96)); // `
        text = text.replaceAll("ł",String.valueOf((char) 123)); // {
        text = text.replaceAll("ß",String.valueOf((char) 124)); // |
        text = text.replaceAll("€",String.valueOf((char) 125)); // }
        text = text.replaceAll("£",String.valueOf((char) 126)); // ~
        return text;
    }

}
