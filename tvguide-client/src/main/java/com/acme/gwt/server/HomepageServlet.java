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
package com.acme.gwt.server;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author colin
 *
 */
@Singleton
public class HomepageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Inject
	TvViewerJsonBootstrap auth;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ServletOutputStream out = resp.getOutputStream();
		resp.setDateHeader("Expires", new Date().getTime() + 60);//1 minute expires
		out.println("<!doctype html>");
		out.println("<html>");
		out.println("<head>");
		out
				.println("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">");
		out.println("<title>GWT TV Guide Sample</title>");

		//TODO inline this content for faster page load - may require gwt linker change
		out
				.println("<script type=\"text/javascript\" language=\"javascript\" src=\"tvguide/tvguide.nocache.js\"></script>");

		//TODO XXX can be attacked through use of a ' mark in any user field
		out.println("<script type=\"text/javascript\">var store = '"
				+ auth.getViewerAsJson() + "';</script>");

		out.println("<body>");
		out
				.println("<iframe src=\"javascript:''\" id=\"__gwt_historyFrame\" tabIndex='-1' style=\"position:absolute;width:0;height:0;border:0\"></iframe>");
		out.println("</body>");
		out.println("</html>");
	}
}
