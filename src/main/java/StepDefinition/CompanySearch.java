package StepDefinition;


import io.cucumber.java.AfterAll;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    @And("the user fetches and store the details in the Excel sheet {string}")
    public void the_User_Fetches_And_Store_The_Details_In_The_Excel_Sheet(String idx) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = currentDateTime.format(formatter);

        String sheetName = "DataSheet";

        XSSFSheet sheet = workbook.getSheet(sheetName);

        if (sheet == null) {
            sheet = workbook.createSheet(sheetName);
        }
        int number = Integer.parseInt(idx);
        int rowIdx=number;

        String companyName = driver.findElement(By.xpath("//span[@id='mainContent_ltrlCompName']")).getText();
        List<WebElement> list = driver.findElements(By.xpath("//p[@id='mainContent_compinfoId']"));


        for (WebElement element : list) {
            String parentText = element.getText();
            List<WebElement> childElements = element.findElements(By.xpath(".//span"));
            for (WebElement childElement : childElements) {
                parentText = parentText.replace(childElement.getText(), "");
            }
            System.out.println(parentText.trim());

        }
        String CurrentNSE = driver.findElement(By.xpath("(//span[@class='Number'])[1]")).getText();

        String companyHeader = "Company";
        String companyValue =  companyName;


        Row companyHeaderRow = sheet.createRow(rowIdx);
        Cell companyHeaderCell = companyHeaderRow.createCell(0);
        companyHeaderCell.setCellValue(companyHeader);

        Row relianceValueRow = sheet.createRow(rowIdx+1);
        Cell relianceValueCell = relianceValueRow.createCell(0);
        relianceValueCell.setCellValue(companyValue);

        Cell currentNSEHeaderCell = companyHeaderRow.createCell(1);
        currentNSEHeaderCell.setCellValue("Current NSE");

        Row valueRow = sheet.getRow( rowIdx+1);
        if (valueRow == null) {
            valueRow = sheet.createRow( rowIdx+1);
        }

        Cell valueCell = valueRow.createCell(1);
        valueCell.setCellValue(CurrentNSE);


        Cell priceSummary = companyHeaderRow.createCell(4);
        priceSummary.setCellValue("Price Summary");

        Row valueRow1 = sheet.getRow( rowIdx+1);
        if (valueRow1 == null) {
            valueRow1 = sheet.createRow( rowIdx+1);
        }

        List<WebElement> list1 = driver.findElements(By.xpath("//div[@id='mainContent_pricesummary']//*[@class=\"col-6 col-md-3 compess\"]"));
        for(int i=0;i<list1.size();i++) {
            String data = list1.get(i).getText();
            Cell valueCell1 = valueRow1.createCell(i+2);
            valueCell1.setCellValue(data);
        }



        Cell finchart = companyHeaderRow.createCell(7);
        finchart.setCellValue("FinStar");

        Row valueRow2 = sheet.getRow( rowIdx+1);
        if (valueRow2 == null) {
            valueRow2 = sheet.createRow( rowIdx+1);
        }

        List<WebElement> list2 = driver.findElements(By.xpath("//div[@id='ratingcollapse']//h6"));
        for(int i=0;i<list2.size();i++) {
            String dataFin = list2.get(i).getText();
            Cell valueCell2 = valueRow2.createCell(i + 6);
            valueCell2.setCellValue(dataFin);
        }

        Cell peHeaderCell = companyHeaderRow.createCell(10);
        peHeaderCell.setCellValue("P/E");

        Row valueRow3 = sheet.getRow( rowIdx+1);
        if (valueRow3 == null) {
            valueRow3 = sheet.createRow( rowIdx+1);
        }
        String PE = driver.findElement(By.xpath("(//div[@class='col-6 col-md-4 compess'])[4]//p")).getText();
        Cell valueCell3 = valueRow3.createCell(10);
        valueCell3.setCellValue(PE);

        Cell divYieldCell = companyHeaderRow.createCell(11);
        divYieldCell.setCellValue("Div.Yield");



        Row valueRow4 = sheet.getRow( rowIdx+1);
        if (valueRow4 == null) {
            valueRow4 = sheet.createRow( rowIdx+1);
        }
        String DivYield = driver.findElement(By.xpath("(//div[@class='col-6 col-md-4 compess'])[7]//p")).getText();
        Cell valueCell4 = valueRow4.createCell(11);
        valueCell4.setCellValue(DivYield);

        Cell priceChart = companyHeaderRow.createCell(16);
        priceChart.setCellValue("Price chart");

        Row valueRow5 = sheet.getRow( rowIdx+1);
        if (valueRow5 == null) {
            valueRow5 = sheet.createRow( rowIdx+1);
        }

        for (int i = 1; i<8; i++) {
            String xpathExpression = "(//span[@class='float-right showprice']//a)[" + (i + 1) + "]";
            WebElement tenure = driver.findElement(By.xpath(xpathExpression));
            tenure.click();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Row valueRow6 = sheet.getRow( rowIdx+1);
            if (valueRow6 == null) {
                valueRow6 = sheet.createRow( rowIdx+1);
            }
            Cell valueCell5 = valueRow6.createCell(13+i-1);
            valueCell5.setCellValue(tenure.getText());

            String getData = driver.findElement(By.xpath("//span[@class='float-left mb-4 d-block d-md-inline-block ml-3']")).getText();


            Row valueRow7 = sheet.getRow( rowIdx+2);
            if (valueRow7 == null) {
                valueRow7 = sheet.createRow( rowIdx+2);
            }
            Cell valueCell6 = valueRow7.createCell(13+i-1);
            valueCell6.setCellValue(getData);


        }

        Cell salesGrowth = companyHeaderRow.createCell(21);
        salesGrowth.setCellValue("Sales Growth");

        Cell profitGrowth = companyHeaderRow.createCell(24);
        profitGrowth.setCellValue("Profit Growth");



        Row valueRow8 = sheet.getRow(rowIdx+1);
        if (valueRow8 == null) {
            valueRow8 = sheet.createRow(rowIdx+1);
        }

        List<WebElement> list3 = driver.findElements(By.xpath("//div[@class='w-100']//div"));


        for (int i = 0; i <=5; i++) {
            String salesData = list3.get(i).getText();
            Cell valueCell7 = valueRow8.createCell(i + 20);
            valueCell7.setCellValue(salesData);
        }



        Cell timeStampCell = companyHeaderRow.createCell(27);
        timeStampCell.setCellValue("Timestamp");

        Row timeRow = sheet.getRow( rowIdx+1);
        if (timeRow == null) {
            timeRow = sheet.createRow( rowIdx+1);
        }

        Cell timeCell = timeRow.createCell(27);
        timeCell.setCellValue(timestamp);



    }

    @And("convert excel data to json array")
    public void convert_Excel_Data_To_JsonArray() {
        String filePath = "C:\\Users\\Shivam.Roy\\SeleniumAssignment\\data.xlsx";

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            JSONArray jsonArray = new JSONArray();

            int lastRowNum = sheet.getLastRowNum();
            System.out.print(lastRowNum);
            Row row = sheet.getRow(1);
            Row row1 = sheet.getRow(2);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Company", row.getCell(0).getStringCellValue());
            jsonObject.put("Current NSE", row.getCell(1).getStringCellValue());
            JSONArray priceSummaryArray = new JSONArray();
            for(int i=2;i<=5;i++) {
                String priceSummaryValue = row.getCell(i).getStringCellValue();
                System.out.println( priceSummaryValue);
                priceSummaryArray.put(priceSummaryValue.trim());
            }
            jsonObject.put("Price Summary", priceSummaryArray);
            JSONArray finStar = new JSONArray();
            for(int i=6;i<=9;i++) {
                String finStarValue = row.getCell(i).getStringCellValue();
                System.out.println( finStarValue);
                finStar.put(finStarValue.trim());
            }
            jsonObject.put("FinStar", finStar);
            jsonObject.put("P/E", row.getCell(10).getStringCellValue());
            jsonObject.put(" Div.Yield", row.getCell(11).getStringCellValue());

            JSONArray priceChart = new JSONArray();
            for(int i=13;i<=18;i++) {
                String priceChartValue = row.getCell(i).getStringCellValue()+"-"+row1.getCell(i).getStringCellValue();
                System.out.println( priceChartValue);
                priceChart.put(priceChartValue.trim());
            }
            jsonObject.put("PriceChart", priceChart);
            JSONArray salesGrowth = new JSONArray();
            for(int i=20;i<=22;i++) {
                String saleGrowthValue = row.getCell(i).getStringCellValue();
                System.out.println( saleGrowthValue);
                salesGrowth.put(saleGrowthValue.trim());
            }
            jsonObject.put("Sales Growth",salesGrowth);
            JSONArray profitGrowth = new JSONArray();
            for(int i=23;i<=25;i++) {
                String profitGrowthValue = row.getCell(i).getStringCellValue();
                System.out.println( profitGrowthValue);
                profitGrowth.put(profitGrowthValue.trim());
            }
            jsonObject.put("Profit Growth",profitGrowth);
            String timeStamp=row.getCell(27).getStringCellValue();
            jsonObject.put("Timestamp",timeStamp);

            jsonArray.put(jsonObject);

            System.out.println(jsonArray.toString());

            JSONArray jsonArray1 = new JSONArray();
            Row row4 = sheet.getRow(5);
            Row row5 = sheet.getRow(6);
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("Company", row4.getCell(0).getStringCellValue());
            jsonObject1.put("Current NSE", row4.getCell(1).getStringCellValue());
            JSONArray priceSummaryArray1 = new JSONArray();
            for(int i=2;i<=5;i++) {
                String priceSummaryValue1 = row4.getCell(i).getStringCellValue();
                System.out.println( priceSummaryValue1);
                priceSummaryArray1.put(priceSummaryValue1.trim());
            }
            jsonObject1.put("Price Summary", priceSummaryArray1);
            JSONArray finStar1 = new JSONArray();
            for(int i=6;i<=9;i++) {
                String finStarValue = row4.getCell(i).getStringCellValue();
                System.out.println( finStarValue);
                finStar1.put(finStarValue.trim());
            }
            jsonObject1.put("FinStar", finStar1);

            jsonObject1.put("P/E", row4.getCell(10).getStringCellValue());
            jsonObject1.put(" Div.Yield", row4.getCell(11).getStringCellValue());

            JSONArray priceChart1 = new JSONArray();
            for(int i=13;i<=18;i++) {
                String priceChartValue = row4.getCell(i).getStringCellValue()+"-"+row5.getCell(i).getStringCellValue();
                System.out.println( priceChartValue);
                priceChart1.put(priceChartValue.trim());
            }
            jsonObject1.put("PriceChart", priceChart1);

            JSONArray salesGrowth1 = new JSONArray();
            for(int i=20;i<=22;i++) {
                String saleGrowthValue1 = row4.getCell(i).getStringCellValue();
                System.out.println( saleGrowthValue1);
                salesGrowth1.put(saleGrowthValue1.trim());
            }
            jsonObject1.put("Sales Growth",salesGrowth1);
            JSONArray profitGrowth1 = new JSONArray();
            for(int i=23;i<=25;i++) {
                String profitGrowthValue1 = row4.getCell(i).getStringCellValue();
                System.out.println( profitGrowthValue1);
                profitGrowth1.put(profitGrowthValue1.trim());
            }
            jsonObject1.put("Profit Growth",profitGrowth1);
            String timeStamp1=row4.getCell(27).getStringCellValue();
            jsonObject1.put("Timestamp",timeStamp1);


            jsonArray1.put(jsonObject1);
            System.out.println(jsonArray1.toString());



            try (FileOutputStream fos = new FileOutputStream("C:\\Users\\Shivam.Roy\\SeleniumAssignment\\file.json")) {
                JSONArray combinedArray = new JSONArray();
                combinedArray.putAll(jsonArray);
                combinedArray.putAll(jsonArray1);

                fos.write(combinedArray.toString().getBytes());

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void before_or_after_all()
    {

        try (FileOutputStream fileOut = new FileOutputStream("data.xlsx"))
        { workbook.write(fileOut); }
        catch (IOException e) { e.printStackTrace(); }
        driver.close();

    }


}
