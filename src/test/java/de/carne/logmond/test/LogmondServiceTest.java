/*
 * Copyright (c) 2018-2019 Holger de Carne and contributors, All Rights Reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.carne.logmond.test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import de.carne.io.IOUtil;
import de.carne.logmond.LogmondService;
import de.carne.lwjsd.api.ServiceInfo;
import de.carne.lwjsd.runtime.config.ConfigStore;
import de.carne.lwjsd.runtime.config.Defaults;
import de.carne.lwjsd.runtime.config.RuntimeConfig;
import de.carne.lwjsd.runtime.server.Server;
import de.carne.nio.file.FileUtil;

/**
 * Test {@linkplain LogmondService} class.
 */
class LogmondServiceTest {

	@Test
	void testLogMonDService() throws Exception {
		RuntimeConfig config = prepareConfig();

		try (Server server = new Server(config)) {
			Thread serverThread = server.start(false);
			ServiceInfo logmondServiceInfo = server.registerService(LogmondService.class.getName());

			server.startService(logmondServiceInfo.id(), false);
			server.requestStop();
			serverThread.join();
		} finally {
			discardConfig(config);
		}
	}

	private RuntimeConfig prepareConfig() throws IOException {
		Path tempDir = Files.createTempDirectory(getClass().getName());

		RuntimeConfig config = new RuntimeConfig(Defaults.get());

		config.setConfDir(tempDir);
		config.setStateDir(tempDir);
		config.setSslKeyStoreFile("localhost.jks");
		config.setSslKeyStoreSecret("secret");
		try (InputStream sslKeyStoreStream = getClass().getResourceAsStream(config.getSslKeyStoreFile())) {
			IOUtil.copyStream(config.getConfDir().resolve(config.getSslKeyStoreFile()).toFile(), sslKeyStoreStream);
		}

		ConfigStore configStore = ConfigStore.create(config);

		configStore.storeConfigFile(config.getConfDir().resolve(ConfigStore.CONFIG_FILE));
		System.out.println("Using test " + config);
		return config;
	}

	public void discardConfig(RuntimeConfig config) throws IOException {
		FileUtil.delete(config.getConfDir());
	}

}
