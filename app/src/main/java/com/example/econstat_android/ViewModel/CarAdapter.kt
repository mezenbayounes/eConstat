package ViewModel

import java.io.File
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.econstat_android.Model.Car
import com.example.econstat_android.R
import com.example.econstat_android.ViewModel.CarDetailsFragment
import com.example.econstat_android.databinding.CarItemBinding
import com.example.econstat_android.utils.Constant
import com.google.android.material.textfield.TextInputEditText
import de.hdodenhof.circleimageview.CircleImageView

class CarAdapter(var cars: List<Car>,private val fragmentManager: FragmentManager) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {

        val mainView = CarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarViewHolder(mainView)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = cars[position]

        Glide.with(holder.itemView.context)
            .load(Constant.image_URL + car.carImage)
            .into(holder.itemView.findViewById<CircleImageView>(R.id.ivCar))

        val tvMatricule = holder.itemView.findViewById<TextView>(R.id.tvMatricule)
        tvMatricule.text = car.numImmatriculation

        /*val tvItemMatricule = holder.itemView.findViewById<TextView>(R.id.tvItemMatricule)
        val tvItemType = holder.itemView.findViewById<TextView>(R.id.tvItemType)
        val tvItemBrand = holder.itemView.findViewById<TextView>(R.id.tvItemBrand)

        val tvItemFiscalPower = holder.itemView.findViewById<TextView>(R.id.tvItemFiscalPower)
        val tvItemNumSerie = holder.itemView.findViewById<TextView>(R.id.tvItemNumSerie)



        tvItemMatricule.text = car.numImmatriculation
        tvItemType.text = car.type
        tvItemBrand.text = car.brand
        tvItemFiscalPower.text= car.fiscalPower.toString()
        tvItemNumSerie.text = car.numSerie
*/




        ///
        holder.itemView.setOnClickListener()
        {
            navigateToCarDetails(car, fragmentManager)
            Toast.makeText(holder.itemView.context, "clicked", Toast.LENGTH_SHORT).show()


        }


    }

    override fun getItemCount(): Int {
        return cars.size
    }


    inner class CarViewHolder(mainView: CarItemBinding) : RecyclerView.ViewHolder(mainView.root) {


    }

    private fun navigateToCarDetails(car: Car, fragmentManager: FragmentManager) {
        val bundle = Bundle().apply {
            putParcelable("car", car)
        }
        val carDetailsFragment = CarDetailsFragment()
        carDetailsFragment.arguments = bundle
        fragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, carDetailsFragment)
            addToBackStack(null)
            commit()
        }
    }
}






