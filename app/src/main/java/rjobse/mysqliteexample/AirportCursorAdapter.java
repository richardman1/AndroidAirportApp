package rjobse.mysqliteexample;

import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.CursorAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by devc0 on 9/13/2016.
 */
public class AirportCursorAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<Airport>> listDataChild;

    public AirportCursorAdapter(Context context, List<String> listDataHeader, HashMap<String, List<Airport>> listChildData){
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String groupTitle = (String) getGroup(groupPosition);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_activity_main, null);
        }

        TextView headerTitle = (TextView) convertView.findViewById(R.id.header_name);
        headerTitle.setTypeface(null, Typeface.BOLD);
        headerTitle.setText(groupTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Airport childAirport = (Airport) getChild(groupPosition, childPosition);

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.row_activity_main, null);
        }
            TextView childIcao = (TextView) convertView.findViewById(R.id.icao);

            TextView childName = (TextView) convertView.findViewById(R.id.name);

            childIcao.setText(childAirport.getIcao());

            childName.setText(childAirport.getName());
            return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



//    LayoutInflater mInflater;
//    Context mContext;
//    ArrayList mArrayList;
//
//    public AirportCursorAdapter(Context context, LayoutInflater layoutInflater, ArrayList<Airport> arrayList){
//        mContext = context;
//        mInflater = layoutInflater;
//        mArrayList = arrayList;
//    }
//
//    @Override
//    public int getCount() {
//        return mArrayList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return mArrayList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        ViewHolder viewHolder;
//
//        // Create new of gebruik een al bestaande (recycled by Android)
//        if(convertView == null) {
//
//            //
//            convertView = mInflater.inflate(R.layout.row_activity_main, null);
//
//            //
//            viewHolder = new ViewHolder();
//            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
//            viewHolder.icao = (TextView) convertView.findViewById(R.id.icao);
//
//            //
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//
//        // En nu de viewHolder invullen met de juiste persons
//        Airport airport = (Airport) mArrayList.get(position);
//
//        viewHolder.name.setText(airport.getName());
//        viewHolder.icao.setText(airport.getIcao());
//
//        return convertView;
//    }
//
//    private class ViewHolder {
//        public TextView name;
//        public TextView icao;
//    }
}
