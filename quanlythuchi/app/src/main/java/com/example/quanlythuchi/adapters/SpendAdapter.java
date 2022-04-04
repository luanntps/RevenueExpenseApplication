package com.example.quanlythuchi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.models.Spend;

import java.util.List;

public class SpendAdapter extends RecyclerView.Adapter<SpendAdapter.SpendViewHolder> {
    private Context context;
    private List spendList;
    public SpendAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<Spend> list){
        this.spendList=list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public SpendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spend_item,parent,false);
        return new SpendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpendAdapter.SpendViewHolder holder, int position) {
        Spend spend = (Spend) spendList.get(position);
        if(spend ==null){
            return;
        }

        StringBuilder spendingAmount=new StringBuilder(spend.getSpendingAmount()+"");
        if(spendingAmount.length()>3){
            for(int i=spendingAmount.length()-3;i>=1;i=i-3){
                spendingAmount.insert(i,".");
            }
        }
        holder.tvNoSpend.setText(position+1+"");
        holder.imgSpend.setImageResource(spend.getImgSpendSrc());
        holder.tvSpendAmount.setText(spendingAmount+" VND");
        holder.tvSpendDate.setText(spend.getDate());
        holder.tvSpendNote.setText(spend.getNote());
        /*PopupMenu popup=new PopupMenu(context,holder.user_item);
        MenuInflater menuInflater=popup.getMenuInflater();
        menuInflater.inflate(R.menu.action_list_menu,popup.getMenu())*/;
        holder.spend_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  popup.show();
            }
        });
        holder.spend_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        if(spendList != null){
            return spendList.size();
        }
        return 0;
    }

    public class SpendViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgSpend;
        private TextView tvNoSpend;
        private TextView tvSpendDate;
        private TextView tvSpendAmount;
        private TextView tvSpendNote;
        private CardView spend_item;
        public SpendViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSpend=itemView.findViewById(R.id.imgSpend);
            tvNoSpend=itemView.findViewById(R.id.tvNoSpend);
            tvSpendDate=itemView.findViewById(R.id.tvSpendDate);
            tvSpendAmount=itemView.findViewById(R.id.tvSpendAmount);
            tvSpendNote=itemView.findViewById(R.id.tvSpendNote);
            spend_item=itemView.findViewById(R.id.spend_item);
        }
    }
}
