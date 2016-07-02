package cn.w.m;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

@WebListener("system init")
public class Init implements ServletContextListener {

	private static final Logger log = Logger.getLogger(Init.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		InitDB initDB = new InitDB();
		if (initDB.isInit()) {
			log.debug("------------------>taoli system init SUCCESS<-----------------------");
		} else {
			log.debug("------------------>taoli system init ERROR<-----------------------");
		}
	}

}
