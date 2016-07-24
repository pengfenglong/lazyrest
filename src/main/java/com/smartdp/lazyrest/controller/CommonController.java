package com.smartdp.lazyrest.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/rest/common")
public class CommonController {
	

	/**
	 *文件上传
	 * @return
	 */
	@RequestMapping(value="/upload",method=RequestMethod.POST)

	 public String upload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, ModelMap model) {

	        String path = request.getSession().getServletContext().getRealPath("upload");
	        String fileName = file.getOriginalFilename();
	        File targetFile = new File(path, fileName);
	        if(!targetFile.exists()){
	            targetFile.mkdirs();
	        }

	        //保存
	        try {
	            file.transferTo(targetFile);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        model.addAttribute("fileUrl", request.getContextPath()+"/upload/"+fileName);

	        return "result";
	    }


	

}
