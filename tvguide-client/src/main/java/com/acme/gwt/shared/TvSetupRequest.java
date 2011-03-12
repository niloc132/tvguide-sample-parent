/**
 *  Copyright 2011 Colin Alworth
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
 *
 */
package com.acme.gwt.shared;

import com.google.gwt.requestfactory.shared.RequestContext;
import com.google.gwt.requestfactory.shared.Service;

/**
 * This RequestContext will be used to inform the server about how a particular locale is set up.
 * This will probably not be used by most users, and it is possible that we want to deny access to
 * this for certain types of users (i.e. users who are not logged in, or are not admins).
 *
 * @author colin
 * @todo No methods yet, I am assuming we will get to this
 */
@Service(TvSetupRequest.SetupReqImpl.class)
public interface TvSetupRequest extends RequestContext {

	public class SetupReqImpl {
	}
}
