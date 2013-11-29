package com.zendaimoney.online.web;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value = "/")
public class MonitorController {

	private final Log log = LogFactory.getLog(MonitorController.class);

	@Autowired
	private DataSource dataSource;

	@RequestMapping(value = "monit")
	@ResponseBody
	public void monit() throws SQLException {
		Connection connection = dataSource.getConnection();
		connection.close();
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public void handleException(Exception e) {
		e.printStackTrace();
		log.error(e);
	}
}
