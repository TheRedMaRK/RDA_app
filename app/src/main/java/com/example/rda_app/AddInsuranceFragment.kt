package com.example.rda_app

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.rda_app.databinding.FragmentAddInsuranceBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddInsuranceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddInsuranceFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    // assign the _binding variable initially to null and
    // also when the view is destroyed again it has to be set to null
    private var _binding: FragmentAddInsuranceBinding? = null

    // with the backing property of the kotlin we extract
    // the non null value of the _binding
    private val binding get() = _binding!!

    //Firebase
    private lateinit var fStore: FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate the layout and bind to the _binding
        _binding = FragmentAddInsuranceBinding.inflate(inflater, container, false)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        //handle click, begin register
        binding.btnRegister.setOnClickListener {

            //validate data
            validateData()
        }

        return binding.root
    }

    private fun clearTextBoxes() {
        binding.txtName.text.clear()
        binding.txtHeadOffice.text.clear()
        binding.txtCEO.text.clear()
        binding.txtAddress.text.clear()
        binding.txtPhone.text.clear()
        binding.txtEmail.text.clear()
        binding.txtPassword.text.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun validateData() {

        email = binding.txtEmail.text.toString().trim()
        password = binding.txtPassword.text.toString().trim()

        //Validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //invalid email format
            binding.txtEmail.error = "Invalid email format"
        } else if (TextUtils.isEmpty(password)) {
            //Password is not entered
            binding.txtPassword.error = "Please enter password"
        } else if (password.length < 6) {
            //password is short
            binding.txtPassword.error = "Password must be at least more than 6 characters"
        } else {
            firebaseRegister()
        }
    }

    private fun firebaseRegister() {
        //create account
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //get current user
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                val userId = firebaseUser.uid

                val name = binding.txtName.text.toString()
                val headOffice = binding.txtHeadOffice.text.toString()
                val ceo = binding.txtCEO.text.toString()
                val address = binding.txtAddress.text.toString()
                val phone = binding.txtPhone.text.toString()
                val type = "insurance"

                fStore = FirebaseFirestore.getInstance()
                val register = AddInsurance(name, headOffice, ceo, address, phone, email, type)
                fStore.collection("users").document(userId).set(register)
                clearTextBoxes()

                Toast.makeText(context ,"Registered insurance staff with $email", Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener { e ->
                //Failed to register
                Toast.makeText(context ,"Registration failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddInsuranceFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddInsuranceFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}