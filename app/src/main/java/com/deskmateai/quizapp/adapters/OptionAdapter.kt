package com.deskmateai.quizapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.deskmateai.quizapp.R
import com.deskmateai.quizapp.models.Questions

class OptionAdapter(val context: Context, val questions: Questions) :
    RecyclerView.Adapter<OptionAdapter.OptionViewHolder>() {

    private var options: List<String> = listOf(questions.option1, questions.option2, questions.option3, questions.option4)

    inner class OptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var optionView = itemView.findViewById<TextView>(R.id.quiz_option)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.option_list, parent, false)
        return  OptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        holder.optionView.text = options[position]

        holder.itemView.setOnClickListener {
            //storing user ans in a property userAns made by us in data class
            questions.userAnswer = options[position]
            notifyDataSetChanged()
        }

        //checking if userAns is equal to our option selected then put a red border outside it
        if (questions.userAnswer == options[position]){
            holder.optionView.setBackgroundResource(R.drawable.option_selected_bg)
        }else{
            holder.optionView.setBackgroundResource(R.drawable.option_bg)
        }


    }

    override fun getItemCount(): Int {
        return options.size
    }
}