/**
 * 
 */
package com.ranlior.smartdroid.model.database;

import android.content.Context;
import android.util.Log;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.ranlior.smartdroid.model.dto.rules.Rule;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public abstract class Db4oHelper {

	private static final String TAG = Db4oHelper.class.getSimpleName();

	private static final String DATABASE_NAME = "smart.db4o";

	private static ObjectContainer OC = null;

	/**
	 * Creates, opens and closes the database.
	 */
	public static ObjectContainer db(Context context) {
		Log.d(TAG, "db(Context context)");

		// Configures the behavior of the databse
		EmbeddedConfiguration dbConfig = Db4oEmbedded.newConfiguration();
		dbConfig.common().messageLevel(3);
		dbConfig.common().objectClass(Rule.class).generateUUIDs(true);
		dbConfig.common().objectClass(Rule.class).cascadeOnActivate(true);
		dbConfig.common().objectClass(Rule.class).cascadeOnUpdate(true);
		dbConfig.common().objectClass(Rule.class).cascadeOnDelete(true);

		String dbFilePath = context.getDir("data", 0) + "/" + DATABASE_NAME;

		try {
			if (OC == null || OC.ext().isClosed()) {
				OC = Db4oEmbedded.openFile(dbConfig, dbFilePath);
			}
			return OC;
		} catch (Exception ie) {
			Log.e(Db4oHelper.class.getName(), ie.toString());
			return null;
		}
	}

	/**
	 * Closes the database.
	 */
	// TODO: use this method smoewhere smart,
	// maybe at the main activity destroy.
	public static void close() {
		if (OC != null) {
			OC.close();
		}
	}

}
