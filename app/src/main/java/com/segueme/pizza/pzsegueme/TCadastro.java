package com.segueme.pizza.pzsegueme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TCadastro extends AppCompatActivity {

    Bean_Cadastro cadastro;
    RadioGroup Rgroup;
    RadioGroup Rgroup1;
    RadioGroup Rgroup2;
    private Button btEnviar;
    private int ind = 0;
    int cont = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcadastro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Em construção.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Intent it = getIntent();
        cont = it.getIntExtra("indice", -1);

        cadastro = new Bean_Cadastro();
        setRadioGroup();
        setButton();


    }


    /*** inicializando radiogroup **/
    public void setRadioGroup() {
        Rgroup = (RadioGroup) findViewById(R.id.rgtipo);
        Rgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.sp:
                        Log.i("Tipo: ", checkedId + "");
                        break;
                    case R.id.ass:
                        Log.i("Tipo: ", checkedId + "");
                        break;
                }
            }
        });

        Rgroup1 = (RadioGroup) findViewById(R.id.rgsabor);
        Rgroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.calab:
                        Log.i("calabresa: ", checkedId + "");
                        break;
                    case R.id.fran:
                        Log.i("frango: ", checkedId + "");
                        break;
                }
            }
        });

        Rgroup2 = (RadioGroup) findViewById(R.id.rgpag);
        Rgroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.pgs:
                        Log.i("Sim: ", checkedId + "");
                        break;
                    case R.id.pgn:
                        Log.i("Não: ", checkedId + "");
                        break;
                }
            }
        });
    }

    /*** inicializando botão enviar**/
    public void setButton() {

        btEnviar = (Button) findViewById(R.id.btenviar);
        btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RadioButton sp, ass, calab, fran, pgs, pgn;
                sp = (RadioButton) findViewById(R.id.sp);
                ass = (RadioButton) findViewById(R.id.ass);
                calab = (RadioButton) findViewById(R.id.calab);
                fran = (RadioButton) findViewById(R.id.fran);
                pgs = (RadioButton) findViewById(R.id.pgs);
                pgn = (RadioButton) findViewById(R.id.pgn);

                boolean bSp, bAs, bCalab, bFran, bPgs, bPgn;
                bSp = sp.isChecked();
                bAs = ass.isChecked();
                bCalab = calab.isChecked();
                bFran = fran.isChecked();
                bPgs = pgs.isChecked();
                bPgn = pgn.isChecked();

                EditText edtnome, edttel, edtend, edtdat;
                edtnome = (EditText) findViewById(R.id.edtnome);
                edttel = (EditText) findViewById(R.id.edttel);
                edtend = (EditText) findViewById(R.id.edtend);
                edtdat = (EditText) findViewById(R.id.edtdata);


                if ((bSp == true || bAs == true) &&
                        (bCalab == true || bFran == true) &&
                        (bPgs == true || bPgn == true)) {
                    if (
                            (edtnome.getText().toString().length() > 4) &&
                                    (edttel.getText().toString().length() >= 8) &&
//                                    (edtend.getText().toString().length()>0)&&
                                    (edtdat.getText().toString().length() >= 8)) {

                        cadastro.setsNome(edtnome.getText().toString());
                        cadastro.setsTel(edttel.getText().toString());
                        cadastro.setsEnd(edtend.getText().toString());
                        cadastro.setsDataEnt(edtdat.getText().toString());
                        if (bSp == true)
                            cadastro.setsTipoPizza("Semi-Pronta");
                        else
                            cadastro.setsTipoPizza("Assada");

                        if (bCalab == true)
                            cadastro.setsSabor("Calabresa");
                        else
                            cadastro.setsSabor("Frango");

                        if (bPgs == true)
                            cadastro.setsPago("Pago");
                        else
                            cadastro.setsPago("Em aberto");

                        salvarFirebase(Integer.toString(cont), cadastro.getsNome(), cadastro.getsTel(), cadastro.getsEnd(),
                                cadastro.getsDataEnt(), cadastro.getsTipoPizza(), cadastro.getsSabor(), cadastro.getsPago());
                        message0();
                    } else
                        message1();
                } else
                    message2();
            }
        });
    }

    private void salvarFirebase(String id, String prNome, String prTelefone, String prEnd,
                                String prDataEntrega, String prTipo, String prSabor, String prPago) {


        CUser user = new CUser(id, prNome, prTelefone, prEnd, prDataEntrega, prTipo, prSabor, prPago);
        //salvando dados em firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();
        databaseReference.child("Pedido").push().setValue(user);

        Intent secondActivity = new Intent(TCadastro.this, MainActivity.class);
        startActivity(secondActivity);
    }


    public void message0() {
        Toast.makeText(this, "Dados gravados com sucesso!", Toast.LENGTH_SHORT).show();
        return;
    }

    public void message1() {
        Toast.makeText(this, "Informe os dados corretamente (Tipo, nome/sobrenome, telefone, data, sabor e pagamento.", Toast.LENGTH_SHORT).show();
        return;
    }

    public void message2() {
        Toast.makeText(this, "Preencha as informações por favor.", Toast.LENGTH_SHORT).show();
        return;
    }
}
