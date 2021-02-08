package com.appDB;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.appDB.database.DictionaryDataBase;
import com.appDB.model.Palabra;

public class MainActivity extends AppCompatActivity {

    private EditText mEditTextWord;
    private EditText mEditTextDefinition;
    private DictionaryDataBase mDB;
    private ListView mListView;
    private Palabra palabraSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDB= new DictionaryDataBase(MainActivity.this);

        mEditTextWord=findViewById(R.id.editTextWord);
        mEditTextDefinition=findViewById(R.id.editTextDefinition);
        Button botonUpdate=findViewById(R.id.buttonAddUpdate);
        mListView=findViewById(R.id.listView);
        cargarListaPalabras();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                palabraSeleccionada=mDB.buscarPorId(id);
                mEditTextWord.setText(palabraSeleccionada.getPalabra());
                mEditTextDefinition.setText(palabraSeleccionada.getDefinicion());
            }


        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("palabras","Borrar id");
                mDB.borrarRegistro(id);
                cargarListaPalabras();
                return false;
            }

        });

    }
    private void cargarListaPalabras(){
        SimpleCursorAdapter simpleCursorAdapter=new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                mDB.getListaPalabras(),
                new String[]{"palabra"},
                new int[]{android.R.id.text1},
                0
                );
        mListView.setAdapter(simpleCursorAdapter);
    }
    public void grabar(View view){
        //  es grabar nuevo o modificar
        if (palabraSeleccionada==null){
            mDB.crearRegistro(mEditTextWord.getText().toString(),
                    mEditTextDefinition.getText().toString());
        }else{
            palabraSeleccionada.setPalabra(mEditTextWord.getText().toString());
            palabraSeleccionada.setDefinicion(mEditTextDefinition.getText().toString());
            mDB.modificarRegistro(palabraSeleccionada);
            palabraSeleccionada=null;
        }
        mEditTextWord.setText("");
        mEditTextDefinition.setText("");
        cargarListaPalabras();
    }
}