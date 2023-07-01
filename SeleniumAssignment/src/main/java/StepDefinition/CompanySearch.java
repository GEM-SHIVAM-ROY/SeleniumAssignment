package StepDefinition;


import io.cucumber.java.After;
import io.cucumber.java.AfterAll;

import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class CompanySearch {
    static WebDriver driver;
    static XSSFWorkbook workbook = new XSSFWorkbook();
    @Before
    public void setup(){
        driver=DriverClass.setup();

    }
    @Given("the user is on the Finology Ticker website")
    public void the_user_is_on_the_finology_ticker_website() {
        driver.get("https://ticker.finology.in/");

    }
    @When("the user clicks on the search box")
    public void the_user_clicks_on_the_search_box() {
        driver.findElement(By.xpath("//input[@id='txtSearchComp']")).click();
    }
    @When("the user enters {string} in the search box")
    public void the_user_enters_in_the_search_box(String string) {
        driver.findElement(By.xpath("//input[@id='txtSearchComp']")).sendKeys(string);
    }
    @Then("the user should see the search results for {string}")
    public void the_user_should_see_the_search_results_for(String string) {
        String company=string.toUpperCase();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebElement companylink=driver.findElement(By.xpath("//span[contains(@class, 'badge badge-primary') and contains(text(),'"+company+"')]"));
        companylink.click();


    }
    @And("the user fetches and store the details in the Excel sheet")
    public void the_User_Fetches_And_Store_The_Details_In_The_Excel_Sheet() {

        int rowIdx=0;

        String companyName = driver.findElement(By.xpath("//span[@id='mainContent_ltrlCompName']")).getText();
        List<WebElement> list = driver.findElements(By.xpath("//p[@id='mainContent_compinfoId']"));

        XSSFSheet sheet = workbook.createSheet(companyName);

        for (WebElement element : list) {
            String parentText = element.getText();
            List<WebElement> childElements = element.findElements(By.xpath(".//span"));
            for (WebElement childElement : childElements) {
                parentText = parentText.replace(childElement.getText(), "");
            }
            System.out.println(parentText.trim());

        }
        String CurrentNSE = driver.findElement(By.xpath("(//span[@class='Number'])[1]")).getText();

        Row row1 = sheet.createRow(rowIdx++);
        row1.createCell(0).setCellValue("Current NSE");
        row1.createCell(1).setCellValue(CurrentNSE);

        rowIdx++;

        Row priceSummary = sheet.createRow(rowIdx++);
        priceSummary.createCell(0).setCellValue("Price Summary");

        List<WebElement> list1 = driver.findElements(By.xpath("//div[@id='mainContent_pricesummary']//*[@class=\"col-6 col-md-3 compess\"]"));

        for (int i=0;i<list1.size();i++) {
            Row rowtemp = sheet.createRow(rowIdx++);



            String data = list1.get(i).getText();
            String [] priceSum=  data.split("\n");
            rowtemp.createCell(0).setCellValue(priceSum[0]);
            rowtemp.createCell(1).setCellValue(priceSum[1]);
        }

        rowIdx++;


        Row finChart = sheet.createRow(rowIdx++);
        finChart.createCell(0).setCellValue("FinStar");
        List<WebElement> list2 = driver.findElements(By.xpath("//div[@id='ratingcollapse']//h6"));
        System.out.print(list.size());
        for (int i=0;i<list2.size();i++) {
            Row rowtemp = sheet.createRow(rowIdx++);
            String dataTmp = list2.get(i).getText();
            String [] finStar=  dataTmp.split("\n");
            rowtemp.createCell(0).setCellValue(finStar[0]);
            rowtemp.createCell(1).setCellValue(finStar[1]);

        }

        rowIdx++;

        String PE = driver.findElement(By.xpath("(//div[@class='col-6 col-md-4 compess'])[4]")).getText();
        String DivYield = driver.findElement(By.xpath("(//div[@class='col-6 col-md-4 compess'])[7]")).getText();

        String [] pe = PE.split("\n");
        String [] divYeild = DivYield.split("\n");

        Row peRow = sheet.createRow(rowIdx++);
        peRow.createCell(0).setCellValue(pe[0]);
        peRow.createCell(1).setCellValue(pe[1]);

        Row divYeildRow = sheet.createRow(rowIdx++);
        divYeildRow.createCell(0).setCellValue(divYeild[0]);
        divYeildRow.createCell(1).setCellValue(divYeild[1]);
        rowIdx++;

        Row pchart = sheet.createRow(rowIdx++);
        pchart.createCell(0).setCellValue("Price Chart");

        for (int i = 1; i<8; i++) {
            String xpathExpression = "(//span[@class='float-right showprice']//a)[" + (i + 1) + "]";
            WebElement tenure = driver.findElement(By.xpath(xpathExpression));
            Row rowPriceChart = sheet.createRow(rowIdx++);
            tenure.click();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            rowPriceChart.createCell(0).setCellValue(tenure.getText());
            String getData = driver.findElement(By.xpath("//span[@class='float-left mb-4 d-block d-md-inline-block ml-3']")).getText();
            String [] value = getData.split("\n");

            rowPriceChart.createCell(1).setCellValue(value[0]);
            rowPriceChart.createCell(2).setCellValue(value[1]);

        }
        rowIdx++;

        List<WebElement> list3 = driver.findElements(By.xpath("//div[@class='w-100']//div"));
        Row salesGrowth = sheet.createRow(rowIdx++);
        salesGrowth.createCell(0).setCellValue("Sales Growth");
        for (int i = 0; i <= 2; i++) {
            WebElement data = list3.get(i);
            String mydata = data.getText();
            Row salesGrowth2 = sheet.createRow(rowIdx++);
            String [] salesGrow = mydata.split("\n");
            salesGrowth2.createCell(0).setCellValue(salesGrow[0]);
            salesGrowth2.createCell(1).setCellValue(salesGrow[1]);
        }
        for (int i = 3; i <= 5; i++) {
            WebElement data = list3.get(i);
            String mydata = data.getText();
            System.out.println(mydata);
        }

    }
    @AfterAll
    public static void before_or_after_all()
    {

        try (FileOutputStream fileOut = new FileOutputStream("data.xlsx"))
        { workbook.write(fileOut); }
        catch (IOException e) { e.printStackTrace(); }

    }
}
