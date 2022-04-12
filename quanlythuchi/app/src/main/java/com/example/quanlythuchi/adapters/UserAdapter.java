package com.example.quanlythuchi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.models.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private Context context;
    private List userList;
    public UserAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<User> list){
        this.userList=list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_scale);
        view.startAnimation(animation);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {
        User chatUser = (User) userList.get(position);
        if(chatUser ==null){
            return;
        }
        holder.tvUserName.setText(chatUser.getName());
        holder.user_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        /*holder.user_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog deleteUser=new AlertDialog.Builder(context).create();
                deleteUser.setTitle("Xóa user");
                deleteUser.setMessage("Bạn muốn xóa user? Xóa user sẽ xóa cả dữ liệu thu và chi của user đó.");
                deleteUser.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AccountManagerSQLite accountManagerSQLite=new AccountManagerSQLite(context);
                        UserDAO userDAO=new UserDAO(accountManagerSQLite);
                        userDAO.deleteUser(chatUser.getName());
                        notifyDataSetChanged();
                    }
                });
                deleteUser.setButton(Dialog.BUTTON_NEGATIVE, "HỦY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                deleteUser.show();
                return false;
            }
        });*/
    }

    @Override
    public int getItemCount() {
        if(userList != null){
            return userList.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        private TextView tvUserName;
        private CardView user_item;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName=itemView.findViewById(R.id.tvUserName);
            user_item=itemView.findViewById(R.id.user_item);
        }
    }
}
