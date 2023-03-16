package com.testtask.core.models;

import lombok.Getter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Getter
@Model(adaptables = Resource.class)
public class NavigationItem {

	@ValueMapValue
	private String title;
	@ValueMapValue
	private String path;
}
