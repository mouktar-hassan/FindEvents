package beans;



import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.findevents.MessageActivity;
import com.example.findevents.R;

import java.util.List;


public class CardsAdapterMessage extends RecyclerView.Adapter<ViewHolder>{

    //FIELDS TO STORE PASSED IN VALUES


    Context context;
    List<Message> list;
    public MessageActivity myaListActivity;


    public CardsAdapterMessage(Context context, List<Message> list)
    {
        //ASSIGN THEM
        this.context = context;
        this.list = list;
        this.myaListActivity = myaListActivity;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //INFLATE A VIEW FROM XML
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.model_message,null);

        //HOLDER
        ViewHolder holder=new ViewHolder(v);

        return holder;
    }




    //DATA IS BEING BOUND TO VIEWS
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final Message message=list.get(position);
        //message.getM_id().

        //BIND DATA
        holder.nameTxt.setText(""+message.getM_id());
        holder.posTxt.setText(message.getM_message());
        holder.img.setImageResource(R.drawable.event_icon);

        //WHEN ITEM IS CLICKED
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                //INTENT OBJ
                /*Intent i=new Intent(context, ShowEventDetailActivity.class);

                //ADD DATA TO OUR INTENT
                i.putExtra("pid",events.getId());
                i.putExtra("pcreator",events.getCreator());
                i.putExtra("ptitle",events.getTitle());
                i.putExtra("pdescription",events.getDescritption());
                i.putExtra("pcreated_at",events.getDate_creation());
                i.putExtra("pupdated_at",events.getDate_updated());
                i.putExtra("pdateEvent",events.getDate_event());
                i.putExtra("platitude",events.getLatitude());
                i.putExtra("plongitude",events.getLongitude());
                i.putExtra("plocation",events.getLocation());
                //i.putExtra("Image",images[position]);

                //START DETAIL ACTIVITY
                context.startActivity(i);*/

            }
        });

    }

    //TOTAL PLAYERS
    @Override
    public int getItemCount() {
        return list.size();
    }
}
