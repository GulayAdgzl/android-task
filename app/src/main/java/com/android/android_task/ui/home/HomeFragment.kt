package com.android.android_task.ui.home

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.android_task.adapter.CharacterAdapter
import com.android.android_task.data.model.CharacterModel
import com.android.android_task.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter:CharacterAdapter
    private val viewModel:HomeViewModel by viewModels()
    private val characterList= arrayListOf<CharacterModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.RecyclerView.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        binding.RecyclerView.adapter=adapter
    }

    private fun getDataApi() {
        viewModel.characterLiveData.observe(viewLifecycleOwner) { task ->
            if (task != null) {
                val newList = task.data ?: emptyList()
                val diffResult = DiffUtil.calculateDiff(
                    CharacterAdapter.DiffCallBack.calculateDiffCallback(characterList, newList)
                )

                characterList.clear()
                characterList.addAll(newList)
                diffResult.dispatchUpdatesTo(adapter)
            }
        }
        Toast.makeText(requireContext(), "Ağdan alınan veri", Toast.LENGTH_LONG).show()
    }

    private fun checkNetwork(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected) {
            when (networkInfo.type) {
                ConnectivityManager.TYPE_WIFI,
                ConnectivityManager.TYPE_MOBILE,
                ConnectivityManager.TYPE_ETHERNET -> return true
                else -> {
                    // Bilinmeyen bir bağlantı türü durumunda
                    Toast.makeText(context, "Unsupported or unknown network type.", Toast.LENGTH_LONG).show()
                }
            }
        } else {
            // Aktif ağ bağlantısı yoksa
            Toast.makeText(context, "Internet connection is not available.", Toast.LENGTH_LONG).show()
        }

        return false
    }





}