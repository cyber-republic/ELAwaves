package cse.uta.elawaves.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Timestamp;
import java.util.ArrayList;

import cse.uta.elawaves.Carrier.Messages.Message;


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
                "message_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "message VARCHAR(255) NOT NULL, " +
                "address CHAR(52) NOT NULL, " +
                "sent_received BIT(1) NOT NULL," +
                "message_timestamp DATETIME NOT NULL);";

        db.execSQL(create_query);
    }

    public void saveMessage(Message message){
        SQLiteDatabase db = this.getWritableDatabase();
        String message_query = "INSERT INTO messages (message, address, sent_received, message_timestamp) VALUES (?,?,?,?)";
        db.execSQL(message_query,
                new String[] {
                        message.getMessage(),
                        message.getAddress(),
                        String.valueOf(message.isReceived() ? 1 : 0),
                        message.getMessageTimeStamp().toString()
        });
    }

    public ArrayList<Message> getMessages(String address){

        ArrayList<Message> messages = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String conversation_query = "SELECT message, sent_received, message_timestamp FROM messages WHERE address = ? ORDER BY message_timestamp ASC;";

        Cursor c = db.rawQuery(conversation_query, new String[]{address});

        if (c.moveToFirst()){
            do {
                // Passing values
                String tmp_message = c.getString(0);
                Boolean tmp_sent_received = c.getString(1).equals("1");
                Timestamp tmp_message_timestamp = Timestamp.valueOf(c.getString(2));

                // Do something Here with values
                Message tmp_msg = new Message(tmp_message, tmp_sent_received, address, tmp_message_timestamp);
                messages.add(tmp_msg);

            } while(c.moveToNext());
        }

        c.close();
        db.close();

        return messages;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}

