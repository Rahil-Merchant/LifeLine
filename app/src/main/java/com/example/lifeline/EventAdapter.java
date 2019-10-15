package com.example.lifeline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventAdapter extends FirestoreRecyclerAdapter<Event, EventAdapter.EventHolder> {

    private onItemClickListener listener;
    FirebaseFirestore firebaseFirestore;
    //firebaseFirestore=FirebaseFirestore.getInstance();
    private Context mContext;
    //Context context1;
    /*public Context getmContext() {
        return mContext;
    }*/

    /*public NoteAdapter(@NonNull Context context)
    {
        mContext=context;
    }*/

    // mContext=parent.getContext();

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public EventAdapter(@NonNull FirestoreRecyclerOptions<Event> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull EventHolder eventHolder, int i, @NonNull Event event) {
        eventHolder.textViewTitle.setText(event.getTitle());
        eventHolder.textViewDate.setText(event.getDate());

        //noteHolder.imageViewBanner.setImageURI(note.getImage());
        //mContext=NoteAdapter;
        //Context context = ;
        //Context context =getmContext();
        //mLayoutManager = new LinearLayoutManager(context.getApplicationContext());
        RequestOptions requestOptions=new RequestOptions();
        requestOptions.placeholder(R.mipmap.ic_launcher);

        //myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        //noteHolder.imageViewBanner.setImageBitmap(myBitmap);

        /*if (note.getImage() == ""){
            noteHolder.imageViewBanner.setImageResource(R.mipmap.ic_launcher);
        }*/
        Glide.with(eventHolder.imageViewBanner.getContext() /* context */)
                .load(event.getImage())
                .into(eventHolder.imageViewBanner);
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item,parent,false);
        return new EventHolder(v);
    }

    public void deleteitem(int position)
    {
        getSnapshots().getSnapshot(position).getReference().delete();

    }

    class EventHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewDate;
        ImageView imageViewBanner;

        public EventHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle=itemView.findViewById(R.id.text_view_title);
            textViewDate=itemView.findViewById(R.id.text_view_date);
            imageViewBanner=itemView.findViewById(R.id.image_view_banner);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION && listener!=null){
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
        }


    }

    public interface onItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.listener=listener;
    }
}
