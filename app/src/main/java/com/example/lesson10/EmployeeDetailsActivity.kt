package com.example.lesson10

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.lesson10.databinding.ActivityEmployeeDetailsBinding
import com.google.firebase.database.FirebaseDatabase

class EmployeeDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmployeeDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeDetailsBinding.inflate(layoutInflater)
       val view = binding.root
        setContentView(view)

        setValueToView()

        binding.btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("empId").toString()
            )
        }
        binding.btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("empId").toString(),
                intent.getStringExtra("empName").toString()
            )
        }

    }

    private fun deleteRecord(id: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val mTask = dbRef.removeValue()
        mTask.addOnSuccessListener {
            Toast.makeText(this,"Delete employee success", Toast.LENGTH_SHORT).show()
            val intent  = Intent(this,FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{err->
            Toast.makeText(this,"Delete err${err.message}", Toast.LENGTH_SHORT).show()

        }

    }

    private fun openUpdateDialog(empId: String, empName: String) {
    val mDialog = AlertDialog.Builder(this)
//        chuyen update_dialog xml sang view
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog,null)
        mDialog.setView(mDialogView)

//        set thong tin cho dialog
        val etempName = mDialogView.findViewById<EditText>(R.id.etEmpName)
        val etempAge =  mDialogView.findViewById<EditText>(R.id.etEmpAge)
        val etempSalary =  mDialogView.findViewById<EditText>(R.id.etEmpSalary)
        val btnUpdateData =  mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etempName.setText( intent.getStringExtra("empName").toString())
        etempAge.setText( intent.getStringExtra("empAge").toString())
        etempSalary.setText( intent.getStringExtra("empSalary").toString())

        mDialog.setTitle("Update employee $empName")
        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
          updatEmpData(
            empId,
              etempName.text.toString(),
              etempAge.text.toString(),
              etempSalary.text.toString()
          )
            Toast.makeText(applicationContext,"Update success", Toast.LENGTH_SHORT)
            binding.tvEmpName.setText(etempName.text.toString())
            binding.tvEmpAge.setText(etempAge.text.toString())
            binding.tvEmpSalary.setText(etempSalary.text.toString())
            alertDialog.dismiss()
        }

    }

    private fun updatEmpData(id: String, name: String, age: String, salary: String) {
            val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val empInfo = EmployeeModel(id,name,age,salary)
        dbRef.setValue(empInfo)
    }


    private fun setValueToView() {
        binding.tvEmpId.text = intent.getStringExtra("empId")
        binding.tvEmpAge.text = intent.getStringExtra("empAge")
        binding.tvEmpName.text = intent.getStringExtra("empName")
        binding.tvEmpSalary.text = intent.getStringExtra("empSalary")
    }
}