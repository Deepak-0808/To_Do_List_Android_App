package com.example.mytask;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;


public class AFragment extends Fragment {


    public AFragment() {
        // Required empty public constructor
    }


    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    DatabaseReference reference;
    FirebaseAuth mAuth1;
    FirebaseUser mUser;
    String onlineUserID;
    ProgressDialog loader;
   public String key="";
    String task;
    String description;
    FirebaseDatabase firebase;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_a, container, false);

        recyclerView=view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        loader=new ProgressDialog(getActivity());

        mAuth1=FirebaseAuth.getInstance();


        firebase=FirebaseDatabase.getInstance();


        mUser= mAuth1.getCurrentUser();
        onlineUserID=mUser.getUid();
        reference= FirebaseDatabase.getInstance().getReference().child("task").child(onlineUserID);
        floatingActionButton=view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }

            private void addTask() {
                AlertDialog.Builder myDialog=new AlertDialog.Builder(getActivity());
                LayoutInflater inflater=LayoutInflater.from(getActivity());

                View myView=inflater.inflate(R.layout.input_file,null);
                myDialog.setView(myView);

                final AlertDialog dialog=myDialog.create();
                dialog.setCancelable(false);

                final EditText task=myView.findViewById(R.id.task);
                final EditText description=myView.findViewById(R.id.description);
                Button save = myView.findViewById(R.id.saveBtn);
                Button cancel = myView.findViewById(R.id.cancelBtn);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                    }
                });

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mTask = task.getText().toString().trim();
                        String mDescription = description.getText().toString().trim();
                        String id= reference.push().getKey();
                        String date= DateFormat.getDateInstance().format(new Date());


                        if (TextUtils.isEmpty(mTask))
                        {
                            task.setError("Task Required");
                            return;
                        }
                        if (TextUtils.isEmpty(mDescription))
                        {
                            description.setError("Description Required");
                            return;
                        }
                        else {
                            loader.setMessage("Adding your date");
                            loader.setCanceledOnTouchOutside(false);
                            loader.show();

                            Model model= new Model(mTask,mDescription,id,date);
                            reference.child(id).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(getActivity(), "Task has been Inserted successfully", Toast.LENGTH_SHORT).show();
                                        loader.dismiss();
                                    }
                                    else {
                                        String error= task.getException().toString();
                                        Toast.makeText(getActivity(), "Failed " + error, Toast.LENGTH_SHORT).show();
                                        loader.dismiss();
                                    }
                                }
                            });
                        }
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }

        });

        return view;

    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Model> options=new FirebaseRecyclerOptions.Builder<Model>()
                .setQuery(reference,Model.class)
                .build();

        FirebaseRecyclerAdapter<Model,MyViewHolder> adapter=new FirebaseRecyclerAdapter<Model, MyViewHolder>(options) {

                @Override
                public void onBindViewHolder(@NonNull MyViewHolder holder,int position, @NonNull Model model) {
                holder.setDate(model.getDate());
                holder.setTask(model.getTask());
                holder.setDesc(model.getDescription());

//                holder.mView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        key=getRef(position).getKey();
//                        task=model.getTask();
//                        description= model.getDescription();
//                        updateTask();
//
//                    }
//                });
            }








            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.retrieved_layout,parent,false);
                return new MyViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }
        public void setTask(String task){
            TextView taskTextView=mView.findViewById(R.id.taskTv);
            taskTextView.setText(task);
        }
        public void setDesc(String desc){
            TextView descTextView=mView.findViewById(R.id.descriptionTv);
            descTextView.setText(desc);
        }
        public void setDate(String date) {
            TextView dateTextView =mView.findViewById(R.id.dateTv);
            dateTextView.setText(date);
        }

    }

    void updateTask(){
         AlertDialog.Builder myDialog=new AlertDialog.Builder(getActivity());
         LayoutInflater inflater=LayoutInflater.from(getActivity());
         View view=inflater.inflate(R.layout.update_data,null);
         myDialog.setView(view);

         AlertDialog dialog= myDialog.create();

         EditText mTask=view.findViewById(R.id.mEditTextTask);
         EditText mDescription=view.findViewById(R.id.mEditTextDescription);
         mTask.setText(task);
         mTask.setSelection(task.length());
         mDescription.setText(description.length());
         mDescription.setSelection(description.length());

         Button delButton=view.findViewById(R.id.btnDelete);
         Button updateButton=view.findViewById(R.id.btnUpdate);

         updateButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 task=mTask.getText().toString().trim();
                 description=mDescription.toString().trim();

                 String date=DateFormat.getDateInstance().format(new Date());
                 Model model=new Model(task,description,key,date);
                 reference.child(key).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                         if (task.isSuccessful()){
                             Toast.makeText(getActivity(), "Data has been update successfully", Toast.LENGTH_SHORT).show();
                         }else {
                             String err=task.getException().toString();
                             Toast.makeText(getActivity(), "Update failed" +err, Toast.LENGTH_SHORT).show();
                         }

                     }
                 });
                 dialog.dismiss();
             }
         });

         delButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 reference.child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                         if (task.isSuccessful()){
                             Toast.makeText(getActivity(), "Task deleted successfully", Toast.LENGTH_SHORT).show();
                         }else {
                             String err=task.getException().toString();
                             Toast.makeText(getActivity(), "Failed to delete task" + err, Toast.LENGTH_SHORT).show();
                         }

                     }
                 });

                 dialog.dismiss();
             }
         });


         dialog.show();

    }

}