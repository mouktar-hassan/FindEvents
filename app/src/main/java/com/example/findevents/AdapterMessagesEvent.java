package com.example.findevents;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterMessagesEvent extends RecyclerView.Adapter<AdapterMessagesEvent.ViewHolder> {

    private ArrayList<ModelMessagesEvent> listMessagesEvents = new ArrayList<>();
    private Context context ;

    public AdapterMessagesEvent(ArrayList<ModelMessagesEvent> listMessagesEvents, Context context) {
        this.listMessagesEvents = listMessagesEvents;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_messages_event, parent, false);
        return new ViewHolder (v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ModelMessagesEvent listItem = listMessagesEvents.get(position);

        //viewHolder.textViewNomEvent.setText("Evénement : "+listItem.getTitreEvent());
        viewHolder.textViewMessage.setText(listItem.getMessageEvent());
        viewHolder.textViewDetails.setText("Écrit par : " +listItem.getPseudoEcrivain()+ " le : " +listItem.getDate());

    }

    @Override
    public int getItemCount() {
        return listMessagesEvents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //public TextView textViewNomEvent;
        public TextView textViewMessage;
        public TextView textViewDetails;

        public ViewHolder(View itemView) {
            super(itemView);

            //textViewNomEvent = (TextView) itemView.findViewById(R.id.textViewNomEvent);
            textViewMessage = (TextView) itemView.findViewById(R.id.textViewMessage);
            textViewDetails = itemView.findViewById(R.id.textViewDetails);
        }
    }
}
