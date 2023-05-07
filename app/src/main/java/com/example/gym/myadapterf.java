package com.example.gym;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class myadapterf extends RecyclerView.Adapter<myadapterf.myViewHolder> {
    Food food;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<model_food> datalist;

    public myadapterf(Food food, ArrayList<model_food> datalist) {
        this.food = food;
        this.datalist = datalist;
    }

    public void updateData(int position){
        model_food item = datalist.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("id" , item.getId());
        bundle.putString("etelNev" , item.getEtelNev());
        bundle.putString("mennyiseg" , item.getMennyiseg());
        bundle.putString("kaloria" , item.getKaloria());
        Intent intent = new Intent(food , Add_food.class);
        intent.putExtras(bundle);
        food.startActivity(intent);
    }

    public void deleteData(int position){
        model_food item = datalist.get(position);
        db.collection("etel").document(item.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            notifyRemoved(position);
                            Toast.makeText(food, "Adat törölve!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(food, "Hiba" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void notifyRemoved(int position){
        datalist.remove(position);
        notifyItemRemoved(position);
        food.showData();
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_food,parent,false);
        return new myadapterf.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.t1.setText("Étel: " + datalist.get(position).getEtelNev());
        holder.t2.setText("Mennyiség (g): " + datalist.get(position).getMennyiseg());
        holder.t3.setText("Kalória: " + datalist.get(position).getKaloria());
        holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.anim_one));
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{

        TextView t1,t2,t3;
        CardView cardView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.t1);
            t2=itemView.findViewById(R.id.t2);
            t3=itemView.findViewById(R.id.t3);
            cardView=itemView.findViewById(R.id.main_card);
        }
    }

}
