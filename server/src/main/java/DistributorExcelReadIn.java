import java.io.*;

import org.apache.poi.xssf.usermodel.*;

import java.util.Objects;

public class DistributorExcelReadIn {

    //Data
    String distributorExcelFile = "resources/Distributors.xlsx";

    //Constructor
    public DistributorExcelReadIn() {}

    //Methods
    public double getCandyAtLowestCost(String candyName) {

        double lowestCost = Integer.MAX_VALUE;

        try {
            File distributorFile = new File(distributorExcelFile);
            FileInputStream fis = new FileInputStream(distributorFile);

            XSSFWorkbook wb = new XSSFWorkbook(fis);

            for (int i = 0; i < wb.getNumberOfSheets(); i++) {

                for (int j = 1; j < wb.getSheetAt(i).getLastRowNum()+1; j++) {

                    //getCell(0) specifies the name of candy
                    String currentCellCandyName = wb.getSheetAt(i).getRow(j).getCell(0).getStringCellValue();

                    if (Objects.equals(candyName, currentCellCandyName)) {
                        double currentCost = wb.getSheetAt(i).getRow(j).getCell(2).getNumericCellValue();
                        if (currentCost < lowestCost) lowestCost = currentCost;
                    }
                }
            }



        } catch (IOException e) {
            System.out.println("COULD NOT READ FILE");
            e.printStackTrace();
        }

        return lowestCost;
    }

}
