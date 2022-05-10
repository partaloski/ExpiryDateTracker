
import android.content.Context

import android.widget.TextView

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup

import android.widget.ArrayAdapter
import com.example.expirydatetracker.R
import com.example.expirydatetracker.database.AppDatabase
import com.example.expirydatetracker.models.Manufacturer


class ManufListAdapter(context: Context, manufacturers: MutableList<Manufacturer>) :
    ArrayAdapter<Manufacturer>(context, R.layout.item_manufacturer, manufacturers) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView: View? = convertView
        val manufacturer = getItem(position)
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_manufacturer, null, true)
        }
        val name : TextView = convertView!!.findViewById(R.id.man_card_Name)
        val id : TextView = convertView!!.findViewById(R.id.tw_manuf_Id)
        name.text = manufacturer.name
        id.text = manufacturer.id.toString()
        return convertView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView: View? = convertView
        val manufacturer = getItem(position)
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_manufacturer, parent, false)
        }
        val name : TextView = convertView!!.findViewById(R.id.man_card_Name)
        val id : TextView = convertView!!.findViewById(R.id.tw_manuf_Id)
        name.text = manufacturer.name
        id.text = manufacturer.id.toString()
        return convertView
    }

    override fun getItem(i: Int): Manufacturer {
        return AppDatabase.manufacturers.data.get(i)
    }

    override fun getItemId(i: Int): Long {
        return 0
    }


}