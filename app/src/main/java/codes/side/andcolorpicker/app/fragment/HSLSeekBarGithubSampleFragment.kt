package codes.side.andcolorpicker.app.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import codes.side.andcolorpicker.app.R
import codes.side.andcolorpicker.converter.setFromColorInt
import codes.side.andcolorpicker.group.PickerGroup
import codes.side.andcolorpicker.group.registerPickers
import codes.side.andcolorpicker.hsl.HSLColorPickerSeekBar.ColoringMode
import codes.side.andcolorpicker.hsl.HSLColorPickerSeekBar.Mode
import codes.side.andcolorpicker.model.IntegerHSLColor
import codes.side.andcolorpicker.view.picker.ColorSeekBar
import codes.side.andcolorpicker.view.picker.OnIntegerHSLColorPickListener
import kotlinx.android.synthetic.main.fragment_hsl_seek_bar_github_sample.*

class HSLSeekBarGithubSampleFragment : Fragment(R.layout.fragment_hsl_seek_bar_github_sample) {
  companion object {
    private const val TAG = "HSLSeekBarGithubSampleFragment"
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(
      view,
      savedInstanceState
    )

    // Configure picker color model programmatically
    hueColorPickerSeekBar.mode = Mode.MODE_HUE // Mode.MODE_SATURATION, Mode.MODE_LIGHTNESS

    // Configure coloring mode programmatically
    hueColorPickerSeekBar.coloringMode = ColoringMode.PURE_COLOR // ColoringMode.OUTPUT_COLOR

    // Group pickers with PickerGroup to automatically synchronize color across them
    val pickerGroup = PickerGroup<IntegerHSLColor>().also {
      it.registerPickers(
        hueColorPickerSeekBar,
        saturationColorPickerSeekBar,
        lightnessColorPickerSeekBar,
        alphaColorPickerSeekBar
      )
    }

    // Get current color immediately
    Log.d(
      TAG,
      "Current color is ${hueColorPickerSeekBar.pickedColor}"
    )

    // Listen individual pickers or groups for changes
    pickerGroup.addListener(
      object : OnIntegerHSLColorPickListener() {
        override fun onColorChanged(
          picker: ColorSeekBar<IntegerHSLColor>,
          color: IntegerHSLColor,
          value: Int
        ) {
          Log.d(
            TAG,
            "$color picked"
          )
          swatchView.setSwatchColor(
            color
          )
        }
      }
    )

    // Set desired color programmatically
    pickerGroup.setColor(
      IntegerHSLColor().also {
        it.setFromColorInt(
          Color.rgb(
            28,
            84,
            187
          )
        )
      }
    )

    // Set color components programmatically
    hueColorPickerSeekBar.progress = 50
  }
}
