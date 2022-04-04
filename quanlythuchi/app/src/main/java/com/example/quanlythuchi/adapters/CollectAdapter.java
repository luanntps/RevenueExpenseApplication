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

import java.util.List;

public class CollectAdapter extends RecyclerView.Adapter<CollectAdapter.CollectViewHolder> {
    private Context context;
    private List collectList;
    public CollectAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<Collect> list){
        this.collectList=list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CollectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collect_item,parent,false);
        return new CollectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectAdapter.CollectViewHolder holder, int position) {
        Collect collect = (Collect) collectList.get(position);
        if(collect ==null){
            return;
        }
        StringBuilder collectAmount=new StringBuilder(collect.getCollectAmount()+"");
        if(collectAmount.length()>3){
            for(int i=collectAmount.length()-3;i>=1;i=i-3){
                collectAmount.insert(i,".");
            }
        }
        holder.tvNoCollect.setText(position+1+"");
        holder.imgCollect.setImageResource(collect.getImgCollectSrc());
        holder.tvCollectAmount.setText(collectAmount+" VND");
        holder.tvCollectDate.setText(collect.getDate());
        holder.tvCollectNote.setText(collect.getNote());
        /*PopupMenu popup=new PopupMenu(context,holder.user_item);
        MenuInflater menuInflater=popup.getMenuInflater();
        menuInflater.inflate(R.menu.action_list_menu,popup.getMenu())*/;
        holder.collect_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  popup.show();
            }
        });
        holder.collect_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        if(collectList != null){
            return collectList.size();
        }
        return 0;
    }

    public class CollectViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgCollect;
        private TextView tvNoCollect;
        private TextView tvCollectDate;
        private TextView tvCollectAmount;
        private TextView tvCollectNote;
        private CardView collect_item;
        public CollectViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCollect=itemView.findViewById(R.id.imgCollect);
            tvNoCollect=itemView.findViewById(R.id.tvNoCollect);
            tvCollectDate=itemView.findViewById(R.id.tvCollectDate);
            tvCollectAmount=itemView.findViewById(R.id.tvCollectAmount);
            tvCollectNote=itemView.findViewById(R.id.tvCollectNote);
            collect_item=itemView.findViewById(R.id.collect_item);
        }
    }
}

