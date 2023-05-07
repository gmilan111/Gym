package com.example.gym;

import android.annotation.SuppressLint;
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

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder>
{
    Proba proba;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<model> datalist;


    public myadapter(Proba proba, ArrayList<model> datalist) {
        this.proba = proba;
        this.datalist = datalist;
    }

    public void updateData(int position){
        model item = datalist.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("id" , item.getId());
        bundle.putString("gyakorlatNev" , item.getGyakorlatNev());
        bundle.putString("ismetlesSzam" , item.getIsmetlesSzam());
        bundle.putString("maxSuly" , item.getMaxSuly());
        bundle.putString("sorozatSzam" , item.getSorozatSzam());
        Intent intent = new Intent(proba , Add.class);
        intent.putExtras(bundle);
        proba.startActivity(intent);
    }

    public void deleteData(int position){
        model item = datalist.get(position);
        db.collection("edzes").document(item.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            notifyRemoved(position);
                            Toast.makeText(proba, "Adat törölve!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(proba, "Hiba" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void notifyRemoved(int position){
        datalist.remove(position);
        notifyItemRemoved(position);
        proba.showData();
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, @SuppressLint("RecyclerView") int position){
      holder.t1.setText("Gyakorlat neve: "+datalist.get(position).getGyakorlatNev());
      holder.t2.setText("Ismétlésszám: "+datalist.get(position).getIsmetlesSzam());
      holder.t3.setText("Max súly: "+datalist.get(position).getMaxSuly());
      holder.t4.setText("Sorozatszám: "+datalist.get(position).getSorozatSzam());

      holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim_one));

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public static class myviewholder extends RecyclerView.ViewHolder
    {
       TextView t1,t2,t3,t4;
       CardView cardView;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.t1);
            t2=itemView.findViewById(R.id.t2);
            t3=itemView.findViewById(R.id.t3);
            t4=itemView.findViewById(R.id.t4);
            cardView=itemView.findViewById(R.id.main_card);
        }
    }

}
