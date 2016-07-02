package cn.w.m.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import cn.w.i.IService;
import cn.w.m.IMessageCode;
import cn.w.m.image.s.ImageService;
import cn.w.utils.Common;
import cn.w.utils.Transfer;

@WebServlet(name = "images", urlPatterns = "/images/01")
@MultipartConfig
public class ImageController extends HttpServlet {
	private static final Logger LOG = Logger.getLogger(ImageController.class);
	private static final long serialVersionUID = 1L;
	private static final String FILE_PATH = "/uploadImages";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String fileid = request.getParameter("id");
		if (fileid != null && !fileid.isEmpty()) {
			ImageService imageService = new ImageService();
			String filepath = imageService.getImagePathById(Integer
					.parseInt(fileid));
			if (filepath != null && !filepath.isEmpty()) {
				String path = ImageController.class.getClassLoader()
						.getResource("/").getPath();
				path = path.substring(0, path.length() - 16);
				File file = new File(path + filepath);
				FileInputStream fis = new FileInputStream(file);
				byte[] buf = new byte[1024];
				ServletOutputStream os = response.getOutputStream();
				while (fis.read(buf) != -1) {
					os.write(buf);
				}
				os.flush();
				fis.close();
				os.close();
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		Date date = null;
		String fileName = null;
		try {
			Part ff = request.getPart("Filename");
			fileName = Transfer.inputStreamToString(ff.getInputStream());
			String[] names = fileName.split("\\.");
			String suffix = null; // 后缀
			if (names.length > 1) {
				suffix = names[1];
			}
			Part filePart = request.getPart("file");
			String path = ImageController.class.getClassLoader()
					.getResource("/").getPath();
			path = path.substring(0, path.length() - 16);
			File fileDir = new File(path + FILE_PATH);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			date = new Date();
			fileName = date.getTime() + "." + (suffix != null ? suffix : "");
			filePart.write(path + FILE_PATH + "/" + fileName);
		} catch (Exception e) {
			LOG.error(e, e);
			out.write(Common.errorResult(IMessageCode.UPLOAD_IMAGE_ERROR));
			out.flush();
			return;
		}
		ImageService imageService = new ImageService();
		String result = imageService.create(fileName, FILE_PATH + "/"
				+ fileName, date);
		if (IService.NUM_ERROR.equals(result)) {
			// 删除保存异常的文件
			File file = new File(ImageController.class.getClassLoader()
					.getResource("/").getPath()
					+ FILE_PATH + "/" + fileName);
			file.delete();
			out.write(Common.errorResult(IMessageCode.UPLOAD_IMAGE_ERROR));
			out.flush();
			return;
		} else {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(IService.CODE, IService.SUCCESS);
			jsonObject.put(IService.ID, result);
			jsonObject.put(IService.SRC, request.getRequestURL().toString()
					+ "?id=" + result);
			out.write(jsonObject.toString());
			out.flush();
			return;
		}
	}
}
