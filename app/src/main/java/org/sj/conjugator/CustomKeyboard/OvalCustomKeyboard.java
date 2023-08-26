package org.sj.conjugator.CustomKeyboard;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.mushafconsolidated.R;
import com.google.android.material.button.MaterialButton;

public class OvalCustomKeyboard extends LinearLayout implements View.OnClickListener {
    private final SparseArray<String> keyValues = new SparseArray<>();

    public Context mycontext;
   OvalCustomKeyboard keyboard;


   private String radioText;

    public OvalCustomKeyboard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public OvalCustomKeyboard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
     LayoutInflater.from(context).inflate(R.layout.round_arabic_keyboard, this, true);

        MaterialButton key_delete = findViewById(R.id.key_delete);
        MaterialButton key_AC = findViewById(R.id.key_AC);
       MaterialButton key_enter = findViewById(R.id.key_enter);
        keyboard = findViewById(R.id.arabic_keyboard);

        MaterialButton dhad = findViewById(R.id.dhad);
        MaterialButton suwad = findViewById(R.id.suwad);
        MaterialButton qaf = findViewById(R.id.qaf);
        MaterialButton fa = findViewById(R.id.fa);
        MaterialButton ghain = findViewById(R.id.ghain);
        MaterialButton ayn = findViewById(R.id.ayn);
        MaterialButton haaa = findViewById(R.id.haaa);
        MaterialButton kha = findViewById(R.id.kha);
        MaterialButton ha = findViewById(R.id.ha);
        MaterialButton jeem = findViewById(R.id.jeem);
        dhad.setOnClickListener(this);
        suwad.setOnClickListener(this);
        qaf.setOnClickListener(this);
        fa.setOnClickListener(this);
        ghain.setOnClickListener(this);
        ayn.setOnClickListener(this);
        haaa.setOnClickListener(this);
        kha.setOnClickListener(this);
        ha.setOnClickListener(this);
        jeem.setOnClickListener(this);
        keyValues.put(R.id.dhad, "ض");
        keyValues.put(R.id.suwad, "ص");
        keyValues.put(R.id.qaf, "ق");
        keyValues.put(R.id.fa, "ف");
        keyValues.put(R.id.ghain, "غ");
        keyValues.put(R.id.ayn, "ع");
        keyValues.put(R.id.haaa, "ه");
        keyValues.put(R.id.kha, "خ");
        keyValues.put(R.id.ha, "ح");
        keyValues.put(R.id.jeem, "ج");
        MaterialButton sheen = findViewById(R.id.sheen);
        MaterialButton seen = findViewById(R.id.seen);
        MaterialButton ya = findViewById(R.id.ya);
        MaterialButton ba = findViewById(R.id.ba);
        MaterialButton lam = findViewById(R.id.lam);
        MaterialButton alif = findViewById(R.id.hamza);
        MaterialButton ta = findViewById(R.id.ta);
        MaterialButton nun = findViewById(R.id.nun);
        MaterialButton meem = findViewById(R.id.meem);
        MaterialButton kaf = findViewById(R.id.kaf);
        sheen.setOnClickListener(this);
        seen.setOnClickListener(this);
        ya.setOnClickListener(this);
        ba.setOnClickListener(this);
        lam.setOnClickListener(this);
        alif.setOnClickListener(this);
        ta.setOnClickListener(this);
        nun.setOnClickListener(this);
        meem.setOnClickListener(this);
        kaf.setOnClickListener(this);
        //   key00.setOnClickListener(this);
        key_delete.setOnClickListener(this);
        key_AC.setOnClickListener(this);
        key_enter.setOnClickListener(this);
     keyValues.put(R.id.sheen, "ش");
        keyValues.put(R.id.seen, "س");
        keyValues.put(R.id.ya, "ي");
        keyValues.put(R.id.ba, "ب");
        keyValues.put(R.id.lam, "ل");
        keyValues.put(R.id.hamza, "ا");
        keyValues.put(R.id.ta, "ت");
        keyValues.put(R.id.nun, "ن");
        keyValues.put(R.id.meem, "م");
        keyValues.put(R.id.kaf, "ك");
        MaterialButton zoay = findViewById(R.id.zoay);
        MaterialButton toay = findViewById(R.id.toay);
        MaterialButton dhal = findViewById(R.id.dhal);
        MaterialButton dal = findViewById(R.id.dal);
        MaterialButton zaa = findViewById(R.id.zaa);
        MaterialButton raa = findViewById(R.id.raa);
        MaterialButton waw = findViewById(R.id.waw);
        MaterialButton tamarboot = findViewById(R.id.tamarboota);
        MaterialButton tha = findViewById(R.id.tha);
        zoay.setOnClickListener(this);
        toay.setOnClickListener(this);
        dhal.setOnClickListener(this);
        dal.setOnClickListener(this);
        zaa.setOnClickListener(this);
        raa.setOnClickListener(this);
        waw.setOnClickListener(this);
        tamarboot.setOnClickListener(this);
        tha.setOnClickListener(this);
        keyValues.put(R.id.zoay, "ظ");
        keyValues.put(R.id.toay, "ط");
        keyValues.put(R.id.dhal, "ذ");
        keyValues.put(R.id.dal, "د");
        keyValues.put(R.id.zaa, "ز");
        keyValues.put(R.id.raa, "ر");
        keyValues.put(R.id.waw, "و");
        keyValues.put(R.id.tamarboota, "ة");
        keyValues.put(R.id.tha, "ث");
    }

    @Override
    public void onClick(View view) {
    }
}
