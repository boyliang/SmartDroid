/**
 * 
 */
package com.ranlior.smartdroid.model.dto.triggers;

import android.content.Context;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.ranlior.smartdroid.config.SmartDroid;
import com.ranlior.smartdroid.model.dto.rules.Rule;

/**
 * @author Ran Haveshush
 * Email: ran.haveshush.shenkar@gmail.com
 * 
 */
@DatabaseTable(tableName = "triggers")
public class Trigger implements Comparable<Trigger> {

	/**
	 * Holds the context that instantiate this action.
	 */
	protected Context context = null;

	/**
	 * Needed for Ormlite (many to one) relation.
	 */
	@DatabaseField(columnName = SmartDroid.Triggers.COLUMN_NAME_RULE_ID, foreign = true, foreignAutoRefresh = true, canBeNull = false)
	private Rule rule = null;

	/**
	 * Holds the trigger's identifier.
	 */
	@DatabaseField(columnName = SmartDroid.Triggers.COLUMN_NAME_ID, generatedId = true)
	private long id = -1L;
	
	/**
	 * Holds the trigger's class name.
	 */
	@DatabaseField(columnName = SmartDroid.Triggers.COLUMN_NAME_CLASS_NAME, canBeNull = false)	
	private String className = null;

	/**
	 * Holds the trigger's name.
	 */
	@DatabaseField(columnName = SmartDroid.Triggers.COLUMN_NAME_NAME, canBeNull = false)
	private String name = null;

	/**
	 * Holds the trigger's description.
	 */
	@DatabaseField(columnName = SmartDroid.Triggers.COLUMN_NAME_DESCRIPTION, canBeNull = false)
	private String description = null;

	/**
	 * Holds the trigger's satisfaction status.
	 */
	@DatabaseField(columnName = SmartDroid.Triggers.COLUMN_NAME_IS_SATISFIED, canBeNull = false)
	private boolean isSatisfied = false;
	
	
	/**
	 * Default constructor.
	 * Ormlite require default constructor.
	 */
	public Trigger() {
		super();
	}

	/**
	 * Full constructor.
	 * 
	 * @param context
	 *            Context the context instantiating this action
	 * @param rule
	 *            Rule represents trigger's rule
	 * @param className
	 * 			  String represents trigger's class name
	 * @param name
	 *            String represents trigger's name
	 * @param description
	 *            String represents trigger's description
	 */
	public Trigger(Context context, Rule rule, String className, String name, String description) {
		super();
		this.context = context;
		this.rule = rule;
		this.className = className;
		this.name = name;
		this.description = description;
	}

	/**
	 * @return the context
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * @return the rule
	 */
	public Rule getRule() {
		return rule;
	}

	/**
	 * @param rule
	 *            the rule to set
	 */
	public void setRule(Rule rule) {
		this.rule = rule;
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
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
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
	 * @return the isSatisfied
	 */
	public boolean isSatisfied() {
		return isSatisfied;
	}

	/**
	 * @param isSatisfied
	 *            the isSatisfied to set
	 */
	public void setSatisfied(boolean isSatisfied) {
		this.isSatisfied = isSatisfied;
	}

	/**
	 * Registers the trigger to the system. Every derived class should
	 * implemenet this method. This is where the trigger registration logic
	 * implementation.
	 */
	public void register() {}
	
	/**
	 * 
	 */
	public void unregister() {}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Trigger another) {
		if (another == null) {
			return 1;
		} else {
			return (int) (id - another.getId());
		}
	};
}
