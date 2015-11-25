package org.lukosan.salix.ui;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.lukosan.salix.MapUtils;
import org.lukosan.salix.ResourceWriter;
import org.lukosan.salix.SalixResourceBinary;
import org.lukosan.salix.SalixResourceJson;
import org.lukosan.salix.SalixResourceText;
import org.lukosan.salix.SalixResourceType;

public class UiSalixResource implements SalixResourceJson, SalixResourceText, SalixResourceBinary {
	
	private static final long serialVersionUID = 1L;
	
	private String resourceId;
	private String scope;
	private String sourceId;
	private String sourceUri;
	private Map<String, Object> map = new HashMap<String, Object>();
	private String text;
	private SalixResourceType resourceType;
	private String contentType;
	private byte[] bytes;
	
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
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getResourceId() {
		return resourceId;
	}
	public String getResourceUri() {
		return null;
	}
	public SalixResourceType getResourceType() {
		return resourceType;
	}
	public String getContentType() {
		return contentType;
	}
	public byte[] getBytes() {
		return bytes;
	}
	public void setResourceType(SalixResourceType resourceType) {
		this.resourceType = resourceType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	@Override
	public void writeTo(ResourceWriter writer) throws IOException {
		switch(getResourceType()) {
			case TEXT : writer.getWriter().write(getText()); break;
			case JSON : writer.getWriter().write(MapUtils.asString(getMap())); break;
			case BINARY : writer.getOutputStream().write(getBytes());
		}
	}

	@Override
	public boolean exists() {
		return false;
	}
}