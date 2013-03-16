/**
 * 
 */
package com.ranlior.smartdroid.model.dao.logic;

import java.util.List;

import android.database.Cursor;

import com.ranlior.smartdroid.model.dto.actions.Action;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
public interface IActionDAO {
	
	/**
	 * Returns all the actions.
	 * 
	 * @return	List of all the actions
	 */
	public List<Action> list();
	
	/**
	 * Returns a specific action by given action's id.
	 * 
	 * @param actionId		Long represents the action's id
	 * @return				Action
	 */
	public Action get(long actionId);
	
	/**
	 * Inserts a action.
	 * 
	 * @param action	Action the action to insert
	 * @return			Action the action just inserted
	 */
	public Action Insert(Action action);
	
	/**
	 * Updates a action.
	 * 
	 * @param action	Action the action to update
	 */
	public void Update(Action action);
	
	/**
	 * Deletes a specific action by given action's id.
	 * 
	 * @param action	Action the action to delete
	 */
	public void Delete(Action action);

}
