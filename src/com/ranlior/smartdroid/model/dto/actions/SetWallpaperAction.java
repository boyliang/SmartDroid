/**
 * 
 */
package com.ranlior.smartdroid.model.dto.actions;

import java.io.IOException;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.ranlior.smartdroid.R;
import com.ranlior.smartdroid.activities.actions.editors.SetWallpaperActionEditorActivity;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class SetWallpaperAction extends Action {

	private static final String TAG = SetWallpaperAction.class.getSimpleName();

	private static final String NAME = "Change wallpaper";

	private static final String DESCRIPTION = "Changes wallpaper";

	private static final int ICON = R.drawable.ic_list_wallpaper;

	/**
	 * Holds the wanted bitmap.
	 */
	private String bitmapFilePath = null;

	public SetWallpaperAction() {
		super(NAME, DESCRIPTION);
	}

	/**
	 * Full constractor.
	 * 
	 * @param context
	 *            Context the context instantiating this action
	 * @param bitmap
	 *            Bitmap the wanted wallpaper
	 */
	public SetWallpaperAction(String bitmapFilePath) {
		super(NAME, DESCRIPTION);
		this.bitmapFilePath = bitmapFilePath;
	}

	/**
	 * @return the bitmapFilePath
	 */
	public String getBitmapFilePath() {
		return bitmapFilePath;
	}

	/**
	 * @param bitmapFilePath
	 *            the bitmapFilePath to set
	 */
	public void setBitmapFilePath(String bitmapFilePath) {
		this.bitmapFilePath = bitmapFilePath;
	}

	@Override
	public void perform(Context context) {
		Log.d(TAG, "perform(Context context)");

		WallpaperManager wallpaperManager = (WallpaperManager) context.getSystemService(Context.WALLPAPER_SERVICE);

		// Changes wallpaper by given file path to bitmap file
		try {
			Bitmap bitmap = BitmapFactory.decodeFile(bitmapFilePath);
			wallpaperManager.setBitmap(bitmap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getIconId() {
		return ICON;
	}

	@Override
	public Bundle getExtras() {
		Bundle extras = new Bundle();
		extras.putString("bitmapFilePath", bitmapFilePath);
		return extras;
	}

	@Override
	public void setExtras(Bundle extras) {
		setBitmapFilePath(extras.getString("bitmapFilePath"));
	}

	@Override
	public Class<? extends Activity> getActionEditor() {
		return SetWallpaperActionEditorActivity.class;
	}

}
