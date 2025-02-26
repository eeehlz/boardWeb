package com.yedam.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 좀 편하게 코딩하고자....
public interface Control {
	public void exec(HttpServletRequest req, HttpServletResponse resp) //
			throws ServletException, IOException;
}
