package com.liuzhen.mylibrary.Utils.SearchView;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liuzhen.mylibrary.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Wxd on 2017-06-10.
 */
public class MySearchView extends LinearLayout {
    private Context context;
    private LinearLayout view1;
    private LinearLayout view2;
    private ImageView delete;
    private EditText editText;
    private ImageView search;
    private boolean showSearch = false;

    public MySearchView(Context context) {
        super(context);
        this.context = context;
        intView();
    }

    public MySearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        intView();
    }

    public MySearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        intView();
    }

    private void intView() {
        setLayoutDirection(LinearLayout.LAYOUT_DIRECTION_INHERIT);
        view1 = new LinearLayout(context);
        view1.setGravity(Gravity.CENTER);
        LinearLayout view01 = new LinearLayout(context);
        ImageView imageView = new ImageView(context);

        LayoutParams imageparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        imageparams.gravity = Gravity.CENTER_VERTICAL;

        try {
            Picasso.with(context).load(R.mipmap.search2).into(imageView);
        } catch (Exception E) {
        }
        TextView textView = new TextView(context);
        textView.setText(context.getResources().getString(R.string.content));

        textView.setTextSize(16);
        textView.setTextColor(getResources().getColor(R.color.hintColor));
        textView.setGravity(Gravity.CENTER);
        view01.addView(imageView, imageparams);

        LayoutParams textparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        textparams.gravity = Gravity.CENTER_VERTICAL;
        textparams.setMargins(10, 0, 0, 0);

        view01.addView(textView, textparams);
        view1.addView(view01, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));

        addView(view1, new LayoutParams(0, LayoutParams.MATCH_PARENT, 1));

        view1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                view1.setVisibility(GONE);
                view2.setVisibility(VISIBLE);
                editText.requestFocus();
                //打开软键盘
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        view2 = new LinearLayout(context);
        view2.setGravity(Gravity.CENTER_VERTICAL);
        //搜索按钮
        ImageView image2 = new ImageView(context);

        try {
            Picasso.with(context).load(R.mipmap.search2).into(image2);

        } catch (Exception E) {
        }
        LayoutParams image2params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        image2params.leftMargin = 30;
        image2params.gravity = Gravity.CENTER_VERTICAL;
        view2.addView(image2, image2params);


        //editview
        editText = new EditText(context);
        LayoutParams editparams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        editparams.gravity = Gravity.BOTTOM;
        editparams.setMargins(0, 0, 0, 0);
        editText.setPadding(20, 0, 0, 0);
        editText.setTextSize(14);
        editText.setHint(getResources().getString(R.string.content));
        editText.setTextColor(getResources().getColor(R.color.textColor));
        editText.setGravity(Gravity.CENTER_VERTICAL);
        editText.setHintTextColor(getResources().getColor(R.color.hintColor));
        editText.setBackground(null);
        editText.setLines(1);
        view2.addView(editText, editparams);


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText.getText().toString().equals("")) {
                    delete.setVisibility(GONE);
                    search.setVisibility(GONE);
                } else {
                    delete.setVisibility(VISIBLE);
                    if (showSearch)
                        search.setVisibility(VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (OnSearching != null) {
                    OnSearching.OnSearching(editText.getText().toString());
                }
            }
        });


        //删除按钮
        delete = new ImageView(context);
        delete.setVisibility(GONE);
        delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });

        try {
            Picasso.with(context).load(R.mipmap.delete).into(delete);
        } catch (Exception E) {
        }
        LayoutParams deleteparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        deleteparams.leftMargin = 20;
        deleteparams.rightMargin = 20;
        deleteparams.gravity = Gravity.CENTER_VERTICAL;
        view2.addView(delete, deleteparams);


        //搜索按钮
        search = new ImageView(context);
        search.setVisibility(GONE);
        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tosearch != null) {
                    tosearch.ToSearch(editText.getText().toString());
                    editText.setText("");
                }
            }
        });

        try {
            Picasso.with(context).load(R.mipmap.search_button).into(search);
        } catch (Exception E) {
        }
        LayoutParams searchparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        searchparams.leftMargin = 20;
        searchparams.rightMargin = 20;
        searchparams.gravity = Gravity.CENTER_VERTICAL;
        view2.addView(search, searchparams);
        view2.setVisibility(GONE);
        addView(view2, new LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
        setBackgroundResource(R.drawable.editshape);

    }

    public void getFocus() {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();

    }

    public void setContent(String content) {
        view1.setVisibility(GONE);
        view2.setVisibility(VISIBLE);
        editText.setText(content);
    }

    public void setSearchButton() {
        showSearch = true;

    }

    private ToSearch tosearch;


    public interface ToSearch {
        void ToSearch(String content);
    }

    public void ToSearch(ToSearch tosearch) {
        this.tosearch = tosearch;
    }

    private OnSearching OnSearching;

    public interface OnSearching {
        void OnSearching(String content);
    }

    public void OnSearching(OnSearching OnSearching) {
        this.OnSearching = OnSearching;
    }
}
