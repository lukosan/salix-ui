package org.lukosan.salix.ui;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.lukosan.salix.SalixUrl;
import org.springframework.http.HttpStatus;

public class UiSalixUrl implements SalixUrl {

	private static final long serialVersionUID = 1L;
	
	private String scope;
	private String url;
	private HttpStatus status;
	private String view;
	private LocalDateTime published;
	private LocalDateTime removed;
	private Map<String, Object> map = new HashMap<String, Object>();
	
	public UiSalixUrl() {
		super();
		setStatus(HttpStatus.OK);
	}
	public UiSalixUrl(String url) {
		this();
		setUrl(url);
	}
	
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	public LocalDateTime getPublished() {
		return published;
	}
	public void setPublished(LocalDateTime published) {
		this.published = published;
	}
	public LocalDateTime getRemoved() {
		return removed;
	}
	public void setRemoved(LocalDateTime removed) {
		this.removed = removed;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
}
