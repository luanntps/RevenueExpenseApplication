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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlythuchi.HomeActivity;
import com.example.quanlythuchi.MainActivity;
import com.example.quanlythuchi.R;
import com.example.quanlythuchi.adapters.SpendAdapter;
import com.example.quanlythuchi.helpers.AccountManagerSQLite;
import com.example.quanlythuchi.helpers.CollectTypeDAO;
import com.example.quanlythuchi.helpers.RecyclerItemClickListener;
import com.example.quanlythuchi.helpers.SpendDAO;
import com.example.quanlythuchi.helpers.SpendTypeDAO;
import com.example.quanlythuchi.models.Spend;
import com.example.quanlythuchi.models.SpendType;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SpendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpendFragment extends Fragment {
    private RecyclerView rcvSpend;
    private SpendAdapter spendAdapter;
    private FloatingActionButton fabCreateSpend;
    private String userName;
    private Context context;
    private ArrayList<Spend> spendsList;
    private SpendDAO spendDAO;
    private AccountManagerSQLite accountManagerSQLite;
    private ActionMode actionMode;
    private int position;
    private int spinnerIdSpendType;
    TextView tvLoaichi;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SpendFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SpendFragment newInstance(String param1, String param2) {
        SpendFragment fragment = new SpendFragment();
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
        View view= inflater.inflate(R.layout.fragment_spend, container, false);
        rcvSpend = view.findViewById(R.id.rcvSpend);
        context=this.getContext();
        accountManagerSQLite=new AccountManagerSQLite(context);
        spendDAO=new SpendDAO(accountManagerSQLite);
        fabCreateSpend=view.findViewById(R.id.fabCreateSpend);
        //
        tvLoaichi=view.findViewById(R.id.tvBarNoSpend);

        HomeActivity activity = (HomeActivity) getActivity();
        userName=activity.getUsername();
        spendsList=spendDAO.getAllSpend(userName);
        //ADD DATA TO SPEND LIST'S RECYCLERVIEW
        createListSpendView(spendsList);
        spendClickEvent();
        //
        fabCreateSpend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diaglogAddSpend();
            }
        });
        return view;
    }
    private void setDataSpinner(Spinner spnClasses) {
        Context context=this.getContext();
        accountManagerSQLite=new AccountManagerSQLite(context);
        SpendTypeDAO spendTypeDAO=new SpendTypeDAO(accountManagerSQLite);
        ArrayList<SpendType> alSpendType = spendTypeDAO.getAllSpendType(userName);

        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        for (SpendType spendType : alSpendType) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("IdSpendType", spendType.getIdSpendType()+"");
            hashMap.put("SpendType", spendType.getNameSpendType());
            list.add(hashMap);
        }
        SimpleAdapter adapter = new SimpleAdapter(context,
                list, R.layout.spend_type_item,
                new String[]{"SpendType"}, new int[]{R.id.tvSpendTypeItem});
        spnClasses.setAdapter(adapter);
    }

    private void diaglogAddSpend(){
        Context context=this.getContext();
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(context);
        View createSpend=getLayoutInflater().inflate(R.layout.create_spend,null);
        alertDialog.setView(createSpend);
        AlertDialog createDialog=alertDialog.create();

        EditText edtCreateSpendAmount=createSpend.findViewById(R.id.edtCreateSpendAmount);
        TextView tvCreateSpendDate=createSpend.findViewById(R.id.tvCreateSpendDate);
        Spinner spSpendType=createSpend.findViewById(R.id.spSpendType);
        EditText edtCreateSpendNote=createSpend.findViewById(R.id.edtCreateSpendNote);
        RelativeLayout btnAddSpend=createSpend.findViewById(R.id.btnAddSpend);


        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        tvCreateSpendDate.setText(currentDate);

        setDataSpinner(spSpendType);

        btnAddSpend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> hashMap = (HashMap<String, String>) spSpendType.getSelectedItem();
                String spendType  = hashMap.get("SpendType");
                int spendAmount=Integer.parseInt(String.valueOf(edtCreateSpendAmount.getText()));
                String createSpendDate=tvCreateSpendDate.getText().toString();
                String crateSpendNote=edtCreateSpendNote.getText().toString();
                spendDAO.createSpend(spendAmount,createSpendDate,userName,spendType,crateSpendNote);
                spendsList.clear();
                spendsList=spendDAO.getAllSpend(userName);
                createListSpendView(spendsList);
                createDialog.cancel();
            }
        });
        createDialog.show();
    }
    public void createListSpendView(ArrayList<Spend> spendsList){
        spendAdapter =new SpendAdapter(context);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        rcvSpend.setLayoutManager(linearLayoutManager);
        spendAdapter.setData(spendsList);
        rcvSpend.setAdapter(spendAdapter);
        spendAdapter.notifyDataSetChanged();
        //registerForContextMenu(rcvSpend);
    }
    public void spendClickEvent(){
        rcvSpend.addOnItemTouchListener(new RecyclerItemClickListener(context, rcvSpend, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int idType=spendsList.get(position).getIdSpendType();
                SpendTypeDAO spendTypeDAO=new SpendTypeDAO(accountManagerSQLite);
                String nameType=spendTypeDAO.getSpendType(idType,userName).getNameSpendType();
                Toast.makeText(context,nameType,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onLongItemClick(View view, int position) {
                if(actionMode!=null){
                }
                setPosition(position);
                actionMode= rcvSpend.startActionMode(callback);
            }
        }));
    }
    public void editSpend(int position){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(context);
        View editSpend=getLayoutInflater().inflate(R.layout.edit_spend,null);
        alertDialog.setView(editSpend);
        AlertDialog editDialog=alertDialog.create();

        EditText edtEditSpendAmount=editSpend.findViewById(R.id.edtEditSpendAmount);
        Spinner spEditSpendType=editSpend.findViewById(R.id.spEditSpendType);
        EditText edtEditSpendNote=editSpend.findViewById(R.id.edtEditSpendNote);
        RelativeLayout btnEditCollect=editSpend.findViewById(R.id.btnEditSpend);
        setDataSpinner(spEditSpendType);

        /*edtEditSpendAmount.setText(spendsList.get(position).getSpendingAmount()+"");
        spEditSpendType.setSelection(spendsList.get(position).getIdSpendType()-1);
        edtEditSpendNote.setText(spendsList.get(position).getNote()+"");*/

        btnEditCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> hashMap = (HashMap<String, String>) spEditSpendType.getSelectedItem();
                String spendType  = hashMap.get("SpendType");
                String spendId=spendsList.get(position).getId()+"";
                int spendAmount=Integer.parseInt(String.valueOf(edtEditSpendAmount.getText()));
                String crateSpendNote=edtEditSpendNote.getText().toString();

                spendDAO.updateSpend(spendId,userName,spendAmount,spendType,crateSpendNote);
                spendsList.clear();
                spendsList=spendDAO.getAllSpend(userName);
                createListSpendView(spendsList);
                editDialog.cancel();
                actionMode.finish();
            }
        });
        editDialog.show();
    }
    public void deleteSpend(int position){
        android.app.AlertDialog deleteUser=new android.app.AlertDialog.Builder(context).create();
        deleteUser.setTitle("Xóa phiếu chi");
        deleteUser.setMessage("Bạn muốn xóa khoản chi?");

        deleteUser.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                spendDAO.deleteSpend(spendsList.get(position).getId());
                spendsList.clear();
                spendsList=spendDAO.getAllSpend(userName);
                createListSpendView(spendsList);
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
                case R.id.rcv_edit: {
                    editSpend(position);
                    break;
                }
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