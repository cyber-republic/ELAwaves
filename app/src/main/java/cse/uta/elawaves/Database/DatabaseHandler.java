package cse.uta.elawaves.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHandler extends SQLiteOpenHelper {
    // Information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "elawaves.db";

    // Initialize the database - *Could* use Cursor factory
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null,  DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_query = "CREATE TABLE IF NOT EXISTS messages( " +
                "message_id INT PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "message VARCHAR(255) NOT NULL, " +
                "address CHAR(52) NOT NULL, " +
                "sent_received BIT(1), " +
                "prev_message INT NOT NULL, " +
                "message_order INT DEFAULT 0, " +
                "message_timestamp DATETIME NOT NULL, " +
                "FOREIGN KEY(prev_message) REFERENCES messages(message_id));";
        db.execSQL(create_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}

