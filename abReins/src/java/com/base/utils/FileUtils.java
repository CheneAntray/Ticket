package com.base.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


/**
 * 文件处理工具
 * @author xianqin-bill
 *
 */
public class FileUtils{
	private static Logger logger = LogUtils.getConsoleLogIns();
	/** 
	 * 删除单个文件 
	 * @param   sPath    被删除文件的文件名 
	 * @return 单个文件删除成功返回true，否则返回false 
	 */  
	public static boolean deleteFile(String sPath) {  
	    boolean flag = false;  
	    File file = new File(sPath);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;  
	}
	/**
	 * 获取路径是否存在,如果存在,则使用ArrayList<File>封装返回路径下所有文件实例,如果不存在,则返回null
	 * 同时可对文件名或文件扩展名进行过滤,
	 * @param filePath 文件路径
	 * @return filterName 过滤器描述如 *.sql 则返回当前目录下所有扩展名为sql的文件对象实例,example.txt 则返回文件名为example.txt的文件对象实例   *.*则返回所有文件
	 */
	public ArrayList<File> getFileNamesByFolder(String filePath,String filterName){
		ArrayList<File> fileArrayList = null;
		File fileIns = null;
		File filePathIns = new File(filePath);
		String insFileName = null;
		
		if (filePathIns.isDirectory()) {
			File[] fileArray = filePathIns.listFiles();
			int len = fileArray.length;
			fileArrayList = new ArrayList<File>();
			for (int i = 0; i < len; i++) {
				fileIns = fileArray[i];
				if(fileIns.isFile()){
					//过滤模式
					insFileName = fileIns.getName();
					if(fileNameIsFilter(insFileName,filterName)){
						fileArrayList.add(fileIns);
					}
				}
			}
		}
		return fileArrayList;
	}
	
