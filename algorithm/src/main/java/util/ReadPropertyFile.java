package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import enums.Browser;

public class ReadPropertyFile {
	Properties prop = null;
	protected InputStream stream = ReadPropertyFile.class.getClassLoader().getResourceAsStream("config/config.properties");

	public ReadPropertyFile(){
		prop = new Properties();
		try {
			prop.load(stream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public Browser Browser(){
		Browser _browser = Browser.Undefined;
		String browser = prop.getProperty("Browser").toLowerCase().trim();
		switch(browser){
			case "android":
			{
				_browser = Browser.Android;
				break;
			}
			case "chrome":
			{
				_browser = Browser.Chrome;
				break;
			}
			case "firefox":
			{
				_browser = Browser.Firefox;
				break;
			}
			case "internetexplorer":
			{
				_browser = Browser.InternetExplorer;
				break;
			}
			default:
			{
				_browser = Browser.Undefined;
				break;
			}
		}
		return _browser;
	}

	public String UserName(){
		return prop.getProperty("UserName");
	}
	
	public String DownloadPath(){
		return prop.getProperty("DownloadPath");
	}

}
