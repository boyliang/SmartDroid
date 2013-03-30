/**
 * 
 */
package com.ranlior.smartdroid.model.dto.actions;

import java.io.IOException;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.ranlior.smartdroid.model.dto.rules.Rule;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
@DatabaseTable(tableName = "set_wallpaper_actions")
public class SetWallpaperAction extends Action {
	
	/**
	 * Holds logger's tag.
	 */
	private static final String TAG = "SetWallpaperAction";
	
	/**
	 * The action's name. 
	 */
	private static final String NAME = "Change wallpaper";
	
	/**
	 * The action's description.
	 */
	private static final String DESCRIPTION = "Changes wallpaper";
	
	/*
	 * Table definition.
	 */
	
	/**
	 * The table name.
	 */
	private static final String TABLE_NAME = "set_wallpaper_actions";
	
	/*
	 * Columns definitions.
	 */
	
	/**
	 * Column name wallpaper bitmap file path.
	 * 
	 * <P>Type: STRING</P>
	 * <P>Constraint: NOT NULL</p>
	 */
	private static final String COLUMN_NAME_BITMAP_FILE_PATH = "bitmap_file_path";

	/*
	 * Instance variables.
	 */
	
	/**
	 * Holds the wanted bitmap.
	 */
	@DatabaseField(columnName = SetWallpaperAction.COLUMN_NAME_BITMAP_FILE_PATH, canBeNull = false)
	private String bitmapFilePath = null;
	

	/**
	 * Default constructor.
	 * ORMLite needs a no-arg constructor.
	 */
	protected SetWallpaperAction() {
		super();
	}

	/**
	 * Full constractor.
	 * 
	 * @param context		Context the context instantiating this action
	 * @param rule			Rule represents action's rule
	 * @param bitmap		Bitmap the wanted wallpaper
	 */
	public SetWallpaperAction(Context context, Rule rule, String bitmapFilePath) {
		super(context, rule, SetWallpaperAction.class.getSimpleName(), NAME, DESCRIPTION);
		this.bitmapFilePath = bitmapFilePath;
	}

	/**
	 * @return the bitmapFilePath
	 */
	public String getBitmapFilePath() {
		return bitmapFilePath;
	}

	/**
	 * @param bitmapFilePath the bitmapFilePath to set
	 */
	public void setBitmapFilePath(String bitmapFilePath) {
		this.bitmapFilePath = bitmapFilePath;
	}

	/* (non-Javadoc)
	 * @see com.ranlior.smartdroid.model.dto.actions.Action#perform()
	 */
	@Override
	public void perform() {
		// Logger
		Log.d(TAG, "perform()");
		
		WallpaperManager wallpaperManager = (WallpaperManager) context.getSystemService(Context.WALLPAPER_SERVICE);
		
		// Changes wallpaper by given file path to bitmap file
		try {
			Bitmap bitmap = BitmapFactory.decodeFile(bitmapFilePath);
			wallpaperManager.setBitmap(bitmap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