	/**
	 * 文件名过滤器主调方法
	 * 只支持标准文件名以及标准规则过滤
	 * 文件名支持xxx.xxx
	 * 过滤规则支持 *.*  xxx.* *.xxx
	 * 过滤规则：
	 * *.* 则无过滤规则
	 * xxx.* 查找当前文件夹下所有文件名称包含xxx字符的文件
	 * *.xxx 查找当前文件夹下所有文件扩展名为XXX的文件
	 * @param fileNameStr 文件名
	 * @param filterName 过滤规则
	 * @return
	 */
	private boolean fileNameIsFilter(String fileNameStr,String filterName){
		String fileCheck[] = null;
		String filterFileName = null;
		String filterFileExpandedName = null;
		String fileName = null;
		String fileExpandedName = null;
		//过滤模式开启标志位
		//*.*为全部匹配模式，直接返回true
		if("*.*".equals(filterName)){
			return true;
		}
		if(filterName!=null && filterName.length()>0){
			//分解参数,使用'.'符号作为分隔符
			fileCheck = filterName.split(".");
			if(fileCheck.length>1){
				//一般只认 xxx.xxx格式的过滤参数
				filterFileName = fileCheck[0];
				filterFileExpandedName = fileCheck[1];
			}else{
				//如果参数为xxx,则认为扩展名不作为过滤条件
				filterFileName = fileCheck[0];
				filterFileExpandedName = "*";
			}
		}
		//解析文件名
		fileCheck = fileNameStr.split(".");
		if(fileCheck.length>1){
			//标准文件名 例如 xxx.xxx
			//首先进行文件名判断
			fileName = fileCheck[0];
			fileExpandedName = fileCheck[1];
			if(fileName.contains(fileName) || "*".equals(filterFileName)){
				//进行扩展名判断
				if(fileExpandedName.equals(filterFileExpandedName) || "*".equals(filterFileExpandedName)){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}
		return false;
	}
	/**
	 * 打印f路径下所有的文件和文件夹
	 * @param fileIns 文件对象
	 */
	public static void printAllFile(File fileIns) {
		// 打印当前文件名
		logger.debug(fileIns.getName());
		// 是否是文件夹
		if (fileIns.isDirectory()) {
			// 获得该文件夹下所有子文件和子文件夹
			File[] f1 = fileIns.listFiles();
			// 循环处理每个对象
			int len = f1.length;
			for (int i = 0; i < len; i++) {
				// 递归调用，处理每个文件对象
				printAllFile(f1[i]);
			}
		}
	}
	
	
	/**
	 * 删除对象f下的所有文件和文件夹
	 * @param fileIns 文件对象实例
	 */
	public static void deleteAll(File fileIns) {
		// 文件
		if (fileIns.isFile()) {
			fileIns.delete();
		} else { // 文件夹
			// 获得当前文件夹下的所有子文件和子文件夹
			File f1[] = fileIns.listFiles();
			// 循环处理每个对象
			int len = f1.length;
			for (int i = 0; i < len; i++) {
				// 递归调用，处理每个文件对象
				deleteAll(f1[i]);
			}
			// 删除当前文件夹
			fileIns.delete();
		}
	}
	
	
	/**
	 * 在指定路径下建立文件
	 * @param path 文件目录
	 * @param folderName 文件名
	 * @throws Exception 
	 */
	public static File createFile(String path,String fileName,boolean isNewFileFlag) throws Exception{
		File myFolder = new File(path);
		File newFile = new File(path,fileName);
		try {
			//目录存在
			if (myFolder.exists()) {
				 //如果不是文件夹，则证明是文件，建立该文件夹
				if(!myFolder.isDirectory()){
					myFolder.mkdir(); //win7下不适用
				}
				if(newFile.exists() && newFile.isFile()){
					if(isNewFileFlag){
						//如果文件存在，新建文件标志为真，则抛出异常
						throw new Exception("已存在目录，请删除后执行程序");
					}else{
						//如果文件存在，新建文件标志为假，则返回文件对象
						return newFile;
					}
				}else{
					//建立新文件
					newFile.createNewFile();
				}
			}else{
				//文件不存在，抛出异常
				throw new Exception("目录不存在");
			}
			return newFile;
		}    
		catch (Exception e) {    
			logger.error("新建目录操作出错:"+e.getMessage());    
			throw e;
		}     
	}
	
	
	/**
	 * 将文件中的内容封装为List实例返回，文件中每个回车符之前的文本信息作为List实例中的对象
	 * @param file 文件实例
	 * @return
	 * @throws Exception
	 */
	public static List<String> getFileContextByFileIns(File file) throws Exception{
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));   
		List<String> fielContextArray = new ArrayList<String>();
		String fileContextStr = br.readLine();    
		String enFlag = "\n";
		while (fileContextStr != null) {    
			fielContextArray.add(fileContextStr+enFlag);
			fileContextStr = br.readLine();
//			logger.debug("riskunitcode --- "+fileContextStr);
		}
		br.close();    
		return fielContextArray;
	}
	
