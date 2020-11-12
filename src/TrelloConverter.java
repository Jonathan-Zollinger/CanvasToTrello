import org.openqa.selenium.WebDriver;

public class TrelloConverter {

    private WebDriver driver;

    public TrelloConverter(String inputFile, String trelloSite){
        driver = Tools.getHeadlessDriverSpecifyDownloadDirectory(
                "geckodriver-v0.27.0-win64.exe",
                "",
                "application/x-gzip");
    }

    //private

}
