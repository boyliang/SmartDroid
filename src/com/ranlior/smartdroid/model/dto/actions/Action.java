/**
 * 
 */
package com.ranlior.smartdroid.model.dto.actions;

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
@DatabaseTable(tableName = "actions")
public class Action {

	/**
	 * Holds the context that instantiate this action.
	 */
	protected Context context = null;

	/**
	 * Needed for Ormlite (many to one) relation.
	 */
	@DatabaseField(columnName = SmartDroid.Actions.COLUMN_NAME_RULE_ID, foreign = true, foreignAutoRefresh = true)
	private Rule rule = null;

	/**
	 * Holds the action's identifier.
	 */
	@DatabaseField(columnName = SmartDroid.Actions.COLUMN_NAME_ID, generatedId = true, allowGeneratedIdInsert = true)
	private Long id = null;
	
	/**
	 * Holds the action's class name.
	 */
	@DatabaseField(columnName = SmartDroid.Actions.COLUMN_NAME_CLASS_NAME, canBeNull = false)	
	private String className = null;

	/**
	 * Holds the action's name.
	 */
	@DatabaseField(columnName = SmartDroid.Actions.COLUMN_NAME_NAME, canBeNull = false)
	private String name = null;

	/**
	 * Holds the action's description.
	 */
	@DatabaseField(columnName = SmartDroid.Actions.COLUMN_NAME_DESCRIPTION, canBeNull = false)
	private String description = null;

	
	/**
	 * Default constructor.
	 * Ormlite require default constructor.
	 */
	public Action() {
		super();
	}

	/**
	 * Full constructor.
	 * 
	 * @param context
	 * @param rule
	 * @param name
	 * @param description
	 */
	public Action(Context context, Rule rule, String className, String name, String description) {
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
	 * @return the identifier
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the identifier to set
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
	protected void setName(String name) {
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
	protected void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Performs the action. Every derived class should implemenet this method.
	 * This is where the action logic implemeneted.
	 */
	public void perform() {};

}
