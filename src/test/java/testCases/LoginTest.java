package testCases;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import utilities.ExcelReader;

public class LoginTest extends BaseTest{

	@Test(dataProvider="dp")
	public void loginTest(String username,String password) {
		type("username_ID", username);
		click("nextBtn_XPATH");
		
	}
	
	
	

	


@DataProvider(name="dp")
public Object[][]getData() {
	String sheetName="loginTest";
	ExcelReader excel=new ExcelReader(".\\src\\test\\resources\\excel\\loginTest.xlsx");
	
	
	int rowCount=excel.getRowCount(sheetName);
	int colCount=excel.getColumnCount(sheetName);
	System.out.println(excel.getCellData(sheetName, 0, 2));
	System.out.println(excel.getCellData(sheetName, "username", 3));
	System.out.println("row count"+rowCount+"col count"+colCount);

	//manually add data 
	Object[][] data=new Object[rowCount-1][colCount];
	

	
	for(int rows=2;rows<=rowCount;rows++) {
		
		for(int cols=0; cols<colCount; cols++) {
			
			data[rows-2][cols]=excel.getCellData(sheetName, cols, rows);
			
		}
		
	}
	return data;
}

}
