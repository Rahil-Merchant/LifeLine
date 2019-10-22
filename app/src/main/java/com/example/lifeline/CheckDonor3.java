package com.example.lifeline;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CheckDonor3.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CheckDonor3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckDonor3 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private CheckBox checkBox4;
    private CheckBox checkBox5;
    private CheckBox checkBox6;
    private CheckBox checkBox7;
    private CheckBox checkBox8;
    private CheckBox checkBox9;
    private CheckBox checkBox10;
    private CheckBox checkBox11;
    private CheckBox checkBox12;
    private CheckBox checkBox13;
    private CheckBox checkBox14;
    private CheckBox checkBox15;
    private CheckBox checkBox16;
    private CheckBox checkBox17;
    private CheckBox checkBox18;
    private CheckBox checkBox19;
    private CheckBox checkBox20;
    private int count;
    private int countFinal;
    Button button;

    private OnFragmentInteractionListener mListener;

    public CheckDonor3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CheckDonor3.
     */
    // TODO: Rename and change types and number of parameters
    public static CheckDonor3 newInstance(String param1, String param2) {
        CheckDonor3 fragment = new CheckDonor3();
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
        //return inflater.inflate(R.layout.fragment_check_donor3, container, false);
        //View itemView =  inflater.inflate(R.layout.fragment_check_donor2 ,container,false);
        View itemView2 =  inflater.inflate(R.layout.fragment_check_donor3 ,container,false);
        checkBox1 = (CheckBox) itemView2.findViewById(R.id.checkbox11);
        checkBox2 = (CheckBox) itemView2.findViewById(R.id.checkbox12);
        checkBox3 = (CheckBox) itemView2.findViewById(R.id.checkbox13);
        checkBox4 = (CheckBox) itemView2.findViewById(R.id.checkbox14);
        checkBox5 = (CheckBox) itemView2.findViewById(R.id.checkbox15);
        checkBox6 = (CheckBox) itemView2.findViewById(R.id.checkbox16);
        checkBox7 = (CheckBox) itemView2.findViewById(R.id.checkbox17);
        checkBox8 = (CheckBox) itemView2.findViewById(R.id.checkbox18);
        checkBox9 = (CheckBox) itemView2.findViewById(R.id.checkbox20);
        checkBox10 = (CheckBox) itemView2.findViewById(R.id.checkbox21);
        checkBox11 = (CheckBox) itemView2.findViewById(R.id.checkbox22);
        checkBox12 = (CheckBox) itemView2.findViewById(R.id.checkbox23);
        //checkBox13 = (CheckBox) itemView2.findViewById(R.id.checkbox11);
        button=itemView2.findViewById(R.id.finish);






       /* final GlobalCount globalCount=(GlobalCount)getActivity().getApplicationContext();
        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(checkBox1.isChecked()){
                        count=globalCount.getCount()+1;
                        globalCount.setCount(count);
                    }

                    else{
                        count=globalCount.getCount()-1;
                        globalCount.setCount(count);
                    }
                Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });
        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(checkBox2.isChecked()){
                        count=globalCount.getCount()+1;
                        globalCount.setCount(count);
                    }

                    else{
                        count=globalCount.getCount()-1;
                        globalCount.setCount(count);
                    }
                Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });
        checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(checkBox3.isChecked()){
                        count=globalCount.getCount()+1;
                        globalCount.setCount(count);
                    }

                    else{
                        count=globalCount.getCount()-1;
                        globalCount.setCount(count);
                    }
                Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });
        checkBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox4.isChecked())
                    if(checkBox1.isChecked()){
                        count=globalCount.getCount()+1;
                        globalCount.setCount(count);
                    }

                    else{
                        count=globalCount.getCount()-1;
                        globalCount.setCount(count);
                    }
                Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });
        checkBox5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox5.isChecked())
                    if(checkBox1.isChecked()){
                        count=globalCount.getCount()+1;
                        globalCount.setCount(count);
                    }

                    else{
                        count=globalCount.getCount()-1;
                        globalCount.setCount(count);
                    }
                Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });
        checkBox6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(checkBox6.isChecked()){
                        count=globalCount.getCount()+1;
                        globalCount.setCount(count);
                    }

                    else{
                        count=globalCount.getCount()-1;
                        globalCount.setCount(count);
                    }
                Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });
        checkBox7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(checkBox7.isChecked()){
                        count=globalCount.getCount()+1;
                        globalCount.setCount(count);
                    }

                    else{
                        count=globalCount.getCount()-1;
                        globalCount.setCount(count);
                    }
                Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });
        checkBox8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox8.isChecked())
                    if(checkBox1.isChecked()){
                        count=globalCount.getCount()+1;
                        globalCount.setCount(count);
                    }

                    else{
                        count=globalCount.getCount()-1;
                        globalCount.setCount(count);
                    }
                Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });
        checkBox9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(checkBox9.isChecked()){
                        count=globalCount.getCount()+1;
                        globalCount.setCount(count);
                    }

                    else{
                        count=globalCount.getCount()-1;
                        globalCount.setCount(count);
                    }
                Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });
        checkBox10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(checkBox10.isChecked()){
                        count=globalCount.getCount()+1;
                        globalCount.setCount(count);
                    }

                    else{
                        count=globalCount.getCount()-1;
                        globalCount.setCount(count);
                    }
                Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });
        checkBox11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(checkBox11.isChecked()){
                        count=globalCount.getCount()+1;
                        globalCount.setCount(count);
                    }

                    else{
                        count=globalCount.getCount()-1;
                        globalCount.setCount(count);
                    }
                Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });
        checkBox12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(checkBox12.isChecked()){
                        count=globalCount.getCount()+1;
                        globalCount.setCount(count);
                    }

                    else{
                        count=globalCount.getCount()-1;
                        globalCount.setCount(count);
                    }
                Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countFinal=globalCount.getCount();
                if(countFinal>1)
                {
                    Toast.makeText(getContext(),"You are not elligible for donating Blood",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getActivity(),homeActivity.class));
                }
            }
        });*/



        return itemView2;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            //throw new RuntimeException(context.toString()
                   // + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
