package org.lukosan.salix.ui;

import org.lukosan.salix.SalixTemplate;

public class UiSalixTemplate implements SalixTemplate {

	private static final long serialVersionUID = 1L;
	
	private String scope;
	private String name;
	private String source;
	
	public UiSalixTemplate() {
		super();
	}
	public UiSalixTemplate(String name) {
		this();
		setName(name);
	}
	
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
}