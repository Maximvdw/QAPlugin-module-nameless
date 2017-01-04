package be.maximvdw.qaplugin.modules.api;

import be.maximvdw.qaplugin.modules.api.report.ReportManager;
import be.maximvdw.qaplugin.modules.api.user.UserManager;

/**
 * NamelessAPI
 *
 * @author Maxim Van de Wynckel
 * @version 1.0
 */
public class NamelessAPI {
    private String url = "";
    private String apiKey = "";
    private int version = 1;

    private UserManager userManager = null;
    private ReportManager reportManager = null;

    /**
     * Initialize the API
     *
     * @param url    Site url
     * @param apiKey API key
     */
    public NamelessAPI(String url, String apiKey) {
        setSiteURL(url);
        setAPIKey(apiKey);
    }

    /**
     * Get the site URL
     *
     * @return site url
     */
    public String getSiteURL() {
        return url;
    }

    /**
     * Set the site URL
     *
     * @param url site url
     */
    public void setSiteURL(String url) {
        if (!url.endsWith("/")) {
            url += "/";
        }
        if (!url.startsWith("http://") && !url.startsWith("https://")){
            url = "http://" + url;
        }
        this.url = url;
    }

    /**
     * Get API key
     *
     * @return API key
     */
    public String getAPIKey() {
        return apiKey;
    }

    /**
     * Set API key
     *
     * @param apiKey API key
     */
    public void setAPIKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Get API version
     *
     * @return API version
     */
    public int getVersion() {
        return version;
    }

    /**
     * Get API URL
     *
     * @return API Url
     */
    public String getAPIURL() {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(getSiteURL());
        urlBuilder.append("api/");
        urlBuilder.append("v").append(getVersion());
        urlBuilder.append("/").append(getAPIKey());
        urlBuilder.append("/");
        return urlBuilder.toString();
    }

    /**
     * Get user manager
     *
     * @return user manager
     */
    public UserManager getUserManager() {
        if (userManager == null) {
            userManager = new UserManager(this);
        }
        return userManager;
    }

    /**
     * Get report manager
     *
     * @return report manager
     */
    public ReportManager getReportManager() {
        if (reportManager == null) {
            reportManager = new ReportManager(this);
        }
        return reportManager;
    }
}
