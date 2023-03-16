package com.example.lesson10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lesson10.databinding.ActivityInsertionBinding
import com.example.lesson10.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityInsertionBinding
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        dbRef = FirebaseDatabase.getInstance().getReference("Employees")
        binding.btnSave.setOnClickListener {
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData() {
        val empName = binding.edtEmpName.text.toString()
        val empAge = binding.edtEmpAge.text.toString()
        val empSalary = binding.edtEmpSalary.text.toString()

        val empId = dbRef.push().key!!
        val employee = EmployeeModel(empId,empName,empAge, empSalary)
        if(empName.isEmpty()){
            binding.edtEmpName.error = "Please enter name"
            return
        }
        if(empAge.isEmpty()){
            binding.edtEmpAge.error = "Please enter Age"
            return
        }
        if(empSalary.isEmpty()){
            binding.edtEmpSalary.error = "Please enter Salary"
            return
        }
        dbRef.child(empId).setValue(employee).addOnCompleteListener {
         Toast.makeText(this,"Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show()
            binding.edtEmpName.setText("")
            binding.edtEmpAge.setText("")
            binding.edtEmpSalary.setText("")

        }.addOnFailureListener {err->
            Toast.makeText(this,"ERR ${err.message}", Toast.LENGTH_SHORT).show()
        }
    }
}