/**
 * Created by Andy Alonso
 * on 7/22/25
 */

package edu.alonso.banddatabase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

class ListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.fragment_list, container, false)
        val layout = rootView as LinearLayout

        // Create the buttons using the band names and ids from BandRepository
        val bandList = Repository.getInstance(requireContext()).bandList
        for (band in bandList) {
            val button = Button(context)
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(0, 0, 0, 10) // 10px bottom margin
            button.layoutParams = layoutParams

            // Display band's name on button
            button.text = band.name
            button.tag = band.id

            // Navigate to detail screen when clicked
            button.setOnClickListener { buttonView: View ->
                // Create fragment arguments containing the selected band ID
                val selectedBandId = buttonView.tag as Int
                val args = Bundle()
                args.putInt(ARG_BAND_ID, selectedBandId)

                // Send band ID to DetailFragment
                Navigation.findNavController(buttonView).navigate(R.id.show_item_detail, args)
            }

            // Add button to the LinearLayout
            layout.addView(button)
        }

        return rootView
    }
}