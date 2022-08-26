package com.example.newbagshop.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.newbagshop.Logging
import com.example.newbagshop.R
import com.example.newbagshop.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_user_fragment.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [user_fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class user_fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var firebaseAuth: FirebaseAuth? = null
    private var users: FirebaseUser? = null
    private val reference:DatabaseReference? = null
    private val userID: String? = null


    lateinit var profileName: TextView
    lateinit var profileEmail: TextView
    lateinit var profileNumber: TextView
    //private val CurrentObj: user_fragment = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firebaseAuth = FirebaseAuth.getInstance()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        logoutbtn.setOnClickListener {
            firebaseAuth?.signOut()
            //startActivity(Intent(this.context,Logging::class.java))
            val intent:Intent = Intent(requireContext(),Logging::class.java)
            requireActivity().startActivity(intent)
            requireActivity().finish()
        }

        profileName = view.findViewById(R.id.uName)
        profileEmail = view.findViewById(R.id.Uemail)
        profileNumber = view.findViewById(R.id.uNumber)

        // textView= view?.findViewById(R.id.uName)
        // textView=view?.findViewById(R.id.uNumber)
        // textView=view?.findViewById(R.id.Uemail)

        firebaseAuth  = FirebaseAuth.getInstance()
        val databaseReference = FirebaseDatabase.getInstance().getReference(firebaseAuth!!.uid!!)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val user: FirebaseUser? = firebaseAuth?.currentUser
                val userProfile = p0.getValue(UserProfile::class.java)
                profileName.text = "Name : " + userProfile?.userName
                profileEmail.text = "Email : " + userProfile?.userEmail
                profileNumber.text = "Number : " + userProfile?.userNumber

            }

            override fun onCancelled(error: DatabaseError) {
                //Toast.makeText(ContextUtils.getActivity(), "Text!", Toast.LENGTH_SHORT).show();
            }


        })


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment user_fragment.
         */
        // TODO: Rename and change types and number of parameters
        /* @JvmStatic
         fun newInstance(param1: String, param2: String): user_fragment =
             user_fragment().apply {
                 arguments = Bundle().apply {
                     putString(ARG_PARAM1, param1)
                     putString(ARG_PARAM2, param2)
                 }
             }*/
    }
}