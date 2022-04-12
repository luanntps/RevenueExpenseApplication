package com.example.quanlythuchi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.models.SpendType;

import java.util.List;

public class SpendTypeAdapter extends RecyclerView.Adapter<SpendTypeAdapter.SpendViewHolder> {
    private Context context;
    private List spendTypeList;
    public SpendTypeAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<SpendType> list){
        this.spendTypeList=list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public SpendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spend_type_item,parent,false);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_scale);
        view.startAnimation(animation);
        return new SpendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpendTypeAdapter.SpendViewHolder holder, int position) {
        SpendType spendType = (SpendType) spendTypeList.get(position);
        if(spendType ==null){
            return;
        }
        holder.imgSpendType.setImageResource(spendType.getSpendTypeIMG());
        holder.tvSpendTypeName.setText(spendType.getNameSpendType());
        /*PopupMenu popup=new PopupMenu(context,holder.user_item);
        MenuInflater menuInflater=popup.getMenuInflater();
        menuInflater.inflate(R.menu.action_list_menu,popup.getMenu())*/;
    }

    @Override
    public int getItemCount() {
        if(spendTypeList != null){
            return spendTypeList.size();
        }
        return 0;
    }

    public class SpendViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgSpendType;
        private TextView tvSpendTypeName;

        public SpendViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSpendType=itemView.findViewById(R.id.imgSpendType);
            tvSpendTypeName=itemView.findViewById(R.id.tvTypeSpendName);
        }
    }
}