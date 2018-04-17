package listview_adapters;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.eslamjava.tripplanner.R;

import java.util.List;

import dto.TripData;
import viewholders.ListViewHolder;

/**
 * Created by eslam java on 2/5/2018.
 */

public class UpcomingListViewAdapter extends ArrayAdapter<TripData> {

    Context x;
    List<TripData> tripData;


    public UpcomingListViewAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List<TripData> objects) {
        super(context, resource, textViewResourceId, objects);

        x = context;
        tripData = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rowView = convertView;
        ListViewHolder holder;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) x.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.custom_item_list_view, parent, false);
            holder = new ListViewHolder(rowView);
            holder.setTripId(tripData.get(position).getId());
            rowView.setTag(holder);
        } else {
            holder = (ListViewHolder) rowView.getTag();
        }


        holder.getTripName().setText(tripData.get(position).getTripName().toString());
        holder.getFrom().setText(tripData.get(position).getStartPointAddress().toString());
        holder.getTo().setText(tripData.get(position).getDestinationAddress().toString());


        return rowView;
    }
}
