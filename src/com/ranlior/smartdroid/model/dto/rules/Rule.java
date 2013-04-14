/**
 * 
 */
package com.ranlior.smartdroid.model.dto.rules;

import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.ranlior.smartdroid.config.SmartDroid;
import com.ranlior.smartdroid.model.dao.SmartDAOFactory;
import com.ranlior.smartdroid.model.dao.logic.IActionDAO;
import com.ranlior.smartdroid.model.dao.logic.ITriggerDAO;
import com.ranlior.smartdroid.model.dto.actions.Action;
import com.ranlior.smartdroid.model.dto.triggers.Trigger;

/**
 * @author Ran Haveshush
 * Email: ran.haveshush.shenkar@gmail.com
 * 
 */
// FIXME: check if the collections should be presisted allways?
@DatabaseTable(tableName = "rules")
public class Rule {
	
	/**
	 * Holds the logger's tag.
	 */
	private static final String TAG = "Rule";
	
	/**
	 * Holds the context that instantiate this rule.
	 */
	protected Context context = null;

	/**
	 * Holds the rule's identifier.
	 */
	@DatabaseField(columnName = SmartDroid.Rules.COLUMN_NAME_ID, generatedId = true)
	private long id = -1L;

	/**
	 * Holds the rule's name.
	 */
	@DatabaseField(columnName = SmartDroid.Rules.COLUMN_NAME_NAME, canBeNull = false)
	private String name = null;

	/**
	 * Holds the rule's description.
	 */
	@DatabaseField(columnName = SmartDroid.Rules.COLUMN_NAME_DESCRIPTION, canBeNull = false)
	private String description = null;

	/**
	 * Holds the rule's satisfaction status.
	 */
	@DatabaseField(columnName = SmartDroid.Rules.COLUMN_NAME_IS_SATISFIED, canBeNull = false)
	private boolean isSatisfied = false;
	
	/**
	 * Holds the rule's not satisfied triggers count.
	 */
	@DatabaseField(columnName = SmartDroid.Rules.COLUMN_NAME_NOT_SATISFIED_TRIGGERS_COUNT, canBeNull = false)
	private int notSatisfiedTriggersCount = 0;

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
		this.context = context;
		this.name = name;
		this.description = description;
	}

	/**
	 * @return the mContext
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * @param mContext the mContext to set
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
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
	public Collection<Trigger> getTriggers() {
		ITriggerDAO triggerDAO = SmartDAOFactory
				.getFactory(SmartDAOFactory.SQLITE)
				.getTriggerDAO(context);
		
		return triggers = triggerDAO.list(this.getId());
	}
	
	/**
	 * @param triggers
	 */
	public void setTriggers(Collection<Trigger> triggers) {
		for (Trigger trigger : triggers) {
			trigger.setRule(this);
		}
		this.triggers = triggers;
	}

	/**
	 * @return the actions
	 */
	public Collection<Action> getActions() {
		IActionDAO actionDAO = SmartDAOFactory
				.getFactory(SmartDAOFactory.SQLITE)
				.getActionDAO(context);
		
		return actions = actionDAO.list(this.getId());
	}
	
	/**
	 * @param actions
	 */
	public void setActions(List<Action> actions) {
		for (Action action : actions) {
			action.setRule(this);
		}
		this.actions = actions;
	}

	/**
	 * Checks that all the triggers in this rule satisfied.
	 */
	public boolean isSatisfied() {
		return isSatisfied;
	}

	/**
	 * Increment not satisfied triggers counter.
	 */
	public void incNotSatisfiedTriggerCount() {
		++this.notSatisfiedTriggersCount;
		
		// If any trigger not satisfied, the rule not statisfied
		this.isSatisfied = false;

		// Logger
		Log.d(TAG, "incNotSatisfiedTriggerCount(): " + this.notSatisfiedTriggersCount);
	}
	
	/**
	 * Decrement not satisfied triggers counter.
	 */
	public void decNotSatisfiedTriggerCount() {
		--this.notSatisfiedTriggersCount;
		
		// If all the trigger are satisfied, the rule statisfied
		if (notSatisfiedTriggersCount == 0) {
			isSatisfied = true;
		}
		// Logger
		Log.d(TAG, "decNotSatisfiedTriggerCount(): " + this.notSatisfiedTriggersCount);
	}

	/**
	 * Registers all triggers.
	 */
	public void register() {
		// Logger
		Log.d(TAG, "register()");
		getTriggers();
		for (Trigger trigger : triggers) {
			trigger.register();
		}
	}

	/**
	 * Performs all the action in this rule.
	 */
	public void perform() {
		// Logger
		Log.d(TAG, "perform()");
		getActions();
		for (Action action : actions) {
			action.perform();
		}
	}

}
