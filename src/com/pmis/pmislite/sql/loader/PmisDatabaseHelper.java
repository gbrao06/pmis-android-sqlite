package com.pmis.pmislite.sql.loader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.*;

public class PmisDatabaseHelper extends SQLiteOpenHelper {

	//singleton to prevent leaks upong opening several instances without closing database.
	private static PmisDatabaseHelper mInstance = null;
	
	//The Android's default system path of your application database.
    private static int DB_VERSION =1;
 
    private static String DB_PATH = "/data/data/com.pmis.pmislite/databases/";
    
    private static String DB_NAME = "mpmisdblite.db";
 
    private SQLiteDatabase myDataBase; 
 
    private final Context myContext;
 
    //should come through getInstance
    private PmisDatabaseHelper(Context ctx) {
		super(ctx, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
		this.myContext=ctx;
	}

    public static PmisDatabaseHelper getInstance(Context ctx) {
        
        // Use the application context, which will ensure that you 
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (mInstance == null) {
          mInstance = new PmisDatabaseHelper(ctx.getApplicationContext());
        }
        return mInstance;
      }
    
    /**
    * Creates a empty database on the system and rewrites it with your own database.
    * */
   public void createDataBase() throws IOException{

   	boolean dbExist = checkDataBase();
   	
   	if(dbExist){
   		//do nothing - database already exist
   	}else{

   		//By calling this method and empty database will be created into the default system path
              //of your application so we are gonna be able to overwrite that database with our database.
   		//this.getReadableDatabase();
   		myDataBase=this.getReadableDatabase();
   		myDataBase.close();
       	try {

   			copyDataBase();

   		} catch (IOException e) {

       		throw new Error("Error copying database");

       	}
   	}

   }
    
 	/**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
 
    	//File dbFile = new File(DB_PATH + DB_NAME);
        //return dbFile.exists();
    	
    	//SQLiteDatabase checkDB = null;
    	
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		//checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    		myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    		//approach 2.
    		//File database = getApplicationContext().getDatabasePath(myPath);
        	//return database.exists();
    	}catch(SQLiteException e){
    		e.printStackTrace();
    		//database does't exist yet.
    	}
 
    	//if(checkDB != null){
 
    		//checkDB.close();
    	//}
 
    	close();
    	//return checkDB != null ? true : false;
    	return myDataBase != null ? true : false;
    }
    
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
 
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
 
    public void openDataBase() throws SQLException{
    	 
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    }
    
    @Override
	public synchronized void close() {
 
    	    if(myDataBase != null)
    		    myDataBase.close();
 
    	    super.close();
 
	}
    
	@Override
	public void onCreate(SQLiteDatabase db){
		// TODO Auto-generated method stub
		
 	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

    // Add your public helper methods to access and get content from the database.
   // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
   // to you to create adapters for your views.

}

