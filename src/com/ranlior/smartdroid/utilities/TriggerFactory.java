package com.ranlior.smartdroid.utilities;

import android.content.Context;

import com.ranlior.smartdroid.model.dto.triggers.BatteryLevelTrigger;
import com.ranlior.smartdroid.model.dto.triggers.BatteryPluggedTrigger;
import com.ranlior.smartdroid.model.dto.triggers.RingerModeTrigger;
import com.ranlior.smartdroid.model.dto.triggers.Trigger;

public class TriggerFactory {
	
	
	/**
	 * This method is using reflection and invoking the default constructor
	 * 
	 * @param triggerCalssName
	 * @return Trigger instance
	 * @throws Exception
	 */
	public static Trigger getTriggerforClassName(String triggerCalssName) throws Exception {
	
			return (Trigger) Class.forName(triggerCalssName).getConstructor().newInstance();
	}
	
	/**
	 * This method is creating Trigger instance by className
	 * 
	 * @param triggerConcreteType
	 * @return Trigger instance
	 *
	 */
	
	public static Trigger getTriggrByTypeName(String triggerConcreteType) {
		
		Trigger trigger = null;
		
		if("RingerModeTrigger".equals(triggerConcreteType)) {
			trigger = new RingerModeTrigger();
		}else if("BttaryLevelTrigger".equals(triggerConcreteType)) {
			trigger = new BatteryLevelTrigger();
		}else if("BattaryPluggedTrigger".equals(triggerConcreteType)){
			trigger = new BatteryPluggedTrigger();
		}
		
		return trigger;
	}
}
