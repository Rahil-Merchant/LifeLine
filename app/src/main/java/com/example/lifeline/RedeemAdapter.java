package com.example.lifeline;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class RedeemAdapter extends FirestoreRecyclerAdapter<Redeem, RedeemAdapter.RedeemHolder> {

    private OnItemClickListener listener;
    public RedeemAdapter(@NonNull FirestoreRecyclerOptions<Redeem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RedeemAdapter.RedeemHolder redeemHolder, int i, @NonNull Redeem redeem) {
        String name = redeem.getFname() + ' ' + redeem.getMname() + ' ' + redeem.getLname();
        redeemHolder.name_tv.setText(name);
        redeemHolder.email_tv.setText(redeem.getEmail());
        redeemHolder.mobile_tv.setText(String.valueOf(redeem.getMobNo()));
        redeemHolder.timesDonatedTv.setText(String.valueOf(redeem.getTimesDonated()));
        redeemHolder.rewards_countTv.setText(String.valueOf(redeem.getRewards_count()));
    }

    @NonNull
    @Override
    public RedeemAdapter.RedeemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.redeem_item,parent,false);
        return new RedeemAdapter.RedeemHolder(v);
    }

    class RedeemHolder extends RecyclerView.ViewHolder {

        TextView name_tv;
        TextView mobile_tv;
        TextView email_tv;
        TextView timesDonatedTv;
        TextView rewards_countTv;


        public RedeemHolder(@NonNull View itemView) {
            super(itemView);

            name_tv=itemView.findViewById(R.id.redeem_name);
            mobile_tv=itemView.findViewById(R.id.redeem_mobile);
            timesDonatedTv=itemView.findViewById(R.id.redeem_rewards_timesDonated);
            rewards_countTv=itemView.findViewById(R.id.redeem_rewards_count);
            email_tv=itemView.findViewById(R.id.redeem_email);

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

    public void setOnClickListener(RedeemAdapter.OnItemClickListener listener){
        this.listener = listener;
    }
}
