package com.yedam.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yedam.control.Control;

public class FullCalendarControl implements Control {

	@Override
	public void exec(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String tilesPath = "chart/fullCal.tiles";
		String jspPath = "WEB-INF/views/chart/fullCal.jsp";

		req.getRequestDispatcher(tilesPath).forward(req, resp);

	}

}
