package com.youcash.uc.common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;


import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


/**
 * 配置文件工具类
 * @version 1.0
 * @date 2012-5-19
 */
public class PropsFileUtil {
	  private static String path="entity.properties";
	  private static Properties props = null;
	  static
	  {
		  try {
			  Resource  r =  new ClassPathResource(path);			  
			  props = new Properties();
			  props.load(r.getInputStream());
			} catch (Exception e) {
				throw new RuntimeException();
			} 
	  }
	  
	  public static void loadProp() {
		  try {
			  Resource  r =  new ClassPathResource(path);	
			  props.load(r.getInputStream());
			} catch (Exception e) {
				throw new RuntimeException();
			} 
	  }
	  
		public static String getValue(String key) {
//			 loadProp();
		     return props.getProperty(key);	
		}
		
		public static Properties  getAllInfo() {
//			loadProp();
			return props;
		}

		@SuppressWarnings("deprecation")
		public static void setProperty(String key,String value)	{
			if(StringUtils.isNotEmpty((String)props.get(key))) {
				props.setProperty(key, value);
			}
			else{
				props.put(key, value);
			}
			Resource resource=new ClassPathResource(path);
			//String filePath=ServletActionContext.getRequest().getRealPath("sl3000Config.properties");
			OutputStream ops=null;
			try {
				ops = new FileOutputStream(resource.getFile());
				props.save(ops, null);
				loadProp();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(ops!=null){
					 try {
						 ops.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} 
			}
		   
			
		}
}
