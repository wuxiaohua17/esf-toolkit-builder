package cn.com.ut.toolkit.builder.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.com.ut.core.common.util.io.FileUtil;
import cn.com.ut.toolkit.builder.MetaDataToPojo;
import cn.com.ut.toolkit.builder.pojo.ColumnType;
import cn.com.ut.toolkit.builder.pojo.FtlValue;
import cn.com.ut.toolkit.builder.util.FreemarkerUtil;
import freemarker.template.TemplateException;

@RestController
@RequestMapping(value = "/tmp")
public class TemplateController {

	@Autowired
	private MetaDataToPojo metaDataToPojo;
	@Autowired
	private FreemarkerUtil freemarkerUtil;

	@RequestMapping(value = "/gen")
	public void gen(@RequestParam(value = "pkg") String pkg,
			@RequestParam(value = "class") String className, @RequestParam(value = "db") String db,
			@RequestParam(value = "table") String table, HttpServletResponse resp)
			throws IOException, TemplateException {

		Map<String, ColumnType> columnTypeMap = metaDataToPojo.buildStaticFieldsByJdbc(pkg,
				className, table, db);

		FtlValue ftlValue = freemarkerUtil.buildModel(columnTypeMap, className, pkg);

		resp.setContentType("text/plain;charset=utf-8");
		OutputStream output = resp.getOutputStream();

		String[] fileNames = new String[] { className + ".java", className + ".json",
				className + "DAOImpl.java", className + "DAO.java", className + "ServiceImpl.java",
				className + "Service.java", className + "Controller.java" };

		for (String fileName : fileNames) {
			freemarkerUtil.genByFileName(ftlValue, output, fileName);
		}

		output.close();

	}

	@RequestMapping(value = "/down")
	public void down(@RequestParam(value = "pkg") String pkg,
			@RequestParam(value = "class") String className, @RequestParam(value = "db") String db,
			@RequestParam(value = "table") String table, HttpServletResponse resp)
			throws IOException, TemplateException {

		Map<String, ColumnType> columnTypeMap = metaDataToPojo.buildStaticFieldsByJdbc(pkg,
				className, table, db);

		FtlValue ftlValue = freemarkerUtil.buildModel(columnTypeMap, className, pkg);

		String zipName = String.valueOf(System.currentTimeMillis());
		resp.setContentType("application/x-msdownload");
		resp.setHeader("content-disposition",
				"attachment;filename=" + URLEncoder.encode(zipName + ".zip", "utf-8"));
		OutputStream output = resp.getOutputStream();

		String temdir = System.getProperty("java.io.tmpdir");

		buildTemdir(temdir, zipName, pkg, className);

		String zipPath = temdir + File.separator + "template" + File.separator + zipName
				+ File.separator;
		String pkgPath = convertPkgToPath(pkg);
		String[] fileNames = new String[] { "entities" + File.separator + className + ".java",
				className + ".json",
				"dao" + File.separator + "impl" + File.separator + className + "DAOImpl.java",
				"dao" + File.separator + className + "DAO.java",
				"service" + File.separator + "impl" + File.separator + className
						+ "ServiceImpl.java",
				"service" + File.separator + className + "Service.java",
				"controller" + File.separator + className + "Controller.java" };

		for (String fileName : fileNames) {
			FileOutputStream fosEntity = new FileOutputStream(zipPath + pkgPath + fileName);
			freemarkerUtil.genByFileName(ftlValue, fosEntity, fileName);
			fosEntity.close();
		}

		Map<String, byte[]> files = new HashMap<String, byte[]>();
		for (String fileName : fileNames) {
			FileInputStream fisEntity = new FileInputStream(zipPath + pkgPath + fileName);
			files.put(pkgPath + fileName, FileUtil.inputStreamReader(fisEntity, true));
		}

		zipOutput(output, files);

	}

	public String convertPkgToPath(String pkg) {

		String pkgPath = pkg.replaceAll("\\.", "\\" + File.separator) + File.separator;
		return pkgPath;
	}

	public void zipOutput(OutputStream output, Map<String, byte[]> files) throws IOException {

		ZipOutputStream zos = new ZipOutputStream(output);
		BufferedOutputStream bos = new BufferedOutputStream(zos);

		for (Entry<String, byte[]> entry : files.entrySet()) {
			String fileName = entry.getKey(); // 每个zip文件名
			byte[] file = entry.getValue(); // 这个zip文件的字节

			BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(file));
			zos.putNextEntry(new ZipEntry(fileName));

			int len = 0;
			byte[] buf = new byte[10 * 1024];
			while ((len = bis.read(buf, 0, buf.length)) != -1) {
				bos.write(buf, 0, len);
			}
			bis.close();
			bos.flush();
		}
		bos.close();
	}

	private void deleteFile(File file) {

		if (file.exists()) {// 判断文件是否存在
			if (file.isFile()) {// 判断是否是文件
				file.delete();// 删除文件
			} else if (file.isDirectory()) {// 否则如果它是一个目录
				File[] files = file.listFiles();// 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) {// 遍历目录下所有的文件
					this.deleteFile(files[i]);// 把每个文件用这个方法进行迭代
				}
				file.delete();// 删除文件夹
			}
		}
	}

	public void buildTemdir(String temdir, String zipName, String pkg, String className) {

		File file = new File(temdir + File.separator + "template");
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		} else {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				this.deleteFile(files[i]);
			}
		}

		String zipPath = temdir + File.separator + "template" + File.separator + zipName
				+ File.separator;
		String pkgPath = convertPkgToPath(pkg);
		String[] fileNames = new String[] { "entities", "dao" + File.separator + "impl", "dao",
				"service" + File.separator + "impl", "service", "controller" };

		for (String fileName : fileNames) {
			File f = new File(zipPath + pkgPath + fileName);

			if (!f.exists() && !f.isDirectory()) {
				f.mkdirs();
			}
		}
	}
}
