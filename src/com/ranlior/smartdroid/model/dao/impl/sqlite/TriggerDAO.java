/**
 * 
 */
package com.ranlior.smartdroid.model.dao.impl.sqlite;

import java.util.Collection;

import android.content.Context;
import android.util.Log;

import com.ranlior.smartdroid.model.dao.logic.ITriggerDAO;
import com.ranlior.smartdroid.model.dto.triggers.Trigger;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class TriggerDAO implements ITriggerDAO {

	private static final String TAG = TriggerDAO.class.getSimpleName();

	private final Context context;

	/**
	 * Full constructor.
	 * 
	 * @param context
	 */
	public TriggerDAO(Context context) {
		super();

		Log.d(TAG, "TriggerDAO(Context context)");

		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ranlior.smartdroid.model.dao.logic.ITriggerDAO#list(long)
	 */
	@Override
	public Collection<Trigger> list(long ruleId) {
		Log.d(TAG, "list(long ruleId)");

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ranlior.smartdroid.model.dao.logic.ITriggerDAO#get(long)
	 */
	@Override
	public Trigger get(long triggerId) {
		Log.d(TAG, "get(long triggerId: " + triggerId + ")");

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ranlior.smartdroid.model.dao.logic
	 * .ITriggerDAO#Insert(com.ranlior.smartdroid.model.dto.triggers.Trigger)
	 */
	@Override
	public Trigger insert(Trigger trigger) {
		Log.d(TAG, "insert(Trigger trigger)");

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ranlior.smartdroid.model.dao.logic
	 * .ITriggerDAO#Update(com.ranlior.smartdroid.model.dto.triggers.Trigger)
	 */
	@Override
	public void update(Trigger trigger) {
		Log.d(TAG, "update(Trigger trigger)");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ranlior.smartdroid.model.dao.logic
	 * .ITriggerDAO#Delete(com.ranlior.smartdroid.model.dto.triggers.Trigger)
	 */
	@Override
	public void delete(Trigger trigger) {
		Log.d(TAG, "delete(Trigger trigger)");

	}

}
