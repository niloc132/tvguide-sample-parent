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

import java.util.Date;

import com.acme.gwt.data.TvAirTime;
import com.acme.gwt.server.InjectingLocator;
import com.google.gwt.requestfactory.shared.ProxyFor;
import com.google.gwt.requestfactory.shared.ValueProxy;

/**
 * Represents a block of time that something can air - start time and duration in minutes. There is
 * probably a neater way to represent this, but I can't think of it right now.
 *
 * @author colin
 */
public
@ProxyFor(value = TvAirTime.class, locator = InjectingLocator.class)
interface TvAirTimeProxy extends ValueProxy {
  Date getStartDate();

  void setStartDate(Date date);

  Integer getDuration();

  void setDuration(Integer duration);

}
