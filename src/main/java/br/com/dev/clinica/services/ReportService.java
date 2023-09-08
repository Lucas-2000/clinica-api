package br.com.dev.clinica.services;

import jakarta.servlet.ServletContext;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;

@Service
public class ReportService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public byte[] generateReport(String reportName, ServletContext servletContext) throws Exception {
        //Obtendo conexão com o banco
        Connection connection = jdbcTemplate.getDataSource().getConnection();

        //Carregando o caminho do arquivo jasper
        String path = "D:\\spring\\intellij\\projects\\clinica\\src\\main\\java\\br\\com\\dev\\clinica\\reports" + File.separator + reportName + ".jasper";

        //Gerar o relatório com os dados e conexão
        JasperPrint print = JasperFillManager.fillReport(path, new HashMap<>(), connection);

        byte [] pdf = JasperExportManager.exportReportToPdf(print);

        //Fechando a conexão com o banco
        connection.close();

        //Exporta para byte array o PDF para fazer o download
        return pdf;
    }

}
