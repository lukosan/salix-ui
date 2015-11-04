package org.lukosan.salix.ui;

import java.util.HashMap;
import java.util.Map;

import org.lukosan.salix.SalixConfiguration;

public class UiSalixConfiguration implements SalixConfiguration {

	private static final long serialVersionUID = 1L;
	
	String scope;
	String target;
	Map<String, Object> map = new HashMap<String, Object>();
	
	public UiSalixConfiguration(String target, String scope) {
		setTarget(target);
		setScope(scope);
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
}
