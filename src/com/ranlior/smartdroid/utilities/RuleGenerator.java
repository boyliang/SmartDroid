package com.ranlior.smartdroid.utilities;

import java.util.ArrayList;
import java.util.List;

import com.ranlior.smartdroid.model.dto.actions.Action;
import com.ranlior.smartdroid.model.dto.rules.Rule;
import com.ranlior.smartdroid.model.dto.triggers.Trigger;

public class RuleGenerator {

	public static List<Rule> getRules(int count) {
		List<Rule> ruleList = new ArrayList<Rule>();
		for (int i = 0; i < count; i++) {
			Rule newRule = new Rule(null, "Rule no ' " + i, "Rule Description no' " + i);
			ruleList.add(newRule);
			if (i == 3) {
				newRule.setName("Rule no ' " + i + " with long name that migth be 2 rows");
				newRule.setDescription("Rule Description no' " + i + "with very long description that might be expanding the card size.");

			}
		}
		return ruleList;
	}

	public static List<Trigger> getTriggers(int count) {
		List<Trigger> triggerList = new ArrayList<Trigger>();

		Trigger newTrigger1 = new Trigger(null, new Rule(null, "", ""),"RingerModeTrigger" ,"Ringer Mode Change", "Triggers when the ringer mode changes to the specified mode");
		Trigger newTrigger2 = new Trigger(null, new Rule(null, "", ""),"BattryPluggedTrigger" ,"Battery Plugged", "Triggers when power source changes to the specified source");
		Trigger newTrigger3 = new Trigger(null, new Rule(null, "", ""),"BatteryLevelTrigger" ,"Battery Level Change", "Triggers when the the battery life bla bla bal");

		triggerList.add(newTrigger1);
		triggerList.add(newTrigger2);
		triggerList.add(newTrigger3);
		

		return triggerList;
	}

	public static List<Action> getActions(int count) {
		List<Action> ActionList = new ArrayList<Action>();
		for (int i = 0; i < count; i++) {
			Action newAction = new Action(null, new Rule(null, "", ""), "Rule no ' " + i, "Rule Description no' " + i, "type");
			if (i == 3) {
				newAction.setClassName("Trigger no ' " + i + " with long name that migth be 2 rows");
//				newAction.setDescription("Trigger Description no' " + i
//						+ "with very long description that might be expanding the card size.");
			}
			ActionList.add(newAction);
		}
		return ActionList;
	}
}
