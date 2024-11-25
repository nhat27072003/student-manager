package vn.edu.hust.studentman

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var adapter: StudentAdapter
    private lateinit var students: MutableList<StudentModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

         students = mutableListOf(
            StudentModel("Nguyễn Văn An", "SV001"),
            StudentModel("Trần Thị Bảo", "SV002"),
            StudentModel("Lê Hoàng Cường", "SV003"),
            StudentModel("Phạm Thị Dung", "SV004"),
            StudentModel("Đỗ Minh Đức", "SV005"),
            StudentModel("Vũ Thị Hoa", "SV006"),
            StudentModel("Hoàng Văn Hải", "SV007"),
            StudentModel("Bùi Thị Hạnh", "SV008"),
            StudentModel("Đinh Văn Hùng", "SV009"),
            StudentModel("Nguyễn Thị Linh", "SV010"),
            StudentModel("Phạm Văn Long", "SV011"),
            StudentModel("Trần Thị Mai", "SV012"),
            StudentModel("Lê Thị Ngọc", "SV013"),
            StudentModel("Vũ Văn Nam", "SV014"),
            StudentModel("Hoàng Thị Phương", "SV015"),
            StudentModel("Đỗ Văn Quân", "SV016"),
            StudentModel("Nguyễn Thị Thu", "SV017"),
            StudentModel("Trần Văn Tài", "SV018"),
            StudentModel("Phạm Thị Tuyết", "SV019"),
            StudentModel("Lê Văn Vũ", "SV020")
        )

        listView = findViewById(R.id.list_view)
        adapter = StudentAdapter(this, students)
        listView.adapter=adapter

        registerForContextMenu(listView)

        listView.setOnItemClickListener { _, _, position, _ ->
            val student = students[position]

            val intent = Intent(this, StudentActivity::class.java)
            intent.putExtra("STUDENT_NAME", student.studentName)
            intent.putExtra("STUDENT_CODE", student.studentId)
            startActivity(intent)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.student_context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val studentName = students[info.position]

        return when (item.itemId) {
            R.id.menu_edit -> {
                val intent = Intent(this, StudentActivity::class.java)
                intent.putExtra("student_name", students[0].studentName)
                intent.putExtra("student_id", students[0].studentId)
                startActivity(intent)
                true
            }
            R.id.menu_delete -> {
                students.removeAt(info.position)
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Đã xóa: ${studentName.studentName}", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_new -> {
                val intent = Intent(this, StudentActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK) {
            val updatedName = data?.getStringExtra("UPDATED_NAME")
            val updatedCode = data?.getStringExtra("UPDATED_CODE")

            if (!updatedName.isNullOrEmpty() && !updatedCode.isNullOrEmpty()) {
                val position = data?.getIntExtra("STUDENT_POSITION", -1) ?: -1
                if (position >= 0) {
                    students[position].studentName = updatedName
                    students[position].studentId = updatedCode

                    adapter.notifyDataSetChanged()

                    Toast.makeText(this, "Thông tin sinh viên đã được cập nhật", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}