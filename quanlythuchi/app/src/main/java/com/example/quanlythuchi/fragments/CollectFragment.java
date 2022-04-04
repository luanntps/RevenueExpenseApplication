package com.example.quanlythuchi.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlythuchi.HomeActivity;
import com.example.quanlythuchi.R;
import com.example.quanlythuchi.adapters.CollectAdapter;
import com.example.quanlythuchi.adapters.SpendAdapter;
import com.example.quanlythuchi.helpers.AccountManagerSQLite;
import com.example.quanlythuchi.helpers.CollectDAO;
import com.example.quanlythuchi.helpers.CollectTypeDAO;
import com.example.quanlythuchi.helpers.RecyclerItemClickListener;
import com.example.quanlythuchi.helpers.SpendDAO;
import com.example.quanlythuchi.helpers.SpendTypeDAO;
import com.example.quanlythuchi.models.Collect;
import com.example.quanlythuchi.models.CollectType;
import com.example.quanlythuchi.models.Spend;
import com.example.quanlythuchi.models.SpendType;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CollectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CollectFragment extends Fragment {
    private RecyclerView rcvCollect;
    private CollectAdapter collectAdapter;
    private FloatingActionButton fabCreateCollect;
    private String userName;
    private Context context;
    private ArrayList<Collect> collectsList;
    private AccountManagerSQLite accountManagerSQLite;
    private ActionMode actionMode;
    private CollectDAO collectDAO;
    private int position;
    private int spinnerIdCollectType;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CollectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CollectFragment newInstance(String param1, String param2) {
        CollectFragment fragment = new CollectFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_collect, container, false);
        rcvCollect = view.findViewById(R.id.rcvCollect);
        fabCreateCollect=view.findViewById(R.id.fabCreateCollect);
        //
        context=this.getContext();
        accountManagerSQLite=new AccountManagerSQLite(context);
        collectDAO=new CollectDAO(accountManagerSQLite);

        //
        HomeActivity activity = (HomeActivity) getActivity();
        userName=activity.getUsername();
        collectsList=collectDAO.getAllCollect(userName);
        //ADD DATA TO SPEND LIST'S RECYCLERVIEW
        createListCollectView(collectsList);
        collectClickEvent();
        //
        fabCreateCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diaglogAddCollect();
            }
        });
        return view;
    }

    private void setDataSpinner(Spinner spnClasses) {
        accountManagerSQLite=new AccountManagerSQLite(context);
        CollectTypeDAO collectTypeDAO=new CollectTypeDAO(accountManagerSQLite);
        ArrayList<CollectType> alCollectType = collectTypeDAO.getAllCollectType(userName);

        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        for (CollectType collectType : alCollectType) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("IdCollectType", collectType.getIdCollectType()+"");
            hashMap.put("CollectType", collectType.getNameCollectType()+"");
            list.add(hashMap);
        }
        SimpleAdapter adapter = new SimpleAdapter(context,
                list, R.layout.collect_type_item,
                new String[]{"CollectType"}, new int[]{R.id.tvCollectTypeItem});
        spnClasses.setAdapter(adapter);
    }

    private void diaglogAddCollect(){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(context);
        View createCollect=getLayoutInflater().inflate(R.layout.create_collect,null);
        alertDialog.setView(createCollect);
        AlertDialog createDialog=alertDialog.create();

        EditText edtCreateCollectAmount=createCollect.findViewById(R.id.edtCreateCollectAmount);
        TextView tvCreateCollectDate=createCollect.findViewById(R.id.tvCreateCollectDate);
        Spinner spCollectType=createCollect.findViewById(R.id.spCollectType);
        EditText edtCreateCollectNote=createCollect.findViewById(R.id.edtCreateCollectNote);
        RelativeLayout btnAddCollect=createCollect.findViewById(R.id.btnAddCollect);


        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        tvCreateCollectDate.setText(currentDate);

        setDataSpinner(spCollectType);

        btnAddCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> hashMap = (HashMap<String, String>) spCollectType.getSelectedItem();
                int collectAmount=Integer.parseInt(String.valueOf(edtCreateCollectAmount.getText()));
                String createCollectDate=tvCreateCollectDate.getText().toString();
                String crateCollectNote=edtCreateCollectNote.getText().toString();
                String collectType  = hashMap.get("CollectType");
                collectDAO.createCollect(collectAmount,createCollectDate,userName,collectType,crateCollectNote);
                collectsList.clear();
                collectsList=collectDAO.getAllCollect(userName);
                createListCollectView(collectsList);
                createDialog.cancel();
            }
        });
        createDialog.show();
    }
    public void createListCollectView(ArrayList<Collect> collectsList){
        collectAdapter =new CollectAdapter(context);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context, RecyclerView.VERTICAL,false);
        rcvCollect.setLayoutManager(linearLayoutManager);
        collectAdapter.setData(collectsList);
        rcvCollect.setAdapter(collectAdapter);
        collectAdapter.notifyDataSetChanged();
    }
    public void collectClickEvent(){
        rcvCollect.addOnItemTouchListener(new RecyclerItemClickListener(context, rcvCollect, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int idType=collectsList.get(position).getIdCollectType();
                CollectTypeDAO collectTypeDAO=new CollectTypeDAO(accountManagerSQLite);
                String nameType=collectTypeDAO.getCollectType(idType,userName).getNameCollectType();
                Toast.makeText(context,nameType,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onLongItemClick(View view, int position) {
                if(actionMode!=null){
                }
                setPosition(position);
                actionMode= rcvCollect.startActionMode(callback);
            }
        }));
    }
    public void deleteSpend(int position){
        android.app.AlertDialog deleteUser=new android.app.AlertDialog.Builder(context).create();
        deleteUser.setTitle("Xóa phiếu thu");
        deleteUser.setMessage("Bạn muốn xóa khoản thu?");

        deleteUser.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                collectsList=collectDAO.getAllCollect(userName);
                collectDAO.deleteCollect(collectsList.get(position).getId());
                collectsList.clear();
                collectsList=collectDAO.getAllCollect(userName);
                createListCollectView(collectsList);
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
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(context);
        View editCollect=getLayoutInflater().inflate(R.layout.edit_collect,null);
        alertDialog.setView(editCollect);
        AlertDialog editDialog=alertDialog.create();

        EditText edtEditCollectAmount=editCollect.findViewById(R.id.edtEditCollectAmount);
        Spinner spEditCollectType=editCollect.findViewById(R.id.spEditCollectType);
        EditText edtEditCollectNote=editCollect.findViewById(R.id.edtEditCollectNote);
        RelativeLayout btnEditCollect=editCollect.findViewById(R.id.btnEditCollect);
        setDataSpinner(spEditCollectType);

        /*edtEditCollectAmount.setText(collectsList.get(position).getCollectAmount()+"");
        spEditCollectType.setSelection(collectsList.get(position).getIdCollectType()-1);
        edtEditCollectNote.setText(collectsList.get(position).getNote()+"");*/
        btnEditCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> hashMap = (HashMap<String, String>) spEditCollectType.getSelectedItem();
                String collectType  = hashMap.get("CollectType");
                String collectId=collectsList.get(position).getId()+"";
                int collectAmount=Integer.parseInt(String.valueOf(edtEditCollectAmount.getText()));
                String crateCollectNote=edtEditCollectNote.getText().toString();

                collectDAO.updateCollect(collectId,userName,collectAmount,collectType,crateCollectNote);
                collectsList.clear();
                collectsList=collectDAO.getAllCollect(userName);
                createListCollectView(collectsList);
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
                    deleteSpend(position);
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