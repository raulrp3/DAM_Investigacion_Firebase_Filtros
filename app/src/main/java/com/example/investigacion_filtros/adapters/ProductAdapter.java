package com.example.investigacion_filtros.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.investigacion_filtros.models.Product;
import com.example.investigacion_filtros.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolderProduct>{

    private Context context;
    private List<Product> products;

    public ProductAdapter(Context context, List<Product> products){
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolderProduct onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product, viewGroup, false);
        final ViewHolderProduct vhp = new ViewHolderProduct(view);

        return vhp;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderProduct viewHolderProduct, int i) {
        viewHolderProduct.tvName.setText(products.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolderProduct extends RecyclerView.ViewHolder{

        TextView tvName;

        public ViewHolderProduct(View itemView){
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}
