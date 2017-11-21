package mypack;

import static org.junit.Assert.*;

import java.nio.file.Files;

import org.junit.Before;
import org.junit.Test;

public class CsvTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test(expected = NullPointerException.class)
	public void testCsv() throws DataException {
		Csv Ariel=new Csv("C:\\Users\\Rachel Plaksin\\Desktop\\tests");
		
	}
	@Test(expected = DataException.class)
	public void testCsv1() throws DataException {
		Csv Ariel=new Csv("C:\\Users\\Rachel Plaksin\\Desktop\\");
		
	}
// tested in the csv() builder.
	@Test(expected = NullPointerException.class)
	public void testSetCsv() throws DataException {
		Csv test=new Csv();
		test.getFiles().add("C:\\Users\\Rachel Plaksin\\Desktop\\tests");
		test.setCsv();
	}

	@Test(expected = NullPointerException.class)
	public void testGetFiles() throws DataException {
		Csv test=new Csv();
		test.getFiles().add("C:\\Users\\Rachel Plaksin\\Desktop\\PCT THEME");	}
	// test mannualy
	@Test
	public void testWritescan() throws DataException {
		Csv Ariel=new Csv("C:\\Users\\Rachel Plaksin\\Desktop\\tests");
		Ariel.writescan("take2");
		Tokml kml=new Tokml("take2.csv");	}

}
