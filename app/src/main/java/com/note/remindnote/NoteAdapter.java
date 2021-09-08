package com.note.remindnote;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends BaseAdapter implements Filterable {
    private Context mContext;

    private List<Note> backList;//用来备份原始数据
    private List<Note> noteList;//这个数据是会改变的，所以要有个变量来备份一下原始数据
    private MyFilter mFilter;

    public NoteAdapter(Context mContext, List<Note> noteList) {
        this.mContext = mContext;
        this.noteList = noteList;
        backList = noteList;
    }

    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Object getItem(int position) {
        return noteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        mContext.setTheme(R.style.DayTheme);
        View v = View.inflate(mContext, R.layout.note_layout, null);
        TextView tv_content = v.findViewById(R.id.tv_content);
        TextView tv_time = v.findViewById(R.id.tv_time);

        //Set text for TextView
        String allText = noteList.get(position).getContent();
        /*if(sharedPreferences.getBoolean("noteTitle", true))
            tv_content.setText(allText.split("\n")[0]);*/
        tv_content.setText(allText);
        tv_time.setText(noteList.get(position).getTime());

        //Save note id to long
        v.setTag(noteList.get(position).getId());

        return v;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new MyFilter();
        }
        return mFilter;
    }

    class MyFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults result = new FilterResults();
            List<Note> list;
            if (TextUtils.isEmpty(constraint)) { //will show all the notes when searching keyword are empty
                list = backList;
            } else {
                list = new ArrayList<>();
                for (Note note : backList) {
                    if (note.getContent().contains(constraint)) {
                        list.add(note);
                    }
                }
            }
            result.values = list; //the result will save into values of FilterResults
            result.count = list.size(); // result size will save into count of FilterResults

            return result;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            noteList = (List<Note>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();//success
            } else {
                notifyDataSetInvalidated();//failure
            }
        }
    }
}


