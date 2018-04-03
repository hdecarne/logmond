/*
 * Copyright (c) 2018 Holger de Carne and contributors, All Rights Reserved.
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
package de.carne.logmond;

import de.carne.boot.logging.Log;
import de.carne.lwjsd.api.Service;
import de.carne.lwjsd.api.ServiceContext;
import de.carne.lwjsd.api.ServiceException;

/**
 * LogmonD {@linkplain Service} class.
 */
public class LogmondService implements Service {

	private static final Log LOG = new Log();

	@Override
	public void start(ServiceContext context) throws ServiceException {
		LOG.info("Starting Logmond...");
	}

	@Override
	public void stop(ServiceContext context) throws ServiceException {
		LOG.info("Stoping Logmond...");
	}

}
