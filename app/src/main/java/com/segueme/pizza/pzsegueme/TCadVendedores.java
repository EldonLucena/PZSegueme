package com.segueme.pizza.pzsegueme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ELDON on 15/03/2017.
 */

public class TCadVendedores extends Activity {

    private EditText vendedor;
    private EditText qtdcartoes;
    private EditText telefone;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendedor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        vendedor = (EditText)findViewById(R.id.nvendedor);
        qtdcartoes = (EditText)findViewById(R.id.qtdcartoes);
        qtdcartoes = (EditText)findViewById(R.id.telefonev);


    }

public void gravar (View view)
{

    //salvando dados em firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    databaseReference.child("Vendedores").push().setValue(vendedor.getText().toString()+"/"+
                                                          qtdcartoes.getText().toString()+"/"+
                                                          qtdcartoes.getText().toString());
}

}


