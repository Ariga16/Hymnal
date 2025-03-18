package com.example.sdahymnal

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dacasa.nyimbozakristo.Adapter.NyimboAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NyimboFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NyimboFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var mParam1: String? = null
    private var mParam2: String? = null

    private lateinit var titles: Array<String>
    private lateinit var contents: Array<String>
    private lateinit var postListView: RecyclerView
    private lateinit var nyimboAdapter: NyimboAdapter
    private lateinit var searchInput: EditText
    private lateinit var rootLayout: RelativeLayout
    private lateinit var layoutManager: LinearLayoutManager
    private var scrollPosition: Int = 0




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // Required to enable menu in fragments
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_nyimbo, container, false)

        titles = requireActivity().resources.getStringArray(R.array.songs_title)
        contents = requireActivity().resources.getStringArray(R.array.songs_content)

        postListView = fragmentView.findViewById(R.id.songListView)
        rootLayout = fragmentView.findViewById(R.id.root_layout)
        searchInput = fragmentView.findViewById(R.id.search_input)
        layoutManager = LinearLayoutManager(activity)

        postListView.layoutManager = layoutManager
        postListView.setHasFixedSize(true)

        val toolbar: Toolbar = fragmentView.findViewById(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true) // Required to enable menu in fragments

        return fragmentView

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NyimboFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NyimboFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onPause() {
        super.onPause()
        scrollPosition = layoutManager.findFirstVisibleItemPosition()
    }

    override fun onResume() {
        super.onResume()
        layoutManager.scrollToPosition(scrollPosition)
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.about_us) {
            val intent = Intent(activity, AboutUsActivity::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }



    override fun onStart() {
        super.onStart()

        nyimboAdapter = NyimboAdapter(requireActivity(), titles, contents)
        postListView.adapter = nyimboAdapter

        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                nyimboAdapter.filter.filter(s)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

}