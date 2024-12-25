package com.ibm.Util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
//import java.util.Properties;
import org.apache.commons.dbutils.DbUtils;
import com.ibm.ConnUtil.DBConnectionUtil;


public class GetFileNetReconCEData {
	private static ReadProperties props = new ReadProperties();
	//static String Const_Path = Paths.get(props.readPropertiesFile().getProperty("Shared_Path")).toString();
	//ReadProperties properties = new ReadProperties();
	//private static String Const_Path="/opt/IBM/FileNet/AE/Router/Automation/FileNetReconCE";
	DBConnectionUtil util = new DBConnectionUtil();

	public void fetchData1(String Const_Path) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MMM/yyyy");
		Date date = new Date();

		String currentDateSimple=dateFormat.format(date);
		//String currentDateQuery=dateFormat1.format(date);
		System.out.println("currentDateSimple : "+currentDateSimple);
		//System.out.println("currentDateQuery : "+currentDateQuery); //No Use (today date in month) less data

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, -1);

		Date newDate1 = c.getTime();
		String prevDateQuery=dateFormat1.format(newDate1);
		String prevDateSimple=dateFormat.format(newDate1);
		System.out.println("prevDateQuery : "+prevDateQuery);
		System.out.println("prevDateSimple : "+prevDateSimple);

		String[] str_split = prevDateSimple.split("/");
		String str = str_split[0]+str_split[1]+str_split[2];
		System.out.println("str(prevDateSimple) : "+str);

		Calendar d = Calendar.getInstance();
		d.setTime(date);
		d.add(Calendar.DATE, -2);
		Date newDate2 = d.getTime();
		String prevToPrevDateQuery=dateFormat1.format(newDate2);
		String prevToPrevDateSimple=dateFormat.format(newDate2);
		System.out.println("prevToPrevDateQuery : "+prevToPrevDateQuery);
		System.out.println("prevToPrevDateSimple : "+prevToPrevDateSimple);

		String[] str_split1 = prevToPrevDateSimple.split("/");
		String str1 = str_split1[0]+str_split1[1]+str_split1[2];
		System.out.println("str1(prevToPrevDateSimple) : "+str1);

		//change from currentDateQuery to prevDateQuery
		String query_west = "select u5f_dcn, from_tz( cast(CREATE_DATE as timestamp), 'GMT' ) at time zone 'US/Eastern','MM/DD/YYYY HH24:MI:SS', u8b_receivefaxnum, UE3E8_SOURCEDOCUMENTID, U9E_F_PAGES from WESTCLAIMS.docversion where object_class_id='3B451C4C08178344A310BF81B70AD703' and trunc(CREATE_DATE) > =TO_DATE('"+prevDateQuery+" 00:00:00', 'dd/mon/yyyy HH24:MI:SS') and U74_SRCID='RAPIDCAP' order by CREATE_DATE";
		//change from currentDateQuery to prevDateQuery
		String query_east = "select u5f_dcn, from_tz( cast(CREATE_DATE as timestamp), 'GMT' ) at time zone 'US/Eastern','MM/DD/YYYY HH24:MI:SS', u8b_receivefaxnum, UE3E8_SOURCEDOCUMENTID, U9E_F_PAGES from EASTCLAIMS.docversion where (object_class_id='DB2A6E7D10BCA14D91B399851544BF8A' or object_class_id='BF3CB24412BC3B429D6233138B0AC668') and trunc(CREATE_DATE) >= TO_DATE('"+prevDateQuery+" 00:00:00', 'dd/mon/yyyy HH24:MI:SS') and U74_SRCID='RAPIDCAP' order by CREATE_DATE";
		//change from currentDateQuery to prevDateQuery
		String query_central1 = "select U64_DCN, from_tz( cast(CREATE_DATE as timestamp), 'GMT' ) at time zone 'US/Eastern','MM/DD/YYYY HH24:MI:SS', U90_RECEIVEFAXNUM , UE3E8_SOURCEDOCUMENTID, UA4_F_PAGES from CENTRALCLAIMS.docversion where (object_class_id='E59A9B05A210FC43826EBD2F834FA956' ) and trunc(CREATE_DATE) >= TO_DATE('"+prevDateQuery+" 00:00:00', 'dd/mon/yyyy HH24:MI:SS') and U79_SRCID='RAPIDCAP' order by CREATE_DATE";
		//change from prevDateQuery to prevToPrevDateQuery
		String query_central2 = "select U64_DCN, from_tz( cast(CREATE_DATE as timestamp), 'GMT' ) at time zone 'US/Eastern','MM/DD/YYYY HH24:MI:SS', U90_RECEIVEFAXNUM , UE3E8_SOURCEDOCUMENTID, UA4_F_PAGES from CENTRALCLAIMS.docversion where (object_class_id='E59A9B05A210FC43826EBD2F834FA956' ) and trunc(CREATE_DATE) >= TO_DATE('"+prevToPrevDateQuery+" 00:00:00', 'dd/mon/yyyy HH24:MI:SS') and U79_SRCID='RAPIDCAP' order by CREATE_DATE";
		//change from currentDateQuery to prevDateQuery
		//String query_fep = "select u5f_dcn, from_tz( cast(CREATE_DATE as timestamp), 'GMT' ) at time zone 'US/Eastern','MM/DD/YYYY HH24:MI:SS', u8b_receivefaxnum, UE3E8_SOURCEDOCUMENTID from FEP.docversion where object_class_id='04E5DA9031B21C42996BFFD539544778' and trunc(CREATE_DATE) > =TO_DATE('"+prevDateQuery+" 00:00:00', 'dd/mon/yyyy HH24:MI:SS') and U74_SRCID='RAPIDCAP' order by CREATE_DATE";

		//change from currentDateQuery to prevDateQuery (Query for FHPS)
		String query_fep_FHPS = "select UCED8_DCN, from_tz( cast(CREATE_DATE as timestamp), 'GMT' ) at time zone 'US/Eastern','MM/DD/YYYY HH24:MI:SS', ucce8_receivefaxnum, UE3E8_SOURCEDOCUMENTID, UE156_F_PAGES from FEP.docversion where object_class_id='04E5DA9031B21C42996BFFD539544778' and trunc(CREATE_DATE) > =TO_DATE('"+prevDateQuery+" 00:00:00', 'dd/mon/yyyy HH24:MI:SS') and UD798_SRCID='RAPIDCAP' order by CREATE_DATE";

		try {
			//prop = properties.readPropertiesFile();
			
			java.sql.Connection conn = util.dbConnectionData();
			Statement statement = conn.createStatement();			

			//1st Excel Data
			//String query1 =prop.getProperty("query1");
			ResultSet result1 = statement.executeQuery(query_west);
			ExcelFormation.ObjectStoreData(result1,"west",str,Const_Path);
			System.out.println("WestClaims Report Done");

			//2nd Excel Data
			//String query2 =prop.getProperty("query2");
			ResultSet result2 = statement.executeQuery(query_central1);
			ExcelFormation.ObjectStoreData(result2,"central1",str,Const_Path);
			System.out.println("CentralClaims1 Report Done");

			//3rd Excel Data
			//String query3 =prop.getProperty("query3");
			ResultSet result3 = statement.executeQuery(query_east);
			ExcelFormation.ObjectStoreData(result3,"east",str,Const_Path);
			System.out.println("EastClaims Report Done");

			//4th Excel Data
			//String query2 =prop.getProperty("query2");
			ResultSet result4 = statement.executeQuery(query_central2);
			ExcelFormation.ObjectStoreData(result4,"central2",str1,Const_Path);
			System.out.println("CentralClaims2 Report Done");

			if (conn != null) {
				DbUtils.close(conn);
			}

			java.sql.Connection conn1 = util.dbConnectionDataFHPS();
			Statement statement1 = conn1.createStatement();

			//5th Excel Data
			ResultSet result5 = statement1.executeQuery(query_fep_FHPS);
			ExcelFormation.ObjectStoreData(result5,"fep",str,Const_Path);
			System.out.println("FHPS Report Done");

			EmailFileNetReconCE email = new EmailFileNetReconCE();
			email.EmailNotification(currentDateSimple,str,str1);
			statement1.close();
			if (conn1 != null) {
				DbUtils.close(conn1);
			}

		} catch (Exception e) {

		}
	}

	public static void main(String[] args)
			throws KeyManagementException, NoSuchAlgorithmException, IOException , NullPointerException {
		//String sourcePath = "\\\\va10vnas007b\\ECM_Prod\\FileNetReconCE";
		//String targetPath = "\\\\va10vnas007b\\ECM_Prod\\FileNetReconCE\\Archive";
		//String Const_Path1 = Paths.get(props.readPropertiesFile().getProperty("Shared_Path")).toString();
		//System.out.println("Const_Path1 : "+Const_Path1);
		//ReadProperties props = new ReadProperties();
		String Const_Path = props.readPropertiesFile().getProperty("Shared_Path");
		System.out.println(Const_Path);
		//String currentDirectory = System.getProperty("user.dir");
		//String Const_Path="/opt/IBM/FileNet/AE/Router/Automation/FileNetReconCE";
		
		String sourcePath = Const_Path;
		System.out.println("sourcePath : "+sourcePath);
		String targetPath = Const_Path + "/Archive";
		System.out.println("targetPath : "+targetPath);
		File allFileList = new File(sourcePath);
		if(allFileList.exists()) {
			System.out.println("Inside File Exist");
			File[] content =allFileList.listFiles();
			for(int i=0;i<content.length;i++) {
				System.out.println("Files Present"+i);
				if(content[i].getName().contains("xlsx")) {
					Files.move(content[i].toPath(), (new File(targetPath + "/" + content[i].getName())).toPath(), StandardCopyOption.REPLACE_EXISTING);
					System.out.println(content[i].getName()+" file moved to Archive folder");
				}
			}
		}
		GetFileNetReconCEData sfnd = new GetFileNetReconCEData();
		sfnd.fetchData1(Const_Path);
		
		System.out.println("Completed");
		System.gc();
	}
}
