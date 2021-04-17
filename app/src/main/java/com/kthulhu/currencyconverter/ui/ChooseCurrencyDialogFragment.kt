package com.kthulhu.currencyconverter.ui

import android.content.DialogInterface
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import com.kthulhu.currencyconverter.R
import kotlinx.android.synthetic.main.dialog_fragment_choose_currency.*
import android.widget.LinearLayout.LayoutParams

class ChooseCurrencyDialogFragment : DialogFragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_fragment_choose_currency, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = (activity as MainActivity).viewModel
        addRadioButtons()

        radio_group.setOnCheckedChangeListener { _, checkedId ->
            val text = view.findViewById<RadioButton>(checkedId).text.toString()
            viewModel.changeName(text)
        }

        btn_cancel.setOnClickListener { dialog?.cancel() }
        btn_ok.setOnClickListener { dialog?.dismiss() }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        viewModel.changeName()
    }

    private fun addRadioButtons(){
        for (currency in viewModel.currencyNames){
            val button = RadioButton(context).apply {
                text = currency
                textSize = 16F
                typeface = ResourcesCompat.getFont(requireContext(), R.font.roboto_medium)
                layoutParams = applyBottomMargin(20F)
                setPadding(20.toPx(), 0, 20.toPx(), 0)//Right padding is needed to support right-to-left locales
            }
            radio_group.addView(button)
        }
    }

    private fun applyBottomMargin(margin: Float): LayoutParams{
        val layoutParams = LayoutParams(LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT))
        layoutParams.bottomMargin = margin.toPx()
        return layoutParams
    }

    private fun Float.toPx(): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, resources.displayMetrics).toInt()
    }

    private fun Int.toPx() = this.toFloat().toPx()
    companion object {
        const val TAG = "ChooseCurrencyDialogFragment"
    }
}