package com.educ.ahmed.miwork;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * WordAdapter extends the ArrayAdapter Class that make us capable of
 * making our own custom adaptor
 */

class WordAdapter extends ArrayAdapter<Word> {

    private int colorResourceID;
    public WordAdapter(@NonNull Context context, @NonNull ArrayList<Word> objects,int colorResourceID) {
        super(context, 0, objects);
        this.colorResourceID=colorResourceID;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Word currentWord=getItem(position);

        View listItemView=convertView;
        if(listItemView==null)
        {
            listItemView= LayoutInflater.from(getContext())
                    .inflate(R.layout.word_template,parent,false);
        }
        TextView miWork=listItemView.findViewById(R.id.miwork_text_view);

        miWork.setText(currentWord.getMiworkWord());

        TextView defualt=listItemView.findViewById(R.id.default_text_view);

        defualt.setText(currentWord.getDefaultWord());

        ImageView imageView=listItemView.findViewById(R.id.test_img);

        if (currentWord.hasImage()) {
            imageView.setImageResource(currentWord.getImageResourceID());
            imageView.setVisibility(View.VISIBLE);
        }
        else {
            imageView.setVisibility(View.GONE);
        }

        //Reference to the layout container(Linear Layout) that contain the text or what ever
        View viewContainer=listItemView.findViewById(R.id.text_container);

        //Find the Color that the colorResourceID refers  to
        int color= ContextCompat.getColor(getContext(),colorResourceID);

        //set the Background Color to the wanted color
        viewContainer.setBackgroundColor(color);

        return listItemView ;
    }


}




