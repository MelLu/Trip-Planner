package viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eslamjava.tripplanner.R;

/**
 * Created by eslam java on 2/5/2018.
 */

public class ListViewHolder {

    private View convert;
    private TextView tripName;
    private TextView from;
    private TextView to;
    int tripId;

    public ListViewHolder(View view) {
        this.convert = view;
    }

    public TextView getTripName() {
        if (tripName == null) {
            tripName = (TextView) convert.findViewById(R.id.tripNametxtv);
        }
        return tripName;
    }

    public TextView getFrom() {

        if (from==null){
            from=(TextView) convert.findViewById(R.id.fromTxtV);
        }
        return from;
    }

    public TextView getTo() {

        if (to==null){
            to=(TextView) convert.findViewById(R.id.toTxtV);
        }
        return to;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }
}
