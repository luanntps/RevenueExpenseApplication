package com.example.quanlythuchi.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.activities.HomeActivity;
import com.example.quanlythuchi.helpers.AccountManagerSQLite;
import com.example.quanlythuchi.helpers.CollectDAO;
import com.example.quanlythuchi.helpers.CollectTypeDAO;
import com.example.quanlythuchi.helpers.SpendDAO;
import com.example.quanlythuchi.models.Collect;
import com.example.quanlythuchi.models.Spend;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private PieChart mChart;
    private String username;
    private AccountManagerSQLite accountManagerSQLite;
    private CollectDAO collectDAO;
    private SpendDAO spendDAO;
    private ArrayList<Collect> alCollect;
    private ArrayList<Spend> alSpend;
    private TextView tvTotalCollectAmount,tvTotalSpendAmount,tvTotalRevenue;
    private FloatingActionButton fabCalendarRevenue;
    static DecimalFormat format = new DecimalFormat("0.#");
    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view=inflater.inflate(R.layout.fragment_profile, container, false);

        HomeActivity activity = (HomeActivity) getActivity();
        username=activity.getUsername();

        tvTotalCollectAmount=view.findViewById(R.id.tvTotalCollectAmount);
        tvTotalSpendAmount=view.findViewById(R.id.tvTotalSpendAmount);
        tvTotalRevenue=view.findViewById(R.id.tvTotalRevenue);
        fabCalendarRevenue=view.findViewById(R.id.fabCalendarRevenue);

        accountManagerSQLite=new AccountManagerSQLite(getContext());
        collectDAO=new CollectDAO(accountManagerSQLite);
        spendDAO=new SpendDAO(accountManagerSQLite);

        alCollect=collectDAO.getAllCollect(username);
        alSpend=spendDAO.getAllSpend(username);

        setTotal(alSpend,alCollect);

        fabCalendarRevenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetDateRevenue();
            }
        });

        return view;
    }
    @SuppressLint("SetTextI18n")
    public void setTotal(@org.jetbrains.annotations.NotNull ArrayList<Spend> alSpend, ArrayList<Collect> alCollect){
        Double totalCollect=0.0;
        Double totalSpend=0.0;
        Double totalRevenue;
        for(Spend s:alSpend){
            totalSpend+=(double)s.getSpendingAmount();
        }
        for(Collect s:alCollect){
            totalCollect+=(double)s.getCollectAmount();
        }
        totalRevenue=totalCollect-totalSpend;
        tvTotalSpendAmount.setText(VNDFormat(totalSpend)+" VND");
        tvTotalCollectAmount.setText(VNDFormat(totalCollect)+" VND");
        tvTotalRevenue.setText(VNDFormat(totalRevenue)+" VND");
        if(totalRevenue<0){
            tvTotalRevenue.setTextColor(Color.parseColor("#90FF0000"));
        }else tvTotalRevenue.setTextColor(Color.parseColor("#9050C878"));
    }

    public void SetDateRevenue() {
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day0fMonth) {
                String stringMonth, stringDay, stringYear, date;
                stringYear=year+"";
                if(month<10){
                    stringMonth="0"+(month+1);
                }else stringMonth=month+"";
                if(day0fMonth<10){
                    stringDay="0"+day0fMonth;
                }else stringDay=day0fMonth+"";
                date=stringDay+"-"+stringMonth+"-"+stringYear;
                alCollect.clear();
                alSpend.clear();
                alCollect=collectDAO.getCollectByDate(username,date);
                alSpend=spendDAO.getSpendByDate(username,date);
                setTotal(alSpend,alCollect);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public StringBuilder VNDFormat(double money){
        StringBuilder stringBuilder=new StringBuilder(format.format(money)+"");
        if(stringBuilder.length()>3){
            for(int i=stringBuilder.length()-3;i>=1;i=i-3){
                stringBuilder.insert(i,".");
            }
        }
        return stringBuilder;
    }

}