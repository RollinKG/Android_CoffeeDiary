package com.teamds.coffeecounter.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.teamds.coffeecounter.R
import com.teamds.coffeecounter.databinding.FragmentReportCoffeeBinding
import com.teamds.coffeecounter.viewmodel.ReportPresenter


class ReportCaffeineFragment : Fragment(), ReportPresenter.View.Caffeine {

    lateinit var binding : FragmentReportCoffeeBinding
    private lateinit var presenter: ReportPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReportCoffeeBinding.inflate(layoutInflater)
        presenter = ReportPresenter(this)


        /*-----------Init View-------------*/
        binding.stat1Desc.text = "권장량 대비 평균"
        binding.stat1Desc.textSize = 17f
        binding.stat1Value.textSize=40f
        binding.stat1UnitDesc.text = "권장량 : 555mg"


        ///
        presenter.updateAvgText(this.requireContext(),"Caffeine")
        binding.textAvgUnit.text="mg"
        binding.textAvgUnitDesc.visibility=View.GONE







        //*------------------chart------------------*/
        val entries =  presenter.getChartEntry(this.requireContext(),"caffeine")
        val labels = presenter.getChartLabel(entries.size-1)

        drawChart(entries, labels)



        return binding.root
    }

    override fun updateAVG(avg: Float) {
        val text = avg.toInt()

        binding.textAvg.text = "$text"

    }

    override fun updateAvgRecommend(value: Float, desc: String, recommend: Int, indicator : Int) {
        val text = value.toInt()


        binding.stat1Value.text = "$text"+ "mg"
        binding.stat1Unit.text = desc
        binding.stat1UnitDesc.text = "권장량 : $recommend"+"mg"
        binding.cardStat1.background.setTint(ContextCompat.getColor(this.requireContext(),indicator))
    }

    private fun drawChart(entries: List<Entry>, labels : List<String>){
        val recommendEntry = mutableListOf<Entry>(Entry(0f,555f),Entry(6f,555f))

        val dataSet = LineDataSet(entries,"mg")
        dataSet.apply {
            color = ContextCompat.getColor(requireContext(),R.color.coffeeBrown)
            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            lineWidth=2f
        }

        val recommendDataSet = LineDataSet(recommendEntry,"일일 권장량")
        recommendDataSet.run {
            color = ContextCompat.getColor(requireContext(),R.color.blue)
            lineWidth=1.5f
            setDrawValues(false)
        }

        val lineData = LineData(dataSet, recommendDataSet)
        lineData.run {
            setValueTextSize(11.5f)
            setValueFormatter(object : ValueFormatter(){
                override fun getFormattedValue(value: Float): String {
                    return value.toInt().toString()
                }
            })
        }

        binding.linechart.run{
            data = lineData
            setScaleEnabled(false)
            setPinchZoom(false)
            description=null
            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP       //Legend는 차트 데이터의 범례를 의미합니다.
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT

            axisRight.setDrawLabels(false)
            xAxis.setDrawGridLines(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setLabelCount(entries.size, true)
            xAxis.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return labels[value.toInt()]
                }
            }

            invalidate()
        }
    }
}