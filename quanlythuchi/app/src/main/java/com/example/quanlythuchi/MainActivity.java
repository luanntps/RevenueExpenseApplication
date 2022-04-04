package com.example.quanlythuchi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.quanlythuchi.adapters.UserAdapter;
import com.example.quanlythuchi.helpers.AccountManagerSQLite;
import com.example.quanlythuchi.helpers.RecyclerItemClickListener;
import com.example.quanlythuchi.helpers.UserDAO;
import com.example.quanlythuchi.models.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rcvUser;
    private UserAdapter userAdapter;
    private RelativeLayout rltAdd;
    private ArrayList<User> list;
    UserDAO userDAO;
    AccountManagerSQLite accountManagerSQLite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcvUser = findViewById(R.id.rcvUser);
        rltAdd=findViewById(R.id.rltAdd);
        list=new ArrayList<User>();
        accountManagerSQLite=new AccountManagerSQLite(MainActivity.this);
        userDAO=new UserDAO(accountManagerSQLite);
        userClickEvent();
        list=userDAO.getAllUser();
        loadMainPage(list);
        rltAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(MainActivity.this);
                View create=getLayoutInflater().inflate(R.layout.create_user,null);
                alertDialog.setView(create);
                AlertDialog createDialog=alertDialog.create();
                EditText edtName=create.findViewById(R.id.edtName);
                RelativeLayout btnAdd=create.findViewById(R.id.btnAdd);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name=edtName.getText()+"";
                        userDAO.createUser(name);
                        list.clear();
                        list=userDAO.getAllUser();
                        accountManagerSQLite.createDefaultSpendTypeAndCollectType(name);
                        loadMainPage(list);
                        createDialog.cancel();
                    }
                });
                createDialog.show();
            }
        });
    }
    public void loadMainPage(ArrayList<User> list){
        userAdapter = new UserAdapter(MainActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
        rcvUser.setLayoutManager(linearLayoutManager);
        userAdapter.setData(list);
        rcvUser.setAdapter(userAdapter);
        userAdapter.notifyDataSetChanged();
        if (userDAO.getAllUser().size()==4){
            rltAdd.setVisibility(View.GONE);
        }else
            rltAdd.setVisibility(View.VISIBLE);
    }

    public void userClickEvent(){
        rcvUser.addOnItemTouchListener(new RecyclerItemClickListener(this, rcvUser, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String name=list.get(position).getName();
                Intent intent=new Intent(MainActivity.this, HomeActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("KEY_NAME",name);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                android.app.AlertDialog deleteUser=new android.app.AlertDialog.Builder(MainActivity.this).create();
                deleteUser.setTitle("Xóa user");
                deleteUser.setMessage("Bạn muốn xóa user? Xóa user sẽ xóa cả dữ liệu thu và chi của user đó.");

                deleteUser.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        userDAO.deleteUser(list.get(position).getName());
                        list.clear();
                        list=userDAO.getAllUser();
                        loadMainPage(list);
                    }
                });
                deleteUser.setButton(Dialog.BUTTON_NEGATIVE, "HỦY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                deleteUser.show();
            }
        }));
    }
}