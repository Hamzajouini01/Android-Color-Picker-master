package codes.side.andcolorpicker.app.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import codes.side.andcolorpicker.app.R
import codes.side.andcolorpicker.converter.setFromColorInt
import codes.side.andcolorpicker.converter.toColorInt
import codes.side.andcolorpicker.model.IntegerHSLColor
import codes.side.andcolorpicker.view.picker.ColorSeekBar
import codes.side.andcolorpicker.view.picker.OnIntegerHSLColorPickListener
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import kotlinx.android.synthetic.main.fragment_hsl_seek_bar_recyclerview.*
import kotlinx.android.synthetic.main.layout_item_hsl_seek_bar.view.*

class HSLSeekBarRecyclerViewFragment : Fragment(R.layout.fragment_hsl_seek_bar_recyclerview) {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(
      view,
      savedInstanceState
    )

    recyclerView.layoutManager = LinearLayoutManager(
      requireContext()
    )

    val adapter = ItemAdapter.items<HSLItem>()
    val items = mutableListOf<HSLItem>()
    repeat(100) {
      items.add(
        HSLItem(
          IntegerHSLColor.createRandomColor(true).toColorInt()
        )
      )
    }
    adapter.add(items)

    recyclerView.adapter = FastAdapter.with(adapter)
  }

  class HSLItem(@ColorInt initialColor: Int) : AbstractItem<HSLItem.ViewHolder>() {
    private val hslColor = IntegerHSLColor().also {
      it.setFromColorInt(initialColor)
    }

    class ViewHolder(itemView: View) : FastAdapter.ViewHolder<HSLItem>(itemView) {
      private var lastBoundItem: HSLItem? = null

      init {
        itemView.hslSeekBar.addListener(
          object : OnIntegerHSLColorPickListener() {
            override fun onColorChanged(
              picker: ColorSeekBar<IntegerHSLColor>,
              color: IntegerHSLColor,
              value: Int
            ) {
              lastBoundItem?.hslColor?.setFrom(color)
              colorize()
            }
          }
        )
      }

      override fun bindView(item: HSLItem, payloads: List<Any>) {
        lastBoundItem = item
        itemView.hslSeekBar.pickedColor = item.hslColor
        colorize()
      }

      override fun unbindView(item: HSLItem) {
        lastBoundItem = null
      }

      private fun colorize() {
        val hslColor = requireNotNull(lastBoundItem).hslColor.clone().also {
          it.floatL += 0.45f
        }
        itemView.cardView.setCardBackgroundColor(
          hslColor.toColorInt()
        )
      }
    }

    override val layoutRes: Int
      get() = R.layout.layout_item_hsl_seek_bar
    override val type: Int
      get() = 0

    override fun getViewHolder(v: View): ViewHolder {
      return ViewHolder(v)
    }
  }
}
