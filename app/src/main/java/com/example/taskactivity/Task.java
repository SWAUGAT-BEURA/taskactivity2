package com.example.taskactivity;

import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Task  implements Serializable {
    public int taskID;
    public String taskTitle;
    public String taskItemsStrings;
 //   public ArrayList<Items> taskItems;


    public static String convertArrayListToJSONArrayString(ArrayList<Items> tasks){
        String itemsArrayString ="";

        JSONArray itemsArray=new JSONArray();
        for (Items item:tasks){
            try {
                JSONObject itemobject=new JSONObject();
                itemobject.put("item_id",item.itemID);
                itemobject.put("item_name",item.itemName);
                itemobject.put("item_is_checked",item.isItemChecked);
                itemsArray.put(itemobject);

            }catch (JSONException e){
                e.printStackTrace();
            }

        }
        itemsArrayString =itemsArray.toString();

        return itemsArrayString;
    }

    public static ArrayList<Items>convertStringstoArrayList(String items){
        ArrayList<Items> taskitems=new ArrayList<>();
        try{
            JSONArray itemarray=new JSONArray(items);
            if (itemarray!=null && itemarray.length()>0){
                for (int i = 0; i<itemarray.length();i++){
                    JSONObject itemobject=itemarray.optJSONObject(i);
                    Items item=new Items();
                    item.itemID=itemobject.optInt("item_id");
                    item.itemName=itemobject.optString("item_name");
                    item.isItemChecked=itemobject.optBoolean("item_is_checked");
                    taskitems.add(item);
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return taskitems;
    }
}
