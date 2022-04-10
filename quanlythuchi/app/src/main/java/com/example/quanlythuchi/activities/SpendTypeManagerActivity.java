package com.example.quanlythuchi.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.adapters.SpendTypeAdapter;
import com.example.quanlythuchi.helpers.AccountManagerSQLite;
import com.example.quanlythuchi.helpers.SpendTypeDAO;
import com.example.quanlythuchi.helpers.RecyclerItemClickListener;
import com.example.quanlythuchi.models.SpendType;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SpendTypeManagerActivity extends AppCompatActivity {
    private RecyclerView rcvSpendType;
    private SpendTypeAdapter spendTypeAdapter;
    private FloatingActionButton fabCreateSpendType;
    private String userName;
    private ArrayList<SpendType> spendTypesList;
    private AccountManagerSQLite accountManagerSQLite;
    private ActionMode actionMode;
    private SpendTypeDAO spendTypeDAO;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spend_type_manager);
        Bundle bundle = getIntent().getExtras();
        userName = bundle.getString("username");

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        rcvSpendType = findViewById(R.id.rcvSpendType);
        fabCreateSpendType= findViewById(R.id.fabCreateSpendType);
        //
        accountManagerSQLite=new AccountManagerSQLite(SpendTypeManagerActivity.this);
        spendTypeDAO=new SpendTypeDAO(accountManagerSQLite);

        //
        spendTypesList=spendTypeDAO.getAllSpendType(userName);
        //ADD DATA TO SPEND LIST'S RECYCLERVIEW
        createListSpendTypeView(spendTypesList);
        spendTypeClickEvent();
        //
        fabCreateSpendType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diaglogAddSpendType();
            }
        });

    }
    private void diaglogAddSpendType(){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(SpendTypeManagerActivity.this);
        View createSpendType=getLayoutInflater().inflate(R.layout.create_spend_type,null);
        alertDialog.setView(createSpendType);
        AlertDialog createDialog=alertDialog.create();

        EditText edtCreateSpendTypeName=createSpendType.findViewById(R.id.edtCreateSpendTypeName);
        RelativeLayout btnAddSpend=createSpendType.findViewById(R.id.btnAddSpendType);


        btnAddSpend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String createSpendTypeName=edtCreateSpendTypeName.getText().toString();
                spendTypeDAO.createSpendType(R.drawable.ic_other_spend,createSpendTypeName,userName);
                spendTypesList.clear();
                spendTypesList=spendTypeDAO.getAllSpendType(userName);
                createListSpendTypeView(spendTypesList);
                createDialog.cancel();
            }
        });
        createDialog.show();
    }
    public void createListSpendTypeView(ArrayList<SpendType> spendTypesList){
        spendTypeAdapter =new SpendTypeAdapter(SpendTypeManagerActivity.this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(SpendTypeManagerActivity.this, RecyclerView.VERTICAL,false);
        rcvSpendType.setLayoutManager(linearLayoutManager);
        spendTypeAdapter.setData(spendTypesList);
        rcvSpendType.setAdapter(spendTypeAdapter);
        spendTypeAdapter.notifyDataSetChanged();
    }
    public void spendTypeClickEvent(){
        rcvSpendType.addOnItemTouchListener(new RecyclerItemClickListener(SpendTypeManagerActivity.this, rcvSpendType, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
            @Override
            public void onLongItemClick(View view, int position) {
                if(actionMode!=null){
                }
                setPosition(position);
                actionMode= rcvSpendType.startActionMode(callback);
            }
        }));
    }
    public void deleteSpendType(int position){
        android.app.AlertDialog deleteUser=new android.app.AlertDialog.Builder(SpendTypeManagerActivity.this).create();
        deleteUser.setTitle("Xóa loại chi");
        deleteUser.setMessage("Bạn muốn xóa khoản chi?");

        deleteUser.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                spendTypesList=spendTypeDAO.getAllSpendType(userName);
                spendTypeDAO.deleteSpendType(spendTypesList.get(position).getIdSpendType());
                spendTypesList.clear();
                spendTypesList=spendTypeDAO.getAllSpendType(userName);
                createListSpendTypeView(spendTypesList);
                actionMode.finish();
            }
        });
        deleteUser.setButton(Dialog.BUTTON_NEGATIVE, "HỦY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        deleteUser.show();
    }
    public void editSpend(int position){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(SpendTypeManagerActivity.this);
        View editSpendType=getLayoutInflater().inflate(R.layout.edit_spend_type,null);
        alertDialog.setView(editSpendType);
        AlertDialog editDialog=alertDialog.create();

        EditText edtEditSpendTypeName=editSpendType.findViewById(R.id.edtEditSpendTypeName);
        RelativeLayout btnEditSpendType=editSpendType.findViewById(R.id.btnEditSpendType);
        /*edtEditSpendAmount.setText(spendsList.get(position).getSpendAmount()+"");
        spEditSpendType.setSelection(spendsList.get(position).getIdSpendType()-1);
        edtEditSpendNote.setText(spendsList.get(position).getNote()+"");*/
        btnEditSpendType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String spendId=spendTypesList.get(position).getIdSpendType()+"";
                String spendTypeName=edtEditSpendTypeName.getText().toString();
                spendTypeDAO.updateSpendType(spendTypeName,spendId+"");
                spendTypesList.clear();
                spendTypesList=spendTypeDAO.getAllSpendType(userName);
                createListSpendTypeView(spendTypesList);
                editDialog.cancel();
                actionMode.finish();
            }
        });
        editDialog.show();
    }
    private ActionMode.Callback callback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            actionMode.getMenuInflater().inflate(R.menu.context_menu,menu);
            actionMode.setTitle("Chọn chức năng");
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.rcv_edit:{
                    editSpend(position);
                    break;}
                case R.id.rcv_delete: {
                    deleteSpendType(position);
                    break;
                }
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
        }
    };

    public void setPosition(int position){
        this.position=position;
    }
}