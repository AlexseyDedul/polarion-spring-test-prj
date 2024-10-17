package com.garantis.polarion.extension.license.analyzer.rest;

import ch.sbb.polarion.extension.generic.rest.GenericRestApplication;
import com.garantis.polarion.extension.license.analyzer.rest.controller.GlobalInternalController;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class LicenseAnalyzerApplication extends GenericRestApplication {

	@Override
	protected @NotNull Set<Object> getExtensionControllerSingletons() {
		return Set.of(
				new GlobalInternalController()
		);
	}


}
