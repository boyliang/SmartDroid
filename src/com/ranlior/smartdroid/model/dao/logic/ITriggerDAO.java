/**
 * 
 */
package com.ranlior.smartdroid.model.dao.logic;

import java.util.List;

import com.ranlior.smartdroid.model.dto.triggers.Trigger;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
public interface ITriggerDAO {
	
	/**
	 * Returns all the triggers.
	 * 
	 * @return	List of all the triggers
	 */
	public List<Trigger> list();
	
	/**
	 * Returns a specific trigger by given trigger's id.
	 * 
	 * @param triggerId		Long represents the trigger's id
	 * @return				Trigger
	 */
	public Trigger get(long triggerId);
	
	/**
	 * Inserts a trigger.
	 * 
	 * @param trigger	Trigger the trigger to insert
	 * @return			Trigger the trigger just inserted
	 */
	public Trigger Insert(Trigger trigger);
	
	/**
	 * Updates a trigger.
	 * 
	 * @param trigger	Trigger the trigger to update
	 */
	public void Update(Trigger trigger);
	
	/**
	 * Deletes a specific trigger by given trigger's id.
	 * 
	 * @param triggerId		Long represents the trigger's id
	 */
	public void Delete(long triggerId);

}
