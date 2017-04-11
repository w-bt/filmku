package id.widhianbramantya.android.filmku;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private Context context;
    private List<MyData> my_data;

    public CustomAdapter(Context context, List<MyData> data_list) {
        this.context = context;
        this.my_data = data_list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(my_data.get(position).getTitle());
        Glide.with(context).load(my_data.get(position).getPoster()).into(holder.imageView);
        holder.plot.setText(my_data.get(position).getPlot());
        holder.rating.setText(my_data.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        public TextView title, plot, rating;
        public ImageView imageView;

        public ViewHolder(View itemView){
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            plot = (TextView) itemView.findViewById(R.id.plot);
            rating = (TextView) itemView.findViewById(R.id.rating);
        }
    }
}
