import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static utils.PdfUtils.savePdf;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class DownloadTests {
    @Test
    void testDownloadHttpClient() {
        String endpoint = "https://alfabank.servicecdn.ru/site-upload/67/dd/356/zayavlenie-IZK.pdf";
        String fileName = "downloaded.pdf";

        Response response =
                given().
                        when().
                        get(endpoint).
                        then().
                        contentType("application/pdf").
                        statusCode(200).
                        extract().response();
        savePdf(response, fileName);
        // Проверяем, что файл был скачан успешно
        File downloadedFile = new File(fileName);
        assertThat(downloadedFile).exists();
    }
}
