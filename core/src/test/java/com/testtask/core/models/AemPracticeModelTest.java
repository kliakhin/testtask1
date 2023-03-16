package com.testtask.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AemContextExtension.class)
@ExtendWith(MockitoExtension.class)
class AemPracticeModelTest {

	public static final String ROOT = "/content/test-page";
	private final AemContext ctx = new AemContext();

	@BeforeEach
	void setUp() {
		ctx.addModelsForClasses(AemPracticeModel.class);
		ctx.load().json("/com/testtask/core/models/aem-practice-model.json", ROOT);
	}

	@Test
	void happyPath() {
		Resource resource = ctx.currentResource(ROOT + "/jcr:content/root/container/container/aempractice");
		assertNotNull(resource);
		final AemPracticeModel model = resource.adaptTo(AemPracticeModel.class);
		assertNotNull(model);
		assertTrue(model.isValid());
		assertEquals("Title", model.getTitle());
		assertEquals("Test Page", model.getPageTitle());
		assertEquals(ROOT, model.getPagePath());
		assertTrue(model.isPublish());
		assertTrue(model.isBackgroundImage());
		assertEquals("/content/dam/testtask/asset.jpg", model.getImagePath());
		List<NavigationItem> navigationItems = model.getNavigationItems();
		assertNotNull(navigationItems);
		assertEquals(3, navigationItems.size());
		NavigationItem lastItem = navigationItems.get(2);
		assertEquals("/content/testtask/us/en/test-page", lastItem.getPath());
		assertEquals("Nav 3", lastItem.getTitle());
	}

	@Test
	void empty() {
		Resource resource = ctx.currentResource(ROOT + "/jcr:content/root/container/container/aempractice-empty");
		assertNotNull(resource);
		final AemPracticeModel model = resource.adaptTo(AemPracticeModel.class);
		assertNotNull(model);
		assertFalse(model.isValid());
	}
}