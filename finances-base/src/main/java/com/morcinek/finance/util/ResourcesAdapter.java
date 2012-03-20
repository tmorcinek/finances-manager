package com.morcinek.finance.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResourcesAdapter {

	private Map<String, String> paths = new HashMap<String, String>();

	private Map<String, Object> cache = new HashMap<String, Object>();

	@PostConstruct
	public void ini() {
		paths.clear();
		cache.clear();
	}

	@Autowired
	public void setRegisterResource(List<List<String>> pPaths) {
		for (List<String> list : pPaths) {
			paths.put(list.get(0), list.get(1));
		}
	}

	public Image getImage(String resourceName) {
		String realPath = getRealPath("png", resourceName);
		Image imageIcon = (Image) cache.get(realPath);
		if (imageIcon == null) {
			InputStream inputStream = getInputStream(realPath);
			try {
				BufferedImage read = ImageIO.read(inputStream);
				cache.put(realPath, read);
				return read;
			} catch (Exception e) {
				System.out.println("File " + realPath + " has not been found.");
			}
		}
		return null;
	}

	public Icon getIcon(String resourceName) {
		Image image = getImage(resourceName);
		if (image != null) {
			return new ImageIcon(image);
		}
		return null;
	}

	private String getRealPath(String ext, String resourceName) {
		String string = paths.get(ext);
		return ((string != null) ? string + "/" : "") + resourceName;
	}

	private InputStream getInputStream(String resourceName) {
		InputStream resource = getClass().getClassLoader().getResourceAsStream(resourceName);
		return resource;
	}

}
