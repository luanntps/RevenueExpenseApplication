package com.example.quanlythuchi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.models.Collect;
import com.example.quanlythuchi.models.CollectType;

import java.util.List;

public class CollectTypeAdapter extends RecyclerView.Adapter<CollectTypeAdapter.CollectViewHolder> {
    private Context context;
    private List collectTypeList;
    public CollectTypeAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<CollectType> list){
        this.collectTypeList=list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CollectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collect_type_item,parent,false);
        return new CollectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectTypeAdapter.CollectViewHolder holder, int position) {
        CollectType collectType = (CollectType) collectTypeList.get(position);
        if(collectType ==null){
            return;
        }
        holder.imgCollectType.setImageResource(collectType.getCollectTypeIMG());
        holder.tvCollectTypeName.setText(collectType.getNameCollectType());
        /*PopupMenu popup=new PopupMenu(context,holder.user_item);
        MenuInflater menuInflater=popup.getMenuInflater();
        menuInflater.inflate(R.menu.action_list_menu,popup.getMenu())*/;
    }

    @Override
    public int getItemCount() {
        if(collectTypeList != null){
            return collectTypeList.size();
        }
        return 0;
    }

    public class CollectViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgCollectType;
        private TextView tvCollectTypeName;

        public CollectViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCollectType=itemView.findViewById(R.id.imgCollectType);
            tvCollectTypeName=itemView.findViewById(R.id.tvTypeCollectName);
        }
    }
}