	/**
	 * 读取jar包内的文件内容,在执行的目录以及文件名下生成新文件
	 * @param packageUrl 源文件的包路径 如:/com/utils/
	 * @param sourceFileName 源文件名称 如:test.sh
	 * @param targetPath 新文件的目标路径 如:/usr/temp/
	 * @param targetFileName 新文件名称 如rd.sh
	 * @return true-创建成功   false-操作失败
	 * @throws Exception
	 */
	public boolean createFileByJarUrl(String packageUrl,String sourceFileName,String targetPath,String targetFileName) throws Exception{
		InputStream in = this.getClass().getResourceAsStream(packageUrl+sourceFileName);
		File targetFile = null;
		if(in!=null){
			BufferedReader br=new BufferedReader(new InputStreamReader(in,"UTF-8"));
			List<String> fielContextArray = new ArrayList<String>();
			String fileContextStr = br.readLine();    
			String enFlag = "\n";
			while (fileContextStr != null) {    
				fielContextArray.add(fileContextStr+enFlag);
				fileContextStr = br.readLine();
			}
			br.close();
			in.close();
			//先删除目标文件
			deleteFile(targetPath+targetFileName);
			//建立新文件
			targetFile = new File(targetPath+targetFileName);
			writeContextToFile(targetFile,fielContextArray);
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 将文件中的内容封装为List实例返回，文件中每个回车符之前的文本信息作为List实例中的对象，可指定解析字符集
	 * @param file 文件实例
	 * @param encodeing 字符集
	 * @return
	 * @throws Exception
	 */
	public static List<String> getFileContextByFileIns(File file,String encodeing) throws Exception{
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file),encodeing));   
		List<String> fielContextArray = new ArrayList<String>();
		String fileContextStr = br.readLine();    
		String enFlag = "\n";
		while (fileContextStr != null) {    
			fielContextArray.add(fileContextStr+enFlag);
			fileContextStr = br.readLine();
		}
		br.close();    
		return fielContextArray;
	}
	
	
	/**
	 * 打印字符数据中的所有元素
	 * @param contextArray
	 */
	public static void prinListContext(List<String> contextArray){
		if(contextArray!=null && contextArray.size()>0){
			int size = contextArray.size();
			for(int index = 0;index<size;index++){
				logger.debug(contextArray.get(index));
			}
		}
	}
	
	/**
	 * 将List中的字符写入到指定的文件中，List实例中的每个对象占一行
	 * @param trageFile 目标文件
	 * @param contextArray 写入文件的List实例对象
	 * @throws Exception
	 */
	public static void writeContextToFile(File trageFile,List<String> contextArray) throws Exception{
		FileWriter fileWriter = new FileWriter(trageFile);
		logger.debug("进入FileUtils的方法");
		int size=0;
		if(contextArray!=null){
			size = contextArray.size();
		}
		for(int index = 0;index<size;index++){
			fileWriter.write(contextArray.get(index)+"\n");
		}
		fileWriter.close();
	}
	
	/**
	 * 将字符串写入到文件当中
	 * @param trageFile 目标文件
	 * @param contextArray 写入文件的List实例对象
	 * @throws Exception
	 */
	public static void writeContextToFileByString(File trageFile,String contextArray) throws Exception{
		logger.debug("开始"+DateUtil.getCurrnetTimestampIns());
		FileWriter fileWriter = new FileWriter(trageFile);
		fileWriter.write(contextArray.toString());
		fileWriter.close();
		logger.debug("结束"+DateUtil.getCurrnetTimestampIns());
	}
	
	/**
	 * 将一个文件的内容以追加方式加入另一个文件的尾部
	 * @param souceFile 源文件，为读取文件内容的文件，该文件中的数据被追加到另一个文件中
	 * @param targeFile 目标文件，为源文件读取内容后注入的宿主文件
	 * @throws Exception 
	 */
   public static void appendFileByFile(File souceFile,File targeFile) throws Exception{
	   FileWriter fileWriter = new FileWriter(targeFile,true);
	   List<String> targeFileContext = getFileContextByFileIns(souceFile);
	   int size = targeFileContext.size();
	   fileWriter.write("\n");
		for(int index = 0;index<size;index++){
			fileWriter.write(targeFileContext.get(index)+"\n");
		}
		fileWriter.close();
   }
   
   public static void initPath(String path) throws IOException{
	   //获取文件分隔符
	   File fileIns = null;
	   fileIns = new File(path);
	   if(!fileIns.isDirectory()){
		   fileIns.mkdirs();
	   }
	
   }
   
   /**
    * 启用缓存机制读取文件，适用于单个文件大于3M的情况
    * @param path 文件保存绝对全路径
    * @param fileName 文件名称
    */
   @SuppressWarnings("resource")
