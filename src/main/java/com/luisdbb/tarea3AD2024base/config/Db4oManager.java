package com.luisdbb.tarea3AD2024base.config;

import org.springframework.stereotype.Component;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
@Component
public class Db4oManager {
	private static final String DATABASE = "C:/Users/alumnoDAM/Downloads/tarea3AD2024/database.db4o";
	private static ObjectContainer db;
	
	
	public ObjectContainer getDbo4() {
        if (db == null|| db.ext().isClosed()) {
            db=Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),DATABASE);
        }
        return db;
    }
	 public void closeConnection() {
	        if (db != null) {
	            db.close();
	            db = null;
	            
	        }
	    }
	
}
