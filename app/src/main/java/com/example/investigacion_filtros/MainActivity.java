package com.example.investigacion_filtros;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.investigacion_filtros.adapters.ProductAdapter;
import com.example.investigacion_filtros.models.Allergen;
import com.example.investigacion_filtros.models.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Product> products;
    private RecyclerView rvProducts;
    private ProductAdapter productAdapter;
    private FirebaseFirestore mFirestore;
    private EditText etFilter;
    private Button btnMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        getProducts();
    }

    private void getProducts(){
        products = new ArrayList<>();

        mFirestore.collection("products").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document: task.getResult()){
                        String name = document.getString("name");
                        String id = document.getId();
                        products.add(new Product(name, id));
                    }

                    productAdapter = new ProductAdapter(MainActivity.this, products);
                    rvProducts.setAdapter(productAdapter);
                }
            }
        });
    }

    private void initUI(){
        mFirestore = FirebaseFirestore.getInstance();
        rvProducts = findViewById(R.id.rvProductos);
        rvProducts.setHasFixedSize(true);
        rvProducts.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        etFilter = findViewById(R.id.etFilter);

        etFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                productAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final List<Allergen> allergens = new ArrayList<>();
                mFirestore.collection("allergens").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document: task.getResult()){
                                String allergen = document.getString("name");
                                String product = document.getString("product");
                                allergens.add(new Allergen(allergen, product));
                            }
                            inflateMenuAllergens(allergens);
                        }
                    }
                });
            }
        });
    }

    private void inflateMenuAllergens(final List<Allergen> allergens){
        final ArrayList allergenFilter = new ArrayList();
        final CharSequence[] cs = new CharSequence[allergens.size()];

        for (int i = 0; i < allergens.size(); i++) {
            cs[i] = allergens.get(i).getName();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Alégenos");
        builder.setMultiChoiceItems(cs, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked){
                    allergenFilter.add(cs[which]);
                }
            }
        }).setPositiveButton("FILTRAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final ArrayList productFilter = new ArrayList();
                final List<Product> result = new ArrayList<>();

                for (int i = 0; i < allergenFilter.size(); i++){
                    String allergen = allergenFilter.get(i).toString();
                    for (int j = 0; j < allergens.size(); j++){
                        String product = allergens.get(j).getProduct();
                        if (allergen.equals(allergens.get(j).getName())){
                            productFilter.add(product);
                        }
                    }
                }

                for (int i = 0; i < productFilter.size(); i++){
                    String product = productFilter.get(i).toString();
                    for (int j = 0; j < products.size(); j++){
                        String id = products.get(j).getId();
                        if (product.equals(id)){
                            result.add(products.get(j));
                        }
                    }
                }

                productAdapter.setProducts(result);
                productAdapter.notifyDataSetChanged();
            }
        });

        builder.show();
        builder.create();
    }
}
