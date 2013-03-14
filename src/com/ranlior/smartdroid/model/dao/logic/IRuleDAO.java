/**
 * 
 */
package com.ranlior.smartdroid.model.dao.logic;

import java.util.List;

import com.ranlior.smartdroid.model.dto.rules.Rule;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
public interface IRuleDAO {
	
	/**
	 * Returns all the rules.
	 * 
	 * @return	List of all the rules
	 */
	public List<Rule> list();
	
	/**
	 * Returns a specific rule by given rule's id.
	 * 
	 * @param ruleId		Long represents the rule's id
	 * @return				Rule
	 */
	public Rule get(long ruleId);
	
	/**
	 * Inserts a rule.
	 * 
	 * @param rule		Rule the rule to insert
	 * @return			Rule the rule just inserted
	 */
	public Rule Insert(Rule rule);
	
	/**
	 * Updates a rule.
	 * 
	 * @param rule	Rule the rule to update
	 */
	public void Update(Rule rule);
	
	/**
	 * Deletes a specific rule by given rule's id.
	 * 
	 * @param ruleId		Long represents the rule's id
	 */
	public void Delete(long ruleId);

}
