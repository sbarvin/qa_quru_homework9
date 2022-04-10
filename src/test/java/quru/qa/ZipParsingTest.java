package quru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ZipParsingTest {

    @Test
    void parseZipTest() throws Exception {
        ZipFile zF = new ZipFile("src/test/resources/files/Downloads.zip");
        Enumeration<? extends ZipEntry> entries = zF.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();

            if (entry.getName().contains("csv")) {
                parseCsvTest(zF.getInputStream(entry));
            } else if (entry.getName().contains("xls")) {
                parseXlsTest(zF.getInputStream(entry));
            } else if (entry.getName().contains("pdf")) {
                parsePdfTest(zF.getInputStream(entry));
            }
        }
    }

    void parseCsvTest(InputStream file) throws Exception {
        try (CSVReader reader = new CSVReader(new InputStreamReader(file));) {
            List<String[]> strA = reader.readAll();
            assertThat(strA.get(0)).contains("Username; Identifier;First name;Last name");
        }
    }

    void parseXlsTest(InputStream file) throws Exception {
        assertThat(new XLS(file).excel
                .getSheetAt(0)
                .getRow(0)
                .getCell(1)
                .getStringCellValue()).contains("First Name");
    }

    void parsePdfTest(InputStream file) throws Exception {
        assertThat(new PDF(file).text).contains("A Simple PDF File");
    }
}
