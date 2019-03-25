package beans;



import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.findevents.R;

public class ViewHolderMessage extends RecyclerView.ViewHolder implements View.OnClickListener {
    //OUR VIEWS

    TextView v_EventTitle;
    TextView v_userPseudo;
    TextView v_messageText;
    private ItemClickListener itemClickListener;

    //our contructor
    public ViewHolderMessage(View itemView) {
        super(itemView);

        v_EventTitle= (TextView) itemView.findViewById(R.id.EventTitle);
        v_userPseudo= (TextView) itemView.findViewById(R.id.userPseudo);
        v_messageText=(TextView) itemView.findViewById(R.id.messageText);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v,getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener ic)
    {
        this.itemClickListener=ic;

    }
}
