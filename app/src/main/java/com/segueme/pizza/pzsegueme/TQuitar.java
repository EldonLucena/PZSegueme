package com.segueme.pizza.pzsegueme;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TQuitar extends Activity {

    private ListView lis;
    final List<CUser> luser = new ArrayList<CUser>();
    private ArrayList<String> chaves = new ArrayList<String>();
    String key = "";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quitar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        lis = (ListView) findViewById(R.id.listquitar);

        // lis.clearDisappearingChildren();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Contato: eldon.lucena@gmail.com", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();
        databaseReference.child("Pedido").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                // retorna nó
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                int cont = 0;
                // Percorre o nó
                for (DataSnapshot child : children) {
                    CUser user = child.getValue(CUser.class);
                    luser.add(user);
                    chaves.add(child.getKey().toString() + "/" + luser.get(cont).getsId()); //guardando chaves e id's
                    Log.i(child.getKey().toString() + "Nome: ", luser.get(cont).getsNome().toString());
                    cont++;
                }

                int tamanho = 0;
                for (int c = 0; c < luser.size(); c++) {
                    if (luser.get(c).getsPago().equals("Em aberto"))
                        tamanho++;
                }
                int limite = 0;
                String[] nomes = new String[tamanho];
                for (int b = 0; b < luser.size(); b++) {
                    if (luser.get(b).getsPago().equals("Em aberto")) {
                        nomes[limite] = (luser.get(b).getsId()) + ">" + luser.get(b).getsNome() + ">" + luser.get(b).getsSabor() + ">Em_aberto";
                        System.out.println("Enviando" + nomes[limite].toString() + " foi");
                        limite++;
                    }
                }
                if (nomes.length > 0)
                    inserir(nomes);
                else message0();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        lis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.i("Retorno ", ((TextView) view).getText().toString());
                String separated = ((TextView) view).getText().toString();
                separated = separated.trim();
                String[] idclic = separated.split(">");
                update(idclic[0]);
            }
        });
    }

    public void update(String id) {


        key="";
        for (int b = 0; b < chaves.size(); b++) {
            String[] chavesId = chaves.get(b).split("/");
            if (chavesId[1].equals(id)) {
                key = chavesId[0].toString();
                Log.i("Chave encontrada: ", key);
            }
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Confirmação de recebimento");
        alertDialogBuilder
                .setMessage("Confirma baixa de Cliente?")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // se sim for clicado ele fecha a
                        // activity atual
                        Map<String, Object> hopperUpdates = new HashMap<String, Object>();
                        hopperUpdates.put("Pedido/" + key + "/sPago", "Pago");
                        databaseReference.updateChildren(hopperUpdates);
                       // TQuitar.this.finish();
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // se não for precionado ele apenas termina o dialog
                        // e fecha a janelinha
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void message0() {
        Toast.makeText(this, "Não há pedidos em aberto.", Toast.LENGTH_SHORT).show();
        return;
    }

    public void inserir(String[] nomes) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nomes);
        lis.setAdapter(adapter);
        //lis.notifyAll();
    }

}
