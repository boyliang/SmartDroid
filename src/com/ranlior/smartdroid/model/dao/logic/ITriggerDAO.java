/**
 * 
 */
package com.ranlior.smartdroid.model.dao.logic;

import java.util.Collection;

import com.ranlior.smartdroid.model.dto.triggers.Trigger;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public interface ITriggerDAO {

	/**
	 * Returns all the triggers of a given rule.
	 * 
	 * @return Collection of all the triggers of a rule
	 */
	public Collection<Trigger> list(long ruleId);

	/**
	 * Returns a specific trigger by given trigger's id.
	 * 
	 * @param triggerId
	 *            Long represents the trigger's id
	 * @return Trigger
	 */
	public Trigger get(long triggerId);

	/**
	 * Inserts a trigger.
	 * 
	 * @param trigger
	 *            Trigger the trigger to insert
	 * @return Trigger the trigger just inserted
	 */
	public Trigger insert(Trigger trigger);

	/**
	 * Updates a trigger.
	 * 
	 * @param trigger
	 *            Trigger the trigger to update
	 */
	public void update(Trigger trigger);

	/**
	 * Deletes a specific trigger by given trigger's id.
	 * 
	 * @param trigger
	 *            Trigger the trigger to delete
	 */
	public void delete(Trigger trigger);

}
