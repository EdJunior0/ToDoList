package com.DataFlair.dataflairtodolist;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.DataFlair.dataflairtodolist.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

class SpeciesViewAdapter extends RecyclerView.Adapter<SpeciesViewAdapter.SpeciesViewHolder> {

    private Context context;
    private List<Task> taskResult = new ArrayList<>();
    private ClickListenerFeature listener;

    public SpeciesViewAdapter(Context context, List<Task> taskResult, ClickListenerFeature listener){
        this.context = context;
        this.taskResult = taskResult;
        this.listener = listener;
    }


    @NonNull
    @Override
    public SpeciesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.todo_task, parent, false);
        return new SpeciesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpeciesViewHolder holder, int position) {
        holder.taskText.setText(taskResult.get(position).task);
    }

    public int getItemCount() {
        if(taskResult == null){
            return 0;
        } else {
            return taskResult.size();
        }
    }

    public class SpeciesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView taskText;

        public SpeciesViewHolder(@NonNull View itemView) {
            super(itemView);

            taskText = itemView.findViewById((R.id.textList));
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }

    public interface ClickListenerFeature{
        void onClick(View v, int position);
    }

}
