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
import android.widget.Toast
import kotlinx.android.synthetic.main.dialog_fragment_choose_currency.scrollView
import kotlinx.android.synthetic.main.dialog_fragment_choose_currency.view.*

class ChooseCurrencyDialogFragment : DialogFragment() {

    private lateinit var viewModel: MainViewModel

    private var checkedButtonText: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_fragment_choose_currency, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = (activity as MainActivity).viewModel

        checkedButtonText = savedInstanceState?.getString(RADIO_BUTTON_TEXT_KEY, "")?: ""

        checkIfDataNotLoaded()

        viewModel.areRatesLoaded().observe(viewLifecycleOwner, {
            if(it){
                progressBar.visibility = View.GONE
                addRadioButtons()
            } else {
                progressBar.visibility = View.VISIBLE
            }
        })

        radio_group.setOnCheckedChangeListener { _, checkedId ->
            val button = view.findViewById<RadioButton>(checkedId)
            val text = button.text.toString()
            checkedButtonText = text
            viewModel.changeName(text)
        }

        btn_cancel.setOnClickListener { dialog?.cancel() }
        btn_ok.setOnClickListener { dialog?.dismiss() }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        viewModel.changeName()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(RADIO_BUTTON_TEXT_KEY, checkedButtonText)
    }

    private fun checkIfDataNotLoaded(){
        if(viewModel.getCurrencyNames().isNotEmpty()) return
        Toast.makeText(requireContext(), resources.getString(R.string.error_no_rates_loaded), Toast.LENGTH_LONG).show()
        viewModel.forceLoadRates()
    }

    private fun addRadioButtons(){
        var checkedButton: RadioButton? = null
        for (currency in viewModel.getCurrencyNames()){
            val button = RadioButton(context).apply {
                text = currency
                textSize = 16F
                typeface = ResourcesCompat.getFont(requireContext(), R.font.roboto_medium)
                layoutParams = applyBottomMargin(20F)
                setPadding(20.toPx(), 0, 20.toPx(), 0)//Right padding is needed to support right-to-left locales

                if(currency == checkedButtonText){
                    checkedButton = this
                }
            }
            radio_group.addView(button)
            checkedButton?.let {
                radio_group.check(it.id)
                scrollView.smoothScrollTo(0, it.bottom)
            }
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
        const val RADIO_BUTTON_TEXT_KEY="KSD#fDF#FDSTFG12SDF"
    }
}