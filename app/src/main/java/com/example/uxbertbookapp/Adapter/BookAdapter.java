package com.example.uxbertbookapp.Adapter;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.uxbertbookapp.Activities.BookListActivity;
import com.example.uxbertbookapp.Activities.EditBookActivity;
import com.example.uxbertbookapp.DBManager.DBHandler;
import com.example.uxbertbookapp.Model.Book;
import com.example.uxbertbookapp.R;

import java.util.ArrayList;


public class BookAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Book> list;
    private DBHandler dbHandler;

    public BookAdapter(Context context, ArrayList<Book> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder = null;


        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.book_list_view, parent, false);

            holder = new ViewHolder();
            /*find layout components id*/
            holder.bookname = (TextView) row.findViewById(R.id.bookName);
            holder.bookaurthor=row.findViewById(R.id.bookAurthor);
            holder.bookPages=row.findViewById(R.id.bookpages);
            holder.cardView=row.findViewById(R.id.bookCard);
            holder.releasebtn=row.findViewById(R.id.releasebtn);

            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        final Book item = list.get(position);
        if (item.getStatus().equals("upcoming"))
        {
            holder.releasebtn.setVisibility(View.VISIBLE);
        }



        holder.bookname.setText(item.getName());
        holder.bookaurthor.setText(item.getAurthor());
        holder.bookPages.setText(String.valueOf(item.getPages()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent =new Intent(context, EditBookActivity.class);
              intent.putExtra("bookId",item.getId());
              context.startActivity(intent);
          }
        });

        holder.releasebtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                dbHandler=new DBHandler(context);
                item.setStatus("new");
                dbHandler.relaseBook(item);
                context.startActivity(new Intent(context,BookListActivity.class));
                if (item.getNotifiable() == 1) {
                    final String NOTIFICATION_CHANNEL_ID = "4565";
//Notification Channel
                    CharSequence channelName = "Uxbert";
                    int importance = NotificationManager.IMPORTANCE_LOW;
                    NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "any", importance);
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.RED);
                    notificationChannel.enableVibration(true);
                    notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

                    Intent intent = new Intent(context, BookListActivity.class);
                    PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    NotificationCompat.Builder b = new NotificationCompat.Builder(context);

                    b.setAutoCancel(true)
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setWhen(System.currentTimeMillis())
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setChannelId(NOTIFICATION_CHANNEL_ID)
                            .setContentTitle("Book Released")
                            .setContentText(item.getName() + " is released today!")
                            .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                            .setContentIntent(contentIntent)
                            .setContentInfo("Info");


                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.createNotificationChannel(notificationChannel);

                    notificationManager.notify(1, b.build());
                }
            }
        });



        return row;
    }
    class ViewHolder {
        TextView bookname;
        TextView bookaurthor;
        TextView bookPages;
        Button releasebtn;
        public CardView cardView;

    }
}