package com.pixcat.warehouseproductscanner.ui.main.about;

import static java.util.stream.Collectors.toList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.pixcat.warehouseproductscanner.R;
import com.pixcat.warehouseproductscanner.data.auth.UserRoles;

import java.util.ArrayList;
import java.util.List;

public class AboutListAdapter extends BaseExpandableListAdapter {

    private final Activity parent;
    private final List<Group> groups;

    public AboutListAdapter(Activity parent) {
        this.parent = parent;

        groups = new ArrayList<>();
        groups.add(new Group(parent.getString(R.string.about_roles)));
        groups.add(new Group(parent.getString(R.string.about_warehouse)));
        groups.get(1).children.add(parent.getString(R.string.about_warehouse_content));
    }

    public void updateRolesList(List<String> roles) {
        groups.get(0).children.clear();
        groups.get(0).children.addAll(
                roles.stream()
                        .map(String::trim)
                        .map(role -> {
                            switch (role) {
                                case UserRoles.READ_BULK_PRODUCTS:
                                    return role + " - " + parent.getString(R.string.role_read_bulk_description);
                                case UserRoles.READ_PRODUCTS:
                                    return role + " - " + parent.getString(R.string.role_read_description);
                                case UserRoles.WRITE_PRODUCTS:
                                    return role + " - " + parent.getString(R.string.role_write_description);
                                default:
                                    return role + " - " + parent.getString(R.string.role_unknown_description);
                            }
                        })
                        .collect(toList())
        );

        parent.runOnUiThread(this::notifyDataSetChanged);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).children.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition).name;
    }

    @Override
    public Object getChild(int groupPosition, int expandedPosition) {
        return groups.get(groupPosition).children.get(expandedPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int expandedPosition) {
        return expandedPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) parent.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.about_list_expandable_group, viewGroup, false);
        }
        TextView listTitleTextView = convertView.findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);

        listTitleTextView.setText(getGroup(groupPosition).toString());
        return listTitleTextView;
    }

    @Override
    public View getChildView(
            int groupPosition,
            int expandedPosition,
            boolean isLastChild,
            View convertView,
            ViewGroup viewGroup) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) parent.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.about_list_expandable_item, viewGroup, false);
        }
        final String expandedListText = getChild(groupPosition, expandedPosition).toString();
        TextView expandedListTextView = convertView.findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int expandedPosition) {
        return false;
    }

    private static class Group {

        private final String name;
        private final List<String> children = new ArrayList<>();

        public Group(String name) {
            this.name = name;
        }
    }
}
