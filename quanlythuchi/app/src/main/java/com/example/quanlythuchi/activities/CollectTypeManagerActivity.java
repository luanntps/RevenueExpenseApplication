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
import com.example.quanlythuchi.adapters.CollectTypeAdapter;
import com.example.quanlythuchi.helpers.AccountManagerSQLite;
import com.example.quanlythuchi.helpers.CollectTypeDAO;
import com.example.quanlythuchi.helpers.RecyclerItemClickListener;
import com.example.quanlythuchi.models.CollectType;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CollectTypeManagerActivity extends AppCompatActivity {
    private RecyclerView rcvCollectType;
    private CollectTypeAdapter collectTypeAdapter;
    private FloatingActionButton fabCreateCollectType;
    private String userName;
    private ArrayList<CollectType> collectTypesList;
    private AccountManagerSQLite accountManagerSQLite;
    private ActionMode actionMode;
    private CollectTypeDAO collectTypeDAO;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_type_manager);
        Bundle bundle = getIntent().getExtras();
        userName = bundle.getString("username");

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        rcvCollectType = findViewById(R.id.rcvCollectType);
        fabCreateCollectType= findViewById(R.id.fabCreateCollectType);
        //
        accountManagerSQLite=new AccountManagerSQLite(CollectTypeManagerActivity.this);
        collectTypeDAO=new CollectTypeDAO(accountManagerSQLite);

        //
        collectTypesList=collectTypeDAO.getAllCollectType(userName);
        //ADD DATA TO SPEND LIST'S RECYCLERVIEW
        createListCollectTypeView(collectTypesList);
        collectTypeClickEvent();
        //
        fabCreateCollectType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diaglogAddCollectType();
            }
        });

    }
    private void diaglogAddCollectType(){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(CollectTypeManagerActivity.this);
        View createCollectType=getLayoutInflater().inflate(R.layout.create_collect_type,null);
        alertDialog.setView(createCollectType);
        AlertDialog createDialog=alertDialog.create();

        EditText edtCreateCollectTypeName=createCollectType.findViewById(R.id.edtCreateCollectTypeName);
        RelativeLayout btnAddCollect=createCollectType.findViewById(R.id.btnAddCollectType);


        btnAddCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String createCollectTypeName=edtCreateCollectTypeName.getText().toString();
                collectTypeDAO.createCollectType(R.drawable.ic_other_collect,createCollectTypeName,userName);
                collectTypesList.clear();
                collectTypesList=collectTypeDAO.getAllCollectType(userName);
                createListCollectTypeView(collectTypesList);
                createDialog.cancel();
            }
        });
        createDialog.show();
    }
    public void createListCollectTypeView(ArrayList<CollectType> collectTypesList){
        collectTypeAdapter =new CollectTypeAdapter(CollectTypeManagerActivity.this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(CollectTypeManagerActivity.this, RecyclerView.VERTICAL,false);
        rcvCollectType.setLayoutManager(linearLayoutManager);
        collectTypeAdapter.setData(collectTypesList);
        rcvCollectType.setAdapter(collectTypeAdapter);
        collectTypeAdapter.notifyDataSetChanged();
    }
    public void collectTypeClickEvent(){
        rcvCollectType.addOnItemTouchListener(new RecyclerItemClickListener(CollectTypeManagerActivity.this, rcvCollectType, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
            @Override
            public void onLongItemClick(View view, int position) {
                if(actionMode!=null){
                }
                setPosition(position);
                actionMode= rcvCollectType.startActionMode(callback);
            }
        }));
    }
    public void deleteCollectType(int position){
        android.app.AlertDialog deleteUser=new android.app.AlertDialog.Builder(CollectTypeManagerActivity.this).create();
        deleteUser.setTitle("Xóa loại thu");
        deleteUser.setMessage("Bạn muốn xóa loại thu?");

        deleteUser.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                collectTypesList=collectTypeDAO.getAllCollectType(userName);
                collectTypeDAO.deleteCollectType(collectTypesList.get(position).getIdCollectType());
                collectTypesList.clear();
                collectTypesList=collectTypeDAO.getAllCollectType(userName);
                createListCollectTypeView(collectTypesList);
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
    public void editCollect(int position){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(CollectTypeManagerActivity.this);
        View editCollectType=getLayoutInflater().inflate(R.layout.edit_collect_type,null);
        alertDialog.setView(editCollectType);
        AlertDialog editDialog=alertDialog.create();

        EditText edtEditCollectTypeName=editCollectType.findViewById(R.id.edtEditCollectTypeName);
        RelativeLayout btnEditCollectType=editCollectType.findViewById(R.id.btnEditCollectType);
        /*edtEditCollectAmount.setText(collectsList.get(position).getCollectAmount()+"");
        spEditCollectType.setSelection(collectsList.get(position).getIdCollectType()-1);
        edtEditCollectNote.setText(collectsList.get(position).getNote()+"");*/
        btnEditCollectType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String collectId=collectTypesList.get(position).getIdCollectType()+"";
                String collectTypeName=edtEditCollectTypeName.getText().toString();
                collectTypeDAO.updateCollectType(collectTypeName,collectId+"");
                collectTypesList.clear();
                collectTypesList=collectTypeDAO.getAllCollectType(userName);
                createListCollectTypeView(collectTypesList);
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
                    editCollect(position);
                    break;}
                case R.id.rcv_delete: {
                    deleteCollectType(position);
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
