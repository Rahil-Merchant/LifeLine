package com.example.lifeline;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class adminReportAdapter extends FirestoreRecyclerAdapter<adminReport, adminReportAdapter.adminReportHolder> {

    private OnItemClickListener listener;
    public adminReportAdapter(@NonNull FirestoreRecyclerOptions<adminReport> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull adminReportAdapter.adminReportHolder adminReportHolder, int i, @NonNull adminReport adminReport) {
        String name = adminReport.getFname() + ' ' + adminReport.getMname() + ' ' + adminReport.getLname();
        adminReportHolder.name_tv.setText(name);
        adminReportHolder.email_tv.setText(adminReport.getEmail());
        adminReportHolder.mobile_tv.setText(String.valueOf(adminReport.getMobNo()));
        adminReportHolder.doa_tv.setText(adminReport.getDoa());
        adminReportHolder.timeSlot_tv.setText(adminReport.getTimeSlot());
    }

    @NonNull
    @Override
    public adminReportAdapter.adminReportHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_report_item,parent,false);
        return new adminReportAdapter.adminReportHolder(v);
    }

    class adminReportHolder extends RecyclerView.ViewHolder {

        TextView name_tv;
        TextView mobile_tv;
        TextView email_tv;
        TextView doa_tv;
        TextView timeSlot_tv;


        public adminReportHolder(@NonNull View itemView) {
            super(itemView);

            name_tv=itemView.findViewById(R.id.adminReport_name);
            mobile_tv=itemView.findViewById(R.id.adminReport_mobile);
            email_tv=itemView.findViewById(R.id.adminReport_email);
            doa_tv=itemView.findViewById(R.id.adminReport_rewards_doa);
            timeSlot_tv=itemView.findViewById(R.id.adminReport_timeSlot);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });

        }
    }

    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnClickListener(adminReportAdapter.OnItemClickListener listener){
        this.listener = listener;
    }
}
