package com.liuzhen.mylibrary.Utils.DialogList;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.liuzhen.mylibrary.R;

import java.util.List;

/**
 * Created by hjm on 2016/5/19.
 */
public class Dialoglist extends PopupWindow {
    private PopupWindow popupWindow;

    private ListView listView;
    private ListAdapter adapter;
    public final int SUCCESS = 1;
    public static final int DATA = 5;
    private View parent;
    private int width;
    private List<String> date;
    private Context context;
    private int Height = 0;


    public Dialoglist(List<String> date, Context context, View paparent) {
        this.date = date;
        this.context = context;
        this.parent = paparent;
        this.width = parent.getWidth();
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void showWindow(final Handler handler) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.poplist, null);
        listView = (ListView) view.findViewById(R.id.list);
        adapter = new ListAdapter(context, parent.getWidth(), date);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message msg = handler.obtainMessage();
                msg.what = SUCCESS;
                msg.arg1 = DATA;
                msg.obj = position;
                handler.sendMessage(msg);
                popupWindow.dismiss();

            }
        });

        if (popupWindow == null) {
//            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//            popupWindow.showAsDropDown(parent, xPos, 4);
            if (Height > 0) {
                popupWindow = new PopupWindow(view, width, Height);
            } else {
                popupWindow = new PopupWindow(view, width, LinearLayout.LayoutParams.WRAP_CONTENT);

            }
        }


        // 使其聚集
        popupWindow.setFocusable(true);
//        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
//        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        popupWindow.showAsDropDown(parent, Gravity.CENTER_HORIZONTAL, 0, 0);

//
//        // 点击其他地方消失
//        view.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (popupWindow != null && popupWindow.isShowing()) {
//                    popupWindow.dismiss();
//                    popupWindow = null;
//                }
//                return true;
//            }
//        });
//
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
                if ((arg1 == KeyEvent.KEYCODE_BACK)
                        && (popupWindow != null && popupWindow.isShowing())) {
                    popupWindow.dismiss();// 点击返回键的popWin退出就行
                    return true;
                }
                return false;
            }
        });


    }

    public void setdismiss() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    public void setHeight(int height) {
        this.Height = height;
    }


    static class ListAdapter extends BaseAdapter {
        private final int widt;
        private Context context;
        private List<String> list;


        public ListAdapter(Context context, int widt, List<String> list) {
            this.context = context;
            this.widt = widt;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            Viewholder viewholder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.poplistitem, null);
                convertView.setLayoutParams(new GridView.LayoutParams(widt, ViewGroup.LayoutParams.WRAP_CONTENT));
                viewholder = new Viewholder();
                viewholder.content = (TextView) convertView.findViewById(R.id.content);
                viewholder.line = (TextView) convertView.findViewById(R.id.line);


                if (position == list.size() - 1) {
                    viewholder.line.setVisibility(View.GONE);
                }
                viewholder.content.setWidth(widt);
                convertView.setTag(viewholder);
            } else {
                viewholder = (Viewholder) convertView.getTag();
            }
            viewholder.content.setText(list.get(position));
            return convertView;
        }

        class Viewholder {
            TextView content;
            TextView line;
        }


    }
}