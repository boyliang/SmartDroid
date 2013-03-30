package com.ranlior.smartdroid.utilities;

import java.util.ArrayList;
import java.util.List;

import com.ranlior.smartdroid.model.dto.rules.Rule;

public class RuleGenerator {

	public static List<Rule> getRules(int count) {
		List<Rule> ruleList = new ArrayList<Rule>();
		for (int i = 0; i < count; i++) {
			if (i == 3) {
				ruleList.add(new Rule(null,"Rule no ' " + i + " with long name that migth be 2 rows" , "Rule Description no' "+ i + "with very long description that might be expanding the card size."));
			} else {
				ruleList.add(new Rule(null ,"Rule no ' " + i, "Rule Description no' "+ i));
			}
		}
		return ruleList;
	}
}
