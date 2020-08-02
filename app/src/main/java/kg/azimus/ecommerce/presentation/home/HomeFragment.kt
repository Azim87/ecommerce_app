package kg.azimus.ecommerce.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kg.azimus.ecommerce.R
import kg.azimus.ecommerce.model.Products
import kg.azimus.ecommerce.presentation.home.adapter.ProductAdapter
import kg.azimus.ecommerce.util.toast
import kotlinx.android.synthetic.main.fragment_home.*

private const val TAG = "HomeFragment"

class HomeFragment : Fragment() {
    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var mAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View? = null
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home, container, false)
        }
        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecycler()
        readDataFromDataBase()
    }


    private fun initRecycler() {
        mAdapter = ProductAdapter()
        home_recycler.layoutManager = LinearLayoutManager(context)
        home_recycler.adapter = mAdapter
    }

    private fun readDataFromDataBase() {
        val productsList = ArrayList<Products>()
        mDatabaseReference = FirebaseDatabase.getInstance().reference.child("Products")
        mDatabaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children) {
                    val products = data.getValue(Products::class.java)
                    productsList.add(products!!)
                }
                mAdapter.setList(productsList)
            }

            override fun onCancelled(error: DatabaseError) {
                activity?.toast(context!!.applicationContext, "Error: ${error.message}")
            }
        })
    }
}