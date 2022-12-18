package one.empty3.feature.app.maxSdk29.pro

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class EffectAdapter(val effects: List<Effect>, val itemClickListener: View.OnClickListener) :
    RecyclerView.Adapter<EffectAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem =
            LayoutInflater.from(parent.context).inflate(R.layout.item_effect, parent, false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val effect = effects[position]
    }

    override fun getItemCount(): Int {
        return effects.size
    }


}