package com.xianqin.controllers;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

public class ApplicationIReportView extends JasperReportsMultiFormatView {

	private JasperReport jasperReport;

	public ApplicationIReportView() {
		super();
	}

	protected JasperPrint fillReport(Map<String, Object> model) throws Exception {
		if (model.containsKey("url")) {
			setUrl(String.valueOf(model.get("url")));
			this.jasperReport = loadReport();
		}
		return super.fillReport(model);
	}

	@Override
	protected void postProcessReport(JasperPrint chartprint, Map<String, Object> model) throws Exception {
		HttpSession session = null;
		if ("html".equalsIgnoreCase((String) model.get("format"))) {
			session = (HttpSession) model.get("session");
			session.setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, chartprint);
		}
		super.postProcessReport(chartprint, model);
	}

	protected JasperReport getReport() {
		return this.jasperReport;
	}
}