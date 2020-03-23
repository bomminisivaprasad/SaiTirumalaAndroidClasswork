package com.example.farmerskart;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MyViewHolder> {
    Context ct;
    List<MyItem> list;
    public ItemsAdapter(Context applicationContext, List<MyItem> itemsList) {
        ct = applicationContext;
        list = itemsList;
    }

    @NonNull
    @Override
    public ItemsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ct).inflate(R.layout.itemrow,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.MyViewHolder holder, int position) {
        holder.tv1.setText(list.get(position).getProductName());
        holder.tv2.setText(list.get(position).getProductPricewithunits());
        Glide.with(ct).load(list.get(position).getItemimagePath()).into(holder.iv);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv1,tv2;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.imageView);
            tv1 = itemView.findViewById(R.id.textView);
            tv2 = itemView.findViewById(R.id.textView2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ct,ItemDetailsActivity.class);
                    i.putExtra("pname",list.get(getAdapterPosition()).getProductName());
                    i.putExtra("pprice",list.get(getAdapterPosition()).getProductPricewithunits());
                    i.putExtra("pimagepath",list.get(getAdapterPosition()).getItemimagePath());
                    i.putExtra("pownername",list.get(getAdapterPosition()).getOwnerName());
                    i.putExtra("pownermobile",list.get(getAdapterPosition()).getOwnerMobile());
                    i.putExtra("pownerdistrict",list.get(getAdapterPosition()).getOwnerDistrict());
                    i.putExtra("powneraddress",list.get(getAdapterPosition()).getOwnerAddress());
                    i.putExtra("pownerimagepath",list.get(getAdapterPosition()).getOwnerImagepath());
                    i.putExtra("pownerlocation",list.get(getAdapterPosition()).getOwnerLocation());
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ct.startActivity(i);
                }
            });
        }
    }
}
