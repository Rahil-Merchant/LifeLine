package com.example.lifeline;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class LeaderboardAdapter extends FirestoreRecyclerAdapter<Leaderboard, LeaderboardAdapter.LeaderboardHolder> {

    public LeaderboardAdapter(@NonNull FirestoreRecyclerOptions<Leaderboard> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull LeaderboardAdapter.LeaderboardHolder leaderboardHolder, int i, @NonNull Leaderboard leaderboard) {
        String fname = leaderboard.getFname();
        String timesDonated = String.valueOf(leaderboard.getTimesDonated());

        leaderboardHolder.fname_tv.setText(fname);
        leaderboardHolder.timesDonated_tv.setText(timesDonated);
    }


    @NonNull
    @Override
    public LeaderboardAdapter.LeaderboardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_item,parent,false);
        return new LeaderboardAdapter.LeaderboardHolder(v);
    }

    class LeaderboardHolder extends RecyclerView.ViewHolder {

        TextView fname_tv;
        TextView timesDonated_tv;

        public LeaderboardHolder(@NonNull View itemView) {
            super(itemView);

            fname_tv = itemView.findViewById(R.id.leaderboard_name);
            timesDonated_tv = itemView.findViewById(R.id.leaderboard_timesDonated);

        }
    }
}
