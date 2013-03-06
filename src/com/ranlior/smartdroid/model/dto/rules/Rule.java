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
	 * Checks that all the riggers in this rule satisfied.
	 */
	public boolean isSatisfied() {
		boolean isSatisfied = true;
		for (Trigger trigger: triggers) {
			if (!trigger.isSatisfaied()) {
				isSatisfied = false;
				break;
			}
		}
		return isSatisfied;
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
