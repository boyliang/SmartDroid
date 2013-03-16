/**
 * 
 */
package com.ranlior.smartdroid.model.dto.rules;

import java.util.Collection;

import android.content.Context;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.ranlior.smartdroid.model.dto.actions.Action;
import com.ranlior.smartdroid.model.dto.triggers.Trigger;

/**
 * @author Ran Haveshush
 * Email:  ran.haveshush.shenkar@gmail.com
 *
 */
@DatabaseTable(tableName="rules")
public class Rule {
	
	/**
	 * Holds the context that instantiate this action.
	 */
	protected Context mContext = null;
	
	/**
	 * Holds the rule's identifier.
	 */
	@DatabaseField(generatedId=true)
	private long id = -1L;
	
	/**
	 * Holds the rule's name.
	 */
	@DatabaseField(canBeNull=false)
	private String name = null;
	
	/**
	 * Holds the rule's description.
	 */
	@DatabaseField(canBeNull=false)
	private String description = null;
	
	/**
	 * Holds the rule's satisfaction status.
	 */
	@DatabaseField(canBeNull=false)
	private boolean isSatisfied = false;
	
	/**
	 * Holds all the triggers of the rule. 
	 */
	@ForeignCollectionField
	private Collection<Trigger> triggers = null;
	
	/**
	 * Holds all the actions of the rule.
	 */
	@ForeignCollectionField
	private Collection<Action> actions = null;

	/**
	 * Default constructor.
	 */
	protected Rule() {
		super();
	}

	/**
	 * Full constructor.
	 * 
	 * @param context
	 * @param name
	 * @param description
	 */
	public Rule(Context context, String name, String description) {
		super();
		this.mContext = context;
		this.name = name;
		this.description = description;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
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
	public Collection<Trigger> getTriggers() {
		return triggers;
	}

	/**
	 * @param triggers the triggers to set
	 */
	public void setTriggers(Collection<Trigger> triggers) {
		this.triggers = triggers;
	}

	/**
	 * @return the actions
	 */
	public Collection<Action> getActions() {
		return actions;
	}

	/**
	 * @param actions the actions to set
	 */
	public void setActions(Collection<Action> actions) {
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
