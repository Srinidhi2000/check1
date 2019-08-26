package com.example.android.status;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class eventAdapter extends RecyclerView.Adapter<eventAdapter.eventViewHolder> {
List<obj> eventList;
Context context;
private OnItemClickListener listener;
    @NonNull
    @Override
    public eventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview,viewGroup,false);
        eventViewHolder eventView=new eventViewHolder(v,listener);
        return eventView;
    }
public eventAdapter(Context context,List<obj> eventList)
{
    this.eventList=eventList;
    this.context=context;
}
    @Override
    public void onBindViewHolder(@NonNull eventViewHolder eventViewHolder, int i) {
obj current=eventList.get(i);
eventViewHolder.id.setText(current.getId());
eventViewHolder.name.setText(current.getName());
eventViewHolder.dept_layout.setVisibility(View.VISIBLE);
eventViewHolder.part_layout.setVisibility(View.VISIBLE);
String elim_type=current.getType();
elim_type=elim_type.substring(0,1).toUpperCase()+elim_type.substring(1).toLowerCase();
eventViewHolder.type.setText(elim_type);
        eventViewHolder.venue.setText(current.getVenue());
        Double d= Double.parseDouble(current.getStart_time());
        Date date=new Date((long) (d*1000L));
        SimpleDateFormat dformat=new SimpleDateFormat("hh:mm aaa");
String time=dformat.format(date);
        eventViewHolder.time.setText(time);
if(current.getParticipating_teams()[0].equals("N/A")) {
    eventViewHolder.part_layout.setVisibility(View.GONE);
    eventViewHolder.dept1.setText(current.getDept1());
    eventViewHolder.dept2.setText(current.getDept2());
    eventViewHolder.dept1_score.setText(current.getDept1_score());
    eventViewHolder.dept2_score.setText(current.getDept2_score());
}
else
{eventViewHolder.dept_layout.setVisibility(View.GONE);
String content="";
for(int j=0;j<current.getParticipating_teams().length;j++)
{content+=current.getParticipating_teams()[j]+"  ";}
eventViewHolder.par_teams.setText(content);}
eventViewHolder.getView().setAnimation(AnimationUtils.loadAnimation(context,R.anim.zoom_in));
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
public interface OnItemClickListener{
        void onItemClick(int position);
}
public void setOnItemClickListener(OnItemClickListener listener)
{
    this.listener=listener;
}
    public static class eventViewHolder extends RecyclerView.ViewHolder{
        TextView id;
        TextView name,dept1,dept2,type,time,venue,dept1_score,dept2_score,par_teams;
        ImageView dept1_icon,dept2_icon;
        LinearLayout dept_layout;
        HorizontalScrollView part_layout;
        View view;
        public eventViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            view=itemView;
            dept_layout=itemView.findViewById(R.id.dept_layout);
id=itemView.findViewById(R.id.ID);
name=itemView.findViewById(R.id.name);
type=itemView.findViewById(R.id.type);
part_layout=itemView.findViewById(R.id.part_layout);
            dept1=itemView.findViewById(R.id.dept1);
            dept2=itemView.findViewById(R.id.dept2);
            dept1_score=itemView.findViewById(R.id.dept1_score);
            dept2_score=itemView.findViewById(R.id.dept2_score);
            time=itemView.findViewById(R.id.time);
            venue=itemView.findViewById(R.id.venue);
            par_teams=itemView.findViewById(R.id.part_teams);
            itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
if(listener!=null)
{
    int position=getAdapterPosition();
    if(position!=RecyclerView.NO_POSITION)
    {listener.onItemClick(position);}
}
}
        });
        }
    public View getView()
    {
        return view;
    }
    }
}
