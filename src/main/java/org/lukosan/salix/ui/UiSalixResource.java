package org.lukosan.salix.ui;

import java.util.HashMap;
import java.util.Map;

import org.lukosan.salix.SalixResource;

public class UiSalixResource implements SalixResource {
	
	private static final long serialVersionUID = 1L;
	
	String scope;
	String sourceId;
	String sourceUri;
	private Map<String, Object> map = new HashMap<String, Object>();
	
	public UiSalixResource(String sourceId, String scope) {
		setSourceId(sourceId);
		setScope(scope);
	}
	
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getSourceUri() {
		return sourceUri;
	}
	public void setSourceUri(String sourceUri) {
		this.sourceUri = sourceUri;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}

}