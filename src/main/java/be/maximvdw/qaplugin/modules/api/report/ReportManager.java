package be.maximvdw.qaplugin.modules.api.report;

import be.maximvdw.qaplugin.modules.api.NamelessAPI;
import be.maximvdw.qaplugin.modules.api.http.HttpMethod;
import be.maximvdw.qaplugin.modules.api.http.HttpRequest;
import be.maximvdw.qaplugin.modules.api.http.HttpResponse;
import be.maximvdw.qaplugin.modules.api.report.exceptions.OpenReportException;
import be.maximvdw.qaplugin.modules.api.user.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * ReportManager
 *
 * @author Maxim Van de Wynckel
 * @version 1.0
 */
public class ReportManager {
    private NamelessAPI api = null;

    public ReportManager(NamelessAPI api) {
        this.api = api;
    }

    /**
     * Create a report on the site
     *
     * @param reporter         Reporter creating the report
     * @param reportedUsername user to report
     * @param reportedUUID     uuid to report
     * @param content          content of the report
     * @return success
     */
    public boolean createReport(User reporter, String reportedUsername, String reportedUUID, String content) throws OpenReportException {
        if (reporter == null) {
            return false;
        }
        String url = api.getAPIURL() + "createReport";
        HttpResponse response = null;
        try {
            response = new HttpRequest(url)
                    .method(HttpMethod.POST)
                    .post("reporter_uuid", reporter.getUUID())
                    .post("reported_uuid", reportedUUID)
                    .post("reported_username", reportedUsername)
                    .post("content", content)
                    .execute();
            JSONParser parser = new JSONParser();
            JSONObject jsonResponse = (JSONObject) parser.parse(response.getSource());
            if (jsonResponse.containsKey("error")) {
                if (((Boolean) jsonResponse.get("error"))) {
                    if (String.valueOf(jsonResponse.get("message")).equals("You still have a report open regarding that player.")){
                        throw new OpenReportException();
                    }
                    return false;
                }
            }
            if (jsonResponse.containsKey("success")) {
                if (((Boolean) jsonResponse.get("success"))) {
                    return true;
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
