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

import com.google.gwt.requestfactory.shared.EntityProxy;

/**
 * An individual episode of a ShowProxy. Contains a reference to the show that it belongs to, and to
 * its place in that show, as well as a brief name and description of the episode.
 * 
 * @todo Do we want a Season object as well? Only utility I could see in that is to provide meta-
 * data to a List of Episodes.
 * 
 * @author colin
 *
 */
public interface EpisodeProxy extends EntityProxy {
	ShowProxy getShow();
	void setShow(ShowProxy show);

	String getName();
	void setName(String name);

	int getSeason();
	void setSeason(int num);

	int getEpisodeNumber();
	void setEpisodeNumber(int num);
}
