package vn.edu.hust.studentman
import android.os.Bundle
import android.widget.EditText
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class StudentActivity : AppCompatActivity() {

    private lateinit var studentNameEditText: EditText
    private lateinit var studentCodeEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_edit)

        studentNameEditText = findViewById(R.id.edtName)
        studentCodeEditText = findViewById(R.id.edtId)
        saveButton = findViewById(R.id.button_save)
        cancelButton = findViewById(R.id.button_cancel)
        val studentName = intent.getStringExtra("STUDENT_NAME") ?: "" // Provide a default value
        val studentCode = intent.getStringExtra("STUDENT_CODE") ?: "" // Provide a default value

        studentNameEditText.setText(studentName)
        studentCodeEditText.setText(studentCode)

        saveButton.setOnClickListener {
            val updatedName = studentNameEditText.text.toString()
            val updatedCode = studentCodeEditText.text.toString()

            if (updatedName.isNotEmpty() && updatedCode.isNotEmpty()) {
                val resultIntent = intent
                resultIntent.putExtra("UPDATED_NAME", updatedName)
                resultIntent.putExtra("UPDATED_CODE", updatedCode)
                setResult(RESULT_OK, resultIntent)
                Toast.makeText(this, "Thông tin đã được cập nhật", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }
}
