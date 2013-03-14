package com.ranlior.smartdroid.utilities;

import java.io.IOException;
import java.sql.SQLException;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;
import com.ranlior.smartdroid.model.dto.actions.NotificationAction;

/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {

	/**
	 * Classes to process their OrmLite annotations.
	 */
	private static final Class<?>[] classes = new Class[] {
		NotificationAction.class,
	};
	
	public static void main(String[] args) throws SQLException, IOException {
		writeConfigFile("ormlite_config.txt", classes);
	}
}
