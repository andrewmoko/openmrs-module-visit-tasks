/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific language governing rights and
 * limitations under the License.
 *
 * Copyright (C) OpenHMIS.  All Rights Reserved.
 */
package org.openmrs.module.visittasks.api.util;

import org.openmrs.Privilege;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.commons.web.PrivilegeConstantsCompatibility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Constants class for module privilege constants.
 */
public class PrivilegeConstants {
	public static final String APP_VIEW_VISIT_TASK_APP = "App: View Visit Task App";
	public static final String TASK_MANAGE_VISIT_TASK_METADATA = "Task: Manage Visit Task Metadata";
	public static final String TASK_VIEW_MY_PREDEFINED_TASKS = "Task: View My Predefined Tasks";
	public static final String TASK_ACCESS_MANAGE_VISIT_TASK_PAGE = "Task: Manage Visit Task Module";
	public static final String TASK_MAKE_PREDEFINED_TASKS_GLOBAL = "Task: Make Visit Task Predefined Task Global";

	public static final String[] PRIVILEGE_NAMES =
	        new String[] { APP_VIEW_VISIT_TASK_APP, TASK_MANAGE_VISIT_TASK_METADATA, TASK_VIEW_MY_PREDEFINED_TASKS,
	                TASK_ACCESS_MANAGE_VISIT_TASK_PAGE, TASK_MAKE_PREDEFINED_TASKS_GLOBAL };

	protected PrivilegeConstants() {}

	/**
	 * Gets all the privileges defined by the module.
	 * @return The module privileges.
	 */
	public static Set<Privilege> getModulePrivileges() {
		Set<Privilege> privileges = new HashSet<Privilege>(PRIVILEGE_NAMES.length);

		UserService service = Context.getUserService();
		if (service == null) {
			throw new IllegalStateException("The OpenMRS user service cannot be loaded.");
		}

		for (String name : PRIVILEGE_NAMES) {
			privileges.add(service.getPrivilege(name));
		}

		return privileges;
	}

	/**
	 * Gets the default privileges needed to fully use the module.
	 * @return A set containing the default set of privileges.
	 */
	public static Set<Privilege> getDefaultPrivileges() {
		Set<Privilege> privileges = getModulePrivileges();

		UserService service = Context.getUserService();
		if (service == null) {
			throw new IllegalStateException("The OpenMRS user service cannot be loaded.");
		}

		List<String> names = new ArrayList<String>();
		PrivilegeConstantsCompatibility privilegeConstantsCompatibility = new PrivilegeConstantsCompatibility();

		names.add(privilegeConstantsCompatibility.getEditPatientIdentifiersPrivilege());
		names.add(privilegeConstantsCompatibility.getViewAdminFunctionsPrivilege());
		names.add(privilegeConstantsCompatibility.getViewConceptsPrivilege());
		names.add(privilegeConstantsCompatibility.getViewLocationsPrivilege());
		names.add(privilegeConstantsCompatibility.getViewNavigationMenuPrivilege());
		names.add(privilegeConstantsCompatibility.getViewUsersPrivilege());
		names.add(privilegeConstantsCompatibility.getViewRolesPrivilege());

		for (String name : names) {
			privileges.add(service.getPrivilege(name));
		}

		return privileges;
	}
}
