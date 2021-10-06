package com.deskmateai.quizapp.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.deskmateai.quizapp.R
import com.deskmateai.quizapp.activities.QuestionActivity
import com.deskmateai.quizapp.models.Quizzes
import com.deskmateai.quizapp.utils.ColorPicker
import com.deskmateai.quizapp.utils.IconPicker

class QuizAdapter(val context:Context,val quizzes:List<Quizzes>):
    RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {

    inner class QuizViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var textviewTitle:TextView = itemView.findViewById(R.id.quiz_title)
        var iconView:ImageView = itemView.findViewById(R.id.quiz_icon)
        var cardview:CardView = itemView.findViewById(R.id.quiz_cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.quiz_item,parent,false)
        return QuizViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.textviewTitle.text = quizzes[position].title
        holder.cardview.setBackgroundColor(Color.parseColor(ColorPicker.getColor()))
        holder.iconView.setImageResource(IconPicker.getIcon())
        holder.itemView.setOnClickListener {
            val intent = Intent(context,QuestionActivity::class.java)
            intent.putExtra("DATE",quizzes[position].title)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return quizzes.size
    }
}