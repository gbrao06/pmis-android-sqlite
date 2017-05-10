package com.pmis.pmislite.tasks;


import java.util.List;
import java.util.Map;

import com.pmis.*;
import com.pmis.pmislite.R;
import com.pmis.pmislite.data.PmisUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
	 
	public class ExpandableTaskListAdapter extends BaseExpandableListAdapter {
	 
	    private Activity context;

	    private String projectName;
	    private Map<String, List<String>> childCollections;
	    private List<String> items;
	 
	    public ExpandableTaskListAdapter(Activity context, String projectName,List<String> laptops,
	            Map<String, List<String>> laptopCollections) {
	        
	    	this.context = context;
	        this.projectName=projectName;
	    	this.childCollections = laptopCollections;
	        this.items = laptops;
	    }
	 
	    public Object getChild(int groupPosition, int childPosition) {
	        return childCollections.get(items.get(groupPosition)).get(childPosition);
	    }
	 
	    public long getChildId(int groupPosition, int childPosition) {
	        return childPosition;
	    }
	 
	     
	    public View getChildView(final int groupPosition, final int childPosition,
	            boolean isLastChild, View convertView, ViewGroup parent) {
	        final String user = (String) getChild(groupPosition, childPosition);
	        LayoutInflater inflater = context.getLayoutInflater();
	 
	        if (convertView == null) {
	            convertView = inflater.inflate(R.layout.project_image_item_view, null);
	        }
	 
	        TextView item = (TextView) convertView.findViewById(R.id.child_item);
	 
	        ImageView delete = (ImageView) convertView.findViewById(R.id.imageDeleteButton);
	        delete.setOnClickListener(new OnClickListener() {
	 
	            public void onClick(View v) {
	                AlertDialog.Builder builder = new AlertDialog.Builder(context);
	                final String parentItem=items.get(groupPosition);
                	
	                builder.setMessage("Do you want to remove?:laptop:"+user+":ParentItem::"+parentItem);
	                builder.setCancelable(false);
	                builder.setPositiveButton("Yes",
	                        new DialogInterface.OnClickListener() {
	                            public void onClick(DialogInterface dialog, int id) {
	                            	
	                                List<String> child =childCollections.get(items.get(groupPosition));
	                                boolean ret=PmisUtils.deleteWbsTask(context,projectName,parentItem,user);
	                                if(ret)
	                                	child.remove(childPosition);
	                                notifyDataSetChanged();
	                            }
	                        });
	                builder.setNegativeButton("No",
	                        new DialogInterface.OnClickListener() {
	                            public void onClick(DialogInterface dialog, int id) {
	                                dialog.cancel();
	                            }
	                        });
	                AlertDialog alertDialog = builder.create();
	                alertDialog.show();
	            }
	        });
	        
	        item.setText(user);
	        return convertView;
	    }
	 
	    public int getChildrenCount(int groupPosition) {
	        return childCollections.get(items.get(groupPosition)).size();
	    }
	 
	    public Object getGroup(int groupPosition) {
	        return items.get(groupPosition);
	    }
	 
	    public int getGroupCount() {
	        return items.size();
	    }
	 
	    public long getGroupId(int groupPosition) {
	        return groupPosition;
	    }
	 
	    public View getGroupView(int groupPosition, boolean isExpanded,
	            View convertView, ViewGroup parent) {
	        String laptopName = (String) getGroup(groupPosition);
	        if (convertView == null) {
	            LayoutInflater infalInflater = (LayoutInflater) context
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.project_item_view,
	                    null);
	        }
	        TextView item = (TextView) convertView.findViewById(R.id.project_name);
	        item.setTypeface(null, Typeface.BOLD);
	        item.setText(laptopName);
	        return convertView;
	    }
	 
	    public boolean hasStableIds() {
	        return true;
	    }
	 
	    
	    public boolean isChildSelectable(int groupPosition, int childPosition) {
	        return true;
	    }
	}