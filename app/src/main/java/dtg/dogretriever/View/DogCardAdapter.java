package dtg.dogretriever.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import dtg.dogretriever.Model.Dog;
import dtg.dogretriever.R;

public class DogCardAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Dog> dogArrayList;
    private LayoutInflater inflater;
    private ViewHolder viewHolder;

    public DogCardAdapter(Context context, ArrayList<Dog> dogArrayList) {
        this.context = context;
        this.dogArrayList = dogArrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
       return dogArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return dogArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.scanner_dog_layout,
                    parent, false);
            viewHolder = new ViewHolder();
            viewHolder.nameTextView = convertView.findViewById(R.id.scanner_dog_name);
            viewHolder.sizeTextView =  convertView.findViewById(R.id.scanner_dog_size);
            viewHolder.colorTextView =  convertView.findViewById(R.id.scanner_dog_color);
            viewHolder.breedTextView =  convertView.findViewById(R.id.scanner_dog_breed);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.nameTextView.setText(dogArrayList.get(position).getName());
        viewHolder.sizeTextView.setText(dogArrayList.get(position).getSize());
        viewHolder.colorTextView.setText(dogArrayList.get(position).getColor());
        viewHolder.breedTextView.setText(dogArrayList.get(position).getBreed());
        return convertView;
        }

    static class ViewHolder {
        private TextView nameTextView;
        private TextView sizeTextView;
        private TextView colorTextView;
        private TextView breedTextView;
    }
}
