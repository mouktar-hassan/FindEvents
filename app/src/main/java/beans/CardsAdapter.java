package beans;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.findevents.R;

import java.util.List;


public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> {

    private Context context;
    private List<Events> list;

    public CardsAdapter(Context context, List<Events> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Events events=list.get(position);


        holder.textTitle.setText(events.getTitle());
        holder.textDecription.setText(events.getDescritption());
        holder.textDateEvent.setText(events.getDate_event());




        //holder.textRating.setText(String.valueOf(movie.getRating()));
        //holder.textYear.setText(String.valueOf(movie.getYear()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textTitle, textDecription, textDateEvent;

        public ViewHolder(View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.main_title);
            textDecription = itemView.findViewById(R.id.main_rating);
            textDateEvent = itemView.findViewById(R.id.main_year);
        }
    }

}