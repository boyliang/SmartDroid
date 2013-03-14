/**
 * 
 */
package com.ranlior.smartdroid.model.dao.impl.sqlite;

import java.util.List;

import android.content.Context;
import android.util.Log;

import com.ranlior.smartdroid.model.dao.logic.ITriggerDAO;
import com.ranlior.smartdroid.model.dto.triggers.Trigger;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
public class TriggerDAO implements ITriggerDAO {
	
	/**
	 * Holds the logger's tag.
	 */
	private static final String TAG = "TriggerDAO";
	
	/**
	 * Holds the invoking context.
	 */
	private final Context context;
	
	/**
	 * Full constructor.
	 * 
	 * @param context
	 */
	public TriggerDAO(Context context) {
		super();
		
		// Logger
		Log.d(TAG, "TriggerDAO(Context context)");
		
		this.context = context;
	}

	@Override
	public List<Trigger> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Trigger get(long triggerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Trigger Insert(Trigger trigger) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void Update(Trigger trigger) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Delete(long triggerId) {
		// TODO Auto-generated method stub
		
	}

}
