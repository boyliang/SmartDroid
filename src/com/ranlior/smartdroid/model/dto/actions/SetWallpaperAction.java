/**
 * 
 */
package com.ranlior.smartdroid.model.dto.actions;

import java.io.IOException;

import com.ranlior.smartdroid.R;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class SetWallpaperAction extends Action {

	private static final String TAG = SetWallpaperAction.class.getSimpleName();

	private static final String NAME = "Change wallpaper";

	private static final String DESCRIPTION = "Changes wallpaper";

	private final String ICON = "ic_list_wallpaper";


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
	public String getIconName() {
		return ICON;
	}

	@Override
	// FIXME: Doesn't work.
	public View getChildView(Context context, View convertView) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.expand_wallpaper_action, null);
		final TextView tvFilePath = (TextView) convertView.findViewById(R.id.tvFilePath);
		tvFilePath.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent pickIntent = new Intent();
				pickIntent.setType("image/*");
				pickIntent.setAction(Intent.ACTION_GET_CONTENT);
				Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				// FIXME: put in string.xml
				String pickTitle = "Select or take a new Picture";
				Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
				chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { takePhotoIntent });
				//fragment.startActivityForResult(chooserIntent, 12);
			}
		});
		convertView.findViewById(R.id.save).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO take the path and store it
			}
		});
		return convertView;
	}

}
