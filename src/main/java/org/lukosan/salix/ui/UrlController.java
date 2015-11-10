package org.lukosan.salix.ui;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.time.LocalDateTime;

import org.lukosan.salix.MapUtils;
import org.lukosan.salix.SalixUrl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/salix/ui/url")
public class UrlController extends ScopedController {
	
	@RequestMapping("/")
	public String index(Authentication authentication, Model model) {
		model.addAttribute("urls", filter(authentication, salixService.allUrls()));		
		return "salix/ui/url/index";
	}

	@PreAuthorize("hasPermission(#scope, 'SALIX_USER')")
	@RequestMapping(value="/{scope}/", method=RequestMethod.GET)
	public String urls(@PathVariable String scope, Model model) {
		model.addAttribute("urls", salixService.urlsIn(scope));
		model.addAttribute("scope", scope);
		return "salix/ui/url/index";
	}

	@PreAuthorize("hasPermission(#scope, 'SALIX_USER')")
	@RequestMapping(value="/{scope}/", method=RequestMethod.POST)
	public String create(@PathVariable String scope, @RequestParam String url) throws UnsupportedEncodingException {
		return "redirect:/salix/ui/url/" + scope + "/" + URLEncoder.encode(URLEncoder.encode(url, "utf8"), "utf8") + "/";
	}

	@PreAuthorize("hasPermission(#scope, 'SALIX_USER')")
	@RequestMapping(value="/{scope}/{urlEncodedUrl}/", method=RequestMethod.GET)
	public String edit(@PathVariable String scope, @PathVariable String urlEncodedUrl, Model model) throws UnsupportedEncodingException {
		String url = URLDecoder.decode(urlEncodedUrl, "utf8");
		SalixUrl salixUrl = salixService.url(url, scope);
		if(null == salixUrl)
			salixUrl = new UiSalixUrl(url);
		model.addAttribute("url", salixUrl);
		return "salix/ui/url/edit";
	}
	
	@PreAuthorize("hasPermission(#scope, 'SALIX_USER')")
	@RequestMapping(value="/{scope}/{urlEncodedUrl}/", method=RequestMethod.POST)
	public String edit(@PathVariable String scope, @PathVariable String urlEncodedUrl, @RequestParam int status, @RequestParam String view,
			@RequestParam(required=false) String published, @RequestParam(required=false) String removed, @RequestParam String map) throws UnsupportedEncodingException {
		String url = URLDecoder.decode(urlEncodedUrl, "utf8");
		LocalDateTime pubd = parseLocalDateTime(published);
		LocalDateTime remd = parseLocalDateTime(removed);
		salixService.save(scope, url, status, view, pubd, remd, MapUtils.fromString(map));
		return "redirect:/salix/ui/url/" + scope + "/";
	}
}