public static String  getFileContextToListByCache(String path,String fileName) throws Exception {
	   final int BUFFER_SIZE = 0x300000;// 缓冲区大小为3M
	   StringBuffer sbf = new StringBuffer();
	   File f = new File(path,fileName);
	   /**
	    * 
	    * map(FileChannel.MapMode mode,long position, long size)
	    * 
	    * mode - 根据是按只读、读取/写入或专用（写入时拷贝）来映射文件，分别为 FileChannel.MapMode 类中所定义的
	    * READ_ONLY、READ_WRITE 或 PRIVATE 之一
	    * 
	    * position - 文件中的位置，映射区域从此位置开始；必须为非负数
	    * 
	    * size - 要映射的区域大小；必须为非负数且不大于 Integer.MAX_VALUE
	    * 
	    * 所以若想读取文件后半部分内容，如例子所写；若想读取文本后1/8内容，需要这样写map(FileChannel.MapMode.READ_ONLY,
	    * f.length()*7/8,f.length()/8)
	    * 
	    * 想读取文件所有内容，需要这样写map(FileChannel.MapMode.READ_ONLY, 0,f.length())
	    * 
	    */

	   MappedByteBuffer inputBuffer = new RandomAccessFile(f, "r")
	     .getChannel().map(FileChannel.MapMode.READ_ONLY,
	       0, f.length());

	   byte[] dst = new byte[BUFFER_SIZE];// 每次读出3M的内容

	   long start = System.currentTimeMillis();

	   for (int offset = 0; offset < inputBuffer.capacity(); offset += BUFFER_SIZE) {
	    if (inputBuffer.capacity() - offset >= BUFFER_SIZE) {
	     for (int i = 0; i < BUFFER_SIZE; i++){
	    	 dst[i] = inputBuffer.get(offset + i);
	     }
	    } else {
	     for (int i = 0; i < inputBuffer.capacity() - offset; i++){
	    	 dst[i] = inputBuffer.get(offset + i);
	     }
	    }
	    int length = (inputBuffer.capacity() % BUFFER_SIZE == 0) ? BUFFER_SIZE
	      : inputBuffer.capacity() % BUFFER_SIZE;
	    sbf.append(new String(dst, 0, length));
	    logger.debug(new String(dst, 0, length));

	   }
	   long end = System.currentTimeMillis();
	   logger.debug("读取文件内容花费：" + (end - start) + "毫秒");
	   return sbf.toString();
	 }
   
   /**
    * 将字符内容使用回车符进行分割，封装为arrayList数据结构返回
    * @param context 需要以回车符分的字符串
    * @return ArrayList<String>实例，每个String对象最后使用回车符('\n')结尾
    */
   public static List<String> getListByString(String context){
	   List<String> fileContextList = null;
	   String[] lineArray = null;
	   String endFlag = "\n";
	   if(context!=null && !"".equals(context)){
		   lineArray = context.split(endFlag);
		   if(lineArray.length>0){
			   int size= lineArray.length;
			   fileContextList = new ArrayList<String>(size);
			   for(int i=0;i<size;i++){
				   fileContextList.add(lineArray[i]+endFlag);
			   }
		   }
	   }
	   return fileContextList;
   }
   
   public void copyFile(String sourceFilePath,String sourceFileName,String targetFilePath,String targerFileName)throws Exception{
	   FileInputStream fis = new FileInputStream(sourceFileName+sourceFileName);
       BufferedInputStream bufis = new BufferedInputStream(fis);

       FileOutputStream fos = new FileOutputStream(targetFilePath+targerFileName);
       BufferedOutputStream bufos = new BufferedOutputStream(fos);

       int len = 0;
       while ((len = bufis.read()) != -1) {
           bufos.write(len);
       }
       bufis.close();
       bufos.close();
   }
   
   /**
	 * 将List中的字符写入到指定的文件中，List实例中的每个对象占一行
	 * @param trageFile 目标文件
	 * @param contextArray 写入文件的List实例对象
	 * @throws Exception
	 */
	public static void writeContext(File trageFile,List<String> contextArray) throws Exception{
		FileWriter fileWriter = new FileWriter(trageFile);
		int size=0;
		if(contextArray!=null){
			size = contextArray.size();
		}
		for(int index = 0;index<size;index++){
			fileWriter.write(contextArray.get(index));
		}
		fileWriter.close();
	}
   
   /**
    * 获得当前运行目录的绝对路径
    * @return
    * @throws IOException
    */
   public String getRunTimeBasePath() throws IOException{
	   return this.getClass().getClassLoader().getResource("").getFile()+File.separator;
   }
    
   	 public static void writeFile(File path, byte[] content) throws IOException {  
  	   
         FileOutputStream fos = new FileOutputStream(path);  
         fos.write(content);  
         fos.close();  
     }  
   
}
