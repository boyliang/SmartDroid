/**
 * 
 */
package com.ranlior.smartdroid.model.dto.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.util.Log;

import com.ranlior.smartdroid.model.dto.actions.Action;
import com.ranlior.smartdroid.model.dto.triggers.Trigger;

/**
 * @author Ran Haveshush Email: ran.haveshush.shenkar@gmail.com
 * 
 */
public class Rule implements Comparable<Rule> {

	private static final String TAG = Rule.class.getSimpleName();

	private UUID id = null;

	private String name = null;

	private String description = null;

	private List<Trigger> triggers = new ArrayList<Trigger>();

	private List<Action> actions = new ArrayList<Action>();

	/**
	 * Full constructor.
	 * 
	 * @param name
	 * @param description
	 */
	public Rule(String name, String description) {
		super();
		this.id = UUID.randomUUID();
		this.name = name;
		this.description = description;
	}

	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
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
	 * @param description
	 *            the description to set
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
	 * @param triggers
	 *            the triggers to set
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
	 * @param actions
	 *            the actions to set
	 */
	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	/**
	 * Checks that all the triggers in this rule satisfied.
	 */
	public boolean isSatisfied() {
		Log.d(TAG, "isSatisfied()");

		for (Trigger trigger : triggers) {
			if (!trigger.isSatisfied()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Registers all triggers.
	 * 
	 * @param context
	 */
	public void register(Context context) {
		Log.d(TAG, "register()");

		for (Trigger trigger : triggers) {
			trigger.register(context);
		}
	}

	/**
	 * Performs all the action in this rule.
	 * 
	 * @param context
	 */
	public void perform(Context context) {
		Log.d(TAG, "perform(Context context)");

		for (Action action : actions) {
			action.perform(context);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Rule another) {
		if (another == null) {
			return 1;
		} else {
			return this.getName().compareTo(another.getName());
		}
	};

}
