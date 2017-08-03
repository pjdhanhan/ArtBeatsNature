package com.liuzhen.mylibrary.Base;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象适配器（免去adapter中有共性的代码
 * 
 * @author pang
 * @param <T>
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {
  private static String TAG="MyBaseAdapter<T>";
	protected LayoutInflater mInflater;
	protected Context mContext;
	public List<T> myList;

	public MyBaseAdapter(Context context, List<T> list) {
		this.mInflater = LayoutInflater.from(context);
		mContext = context;
		if (list == null) {
			myList = new ArrayList<T>();
		}
		myList = list;
	}

	public void addList(List<T> list) {
		myList.addAll(list);
	}

	public void setList(List<T> list) {
		myList = list;
	}
	
	public void addItem(T t)
	{
		myList.add(t);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return myList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return myList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	@Override
     public View getView(int position, View convertView, ViewGroup parent) {
         ViewHolder holder;
         if(null == convertView){
             holder = new ViewHolder();
			 try {
				 convertView = LayoutInflater.from(mContext).inflate(itemLayoutRes(), null);
				 convertView.setTag(holder);
			 } catch (Exception e) {
				 e.printStackTrace();
			 }
            convertView.setTag(holder);
         }else{
             holder = (ViewHolder) convertView.getTag();
         }
         return getView(position, convertView, parent, holder);
     }



	public String DateDuplicateRemoval(String s) {
		String result="";
		String[] sArray= s.split(",");
		for (int i=0;i<sArray.length;i++){
			if (!result.contains(sArray[i])){
				result+=sArray[i]+",";
			}
		}
		if (result.substring(result.length()-1).equals(",")){
			Log.d(TAG, "DateDuplicateRemoval: "+result);
			result=result.substring(0,result.length()-1);
		}
		return result.replace(",", "\n");
	}
	 /**
      * 该方法需要子类实现，需要返回item布局的resource id
      * @return
      */
 	public abstract int itemLayoutRes();
 	/**
      * 使用该getView方法替换原来的getView方法，需要子类实现
      * @param position
      * @param convertView
      * @param parent
      * @param holder
      * @return
      */
 	public abstract View getView(int position, View convertView, ViewGroup parent, ViewHolder holder);
	
	/**
      * 各个控件的缓存
      */
     public class ViewHolder{
         public SparseArray<View> views = new SparseArray<View>();
 
         /**
          * 指定resId和类型即可获取到相应的view
          * @param convertView
          * @param resId
          * @param <T>
          * @return
          */
         public <T extends View> T obtainView(View convertView, int resId){
             View v = views.get(resId);
             if(null == v){
                 v = convertView.findViewById(resId);
                 views.put(resId, v);
             }
             return (T)v;
         }
 
     }

}
