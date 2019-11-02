package com.example.investigacion_filtros.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.investigacion_filtros.models.Product;
import com.example.investigacion_filtros.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolderProduct> implements Filterable {

    private Context context;
    private List<Product> products;
    private List<Product> fullProducts;

    private Filter productsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Product> filteredlist = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0){
                filteredlist.addAll(fullProducts);
            }else{
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Product item: fullProducts){
                    if (item.getName().toLowerCase().contains(filterPattern)){
                        filteredlist.add(item);
                    }
                }
            }

            FilterResults result = new FilterResults();
            result.values = filteredlist;

            return result;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            products.clear();
            products.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public ProductAdapter(Context context, List<Product> products){
        this.context = context;
        this.products = products;
        fullProducts = new ArrayList<>(products);
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

    public void setProducts(List<Product> products){
        this.products = products;
    }

    public class ViewHolderProduct extends RecyclerView.ViewHolder{

        TextView tvName;

        public ViewHolderProduct(View itemView){
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
        }
    }

    @Override
    public Filter getFilter() {
        return productsFilter;
    }
}
