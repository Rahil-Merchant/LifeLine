package com.example.lifeline;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.chip.ChipGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CheckDonor2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CheckDonor2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckDonor2 extends Fragment {
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
    RadioButton radioButton1;
    RadioButton radioButton2;
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
    public int count=0;
    private int countr=0;
    private Button button;


    private OnFragmentInteractionListener mListener;

    public CheckDonor2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CheckDonor2.
     */
    // TODO: Rename and change types and number of parameters
    public static CheckDonor2 newInstance(String param1, String param2) {
        CheckDonor2 fragment = new CheckDonor2();
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

   /* public void onCheckboxClicked(View view) {

// now check that Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        try {

            if ((view == checkBox1) || (view == checkBox2) || (view == checkBox3)) {

                // You can perform a common action for all check box clicks here
                count++;
                Toast.makeText(getContext(),+count,Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("onClick", e + "");
        }

    }*/



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_check_donor2, container, false);
        View itemView =  inflater.inflate(R.layout.fragment_check_donor2 ,container,false);

        checkBox1 = itemView.findViewById(R.id.checkbox1);
        checkBox2 = itemView.findViewById(R.id.checkbox2);
        checkBox3 = itemView.findViewById(R.id.checkbox3);
        checkBox4 = itemView.findViewById(R.id.checkbox4);
        checkBox5 = itemView.findViewById(R.id.checkbox8);
        checkBox6 = itemView.findViewById(R.id.checkbox9);
        checkBox7 = itemView.findViewById(R.id.checkbox10);
        checkBox8=itemView.findViewById(R.id.checkbox11);
        checkBox9=itemView.findViewById(R.id.checkbox12);
        checkBox10=itemView.findViewById(R.id.checkbox13);
        checkBox11=itemView.findViewById(R.id.checkbox14);
        checkBox12=itemView.findViewById(R.id.checkbox15);
        checkBox13=itemView.findViewById(R.id.checkbox16);
        checkBox14=itemView.findViewById(R.id.checkbox17);
        checkBox15=itemView.findViewById(R.id.checkbox18);
        checkBox16=itemView.findViewById(R.id.checkbox20);
        checkBox17=itemView.findViewById(R.id.checkbox21);
        checkBox18=itemView.findViewById(R.id.checkbox22);
        checkBox19=itemView.findViewById(R.id.checkbox23);
        button=itemView.findViewById(R.id.finish);









        //final GlobalCount globalCount=(GlobalCount) getApplicationContext();



        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox1.isChecked()){
                    count++;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });


        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox2.isChecked()) {
                    count++;
                }
                else{
                   count--;
                }
                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });


        checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox3.isChecked()){
                    count++;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });

        checkBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox4.isChecked()){
                   count--;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });

        checkBox5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox5.isChecked()){
                    count++;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });


        checkBox6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox6.isChecked()){
                    count++;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });

        checkBox7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox7.isChecked()){
                    count++;
                }

                else{
                   count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });

        checkBox8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox8.isChecked()){
                    count++;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });

        checkBox9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox9.isChecked()){
                    count++;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });

        checkBox10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox10.isChecked()){
                    count++;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });

        checkBox11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox11.isChecked()){
                    count++;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });

        checkBox12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox12.isChecked()){
                    count++;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });

        checkBox13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox13.isChecked()){
                    count++;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });

        checkBox14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox14.isChecked()){
                    count++;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });

        checkBox15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox15.isChecked()){
                    count++;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });

        checkBox16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox16.isChecked()){
                    count++;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });

        checkBox17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox17.isChecked()){
                    count++;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });

        checkBox18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox7.isChecked()){
                    count++;
                }

                else{
                    count--;
                }

                //Toast.makeText(getContext()," "+count,Toast.LENGTH_LONG).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count==0)
                {
                    Toast.makeText(getContext(),"You are Eligible for Donating Blood",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getActivity(),homeActivity.class));
                }
                else {
                    Toast.makeText(getContext(),"You are Not Eligible for Donating Blood",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getActivity(),homeActivity.class));
                }
            }
        });


        return itemView;

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
                    //+ " must implement OnFragmentInteractionListener");
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
