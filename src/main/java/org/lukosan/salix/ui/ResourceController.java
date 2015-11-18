package org.lukosan.salix.ui;

import java.io.IOException;

import org.lukosan.salix.MapUtils;
import org.lukosan.salix.SalixResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/salix/ui/resource")
public class ResourceController extends ScopedController {

	@RequestMapping("/")
	public String index(Authentication authentication, Model model) {
		model.addAttribute("resources", filter(authentication, salixService.allResources()));		
		return "salix/ui/resource/index";
	}
	
	@PreAuthorize("hasPermission(#scope, 'SALIX_USER')")
	@RequestMapping(value="/{scope}/", method=RequestMethod.GET)
	public String templates(@PathVariable String scope, Model model) {
		model.addAttribute("resources", salixService.resourcesIn(scope));
		model.addAttribute("scope", scope);
		return "salix/ui/resource/index";
	}
	
	@PreAuthorize("hasPermission(#scope, 'SALIX_USER')")
	@RequestMapping(value="/{scope}/", method=RequestMethod.POST)
	public String create(@PathVariable String scope, @RequestParam String sourceId) {
		return "redirect:/salix/ui/resource/" + scope + "/" + sourceId + "/";
	}
	
	@PreAuthorize("hasPermission(#scope, 'SALIX_USER')")
	@RequestMapping(value="/{scope}/{sourceId}/", method=RequestMethod.GET)
	public String edit(@PathVariable String scope, @PathVariable String sourceId, Model model) {
		SalixResource resource = salixService.resource(sourceId, scope);
		if(null == resource)
			resource = new UiSalixResource(sourceId, scope);
		model.addAttribute("resource", resource);
		return "salix/ui/resource/edit";
	}

	@PreAuthorize("hasPermission(#scope, 'SALIX_USER')")
	@RequestMapping(value="/{scope}/{sourceId}/", method=RequestMethod.POST, params="!resourceType")
	public String save(@PathVariable String scope, @PathVariable String sourceId, @RequestParam String contentType,
			@RequestParam String sourceUri, @RequestParam(required=false) String map,
			@RequestParam(required=false) String text, @RequestParam(required=false) MultipartFile file) throws IOException {
		if(! file.isEmpty())
			salixService.save(scope, sourceId, sourceUri, contentType, file.getBytes());
		else if(StringUtils.hasText(text))
			salixService.save(scope, sourceId, sourceUri, contentType, text);
		else if(StringUtils.hasText(map))
			salixService.save(scope, sourceId, sourceUri, MapUtils.fromString(map));
		return "redirect:/salix/ui/resource/" + scope + "/";
	}
	
	@PreAuthorize("hasPermission(#scope, 'SALIX_USER')")
	@RequestMapping(value="/{scope}/{sourceId}/", method=RequestMethod.POST, params="resourceType=1")
	public String json(@PathVariable String scope, @PathVariable String sourceId, @RequestParam String contentType,
			@RequestParam String sourceUri, @RequestParam String map) {
		salixService.save(scope, sourceId, sourceUri, MapUtils.fromString(map));
		return "redirect:/salix/ui/resource/" + scope + "/";
	}
	
	@PreAuthorize("hasPermission(#scope, 'SALIX_USER')")
	@RequestMapping(value="/{scope}/{sourceId}/", method=RequestMethod.POST, params="resourceType=2")
	public String text(@PathVariable String scope, @PathVariable String sourceId, @RequestParam String contentType,
			@RequestParam String sourceUri, @RequestParam String text) {
		salixService.save(scope, sourceId, sourceUri, contentType, text);
		return "redirect:/salix/ui/resource/" + scope + "/";
	}
	
	@PreAuthorize("hasPermission(#scope, 'SALIX_USER')")
	@RequestMapping(value="/{scope}/{sourceId}/", method=RequestMethod.POST, params="resourceType=0")
	public String text(@PathVariable String scope, @PathVariable String sourceId, @RequestParam String contentType,
			@RequestParam String sourceUri, @RequestParam(required=false) MultipartFile file) throws IOException {
		salixService.save(scope, sourceId, sourceUri, contentType, null == file || file.isEmpty() ? new byte[0] : file.getBytes());
		return "redirect:/salix/ui/resource/" + scope + "/";
	}
	
}