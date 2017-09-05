/*
 * Copyright 2017 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.gchq.gaffer.traffic;

import uk.gov.gchq.gaffer.commonutil.StreamUtil;
import uk.gov.gchq.gaffer.graph.Graph;
import uk.gov.gchq.gaffer.proxystore.ProxyProperties;
import uk.gov.gchq.gaffer.proxystore.ProxyStore;
import uk.gov.gchq.gaffer.user.User;

import java.io.IOException;
import java.net.URL;

/**
 * Spins up the Gaffer REST API and loads the example Road Traffic data set into the store specified by the 'store.type'
 * property and then runs the {@link RoadTrafficTestQueries} against it.
 */
public class RoadTrafficRestApiITs extends RoadTrafficTestQueries {

	@Override
	public void prepareProxy() throws IOException {
		// Create a proxy store that will proxy all queries to the REST API that has been spun up
		ProxyProperties props = new ProxyProperties(System.getProperties());
		props.setStoreClass(ProxyStore.class);
		props.setStorePropertiesClass(props.getClass());

		final URL restURL = new URL(client.getUri());
		props.setGafferHost(restURL.getHost());
		props.setGafferPort(restURL.getPort());
		props.setGafferContextRoot(restURL.getPath());

		this.graph = new Graph.Builder()
			.config(StreamUtil.graphConfig(this.getClass()))
			.storeProperties(props)
			.build();

		this.user = new User();
	}

}
