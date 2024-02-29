package co.edu.upb.ejercicios_fundamentos2.com.example.crud_estudiantes2

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlin.Exception

class SqlHelper(context: MainActivity): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object{
        private const val  DB_VERSION = 1
        private const val  DB_NAME = "student.db"
        private const val  DB_TABLE = "tbl_estudent"
        private const val  ID_EST = "id"
        private const val  NOMBRE = "nombre"
        private const val  CORREO = "correo"
        private const val  CURSO = "curso"
    }


    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = (
                "CREATE TABLE $DB_TABLE(" +
                        "$ID_EST INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "$NOMBRE TEXT," +
                        "$CORREO TEXT," +
                        "$CURSO TEXT)"
                )
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $DB_TABLE")
        onCreate(db)
    }


    @SuppressLint("Range") // Obligando al metodo que reciba un String

    // Se crea la informacion, se crea la lista y se envia

    fun getListEst():ArrayList<EstModel>{

        val listEst: ArrayList<EstModel> = ArrayList()

        val sql = "SELECT * FROM $DB_TABLE ORDER BY $ID_EST DESC"
        val db = this.readableDatabase
        val cursor: Cursor        // Cursor es un objeto complejo: Reconoce la respuesta de la BBDD, Metodos especificos

        try {

            cursor = db.rawQuery(sql,null)

        } catch (err: Exception){

            err.printStackTrace()  // Error de la consola

            return ArrayList()
        }

        var id:Int
        var nombre: String
        var correo: String
        var curso: String

        if (cursor.moveToFirst()){

            do {
                id = cursor.getInt(cursor.getColumnIndex(ID_EST)) //  getColumnIndex: metodo para extraer el indice por medio del nombre
                nombre = cursor.getString(cursor.getColumnIndex(NOMBRE))
                correo = cursor.getString(cursor.getColumnIndex(CORREO))
                curso = cursor.getString(cursor.getColumnIndex(CURSO))

                val est = EstModel(id, nombre, correo, curso)

                listEst.add(est)


            }while (cursor.moveToNext())

        }

        return  listEst

    }


    fun insertStudent(est: EstModel): Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID_EST, est.id)
        contentValues.put(NOMBRE, est.nombre)
        contentValues.put(CORREO, est.correeo)
        contentValues.put(CURSO, est.curso)

        val success = db.insert(DB_TABLE, null,contentValues)
        db.close()
        return success
    }
}