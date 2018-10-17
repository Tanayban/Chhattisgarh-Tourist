package com.example.user.chhatisgarhtourist

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class Home:Fragment() {

    lateinit var mrecyclerview:RecyclerView
    lateinit var ref:DatabaseReference


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val placeview= inflater.inflate(R.layout.activity_home,container,false);



        ref = FirebaseDatabase.getInstance().getReference().child("Place")
        mrecyclerview = placeview.findViewById<RecyclerView>(R.id.recycler_view)
        mrecyclerview.layoutManager = LinearLayoutManager(context)

        return placeview
    }

    override fun onStart() {
        super.onStart()

        val option =  FirebaseRecyclerOptions.Builder<Model>()
                .setQuery(ref,Model::class.java)
                .build()


        val FirebaseRecyclerAdapter = object:FirebaseRecyclerAdapter<Model,MyViewHolder>(option) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                val itemView = LayoutInflater.from(context).inflate(R.layout.cardview,parent,false)
                return MyViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: Model) {
                val placeid = getRef(position).key.toString()

                ref.child(placeid).addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {

                            holder.txt_name.setText(model.Name)
                            Picasso.get().load(model.Image).into(holder.img_vet)

                    }

                })
            }
        }

        mrecyclerview.adapter = FirebaseRecyclerAdapter
        FirebaseRecyclerAdapter.startListening()
    }


    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        internal var txt_name: TextView
        internal var img_vet: ImageView

        init{
            txt_name = itemView!!.findViewById<TextView>(R.id.Display_title)
            img_vet = itemView.findViewById<ImageView>(R.id.Display_img)
        }

    }
}








