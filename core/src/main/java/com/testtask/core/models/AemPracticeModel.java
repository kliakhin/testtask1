/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.testtask.core.models;

import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.PageManager;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.settings.SlingSettingsService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Model(adaptables = Resource.class,
		resourceType = AemPracticeModel.RESOURCE_TYPE,
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AemPracticeModel {

	protected static final String RESOURCE_TYPE = "testtask/components/aempractice";

	@Self
	private Resource resource;

	@OSGiService
	private SlingSettingsService slingSettingsService;

	@Getter
	@ValueMapValue
	private String imagePath;

	@Getter
	@ValueMapValue
	private boolean backgroundImage;

	@Getter
	@ValueMapValue
	private String title;

	@Getter
	@ChildResource
	private List<NavigationItem> navigationItems;

	@Getter
	private boolean isPublish;

	@Getter
	private String pageTitle;

	@Getter
	private String pagePath;

	@Getter
	private boolean valid;

	@PostConstruct
	protected void init() {
		this.isPublish = Optional.ofNullable(slingSettingsService)
				.map(SlingSettingsService::getRunModes)
				.map(runModes -> runModes.contains(Externalizer.PUBLISH))
				.orElse(Boolean.FALSE);
		Optional.ofNullable(resource)
				.map(Resource::getResourceResolver)
				.map(resourceResolver -> resourceResolver.adaptTo(PageManager.class))
				.map(pageManager -> pageManager.getContainingPage(resource))
				.ifPresent(page -> {
					this.pageTitle = page.getTitle();
					this.pagePath = page.getPath();
				});
		this.valid = CollectionUtils.isNotEmpty(navigationItems);
	}
}
