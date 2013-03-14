/**
 * 
 */
package com.ranlior.smartdroid.model.dto.rules;

import java.util.List;

import android.content.Context;

import com.ranlior.smartdroid.model.dto.actions.Action;
import com.ranlior.smartdroid.model.dto.triggers.Trigger;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
public class Rule {
	
	/**
	 * Holds the context that instantiate this action.
	 */
	protected final Context mContext;
	
	/**
	 * Holds the rule's identifier.
	 */
	private long mId = -1L;
	
	/**
	 * Holds the rule's name.
	 */
	private String name = null;
	
	/**
	 * Holds the rule's description.
	 */
	private String description = null;
	
	/**
	 * Holds the rule's satisfaction status.
	 */
	private boolean isSatisfied = false;
	
	/**
	 * Holds all the triggers of the rule. 
	 */
	private List<Trigger> triggers = null;
	
	/**
	 * Holds all the actions of the rule.
	 */
	private List<Action> actions = null;
	

	/**
	 * Full constructor.
	 * 
	 * @param context
	 * @param triggers
	 * @param actions
	 */
	public Rule(Context context, List<Trigger> triggers, List<Action> actions) {
		super();
		this.mContext = context;
		this.triggers = triggers;
		this.actions = actions;
	}

	/**
	 * @return the mId
	 */
	public long getmId() {
		return mId;
	}

	/**
	 * @param mId the mId to set
	 */
	public void setmId(long mId) {
		this.mId = mId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the triggers
	 */
	public List<Trigger> getTriggers() {
		return triggers;
	}

	/**
	 * @param triggers the triggers to set
	 */
	public void setTriggers(List<Trigger> triggers) {
		this.triggers = triggers;
	}

	/**
	 * @return the actions
	 */
	public List<Action> getActions() {
		return actions;
	}

	/**
	 * @param actions the actions to set
	 */
	public void setActions(List<Action> actions) {
		this.actions = actions;
	}
	
	/**
	 * Checks that all the triggers in this rule satisfied.
	 */
	public boolean isSatisfied() {
		setSatisfied(true);
		for (Trigger trigger: triggers) {
			if (!trigger.isSatisfied()) {
				setSatisfied(false);
				break;
			}
		}
		return isSatisfied;
	}

	/**
	 * @param isSatisfied the isSatisfied to set
	 */
	public void setSatisfied(boolean isSatisfied) {
		this.isSatisfied = isSatisfied;
	}

	/**
	 * Registers all triggers.
	 */
	public void register() {
		for (Trigger trigger : triggers) {
			trigger.register();
		}
	}
	
	/**
	 * Performs all the action in this rule.
	 */
	public void perform() {
		for (Action action : actions) {
			action.perform();
		}
	}

}
