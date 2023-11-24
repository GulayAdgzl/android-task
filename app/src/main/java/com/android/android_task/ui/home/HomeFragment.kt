package com.android.android_task.ui.home


import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import java.util.concurrent.TimeUnit
import android.widget.SearchView
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.android.android_task.R
import com.android.android_task.adapter.CharacterAdapter
import com.android.android_task.data.model.CharacterModel
import com.android.android_task.databinding.FragmentHomeBinding
import com.android.android_task.ui.Qr.QrFragment
import com.android.android_task.worker.RefreshWorker
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val adapter by lazy { CharacterAdapter(requireContext()) }
    private val viewModel:HomeViewModel by viewModels()
    private val characterList= arrayListOf<CharacterModel>()
    val QR_REQUEST_CODE = 123



    // Aktivite sonucunu işlemek için kullanılan ResultLauncher
    private val qrResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val resultCode = result.resultCode
        val data = result.data
        handleActivityResult(resultCode, data)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val refreshDataWorkRequest = PeriodicWorkRequest.Builder(
            RefreshWorker::class.java,
            60,
            TimeUnit.MINUTES
        ).build()


        WorkManager
            .getInstance(requireContext())
            .enqueueUniquePeriodicWork(
                RefreshWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                refreshDataWorkRequest
            )

        binding.RecyclerView.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        binding.RecyclerView.adapter=adapter

        if (checkNetwork(requireContext())){
            getDataApi()
        }else getDataLocal()

        binding.swipeRefresh.setOnRefreshListener {
            if (checkNetwork(requireContext())){
                binding.swipeRefresh.isRefreshing = false
                getDataApi()
            }else getDataLocal()
            binding.swipeRefresh.isRefreshing = false


        }


        binding.SearchView.setOnQueryTextListener(object  : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
                filter(msg)
                return false
            }
        })
        binding.SearchView.setOnSearchClickListener {
            binding.textView.visibility = View.GONE
        }

        binding.SearchView.setOnCloseListener(object : SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                binding.textView.visibility = View.VISIBLE
                characterList.clear()
                if (checkNetwork(requireContext())){
                    getDataApi()
                }else getDataLocal()
                return false
            }
        })

        binding.qrButton.setOnClickListener {
            val intent = Intent(requireContext(), QrFragment::class.java)
            qrResultLauncher.launch(intent)
        }


    }

   // Eski onActivityResult metodu yerine kullanılacak olan yeni metot
    private fun handleActivityResult(resultCode: Int, data: Intent?) {
        if (resultCode == QR_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val resultString = data?.getStringExtra("result")
            filter(resultString)
        }
    }






    private fun getDataApi(){
        viewModel.characterLiveData.observe(viewLifecycleOwner){task->
            if(task != null){
                characterList.addAll(task.data ?: arrayListOf())
                adapter.differ.submitList(characterList)
            }
        }
        Toast.makeText(requireContext(),"data from network",Toast.LENGTH_LONG).show()
    }
    private fun getDataLocal(){
        adapter.differ.submitList(viewModel.getLocalTasks())

        Toast.makeText(requireContext(),"data from local",Toast.LENGTH_LONG).show()
    }

    private fun filter(text: String?){
        val filteredList: ArrayList<CharacterModel> = ArrayList()
        if (!text.isNullOrEmpty()) {
            for (item in characterList) {
                when {
                    item.task?.lowercase()?.contains(text.lowercase()) == true -> filteredList.add(
                        item
                    )
                    item.description?.lowercase()
                        ?.contains(text.lowercase()) == true -> filteredList.add(item)
                    item.colorCode?.lowercase()
                        ?.contains(text.lowercase()) == true -> filteredList.add(item)
                    item.title?.lowercase()?.contains(text.lowercase()) == true -> filteredList.add(
                        item
                    )
                    item.businessUnit?.lowercase()
                        ?.contains(text.lowercase()) == true -> filteredList.add(item)
                    item.businessUnitKey?.lowercase()
                        ?.contains(text.lowercase()) == true -> filteredList.add(item)
                    item.parentTaskId?.lowercase()
                        ?.contains(text.lowercase()) == true -> filteredList.add(item)
                    item.sort?.lowercase()?.contains(text.lowercase()) == true -> filteredList.add(
                        item
                    )
                    item.wageType?.lowercase()
                        ?.contains(text.lowercase()) == true -> filteredList.add(item)

                }
            }

            if (!filteredList.isEmpty()){
                adapter.differ.submitList(filteredList)
            }

        }
    }


    private fun checkNetwork(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                return true
            }
        }else{
            Toast.makeText(context,"Internet connection is not available.",Toast.LENGTH_LONG).show()
        }

        return false
    }





}