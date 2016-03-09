package org.lukosan.salix.ui;

import org.lukosan.salix.SalixTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/salix/ui/template")
public class TemplateController extends ScopedController {

	@RequestMapping("/")
	public String index(Authentication authentication, Model model) {
		model.addAttribute("templates", filter(authentication, salixService.allTemplates()));		
		return "salix/ui/template/index";
	}

	@PreAuthorize("hasPermission(#scope, 'SALIX_USER')")
	@RequestMapping(value="/{scope}/", method=RequestMethod.GET)
	public String templates(@PathVariable String scope, Model model) {
		model.addAttribute("templates", salixService.templatesIn(scope));
		model.addAttribute("scope", scope);
		return "salix/ui/template/index";
	}
	
	@PreAuthorize("hasPermission(#scope, 'SALIX_USER')")
	@RequestMapping(value="/{scope}/", method=RequestMethod.POST)
	public String create(@PathVariable String scope, @RequestParam String name) {
		return "redirect:/salix/ui/template/" + scope + "/" + name + "/";
	}

	@PreAuthorize("hasPermission(#scope, 'SALIX_USER')")
	@RequestMapping(value="/{scope}/{name}/", method=RequestMethod.GET, params="preview")
	public String preview(@PathVariable String scope, @PathVariable String name, Model model) {
		SalixTemplate template = salixService.template(name, scope);
		if(null == template)
			template = new UiSalixTemplate(name);
		model.addAttribute("template", template);
		return "salix/ui/template/preview";
	}

	@PreAuthorize("hasPermission(#scope, 'SALIX_USER')")
	@RequestMapping(value="/{scope}/{name}/", method=RequestMethod.GET, params="!preview")
	public String edit(@PathVariable String scope, @PathVariable String name, Model model) {
		SalixTemplate template = salixService.template(name, scope);
		if(null == template)
			template = new UiSalixTemplate(name);
		model.addAttribute("template", template);
		return "salix/ui/template/edit";
	}
	
	@PreAuthorize("hasPermission(#scope, 'SALIX_USER')")
	@RequestMapping(value="/{scope}/{name}/", method=RequestMethod.POST)
	public String edit(@PathVariable String scope, @PathVariable String name, @RequestParam String source) {
		salixService.save(scope, name, source);
		return "redirect:/salix/ui/template/" + scope + "/";
	}
	
}