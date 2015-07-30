package com.example.bass.instagramclient;

import android.content.Context;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by bass on 2015/7/29.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
    private static class ViewHolder {
        ImageView ivHead;
        TextView  tvUserName;
        TextView  tvTime;

        TextView  tvCaption;
        ImageView ivPhoto;


    }


    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1,objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo,parent,false);
            viewHolder.tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
            viewHolder.ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
            viewHolder.ivHead = (ImageView) convertView.findViewById(R.id.ivHead);
            viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //set head photo
        viewHolder.ivHead.setImageResource(0);
        Picasso.with(getContext()).load(photo.profilePicture).transform(new CircleTransform()).into(viewHolder.ivHead);
        //username
        viewHolder.tvUserName.setText(photo.username);

        //set time
        String strTime = DateUtils.getRelativeTimeSpanString(photo.createdTime * 1000, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString();
        viewHolder.tvTime.setText(strTime);

        //set caption

         //add comma to like count
        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);
        String likeWithComma = myFormat.format(photo.likesCount);

        //show likes
        String formattedText = "<b><font color=\"#125688\">" + likeWithComma + " likes</font></b><br>";
        //show caption
        formattedText += photo.caption + "<br>";
        //show total count of comments
        formattedText += "<font color=\"grey\">" + myFormat.format(photo.commentsCount) + " Comments</font><br>";

        //show  comments (name  text)
        for(int i=0;i<photo.comments.size();i++) {
            InstagramComment comment = (InstagramComment) photo.comments.get(i);
            formattedText += "<b><font color=\"#125688\">" + comment.commentUserName + "</font></b> " + comment.text + "<br>";
        }


        viewHolder.tvCaption.setText(Html.fromHtml(formattedText));

        //set photo
        viewHolder.ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.imageUrl)
                .fit().centerInside()
                .placeholder(R.drawable.loading)
                .error(R.drawable.fail).into(viewHolder.ivPhoto);
        return convertView;
    }
}
