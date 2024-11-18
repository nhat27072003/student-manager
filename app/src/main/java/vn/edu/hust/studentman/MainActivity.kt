package vn.edu.hust.studentman

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var studentAdapter: StudentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val students = mutableListOf(
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

        fun showUndoSnackbar(removedStudent: StudentModel, position: Int) {
            val snackbar = Snackbar.make(
                findViewById(R.id.recycler_view_students),
                "${removedStudent.studentName} đã bị xóa",
                Snackbar.LENGTH_LONG
            )
            snackbar.setAction("Undo") {
                students.add(position, removedStudent)
                studentAdapter.notifyItemInserted(position)
            }
            snackbar.show()
        }

        fun showAddStudentDialog(onAdd: (StudentModel) -> Unit) {
            val dialogView = layoutInflater.inflate(R.layout.dialog_edit, null)
            val editName = dialogView.findViewById<EditText>(R.id.edtName)
            val editId = dialogView.findViewById<EditText>(R.id.edtId)

            AlertDialog.Builder(this)
                .setTitle("Thêm mới sinh viên")
                .setView(dialogView)
                .setPositiveButton("Thêm") { _, _ ->
                    val newStudent = StudentModel(
                        studentName = editName.text.toString(),
                        studentId = editId.text.toString()
                    )
                    onAdd(newStudent)
                }
                .setNegativeButton("Hủy", null)
                .show()
        }

        fun showDeleteConfirmationDialog(
            student: StudentModel,
            onConfirm: (Boolean) -> Unit
        ) {
            AlertDialog.Builder(this)
                .setTitle("Xóa sinh viên")
                .setMessage("Bạn có muốn xóa sinh viên ${student.studentName}?")
                .setPositiveButton("Có") { _, _ -> onConfirm(true) }
                .setNegativeButton("Không") { _, _ -> onConfirm(false) }
                .show()
        }


        fun showEditStudentDialog(
            student: StudentModel,
            onSave: (StudentModel) -> Unit
        ) {
            val dialogView = layoutInflater.inflate(R.layout.dialog_edit, null)
            val editName = dialogView.findViewById<EditText>(R.id.edtName)
            val editId = dialogView.findViewById<EditText>(R.id.edtId)

            editName.setText(student.studentName)
            editId.setText(student.studentId)

            AlertDialog.Builder(this)
                .setTitle("Cập nhật sinh viên")
                .setView(dialogView)
                .setPositiveButton("Lưu") { _, _ ->
                    val updatedStudent = StudentModel(
                        studentName = editName.text.toString(),
                        studentId = editId.text.toString()
                    )
                    onSave(updatedStudent)
                }
                .setNegativeButton("Hủy", null)
                .show()
        }

        studentAdapter = StudentAdapter(
            students,
            onEditClick = { student ->
                showEditStudentDialog(student) { updatedStudent ->
                    val index = students.indexOfFirst { it.studentId == student.studentId }
                    if (index != -1) {
                        students[index] = updatedStudent
                        studentAdapter.notifyItemChanged(index)
                    }
                }
            },
            onRemoveClick = { student ->
                showDeleteConfirmationDialog(student) { confirmed ->
                    if (confirmed) {
                        val index = students.indexOfFirst { it.studentId == student.studentId }
                        if (index != -1) {
                            val removedStudent = students.removeAt(index)
                            studentAdapter.notifyItemChanged(index)
                            showUndoSnackbar(removedStudent, index)
                        }
                    }
                }
            }
        )

        findViewById<RecyclerView>(R.id.recycler_view_students).run {
            adapter = studentAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        findViewById<Button>(R.id.btn_add_new).setOnClickListener {
            showAddStudentDialog { newStudent ->
                students.add(newStudent)
                studentAdapter.notifyItemInserted(students.size - 1)
            }
        }

    }

}