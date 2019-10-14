package com.example.lifeline;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class PendingApplicationsAdapter extends FirestoreRecyclerAdapter<adminReport, PendingApplicationsAdapter.PendingApplicationsHolder> {

    private OnItemClickListener listener;
    public PendingApplicationsAdapter(@NonNull FirestoreRecyclerOptions<adminReport> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PendingApplicationsAdapter.PendingApplicationsHolder pendingApplicationsHolder, int i, @NonNull adminReport adminReport) {
        String name = adminReport.getFname() + ' ' + adminReport.getMname() + ' ' + adminReport.getLname();
        pendingApplicationsHolder.name_tv.setText(name);
        pendingApplicationsHolder.email_tv.setText(adminReport.getEmail());
        pendingApplicationsHolder.mobile_tv.setText(String.valueOf(adminReport.getMobNo()));
        pendingApplicationsHolder.timeSlot_tv.setText(adminReport.getTimeSlot());
        pendingApplicationsHolder.organization_tv.setText(adminReport.getOrganization());
        pendingApplicationsHolder.occupation_tv.setText(adminReport.getOccupation());
    }

    @NonNull
    @Override
    public PendingApplicationsAdapter.PendingApplicationsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_applications_item,parent,false);
        return new PendingApplicationsAdapter.PendingApplicationsHolder(v);
    }

    class PendingApplicationsHolder extends RecyclerView.ViewHolder {

        TextView name_tv;
        TextView mobile_tv;
        TextView email_tv;
        TextView timeSlot_tv;
        TextView organization_tv;
        TextView occupation_tv;
        Button donated_btn;


        public PendingApplicationsHolder(@NonNull View itemView) {
            super(itemView);

            name_tv=itemView.findViewById(R.id.pending_name);
            mobile_tv=itemView.findViewById(R.id.pending_mobile);
            email_tv=itemView.findViewById(R.id.pending_email);
            timeSlot_tv=itemView.findViewById(R.id.pending_timeSlot);
            organization_tv = itemView.findViewById(R.id.pending_organization);
            occupation_tv = itemView.findViewById(R.id.pending_occupation);
            donated_btn = itemView.findViewById(R.id.pending_donated);

            donated_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null){
                        listener.onDonatedBtnClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });

        }
    }

    public interface OnItemClickListener{
        void onDonatedBtnClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnClickListener(PendingApplicationsAdapter.OnItemClickListener listener){
        this.listener = listener;
    }
}
