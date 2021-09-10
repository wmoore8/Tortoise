import java.io.*;

import org.apache.poi.xssf.usermodel.*;
import com.google.gson.*;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class InventoryExcelReadIn {

    //Data
    String inventoryExcelFile = "resources/Inventory.xlsx";

    //Constructor
    public InventoryExcelReadIn() {}

    //Methods
    public String getLowStockCandy() {
        ArrayList<Candy> lowStockCandyList = new ArrayList<>();

        try {
            File file = new File(inventoryExcelFile);
            FileInputStream fis = new FileInputStream(file);

            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet invSheet = wb.getSheetAt(0);

            //'i' starts at 1 to skip top line of excel file
            for (int i = 1; i < invSheet.getLastRowNum()+1; i++) {
                XSSFRow currentRow = invSheet.getRow(i);

                String candyName = currentRow.getCell(0).getStringCellValue();
                double candyStock = currentRow.getCell(1).getNumericCellValue();
                double candyCapacity = currentRow.getCell(2).getNumericCellValue();
                double candySKU = currentRow.getCell(3).getNumericCellValue();

                //Calculate if candy is low on stock
                double stockPercentage =
                        currentRow.getCell(1).getNumericCellValue() / currentRow.getCell(2).getNumericCellValue();

                if (stockPercentage < 0.25) {
                    //Create candy object only for those with low stock, use Candy object for gson -> json formatting
                    Candy candy = new Candy(candyName, candyStock, candyCapacity, candySKU);
                    lowStockCandyList.add(candy);

                }
            }

        } catch (IOException e) {
            System.out.println("COULD NOT READ FILE");
            e.printStackTrace();
        }

        return JsonUtil.toJson(lowStockCandyList);
    }

    public String getRestockCost(String jsonCandyRequest) {

        double totalCandyRestockCost = 0;
        DistributorExcelReadIn distributorFile = new DistributorExcelReadIn();

        JsonArray jsonArray = JsonUtil.deserializeJson(jsonCandyRequest);

        for (JsonElement e : jsonArray) {
            String candyName = e.getAsJsonObject().get("name").getAsString();
            double restockAmount = e.getAsJsonObject().get("restockAmount").getAsDouble();

            double lowestCost = distributorFile.getCandyAtLowestCost(candyName);
            double restockCost = lowestCost * restockAmount;

            totalCandyRestockCost += restockCost;

        }

        //Format to 2 decimal points
        DecimalFormat df2 = new DecimalFormat("#.##");

        return JsonUtil.toJson(df2.format(totalCandyRestockCost));

    }

}