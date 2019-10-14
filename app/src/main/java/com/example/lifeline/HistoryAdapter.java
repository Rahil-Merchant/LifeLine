package com.example.lifeline;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class HistoryAdapter extends FirestoreRecyclerAdapter<History, HistoryAdapter.HistoryHolder> {

    public HistoryAdapter(@NonNull FirestoreRecyclerOptions<History> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull HistoryAdapter.HistoryHolder historyHolder, int i, @NonNull History history) {
        final String repdoc = history.getRepdoc();
        historyHolder.doa_tv.setText(history.getDoa());
        historyHolder.timeSlot_tv.setText(history.getTimeSlot());
        if(!(repdoc.equals("no"))){
            historyHolder.download_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(repdoc));
                    view.getContext().startActivity(browserIntent);
                }
            });
        }
        else{
            historyHolder.download_btn.setVisibility(View.GONE);
        }

    }

    @NonNull
    @Override
    public HistoryAdapter.HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item,parent,false);
        return new HistoryAdapter.HistoryHolder(v);
    }

    class HistoryHolder extends RecyclerView.ViewHolder {

        TextView timeSlot_tv;
        TextView doa_tv;
        Button download_btn;

        public HistoryHolder(@NonNull View itemView) {
            super(itemView);

            timeSlot_tv = itemView.findViewById(R.id.history_timeSlot);
            doa_tv = itemView.findViewById(R.id.history_doa);
            download_btn=itemView.findViewById(R.id.history_download);

        }
    }
}
