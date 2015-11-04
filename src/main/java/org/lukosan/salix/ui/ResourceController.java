package org.lukosan.salix.ui;

import org.lukosan.salix.MapUtils;
import org.lukosan.salix.SalixResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	@RequestMapping(value="/{scope}/{sourceId}/", method=RequestMethod.POST)
	public String edit(@PathVariable String scope, @PathVariable String sourceId, @RequestParam String sourceUri, @RequestParam String map) {
		salixService.save(scope, sourceId, sourceUri, MapUtils.fromString(map));
		return "redirect:/salix/ui/resource/" + scope + "/";
	}
	
}