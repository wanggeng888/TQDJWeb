package cn.w.m.image.s;

import java.util.Date;
import java.util.Map;

import cn.w.bean.Images;
import cn.w.dao.ImageDao;

public class ImageService {

	private ImageDao imageDao;

	public ImageService() {
		imageDao = new ImageDao();
	}

	public String create(String name, String path, Date crtDate) {
		Images images = new Images();
		images.setName(name);
		images.setPath(path);
		images.setCrtdate(crtDate);
		return imageDao.create(images);
	}

	public String getImagePathById(int id) {
		Map<String, String> result = imageDao.searchById(id);
		if (result == null || result.isEmpty()) {
			return null;
		}
		return result.get("path");
	}

}
