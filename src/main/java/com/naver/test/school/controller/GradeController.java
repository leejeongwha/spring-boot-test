package com.naver.test.school.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.naver.test.school.model.Grade;
import com.naver.test.school.model.Role;
import com.naver.test.school.model.SchoolUser;
import com.naver.test.school.model.Subject;
import com.naver.test.school.repository.GradeRepository;
import com.naver.test.school.repository.SchoolUserRepository;
import com.naver.test.school.repository.SubjectRepository;

@Controller
@RequestMapping("school")
public class GradeController {
	private static final Logger logger = LoggerFactory.getLogger(GradeController.class);

	@Autowired
	private GradeRepository gradeRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private SchoolUserRepository schoolUserRepository;

	/**
	 * 성적 리스트(조회 포함)
	 * 
	 * @param model
	 * @param name
	 * @return
	 */
	@RequestMapping("grades")
	public String grades(Model model, @RequestParam(value = "name", defaultValue = "") String name) {
		if (StringUtils.isEmpty(name)) {
			model.addAttribute("gradeList", gradeRepository.findAll());
			model.addAttribute("gradeAvg", gradeRepository.getAllAverage());
		} else {
			List<Grade> gradeList = new ArrayList<Grade>();

			List<SchoolUser> userList = schoolUserRepository.findByNameAndRole(name, Role.STUDENT);

			for (SchoolUser user : userList) {
				gradeList.addAll(user.getGrades());
			}

			model.addAttribute("gradeList", gradeList);
			model.addAttribute("gradeAvg", gradeRepository.getUserAverage(name));
			model.addAttribute("searchParam", name);
		}

		return "school/grades";
	}

	/**
	 * 성적 단건 제거
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("grades/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable int id) {
		try {
			gradeRepository.delete(id);
		} catch (Exception e) {
			return "fail";
		}

		return "success";
	}

	/**
	 * 과목별 성적 일괄 반영
	 * 
	 * @param model
	 * @param subjectName
	 * @param userName
	 * @param score
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("grades/save")
	@Transactional(value = "jpaTransaction")
	public String grades(Model model, @RequestParam String[] subjectName, @RequestParam String[] userName,
			@RequestParam Integer[] score) throws Exception {

		// 해당 과목의 모든 성적 제거
		Subject subject = subjectRepository.findBySubjectName(subjectName[0]);
		Integer subjectId = subject.getId();
		gradeRepository.deleteBySubjectId(subjectId);

		for (int i = 0; i < subjectName.length; i++) {
			Grade grade = new Grade();
			grade.setSubjectId(subjectId);
			List<SchoolUser> findByName = schoolUserRepository.findByNameAndRole(userName[i], Role.STUDENT);
			if (findByName.size() == 0) {
				logger.error("Can not find student : {}", userName[i]);
				continue;
			}
			grade.setUserId(findByName.get(0).getId());
			grade.setScore(score[i]);

			gradeRepository.save(grade);
		}

		return "redirect:/school/subjects/" + subjectId + "/grades";
	}
}
