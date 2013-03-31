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
			Rule newRule = new Rule(null ,"Rule no ' " + i, "Rule Description no' "+ i);
			ruleList.add(newRule);
			if (i == 3) {
				newRule.setName("Rule no ' " + i + " with long name that migth be 2 rows");
				newRule.setDescription("Rule Description no' "+ i + "with very long description that might be expanding the card size.");
			
			}
		}
		return ruleList;
	}
	

	public static List<Trigger> getTriggers(int count) {
		List<Trigger> triggerList = new ArrayList<Trigger>();
		for (int i = 0; i < count; i++) {
			Trigger newTrigger = new Trigger(null ,new Rule(null,"",""), "Rule no ' " + i, "Rule Description no' "+ i, "type");
			if (i == 3) {
				newTrigger.setName("Trigger no ' " + i + " with long name that migth be 2 rows");
				newTrigger.setDescription("Trigger Description no' "+ i + "with very long description that might be expanding the card size.");
			}
			triggerList.add(newTrigger);
		}
		return triggerList;
	}
	
	public static List<Action> getActions(int count) {
		List<Action> ActionList = new ArrayList<Action>();
		for (int i = 0; i < count; i++) {
			Action newAction = new Action(null ,new Rule(null,"",""), "Rule no ' " + i, "Rule Description no' "+ i, "type");
			if (i == 3) {
				newAction.setName("Trigger no ' " + i + " with long name that migth be 2 rows");
				newAction.setDescription("Trigger Description no' "+ i + "with very long description that might be expanding the card size.");
			}
			ActionList.add(newAction);
		}
		return ActionList;
	}
}
