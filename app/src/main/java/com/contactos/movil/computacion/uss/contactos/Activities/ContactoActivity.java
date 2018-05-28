package com.contactos.movil.computacion.uss.contactos.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.contactos.movil.computacion.uss.contactos.Modelo.DAO.DaoContacto;
import com.contactos.movil.computacion.uss.contactos.Modelo.Contacto;
import com.contactos.movil.computacion.uss.contactos.R;

public class ContactoActivity extends AppCompatActivity {

    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    EditText etNombreE, etTelefonoE, etEmailE;
    DaoContacto daocontacto;
    Contacto contacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        etNombreE = (EditText) findViewById(R.id.etNombreE);
        etTelefonoE = (EditText) findViewById(R.id.etTelefonoE);
        etEmailE = (EditText) findViewById(R.id.etEmailE);
        daocontacto = new DaoContacto(this);

        if (bundle != null) {
            contacto = new Contacto(bundle.get("email").toString(),
                    bundle.get("phone").toString(),
                    bundle.get("nombre").toString(),
                    Integer.parseInt(bundle.get("id").toString()));
            etNombreE.setText(contacto.getNombre());
            etTelefonoE.setText(contacto.getTelefono());
            etEmailE.setText(contacto.getEmail());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contacto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_guardar:
                if (!etNombreE.getText().toString().equals("") &&
                        !etTelefonoE.getText().toString().equals("") &&
                        !etEmailE.getText().toString().equals("")) {

                    if (etEmailE.getText().toString().matches(PATTERN_EMAIL)) {

                        daocontacto.updateEntry(new Contacto(etEmailE.getText().toString()
                                , etTelefonoE.getText().toString()
                                , etNombreE.getText().toString()
                                , contacto.getId()));
                        Intent i = new Intent(ContactoActivity.this, MainActivity.class);
                        startActivity(i);
                        Toast.makeText(ContactoActivity.this,"Editado Correctamente",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ContactoActivity.this, "Email incorrecto", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(ContactoActivity.this, "Ingreso los datos requeridos", Toast.LENGTH_SHORT).show();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
